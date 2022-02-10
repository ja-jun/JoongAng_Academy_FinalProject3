package com.ja.finalproject.board.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ja.finalproject.board.service.BoardService;
import com.ja.finalproject.vo.BoardLikeVo;
import com.ja.finalproject.vo.CommentVo;
import com.ja.finalproject.vo.MemberVo;

@RestController
@RequestMapping("/board/*")
public class RestBoardController {
	
	@Autowired
	private BoardService boardService;
		
	@RequestMapping("doLike")
	public HashMap<String, Object> doLike(BoardLikeVo param , HttpSession session){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		MemberVo sessionUser = (MemberVo)session.getAttribute("sessionUser");
		if(sessionUser == null) {
			data.put("result" , "error");
			data.put("reason" , "로그인이 필요합니다.");
			return data;			
		}
		
		int memberNo = sessionUser.getMember_no();
		param.setMember_no(memberNo);
		
		boardService.dolike(param);
		
		data.put("result", "success");
		
		return data;
	}
	
	@RequestMapping("getMyLikeStatus")
	public HashMap<String, Object> getMyLikeStatus(BoardLikeVo param , HttpSession session){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		MemberVo sessionUser = (MemberVo)session.getAttribute("sessionUser");
		if(sessionUser == null) {
			data.put("result" , "error");
			data.put("reason" , "로그인이 필요합니다.");
			return data;			
		}
		
		param.setMember_no(sessionUser.getMember_no());
		
		int myLikeCount = boardService.getMyLikeCount(param);
		
		data.put("result", "success");

		if(myLikeCount > 0) {
			data.put("status", "like");
		}else {
			data.put("status", "unlike");
		}
		
		return data;
	}
	
	@RequestMapping("getTotalLikeCount")
	public HashMap<String, Object> getTotalLikeCount(int board_no){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		int totalLikeCount = boardService.getTotalLikecount(board_no);
		data.put("totalLikeCount", totalLikeCount);
		
		return data;
	}
	
	//....
	@RequestMapping("writeComment")
	public HashMap<String, Object> writeComment(CommentVo param , HttpSession session){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		MemberVo memberVo = (MemberVo)session.getAttribute("sessionUser");
		int memberNo = memberVo.getMember_no();
		
		param.setMember_no(memberNo);
		
		boardService.insertComment(param);
		
		data.put("result", "success");
		
		return data;
	}
	
	@RequestMapping("deleteComment")
	public HashMap<String, Object> deleteComment(int comment_no){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		boardService.deleteComment(comment_no);
		
		data.put("result", "success");
		
		return data;		
	}
	
	@RequestMapping("updateComment")
	public HashMap<String, Object> updateComment(CommentVo param){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		boardService.updateComment(param);
		
		data.put("result", "success");
		
		return data;		
	}
	
	@RequestMapping("getCommentList")
	public HashMap<String, Object> getCommentList(int board_no){
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		ArrayList<HashMap<String, Object>> commentList = boardService.getCommentList(board_no);
		
		data.put("result", "success");
		data.put("commentList", commentList);
		
		return data;		
	}

}
