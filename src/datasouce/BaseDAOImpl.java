package datasouce;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
�����ȡ��������DAO�ӿڵ�ʵ����Ĺ������࣬����Ϊ����ģ�Ŀ����ϣ������Ա��Ҫֱ�Ӵ���BaseDAOImpl�Ķ���
���Ǵ����������������ʹ�þ���Ĺ��ܡ�
 */
public abstract class BaseDAOImpl {

    /**
     * ͨ�õ���ɾ�ĵķ���
     * @param sql String Ҫִ�е�sql
     * @param args Object... ���sql���У����ʹ����Ӧ�����ģ�Ҫ����ֵ
     * @return int ִ�еĽ��
     */
    protected int update(String sql,Object... args) {
        try {
            PreparedStatement pst = JDBCTools.getConnection().prepareStatement(sql);
            //���sql���ܴ���
            //������Щ��
            for(int i=1; i<=args.length; i++){
                pst.setObject(i, args[i-1]);//��Ȼ��������Ǵ�1��ʼ��args[i-1]��Ϊ������±��Ǵ�0��ʼ
            }
            int i = pst.executeUpdate();
            pst.close();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);//�ѱ���ʱ�쳣תΪ����ʱ�쳣
        }
    }

    /**
     * ��ѯ��������ķ���
     * @param clazz Class ��¼��Ӧ��������
     * @param sql String ��ѯ���
     * @param args Object... ���sql���У���������������ѯ���������ã���ֵ
     * @param <T> ���ͷ��������ķ�������
     * @return  T һ������
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
     * ͨ�ò�ѯ�������ķ���
     * @param clazz Class ��¼��Ӧ��������
     * @param sql String ��ѯ���
     * @param args Object... ���sql���У���������������ѯ���������ã���ֵ
     * @param <T> ���ͷ��������ķ�������
     * @return List<T> �Ѷ������ŵ���List����
     */
    protected <T> List<T> getList(Class<T> clazz, String sql, Object... args){
        ArrayList<T> list = new ArrayList<>();
        PreparedStatement pst ;
        try {
            pst = JDBCTools.getConnection().prepareStatement(sql);
            //���sql���ܴ���
            //������Щ��
            for(int i=1; i<=args.length; i++){
                pst.setObject(i, args[i-1]);//��Ȼ��������Ǵ�1��ʼ��args[i-1]��Ϊ������±��Ǵ�0��ʼ
            }

            ResultSet rs = pst.executeQuery();

            //ResultSet������в����������ݣ����б�ͷ���ֶ������Լ��ֶε��������������Ե�������
            //���Javabean�Ƕ�Ӧ��ϵ
            //t_department���Department���Ƕ�Ӧ��ϵ
            //t_employee���Employee���Ƕ�Ӧ��ϵ
            ResultSetMetaData metaData = rs.getMetaData();//Ԫ���ݣ����Ǳ�ͷ��Щ��Ϣ

            while(rs.next()){//whileѭ��ѭ��һ�Σ���ʾ��һ�м�¼����Ӧ����Java��һ������
                //T t = T.class.newInstance();//δ֪����T���ǲ���ͨ��T.class��ȡ����Class����
                T t = clazz.newInstance();//Ҫ�����Javabean�����ͱ������޲ι���

                //�޲ι��촴���Ķ����������Զ���Ĭ��ֵ
                //��Ҫ�ӽ�����аѼ�¼�е�Ԫ�������ȡ��������������Ը�ֵ
//                Field field = clazz.getDeclaredField("������");
//                field.set(t, "����ֵ");
                int columnCount = metaData.getColumnCount();//�е��������ֶε����������Ե�����
                for(int i=1; i<=columnCount; i++){//forѭ��ѭ��һ�Σ��ǻ�ȡһ����¼��һ����Ԫ��
                    Object value = rs.getObject(i);//����ֵ

                   // String columnName = metaData.getColumnName(i);//��ȡ��i����Ԫ��ı��⣬�ֶ�����������
                    //getColumnName�Ǳ�ṹ���ֶ���
                    //������bean�е��������ͱ��е��ֶ������ܲ���Ӧ��ͨ��ȡ�����ķ�ʽ��Ӧ��
                    String columnName = metaData.getColumnLabel(i);//����ֶ�û��ȡ��������ô����ԭ������ȡ�������ñ���
                    //��getColumnLabel������ȡ ������ı�ͷ����ʾ�ı�ͷ

                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);//����˽�л�����Ҫ����setAccessible(true)���ܲ���
                    field.set(t, value);
                }
                list.add(t);
            }

            pst.close();
        } catch (Exception e) {
            throw new RuntimeException(e);//�ѱ���ʱ�쳣תΪ����ʱ�쳣
        }

        return list;
    }

}
