package com.ja.finalproject.test.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ja.finalproject.board.service.BoardService;
import com.ja.finalproject.member.service.MemberService;
import com.ja.finalproject.vo.MemberVo;

@Controller
@RequestMapping("/test/*")
public class TestController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("test1") // test/test1
	public void test1() {
		System.out.println("안녕하1");
		
		// test/test1.jsp forwading...
	}
	
	@RequestMapping("test2")
	public String test2() {
		System.out.println("안녕하2");
		
		return "test/test2"; //forwarding...
	}
	
	@RequestMapping("test3.do")
	public ModelAndView test3() {
		System.out.println("안녕하3");
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test/test2");
		
		return mv;
	}
	
	@ResponseBody // 더이상 포워딩 하지 않고....JSON으로 응답한다
	@RequestMapping("test4")
	public HashMap<String, Object> test4() {
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("a" , 10);
		data.put("b" , 20);
		data.put("v1" , 20);
		
		int [] arr = {1,2,3,4,5};
		
		data.put("ttt" , arr);
		
		return data;
	}
	
	@ResponseBody
	@RequestMapping("test5")
	public HashMap<String, Object> test5(){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		MemberVo vo1 = new MemberVo();
		vo1.setMember_id("s001");
		MemberVo vo2 = new MemberVo();
		vo2.setMember_id("s002");
				
		data.put("member1", vo1);
		data.put("member2", vo2);		
		
		return data;
		
	}
	
	@ResponseBody
	@RequestMapping("test6")
	public HashMap<String, Object> test6(){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("boardList", boardService.getBoardList(null, null, 1));
		
		return data;
	}
}