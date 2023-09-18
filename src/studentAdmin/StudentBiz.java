package studentAdmin;

import dao.StudentDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentBiz {
    Scanner input = new Scanner(System.in);
    // 人员数据存储的数组
    ArrayList<Student> stulist = new ArrayList<Student>();
    ArrayList<Score> admlist = new ArrayList<Score>();


    // 存储当前登录用户的uid
    private String uid;

    // 存储当前登录用户的对象地址
    String nowident = "";

    // 初始化人员
//	public void initList() {
//		stulist.add(new student("1", "小明", 100, 80, 80));
//		stulist.add(new student("2", "小红", 100, 100, 100));
//		stulist.add(new student("3", "小刚", 80, 100, 80));
//		stulist.add(new student("4", "张三", 40, 40, 40));
//		admlist.add(new adminer("root", "123456"));
//	}

    // 登陆界面
    public void menu() {
        System.out.println("======欢迎使用学生管理系统======");
        System.out.println("      	    1.登陆");
        System.out.println("      	    2.注册(管理员)");
        System.out.println("      	    3.退出");
        System.out.println("================================");
        System.out.print("->");
        int key = input.nextInt();
        switch (key) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("警告：输入不明字符，程序已退出。");
                System.exit(0);
                break;
        }
    }

    // 登陆页面
    public void login() {
        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("======欢迎登陆学生管理系统======");
        System.out.print("用户名（用户名为id）：");
        String localUid = input.next();
        System.out.print("密码（密码默认为123456）：");
        String pw = input.next();

        //根据用户名查询登录
        Student student = studentDao.selectById(localUid);
        if (student == null) {
            System.out.println("查无此人，请重新输入\n");
            login();
        }

        // 判断身份
        String identity = student.getIdent();// 存储身份信息
        uid = student.getUid();//设置全局uid  表登录
        // 验证密码是否相同
        if (identity.equals("stu")) {
            if (student.getPassword().equals(pw)) {
                System.out.println("登陆成功，欢迎学生登陆本系统。");
                System.out.println("================================\n");
                // 存储当前用户数组下标和身份
//				nowuser = i;
                nowident = "stu";
                // 进入学生界面
                stumenu();
            } else {
                System.out.println("密码错误，返回主菜单。");
                System.out.println("================================\n");
                // 进入主菜单
                menu();
            }
        } else if (identity.equals("adm")) {
            if (student.getPassword().equals(pw)) {
                System.out.println("登陆成功，欢迎管理员登陆本系统。");
                System.out.println("================================\n");
                // 存储当前用户数组下标和身份
//				nowuser = i;
                nowident = "adm";
                // 进入管理员界面
                admmenu();
            } else {
                System.out.println("密码错误，返回主菜单。");
                System.out.println("================================\n");
                // 进入主菜单
                menu();
            }
        } else {
            System.out.println("查无此人，请重新输入\n");
            login();
        }
    }

    // 注册页面
    public void register() {
        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("======欢迎注册学生管理系统======");
        System.out.print("姓名：");
        String username = input.next();
        System.out.print("学号：");
        String localUid = input.next();
        System.out.print("身份（stu或adm）：");
        String ident = input.next();
        System.out.print("密码：");
        String pw1 = input.next();
        System.out.print("再输入一次密码：");
        String pw2 = input.next();

        // 查询学号是否重复
        Student student = studentDao.selectById(localUid);

        if (student != null) {
            System.out.println("用户名已存在，请重新注册。");
            System.out.println("================================\n");
            register();
        }
        // 判断2次密码是否相同
        if (pw1.equals(pw2)) {
            Student regisStudent = new Student();
            regisStudent.setUid(uid);
            regisStudent.setUsername(username);
            regisStudent.setPassword(pw1);
            regisStudent.setIdent(ident);

            int i = studentDao.saveStudent(regisStudent);
            if (i <= 0) {
                System.out.println("注册失败！请重新注册。");
                System.out.println("================================\n");
                register();
            }

            System.out.println("注册成功，转登陆界面。");
            System.out.println("================================\n");
            login();
        } else {
            System.out.println("两次密码需要一致！请重新注册。");
            System.out.println("================================\n");
            register();
        }

    }

    // 学生界面
    public void stumenu() {
        System.out.println("===========学生界面=============");
        System.out.println("	1.查询所有人成绩");
        System.out.println("	2.查询指定学生成绩");
        System.out.println("	3.查询奖学金名单（总分前三名）");
        System.out.println("	4.修改密码");
        System.out.println("	5.退出");
        System.out.println("================================");
        System.out.print("->");
        int key = input.nextInt();
        switch (key) {
            case 1:
                showDegree();
                stumenu();
                break;
            case 2:
                showTarget();
                stumenu();
                break;
            case 3:
                showWinnerList();
                stumenu();
                break;
            case 4:
                changePw();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("警告：输入不明字符，系统返回学生界面。");
                stumenu();
                break;
        }
    }

    // 显示所有学生成绩
    public void showDegree() {
        int year = selectYear();
        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("=============学生成绩===============");
        System.out.println("学号\t姓名\t语文\t数学\t英语\t总分");

        //查询所有学生成绩
        List<Score> allScore = studentDao.getAllScore(year);

        for (int i = 0; i < allScore.size(); i++) {
            System.out.println(
                    allScore.get(i).getUid() + "\t" + allScore.get(i).getUsername() + "\t" + allScore.get(i).getChinese()
                            + "\t" + allScore.get(i).getMath() + "\t" + allScore.get(i).getEnglish()+"\t" + allScore.get(i).getCount());
        }
        System.out.println("====================================\n");

    }

    // 显示指定学生成绩
    public void showTarget() {
        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("==========查询指定学生成绩============");
        System.out.print("输入姓名或学号：");
        String search = input.next();
        int year = selectYear();

        //根据学号查询成绩
        Score score = studentDao.getScore(search,year);
        if (score==null){
            System.out.println("查无此人，返回管理菜单。");
            stumenu();
        }

        System.out.println("学号\t姓名\t语文\t数学\t英语\t总分\t平均分");
        System.out.println(
                score.getUid() + "\t" + score.getUsername() + "\t" + score.getChinese()
                        + "\t" + score.getMath() + "\t" + score.getEnglish() + "\t" + score.getCount() + "\t" + score.getAvg());

        System.out.println("====================================\n");
    }

    // 修改密码界面
    public void changePw() {
        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("=============修改密码===============");
        System.out.print("输入旧密码：");
        String oldpw = input.next();
        System.out.print("输入新密码：");
        String newpw = input.next();
        System.out.print("再次输入新密码：");
        String newpw2 = input.next();


        //查询出当前用户的
        Student student = studentDao.selectById(uid);

        // 判断是什么用户
        if (student.getIdent().equals("stu")) {
            // 旧密码相同
            if (student.getPassword().equals(oldpw)) {
                // 相同的话继续判断新输入密码和再次输入密码是否相同
                if (newpw.equals(newpw2)) {

                    studentDao.updateById(uid, newpw);

                    System.out.println("密码修改成功,请重新登录。");
                    System.out.println("====================================\n");
                    menu();
                } else {
                    System.out.println("两次密码输入不一致，请重新输入");
                    System.out.println("====================================\n");
                    changePw();
                }
            } else {
                // 旧密码不同
                System.out.println("旧密码输入错误，返回主菜单。");
                System.out.println("====================================\n");
                stumenu();
            }
        } else if (nowident.equals("adm")) {
            // 旧密码相同
            if (student.getPassword().equals(oldpw)) {
                // 相同的话继续判断新输入密码和再次输入密码是否相同
                if (newpw.equals(newpw2)) {

                    studentDao.updateById(uid, newpw);

                    System.out.println("密码修改成功,请重新登录。");
                    System.out.println("====================================\n");
                    menu();
                } else {
                    System.out.println("两次密码输入不一致，请重新输入");
                    System.out.println("====================================\n");
                    changePw();
                }
            } else {
                // 旧密码不同
                System.out.println("旧密码输入错误，返回主菜单。");
                System.out.println("====================================\n");
                stumenu();
            }
        } else {
            System.out.println("不明身份，无法识别。");
            System.out.println("====================================\n");
            System.exit(0);
        }

    }

    // 管理员界面
    public void admmenu() {

        System.out.println("===========管理界面=============");
        System.out.println("	1.查询所有人成绩");
        System.out.println("	2.查询指定学生成绩");
        System.out.println("	3.录入学生");
        System.out.println("	4.修改学生信息");
        System.out.println("	5.删除学生信息");
        System.out.println("	6.修改密码");
        System.out.println("	7.退出");
        System.out.println("================================");
        System.out.print("->");
        int key = input.nextInt();

        switch (key) {
            case 1:
                showDegree();
                admmenu();
                break;
            case 2:
                showTarget();
                admmenu();
                break;
            case 3:
                entryStu();
                break;
            case 4:
                changScore();
                break;
            case 5:
                removeStu();
                break;
            case 6:
                changePw();
                break;
            case 7:
                System.exit(0);
                break;
            default:
                System.out.println("警告：输入不明字符，系统返回管理界面。");
                admmenu();
                break;
        }
    }

    // 录入学生
    public void entryStu() {
        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        String choose2 = "y";
        do {
            System.out.println("===========录入学生=============");
            System.out.print("学号：");
            String id = input.next();

            Student student = studentDao.selectById(id);
            // 学号去重
            if (student != null) {
                System.out.println("学号重复，请重新录入。");
                System.out.println("================================");
                admmenu();
            }

            System.out.print("姓名：");
            String name = input.next();
            System.out.print("语文：");
            double ch = input.nextDouble();
            System.out.print("数学：");
            double ma = input.nextDouble();
            System.out.print("英语：");
            double en = input.nextDouble();
            System.out.print("学年：");
            int year = input.nextInt();

            Student newStudent = new Student(id, name, "123456", "stu");
            Score newScore = new Score(id, name, ch, ma, en, year);

            studentDao.saveStudent(newStudent);
            studentDao.saveScore(newScore);

            System.out.print("录入成功,是否继续录入？(y/n)：");
            choose2 = input.next();
            System.out.println("================================");


        } while (choose2.equals("y"));

        admmenu();
    }

    // 修改学生信息
    public void changScore() {

        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        String choose3 = "y";
        do {
            System.out.println("==========修改指定学生成绩============");
            System.out.print("输入姓名或学号：");
            String search = input.next();
            int year = selectYear();
            System.out.println("可修改项：1.姓名 2.语文 3.数学 4.英语");
            System.out.print("输入要修改的项：");
            String key = input.next();
            System.out.print("修改为：");
            String value = input.next();
            int n;
            Boolean nobody = true;

            Student student = studentDao.selectById(search);

            if (student != null) {
                nobody = false;

                studentDao.updateScoreById(key, value, search ,year);

                System.out.print("修改成功，是否继续修改(y/n)：");
                choose3 = input.next();
//                admmenu();
                System.out.println("================================");
            }

            if (nobody) {
                System.out.println("查无此人，返回管理菜单。");
                admmenu();
            }
        } while (choose3.equals("y"));
        admmenu();


    }

    // 删除学生信息
    public void removeStu() {

        //初始化Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        String choose4 = "y";
        String choose5 = "y";
        do {
            System.out.println("==========删除指定学生信息============");
            System.out.print("输入姓名或学号：");
            String search = input.next();

            System.out.print("是否确认删除(y/n)？");
            choose5 = input.next();
            if (choose5.equals("n")) {
                admmenu();
                continue;
            }

            studentDao.removeStuInfo(search);

            System.out.print("删除成功，是否继续删除(y/n)：");
            choose4 = input.next();
            System.out.println("================================");


        } while (choose4.endsWith("y"));
        admmenu();
    }

    public static void main(String[] args) {
        StudentBiz biz = new StudentBiz();
        biz.menu();
    }



    public void showWinnerList(){
        StudentDaoImpl studentDao = new StudentDaoImpl();
        List<Score> winnerList = studentDao.getWinnerList();

        System.out.println("=============获奖名单===============");
        System.out.println("学号\t姓名\t语文\t数学\t英语\t总分");

        for (int i = 0; i < winnerList.size(); i++) {
            System.out.println(
                    winnerList.get(i).getUid() + "\t" + winnerList.get(i).getUsername() + "\t" + winnerList.get(i).getChinese()
                            + "\t" + winnerList.get(i).getMath() + "\t" + winnerList.get(i).getEnglish()+"\t" + winnerList.get(i).getCount());
        }

    }

    public int selectYear(){
        System.out.print("查询第几学年：");
        int year = input.nextInt();
        return year;
    }

}
