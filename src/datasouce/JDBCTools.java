package datasouce;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
这个类专门封装获取数据库连接对象的方法，和释放数据库连接对象的方法。
因为我们已经讲了数据库连接池的好处，所以，就直接用数据库连接池的方式来创建连接。
 */
public class JDBCTools {
    // 1、数据源,即连接池
    private static DataSource ds;

    //静态变量的初始化，可以使用静态代码块
    static{
        try {
            Properties pro = new Properties();
            pro.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //获取链接池对象
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void freeConnection(Connection conn) throws SQLException {
        if(conn != null){
            //因为有的用户会把数据库连接设置为手动提交模式  conn.setAutoCommit(false)
            //为了不影响其他用户使用，默认将 事务提交模式还原成自动提交模式
            //因为这里close是把连接放回池中，大家重复使用
            conn.setAutoCommit(true);//事务提交模式还原成自动提交模式
            conn.close();
        }
    }
}
