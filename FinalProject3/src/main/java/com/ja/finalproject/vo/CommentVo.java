package com.ja.finalproject.vo;

import java.util.Date;

public class CommentVo {

	private int comment_no;
	private int board_no;
	private int member_no;
	private String comment_content;
	private Date comment_writedate;
	
	public CommentVo() {
		super();
	}

	public CommentVo(int comment_no, int board_no, int member_no, String comment_content, Date comment_writedate) {
		super();
		this.comment_no = comment_no;
		this.board_no = board_no;
		this.member_no = member_no;
		this.comment_content = comment_content;
		this.comment_writedate = comment_writedate;
	}

	public int getComment_no() {
		return comment_no;
	}

	public void setComment_no(int comment_no) {
		this.comment_no = comment_no;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public int getMember_no() {
		return member_no;
	}

	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Date getComment_writedate() {
		return comment_writedate;
	}

	public void setComment_writedate(Date comment_writedate) {
		this.comment_writedate = comment_writedate;
	}
	
}
