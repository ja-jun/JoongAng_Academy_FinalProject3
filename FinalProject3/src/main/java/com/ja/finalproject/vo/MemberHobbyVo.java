package com.ja.finalproject.vo;

public class MemberHobbyVo {
	//필드
	private int memberhobby_no;
	private int member_no;
	private int hobby_category_no;
	//생성자
	public MemberHobbyVo() {
		super();
	}
	public MemberHobbyVo(int memberhobby_no, int member_no, int hobby_category_no) {
		super();
		this.memberhobby_no = memberhobby_no;
		this.member_no = member_no;
		this.hobby_category_no = hobby_category_no;
	}
	//메소드
	public int getMemberhobby_no() {
		return memberhobby_no;
	}
	public void setMemberhobby_no(int memberhobby_no) {
		this.memberhobby_no = memberhobby_no;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public int getHobby_category_no() {
		return hobby_category_no;
	}
	public void setHobby_category_no(int hobby_category_no) {
		this.hobby_category_no = hobby_category_no;
	}
	
	
	
}
