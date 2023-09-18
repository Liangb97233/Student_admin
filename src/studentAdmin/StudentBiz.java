package studentAdmin;

import dao.StudentDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentBiz {
    Scanner input = new Scanner(System.in);
    // ��Ա���ݴ洢������
    ArrayList<Student> stulist = new ArrayList<Student>();
    ArrayList<Score> admlist = new ArrayList<Score>();


    // �洢��ǰ��¼�û���uid
    private String uid;

    // �洢��ǰ��¼�û��Ķ����ַ
    String nowident = "";

    // ��ʼ����Ա
//	public void initList() {
//		stulist.add(new student("1", "С��", 100, 80, 80));
//		stulist.add(new student("2", "С��", 100, 100, 100));
//		stulist.add(new student("3", "С��", 80, 100, 80));
//		stulist.add(new student("4", "����", 40, 40, 40));
//		admlist.add(new adminer("root", "123456"));
//	}

    // ��½����
    public void menu() {
        System.out.println("======��ӭʹ��ѧ������ϵͳ======");
        System.out.println("      	    1.��½");
        System.out.println("      	    2.ע��(����Ա)");
        System.out.println("      	    3.�˳�");
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
                System.out.println("���棺���벻���ַ����������˳���");
                System.exit(0);
                break;
        }
    }

    // ��½ҳ��
    public void login() {
        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("======��ӭ��½ѧ������ϵͳ======");
        System.out.print("�û������û���Ϊid����");
        String localUid = input.next();
        System.out.print("���루����Ĭ��Ϊ123456����");
        String pw = input.next();

        //�����û�����ѯ��¼
        Student student = studentDao.selectById(localUid);
        if (student == null) {
            System.out.println("���޴��ˣ�����������\n");
            login();
        }

        // �ж����
        String identity = student.getIdent();// �洢�����Ϣ
        uid = student.getUid();//����ȫ��uid  ���¼
        // ��֤�����Ƿ���ͬ
        if (identity.equals("stu")) {
            if (student.getPassword().equals(pw)) {
                System.out.println("��½�ɹ�����ӭѧ����½��ϵͳ��");
                System.out.println("================================\n");
                // �洢��ǰ�û������±�����
//				nowuser = i;
                nowident = "stu";
                // ����ѧ������
                stumenu();
            } else {
                System.out.println("������󣬷������˵���");
                System.out.println("================================\n");
                // �������˵�
                menu();
            }
        } else if (identity.equals("adm")) {
            if (student.getPassword().equals(pw)) {
                System.out.println("��½�ɹ�����ӭ����Ա��½��ϵͳ��");
                System.out.println("================================\n");
                // �洢��ǰ�û������±�����
//				nowuser = i;
                nowident = "adm";
                // �������Ա����
                admmenu();
            } else {
                System.out.println("������󣬷������˵���");
                System.out.println("================================\n");
                // �������˵�
                menu();
            }
        } else {
            System.out.println("���޴��ˣ�����������\n");
            login();
        }
    }

    // ע��ҳ��
    public void register() {
        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("======��ӭע��ѧ������ϵͳ======");
        System.out.print("������");
        String username = input.next();
        System.out.print("ѧ�ţ�");
        String localUid = input.next();
        System.out.print("��ݣ�stu��adm����");
        String ident = input.next();
        System.out.print("���룺");
        String pw1 = input.next();
        System.out.print("������һ�����룺");
        String pw2 = input.next();

        // ��ѯѧ���Ƿ��ظ�
        Student student = studentDao.selectById(localUid);

        if (student != null) {
            System.out.println("�û����Ѵ��ڣ�������ע�ᡣ");
            System.out.println("================================\n");
            register();
        }
        // �ж�2�������Ƿ���ͬ
        if (pw1.equals(pw2)) {
            Student regisStudent = new Student();
            regisStudent.setUid(uid);
            regisStudent.setUsername(username);
            regisStudent.setPassword(pw1);
            regisStudent.setIdent(ident);

            int i = studentDao.saveStudent(regisStudent);
            if (i <= 0) {
                System.out.println("ע��ʧ�ܣ�������ע�ᡣ");
                System.out.println("================================\n");
                register();
            }

            System.out.println("ע��ɹ���ת��½���档");
            System.out.println("================================\n");
            login();
        } else {
            System.out.println("����������Ҫһ�£�������ע�ᡣ");
            System.out.println("================================\n");
            register();
        }

    }

    // ѧ������
    public void stumenu() {
        System.out.println("===========ѧ������=============");
        System.out.println("	1.��ѯ�����˳ɼ�");
        System.out.println("	2.��ѯָ��ѧ���ɼ�");
        System.out.println("	3.��ѯ��ѧ���������ܷ�ǰ������");
        System.out.println("	4.�޸�����");
        System.out.println("	5.�˳�");
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
                System.out.println("���棺���벻���ַ���ϵͳ����ѧ�����档");
                stumenu();
                break;
        }
    }

    // ��ʾ����ѧ���ɼ�
    public void showDegree() {
        int year = selectYear();
        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("=============ѧ���ɼ�===============");
        System.out.println("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�");

        //��ѯ����ѧ���ɼ�
        List<Score> allScore = studentDao.getAllScore(year);

        for (int i = 0; i < allScore.size(); i++) {
            System.out.println(
                    allScore.get(i).getUid() + "\t" + allScore.get(i).getUsername() + "\t" + allScore.get(i).getChinese()
                            + "\t" + allScore.get(i).getMath() + "\t" + allScore.get(i).getEnglish()+"\t" + allScore.get(i).getCount());
        }
        System.out.println("====================================\n");

    }

    // ��ʾָ��ѧ���ɼ�
    public void showTarget() {
        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("==========��ѯָ��ѧ���ɼ�============");
        System.out.print("����������ѧ�ţ�");
        String search = input.next();
        int year = selectYear();

        //����ѧ�Ų�ѯ�ɼ�
        Score score = studentDao.getScore(search,year);
        if (score==null){
            System.out.println("���޴��ˣ����ع���˵���");
            stumenu();
        }

        System.out.println("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�\tƽ����");
        System.out.println(
                score.getUid() + "\t" + score.getUsername() + "\t" + score.getChinese()
                        + "\t" + score.getMath() + "\t" + score.getEnglish() + "\t" + score.getCount() + "\t" + score.getAvg());

        System.out.println("====================================\n");
    }

    // �޸��������
    public void changePw() {
        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        System.out.println("=============�޸�����===============");
        System.out.print("��������룺");
        String oldpw = input.next();
        System.out.print("���������룺");
        String newpw = input.next();
        System.out.print("�ٴ����������룺");
        String newpw2 = input.next();


        //��ѯ����ǰ�û���
        Student student = studentDao.selectById(uid);

        // �ж���ʲô�û�
        if (student.getIdent().equals("stu")) {
            // ��������ͬ
            if (student.getPassword().equals(oldpw)) {
                // ��ͬ�Ļ������ж�������������ٴ����������Ƿ���ͬ
                if (newpw.equals(newpw2)) {

                    studentDao.updateById(uid, newpw);

                    System.out.println("�����޸ĳɹ�,�����µ�¼��");
                    System.out.println("====================================\n");
                    menu();
                } else {
                    System.out.println("�����������벻һ�£�����������");
                    System.out.println("====================================\n");
                    changePw();
                }
            } else {
                // �����벻ͬ
                System.out.println("������������󣬷������˵���");
                System.out.println("====================================\n");
                stumenu();
            }
        } else if (nowident.equals("adm")) {
            // ��������ͬ
            if (student.getPassword().equals(oldpw)) {
                // ��ͬ�Ļ������ж�������������ٴ����������Ƿ���ͬ
                if (newpw.equals(newpw2)) {

                    studentDao.updateById(uid, newpw);

                    System.out.println("�����޸ĳɹ�,�����µ�¼��");
                    System.out.println("====================================\n");
                    menu();
                } else {
                    System.out.println("�����������벻һ�£�����������");
                    System.out.println("====================================\n");
                    changePw();
                }
            } else {
                // �����벻ͬ
                System.out.println("������������󣬷������˵���");
                System.out.println("====================================\n");
                stumenu();
            }
        } else {
            System.out.println("������ݣ��޷�ʶ��");
            System.out.println("====================================\n");
            System.exit(0);
        }

    }

    // ����Ա����
    public void admmenu() {

        System.out.println("===========�������=============");
        System.out.println("	1.��ѯ�����˳ɼ�");
        System.out.println("	2.��ѯָ��ѧ���ɼ�");
        System.out.println("	3.¼��ѧ��");
        System.out.println("	4.�޸�ѧ����Ϣ");
        System.out.println("	5.ɾ��ѧ����Ϣ");
        System.out.println("	6.�޸�����");
        System.out.println("	7.�˳�");
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
                System.out.println("���棺���벻���ַ���ϵͳ���ع�����档");
                admmenu();
                break;
        }
    }

    // ¼��ѧ��
    public void entryStu() {
        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        String choose2 = "y";
        do {
            System.out.println("===========¼��ѧ��=============");
            System.out.print("ѧ�ţ�");
            String id = input.next();

            Student student = studentDao.selectById(id);
            // ѧ��ȥ��
            if (student != null) {
                System.out.println("ѧ���ظ���������¼�롣");
                System.out.println("================================");
                admmenu();
            }

            System.out.print("������");
            String name = input.next();
            System.out.print("���ģ�");
            double ch = input.nextDouble();
            System.out.print("��ѧ��");
            double ma = input.nextDouble();
            System.out.print("Ӣ�");
            double en = input.nextDouble();
            System.out.print("ѧ�꣺");
            int year = input.nextInt();

            Student newStudent = new Student(id, name, "123456", "stu");
            Score newScore = new Score(id, name, ch, ma, en, year);

            studentDao.saveStudent(newStudent);
            studentDao.saveScore(newScore);

            System.out.print("¼��ɹ�,�Ƿ����¼�룿(y/n)��");
            choose2 = input.next();
            System.out.println("================================");


        } while (choose2.equals("y"));

        admmenu();
    }

    // �޸�ѧ����Ϣ
    public void changScore() {

        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        String choose3 = "y";
        do {
            System.out.println("==========�޸�ָ��ѧ���ɼ�============");
            System.out.print("����������ѧ�ţ�");
            String search = input.next();
            int year = selectYear();
            System.out.println("���޸��1.���� 2.���� 3.��ѧ 4.Ӣ��");
            System.out.print("����Ҫ�޸ĵ��");
            String key = input.next();
            System.out.print("�޸�Ϊ��");
            String value = input.next();
            int n;
            Boolean nobody = true;

            Student student = studentDao.selectById(search);

            if (student != null) {
                nobody = false;

                studentDao.updateScoreById(key, value, search ,year);

                System.out.print("�޸ĳɹ����Ƿ�����޸�(y/n)��");
                choose3 = input.next();
//                admmenu();
                System.out.println("================================");
            }

            if (nobody) {
                System.out.println("���޴��ˣ����ع���˵���");
                admmenu();
            }
        } while (choose3.equals("y"));
        admmenu();


    }

    // ɾ��ѧ����Ϣ
    public void removeStu() {

        //��ʼ��Dao
        StudentDaoImpl studentDao = new StudentDaoImpl();

        String choose4 = "y";
        String choose5 = "y";
        do {
            System.out.println("==========ɾ��ָ��ѧ����Ϣ============");
            System.out.print("����������ѧ�ţ�");
            String search = input.next();

            System.out.print("�Ƿ�ȷ��ɾ��(y/n)��");
            choose5 = input.next();
            if (choose5.equals("n")) {
                admmenu();
                continue;
            }

            studentDao.removeStuInfo(search);

            System.out.print("ɾ���ɹ����Ƿ����ɾ��(y/n)��");
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

        System.out.println("=============������===============");
        System.out.println("ѧ��\t����\t����\t��ѧ\tӢ��\t�ܷ�");

        for (int i = 0; i < winnerList.size(); i++) {
            System.out.println(
                    winnerList.get(i).getUid() + "\t" + winnerList.get(i).getUsername() + "\t" + winnerList.get(i).getChinese()
                            + "\t" + winnerList.get(i).getMath() + "\t" + winnerList.get(i).getEnglish()+"\t" + winnerList.get(i).getCount());
        }

    }

    public int selectYear(){
        System.out.print("��ѯ�ڼ�ѧ�꣺");
        int year = input.nextInt();
        return year;
    }

}
