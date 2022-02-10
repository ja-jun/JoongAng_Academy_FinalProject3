package com.ja.finalproject.member.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ja.finalproject.member.service.MemberService;
import com.ja.finalproject.vo.MemberVo;

//restapi : jsp로 포워딩하지 않고 JSON으로 응답한다.
//기존 model에 담을 데이터를 map담고 응답하면 됨...(Model객체를 쓰면 안된다(포워딩을 위한 객체))

@RestController //@Controller + @ResponseBody
@RequestMapping("/member/*")
public class RestMemberController {

	@Autowired
	private MemberService memberService; 
	
	@RequestMapping("isExistId")
	public HashMap<String, Object> isExistId(String id){
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		boolean result = memberService.isExistId(id);
		
		System.out.println("야호 : " + result);
		
		data.put("result", result);
		
		return data;		
	}
	
	@RequestMapping("getSessionInfo")
	public HashMap<String, Object> getSessionInfo(HttpSession session){
		HashMap<String, Object>  data = new HashMap<String, Object>();
		
		MemberVo sessionUser = (MemberVo)session.getAttribute("sessionUser");
		
		if(sessionUser == null) {
			data.put("result", "empty");
		}else {
			data.put("result", "success");
			data.put("memberNo", sessionUser.getMember_no());
			data.put("memberNick", sessionUser.getMember_nick());
		}
		
		return data;
	}
}
