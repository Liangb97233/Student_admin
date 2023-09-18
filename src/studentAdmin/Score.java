package studentAdmin;

public class Score {
	private String uid;//学号
	private String username;//姓名
	private double chinese;//语文成绩
	private double math;//数学成绩
	private double english;//英语成绩
	private Integer year;

	private double count;

	public Score(String uid, String username, double chinese, double math, double english, Integer year) {
		this.uid = uid;
		this.username = username;
		this.chinese = chinese;
		this.math = math;
		this.english = english;
		this.year = year;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public double getCount() {
		count = math+chinese+english;
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public Score() {
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getChinese() {
		return chinese;
	}

	public void setChinese(double chinese) {
		this.chinese = chinese;
	}

	public double getMath() {
		return math;
	}

	public void setMath(double math) {
		this.math = math;
	}

	public double getEnglish() {
		return english;
	}

	public void setEnglish(double english) {
		this.english = english;
	}


	public double getAvg(){
		return (math+chinese+english)/3;
	}


}
