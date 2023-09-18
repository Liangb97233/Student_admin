package datasouce;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
�����ר�ŷ�װ��ȡ���ݿ����Ӷ���ķ��������ͷ����ݿ����Ӷ���ķ�����
��Ϊ�����Ѿ��������ݿ����ӳصĺô������ԣ���ֱ�������ݿ����ӳصķ�ʽ���������ӡ�
 */
public class JDBCTools {
    // 1������Դ,�����ӳ�
    private static DataSource ds;

    //��̬�����ĳ�ʼ��������ʹ�þ�̬�����
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
    
    //��ȡ���ӳض���
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void freeConnection(Connection conn) throws SQLException {
        if(conn != null){
            //��Ϊ�е��û�������ݿ���������Ϊ�ֶ��ύģʽ  conn.setAutoCommit(false)
            //Ϊ�˲�Ӱ�������û�ʹ�ã�Ĭ�Ͻ� �����ύģʽ��ԭ���Զ��ύģʽ
            //��Ϊ����close�ǰ����ӷŻس��У�����ظ�ʹ��
            conn.setAutoCommit(true);//�����ύģʽ��ԭ���Զ��ύģʽ
            conn.close();
        }
    }
}
