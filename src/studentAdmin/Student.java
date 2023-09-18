package studentAdmin;

public class Student {
	private String uid;//学号
	private String username;//姓名
	private String password;//密码
	private String ident;//身份


	public Student() {
	}

	public Student(String uid, String username, String password, String ident) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.ident = ident;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	@Override
	public String toString() {
		return "Student{" +
				"uid='" + uid + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", ident='" + ident + '\'' +
				'}';
	}
}
