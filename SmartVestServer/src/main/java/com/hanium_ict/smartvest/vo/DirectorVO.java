package com.hanium_ict.smartvest.vo;

public class DirectorVO {
	
	private int director_id;
	private String loginname;
	private String password;
	private String salt;
	private int field_id;
	
	public DirectorVO() {
		
	}
	
	public DirectorVO(int director_id, String loginname, String password, int field_id, String salt) {
		setDirector_id(director_id);
		setLoginname(loginname);
		setPassword(password);
		setField_id(field_id);
		setSalt(salt);
	}
	
	public DirectorVO(String loginname, String password, String salt) {
		setLoginname(loginname);
		setPassword(password);
		setSalt(salt);
	}
	
	public DirectorVO(String loginname, String password, int field_id, String salt) {
		// TODO Auto-generated constructor stub
		setLoginname(loginname);
		setPassword(password);
		setField_id(field_id);
		setSalt(salt);
	}

	public int getDirector_id() {
		return director_id;
	}
	public void setDirector_id(int director_id) {
		this.director_id = director_id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getField_id() {
		return field_id;
	}
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String toString() {
		return "Director (director_id: " + director_id +", loginname: " + loginname +", password: "+password +", field_id: " + field_id;
	}
}
