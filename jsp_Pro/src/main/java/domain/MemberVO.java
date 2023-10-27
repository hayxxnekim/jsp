package domain;

public class MemberVO {
	private String id;
	private String pwd;
	private String email;
	private int age;
	private String regdate;
	private String lastlogin;
	
	public MemberVO() {}

	//로그인
	public MemberVO(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}
	
	//회원가입
	public MemberVO(String id, String pwd, String email, int age) {
		this.id = id;
		this.pwd = pwd;
		this.email = email;
		this.age = age;
	}

	public MemberVO(String id, String pwd, String email, int age, String regdate, String lastlogin) {
		this.id = id;
		this.pwd = pwd;
		this.email = email;
		this.age = age;
		this.regdate = regdate;
		this.lastlogin = lastlogin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", email=" + email + ", age=" + age + ", regdate=" + regdate
				+ ", lastlogin=" + lastlogin + ", getId()=" + getId() + ", getPwd()=" + getPwd() + ", getEmail()="
				+ getEmail() + ", getAge()=" + getAge() + ", getRegdate()=" + getRegdate() + ", getLastlogin()="
				+ getLastlogin() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
