package com.ja.finalproject.vo;

public class HobbyCategoryVo {
	
	private int hobby_category_no;
	private String hobby_category_name;
	
	public HobbyCategoryVo() {
		super();
	}
	public HobbyCategoryVo(int hobby_category_no, String hobby_category_name) {
		super();
		this.hobby_category_no = hobby_category_no;
		this.hobby_category_name = hobby_category_name;
	}
	
	public int getHobby_category_no() {
		return hobby_category_no;
	}
	public void setHobby_category_no(int hobby_category_no) {
		this.hobby_category_no = hobby_category_no;
	}
	public String getHobby_category_name() {
		return hobby_category_name;
	}
	public void setHobby_category_name(String hobby_category_name) {
		this.hobby_category_name = hobby_category_name;
	}
	
	
}
