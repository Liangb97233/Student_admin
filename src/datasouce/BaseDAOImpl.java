package datasouce;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
这里抽取的是所有DAO接口的实现类的公共父类，声明为抽象的，目的是希望程序员不要直接创建BaseDAOImpl的对象，
而是创建它的子类对象来使用具体的功能。
 */
public abstract class BaseDAOImpl {

    /**
     * 通用的增删改的方法
     * @param sql String 要执行的sql
     * @param args Object... 如果sql中有？，就传入对应个数的？要设置值
     * @return int 执行的结果
     */
    protected int update(String sql,Object... args) {
        try {
            PreparedStatement pst = JDBCTools.getConnection().prepareStatement(sql);
            //这个sql可能带？
            //设置这些？
            for(int i=1; i<=args.length; i++){
                pst.setObject(i, args[i-1]);//虽然？的序号是从1开始，args[i-1]因为数组的下标是从0开始
            }
            int i = pst.executeUpdate();
            pst.close();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);//把编译时异常转为运行时异常
        }
    }

    /**
     * 查询单个对象的方法
     * @param clazz Class 记录对应的类类型
     * @param sql String 查询语句
     * @param args Object... 如果sql中有？，即根据条件查询，可以设置？的值
     * @param <T> 泛型方法声明的泛型类型
     * @return  T 一个对象
     */
    protected <T> T getBean(Class<T> clazz, String sql, Object... args){
		List<T> list = getList(clazz, sql, args);
		if(list != null && list.size()>0) {
            T t = getList(clazz, sql, args).get(0);
            return t;
		}
		return null;
	}

    /**
     * 通用查询多个对象的方法
     * @param clazz Class 记录对应的类类型
     * @param sql String 查询语句
     * @param args Object... 如果sql中有？，即根据条件查询，可以设置？的值
     * @param <T> 泛型方法声明的泛型类型
     * @return List<T> 把多个对象放到了List集合
     */
    protected <T> List<T> getList(Class<T> clazz, String sql, Object... args){
        ArrayList<T> list = new ArrayList<>();
        PreparedStatement pst ;
        try {
            pst = JDBCTools.getConnection().prepareStatement(sql);
            //这个sql可能带？
            //设置这些？
            for(int i=1; i<=args.length; i++){
                pst.setObject(i, args[i-1]);//虽然？的序号是从1开始，args[i-1]因为数组的下标是从0开始
            }

            ResultSet rs = pst.executeQuery();

            //ResultSet结果集中不仅仅有数据，还有表头（字段名）以及字段的数量（就是属性的数量）
            //表和Javabean是对应关系
            //t_department表和Department类是对应关系
            //t_employee表和Employee类是对应关系
            ResultSetMetaData metaData = rs.getMetaData();//元数据，就是表头那些信息

            while(rs.next()){//while循环循环一次，表示有一行记录，对应的是Java的一个对象
                //T t = T.class.newInstance();//未知类型T，是不能通过T.class获取它的Class对象
                T t = clazz.newInstance();//要求这个Javabean的类型必须有无参构造

                //无参构造创建的对象，所有属性都是默认值
                //需要从结果集中把记录中单元格的数据取出来给对象的属性赋值
//                Field field = clazz.getDeclaredField("属性名");
//                field.set(t, "属性值");
                int columnCount = metaData.getColumnCount();//列的数量，字段的数量，属性的数量
                for(int i=1; i<=columnCount; i++){//for循环循环一次，是获取一条记录的一个单元格
                    Object value = rs.getObject(i);//属性值

                   // String columnName = metaData.getColumnName(i);//获取第i个单元格的标题，字段名，属性名
                    //getColumnName是表结构中字段名
                    //而我们bean中的属性名和表中的字段名可能不对应，通过取别名的方式对应了
                    String columnName = metaData.getColumnLabel(i);//如果字段没有取别名，那么就用原名，有取别名就用别名
                    //即getColumnLabel真正获取 结果集的表头（显示的表头

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);//属性私有化，需要设置setAccessible(true)才能操作
                    field.set(t, value);
                }
                list.add(t);
            }

            pst.close();
        } catch (Exception e) {
            throw new RuntimeException(e);//把编译时异常转为运行时异常
        }

        return list;
    }

}
