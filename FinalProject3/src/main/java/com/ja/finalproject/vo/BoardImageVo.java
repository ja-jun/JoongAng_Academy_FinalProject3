package com.ja.finalproject.vo;

public class BoardImageVo {
	private int image_no; 
	private int board_no; 
	private String image_url;
	private String image_original_filename;
	
	public BoardImageVo() {
		super();
	}

	public BoardImageVo(int image_no, int board_no, String image_url, String image_original_filename) {
		super();
		this.image_no = image_no;
		this.board_no = board_no;
		this.image_url = image_url;
		this.image_original_filename = image_original_filename;
	}

	public int getImage_no() {
		return image_no;
	}

	public void setImage_no(int image_no) {
		this.image_no = image_no;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_original_filename() {
		return image_original_filename;
	}

	public void setImage_original_filename(String image_original_filename) {
		this.image_original_filename = image_original_filename;
	}
		
}
