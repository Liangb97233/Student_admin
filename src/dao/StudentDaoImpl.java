package dao;

import datasouce.BaseDAOImpl;
import studentAdmin.Score;
import studentAdmin.Student;

import java.util.List;

public class StudentDaoImpl extends BaseDAOImpl {
    public Student selectById(String uid){
        String sql = "SELECT * FROM s_user WHERE uid = ? OR username = ?";
        return getBean(Student.class, sql, uid,uid);
    }

    public int saveStudent(Student student){
        String sql = "INSERT INTO s_user(username,uid,ident,`password`) VALUES(?,?,?,?)";
        return update(sql,student.getUsername(),student.getUid(),student.getIdent(),student.getPassword());
    }

    public int saveScore(Score score){
        String sql = "INSERT INTO score (uid,username,chinese,math,english,count,year) VALUES(?,?,?,?,?,?,?)";
        return update(sql,score.getUid(),score.getUsername(),score.getChinese(),score.getMath(),score.getEnglish(),score.getCount(),score.getYear());
    }

    public List<Score> getAllScore(int year){
        String sql= "SELECT * FROM score WHERE year = ?";
        List<Score> list = getList(Score.class, sql,year);
        return list;
    }


    public Score getScore(String search,int year){
        String sql = "SELECT * FROM score WHERE uid = ? OR username LIKE ? and `year` = ?";
        return getBean(Score.class, sql, search,search ,year);
    }

    public int updateById(String uid, String newPassword){
        String sql = "update s_user set `password`=? where uid = ? ";
        int update = update(sql, uid, newPassword );
        return update;
    }

    public int updateScoreById(String key ,String newScore, String uid ,int year){
        String colum ="";

        if (key.equals("1") || key.equals("2") || key.equals("3") || key.equals("4")){

            switch (key) {
                case "1":
                    colum = "username";
                    break;
                case "2":
                    colum = "chinese";
                    break;
                case "3":
                    colum = "math";
                    break;
                case "4":
                    colum = "english";
                    break;
                default:
                    break;
            }



            String sql = "UPDATE score SET " +colum+"= ? WHERE uid = ? OR username = ? AND `year` = ?";
            int update = update(sql,newScore, uid, uid,year);
            if (key.equals("1")){
                String userSql = "UPDATE s_user SET " +colum+"= ? WHERE uid = ? OR username = ?";
                update(userSql,newScore, uid, uid);
            }
            return update;
        }

        return 0;

    }


    public void removeStuInfo(String search){
        String user_sql = "DELETE FROM score WHERE uid = ? OR username = ?";
        String score_sql = "DELETE FROM s_user WHERE uid = ? OR username = ?";
        update(user_sql,search ,search);
        update(score_sql,search ,search);
    }

    public List<Score> getWinnerList() {
        String sql ="SELECT * FROM score ORDER BY `count` DESC LIMIT 0,3;";
        List<Score> list = getList(Score.class, sql);
        return list;
    }
}
