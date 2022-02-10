package com.ja.finalproject.member.service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ja.finalproject.commons.MailSenderThread;
import com.ja.finalproject.commons.MessageDigestUtil;
import com.ja.finalproject.member.mapper.MemberSQLMapper;
import com.ja.finalproject.vo.HobbyCategoryVo;
import com.ja.finalproject.vo.MailAuthVo;
import com.ja.finalproject.vo.MemberHobbyVo;
import com.ja.finalproject.vo.MemberVo;

@Service
public class MemberService {

	@Autowired
	private MemberSQLMapper memberSQLMapper; 
	
	@Autowired
	private JavaMailSender javaMailSender; 
	
	public void joinMember(MemberVo vo , int [] hobby_category_no) {
		
		//파라미터 데이터를 가지고... 코어로직(비지니스 로직) 수행한다...!!!
		
		//멤버 키를 먼저 생성...//SELECT FP_MEMBER_seq.nextval FROM Dual
		int memberNo = memberSQLMapper.createMemberPk();
		
		//DB...insert..
		vo.setMember_no(memberNo);
		
		// 비밀번호 해싱...
		String password = vo.getMember_pw();
		password = MessageDigestUtil.getPasswordHashCode(password);
		vo.setMember_pw(password);
		
		memberSQLMapper.joinMember(vo);
		
		if(hobby_category_no != null) {			
			for(int hcNo : hobby_category_no) {				
				MemberHobbyVo hVo = new MemberHobbyVo();
				hVo.setMember_no(memberNo);
				hVo.setHobby_category_no(hcNo);
				memberSQLMapper.insertMemberHobby(hVo);				
			}						
		}
		
		// 메일 인증 관련...
		UUID uuid = UUID.randomUUID();
		String authKey = uuid.toString();
		
		MailAuthVo mailAuthVo = new MailAuthVo();
		mailAuthVo.setMailauth_key(authKey);
		mailAuthVo.setMember_no(memberNo);
		
		memberSQLMapper.insertMailAuth(mailAuthVo);
		
		//키를 메일로 보낸다...		
		String text = "";
		text += "회원가입을 축하드립니다. 아래 링크를 클릭하셔서 메일 인증 완료를 부탁드립니다.<br>";
		text += "<a href='http://localhost:8181/finalproject/member/mailAuthProcess?authKey=" + authKey + "'>메일 인증하기</a>";			
		
		MailSenderThread mst = new MailSenderThread(javaMailSender, vo.getMember_email(), text);
		mst.start(); //쓰레드 실행... 클래스의 run 메소드가 쓰레드로 실행된다....
	}
	
	public boolean isExistId(String id) {
		
		int count = memberSQLMapper.getCountById(id);
		
		if(count > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public MemberVo login(MemberVo vo) {
		
		// 비밀번호 해싱...
		String password = vo.getMember_pw();
		password = MessageDigestUtil.getPasswordHashCode(password);
		vo.setMember_pw(password);
		
		
		MemberVo result = memberSQLMapper.getMemberByIdAndPw(vo);
		
		return result;
	}
	
	
	public ArrayList<HobbyCategoryVo> getHobbyCategoryList(){
		return memberSQLMapper.getHobbyCategoryList();
	}
	
	public void authMail(String key) {
		
		memberSQLMapper.updateMailAuthComplete(key);
		
	}
	
}








