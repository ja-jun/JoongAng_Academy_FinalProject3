package com.ja.finalproject.vo;

import java.util.Date;

public class BoardLikeVo {

	private int	like_no;
	private int	member_no;
	private int	board_no;
	private Date like_date;
	
	public BoardLikeVo() {
		super();
	}

	public BoardLikeVo(int like_no, int member_no, int board_no, Date like_date) {
		super();
		this.like_no = like_no;
		this.member_no = member_no;
		this.board_no = board_no;
		this.like_date = like_date;
	}

	public int getLike_no() {
		return like_no;
	}

	public void setLike_no(int like_no) {
		this.like_no = like_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public Date getLike_date() {
		return like_date;
	}

	public void setLike_date(Date like_date) {
		this.like_date = like_date;
	}	
}
