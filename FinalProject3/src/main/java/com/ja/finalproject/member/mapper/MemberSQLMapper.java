package com.ja.finalproject.member.mapper;

import java.util.ArrayList;

import com.ja.finalproject.vo.HobbyCategoryVo;
import com.ja.finalproject.vo.MailAuthVo;
import com.ja.finalproject.vo.MemberHobbyVo;
import com.ja.finalproject.vo.MemberVo;

public interface MemberSQLMapper {
	
	//return 타입 : insert,update,delete - void , select - Vo
	
	public int createMemberPk();
	
	public void joinMember(MemberVo vo); //insert...
	public MemberVo getMemberByIdAndPw(MemberVo abc); //select...
	
	public MemberVo getMemberByNo(int no);
	
	public int getCountById(String id);
	
	//취미 카테고리 관련
	public ArrayList<HobbyCategoryVo> getHobbyCategoryList();
	
	//회원 취미 관련
	public void insertMemberHobby(MemberHobbyVo vo);
	
	// 메일 인증 T
	public void insertMailAuth(MailAuthVo vo);
	public void updateMailAuthComplete(String authkey);
	
}
