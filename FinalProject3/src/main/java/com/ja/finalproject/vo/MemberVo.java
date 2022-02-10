package com.ja.finalproject.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MemberVo {
	private int member_no;
	private String member_id;
	private String member_pw;
	private String member_nick;
	private String member_gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd") //왼쪽 패턴에 해당하는 파라미터 문자를 수용하게 한다.
	private Date member_birth; // 2021/01/01 O ... 2021-01-01 X
	private String member_phone;
	private String member_email;
	private Date member_joindate;
	public MemberVo() {
		super();
	}
	public MemberVo(int member_no, String member_id, String member_pw, String member_nick, String member_gender,
			Date member_birth, String member_phone, String member_email, Date member_joindate) {
		super();
		this.member_no = member_no;
		this.member_id = member_id;
		this.member_pw = member_pw;
		this.member_nick = member_nick;
		this.member_gender = member_gender;
		this.member_birth = member_birth;
		this.member_phone = member_phone;
		this.member_email = member_email;
		this.member_joindate = member_joindate;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public String getMember_nick() {
		return member_nick;
	}
	public void setMember_nick(String member_nick) {
		this.member_nick = member_nick;
	}
	public String getMember_gender() {
		return member_gender;
	}
	public void setMember_gender(String member_gender) {
		this.member_gender = member_gender;
	}
	public Date getMember_birth() {
		return member_birth;
	}
	public void setMember_birth(Date member_birth) {
		this.member_birth = member_birth;
	}
	public String getMember_phone() {
		return member_phone;
	}
	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public Date getMember_joindate() {
		return member_joindate;
	}
	public void setMember_joindate(Date member_joindate) {
		this.member_joindate = member_joindate;
	}

	
	
	
}
