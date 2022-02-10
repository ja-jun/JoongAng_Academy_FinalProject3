package com.ja.finalproject.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ja.finalproject.board.service.BoardService;
import com.ja.finalproject.vo.BoardImageVo;
import com.ja.finalproject.vo.BoardLikeVo;
import com.ja.finalproject.vo.BoardVo;
import com.ja.finalproject.vo.MemberVo;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Autowired
	private BoardService boardService; 
	
	
	@RequestMapping("mainPage")
	public String mainPage(
			Model model,
			String searchOption,
			String searchWord,
			@RequestParam(value = "pageNum" , defaultValue = "1") int pageNum) {
		
		ArrayList<HashMap<String, Object>> dataList = boardService.getBoardList(searchOption,searchWord,pageNum);

		int count = boardService.getBoardCount(searchOption, searchWord);
		
		int totalPageCount = (int)Math.ceil(count/10.0);
		
		// 1 2 3 4 5 , 6 7 8 9 10
		int startPage = ((pageNum-1)/5)*5 + 1;
		int endPage = ((pageNum-1)/5 + 1)*(5);
		if(endPage > totalPageCount){
			endPage = totalPageCount;
		}
		
		//페이징 링크 검색 추가 옵션...
		String additionalParam = "";
		
		if(searchOption != null) {
			additionalParam += "&searchOption=" + searchOption;
		}
		
		if(searchWord != null) {
			//URL encoding -> 영어 숫자 특수 문자 아닌 값이 존재 할때...
			try {
				searchWord = URLEncoder.encode(searchWord, "utf-8");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			additionalParam += "&searchWord=" + searchWord;
		}
		
		
		
		model.addAttribute("additionalParam", additionalParam);
		model.addAttribute("count", count);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("dataList", dataList);
		
		return "board/mainPage";
	}
	
	
	@RequestMapping("writeContentPage")
	public String writeContentPage() {
		return "board/writeContentPage";
	}
	
	@RequestMapping("writeContentProcess")
	public String writeContentProcess(BoardVo param , MultipartFile [] uploadFiles , HttpSession session) {
	
		ArrayList<BoardImageVo> boardImageVoList = new ArrayList<BoardImageVo>();
		
		
		String uploadFolder = "C:/uploadFolder/";				
		
		//파일 업로드...
		if(uploadFiles != null) {
			
			for(MultipartFile uploadFile : uploadFiles) {
				
				if(uploadFile.isEmpty()) {
					continue;
				}

				// 날짜별 폴더 생성... 2022/01/19/
				Date today = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
				String folderPath = sdf.format(today);
				
				File todayFolder = new File(uploadFolder + folderPath);
				if(!todayFolder.exists()) {
					todayFolder.mkdirs();
				}
				
				
				//중복되지 않게 저장해야된다...!!...
				//방법 : 랜덤 + 시간
				String fileName = "";
				UUID uuid = UUID.randomUUID();
				fileName += uuid.toString();
				
				long currentTime = System.currentTimeMillis();
				fileName += "_" + currentTime;
				
				// 확장자 추가...
				String originalFileName = uploadFile.getOriginalFilename();
				String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
				fileName += ext;
				
				try {
					uploadFile.transferTo(new File(uploadFolder + folderPath + fileName));
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				// 서비스에 보내기 위한 데이터...구성...
				BoardImageVo boardImageVo = new BoardImageVo();
				boardImageVo.setImage_url(folderPath + fileName);
				boardImageVo.setImage_original_filename(originalFileName);
				boardImageVoList.add(boardImageVo);
				
			}
			
		}
		
		//파라미터로 2개값 + 세션(행위자)에서 회원 번호 뽑아서 param메모리에 세팅해서 총 3개값 세팅...
		
		MemberVo sessionUser = (MemberVo)session.getAttribute("sessionUser"); //(중요)
		int memberNo = sessionUser.getMember_no();
		param.setMember_no(memberNo);
		
		//service(class) -> mapper(xml,interface) -> insert
		boardService.writeContent(param , boardImageVoList);
		
		return "redirect:./mainPage";
	}
	
	@RequestMapping("readContentPage")
	public String readContentPage(int board_no ,Model model , HttpSession session) {
		
		boardService.increaseReadCount(board_no);
		
		HashMap<String, Object> map = boardService.getBoard(board_no , true);
		model.addAttribute("data" , map);
		
		int totalLikeCount = boardService.getTotalLikecount(board_no);
		model.addAttribute("totalLikeCount", totalLikeCount);
		
		MemberVo sessionUser = (MemberVo)session.getAttribute("sessionUser");
		if(sessionUser != null) {
			// 로그인을 했을 때...
			int memberNo = sessionUser.getMember_no();			
			BoardLikeVo boardLikeVo = new BoardLikeVo();
			boardLikeVo.setMember_no(memberNo);
			boardLikeVo.setBoard_no(board_no);
			
			int myLikeCount = boardService.getMyLikeCount(boardLikeVo);
			model.addAttribute("myLikeCount", myLikeCount);			
		}
		
		return "board/readContentPage";
	}
	
	@RequestMapping("deleteContentProcess")
	public String deleteContentProcess(int board_no) {
		
		boardService.deleteBoard(board_no);
		
		return "redirect:./mainPage";
	}
	
	
	@RequestMapping("updateContentPage")
	public String updateContentPage(int board_no , Model model) {
		
		HashMap<String, Object> map = boardService.getBoard(board_no , false);
		model.addAttribute("data" , map);
		
		
		return "board/updateContentPage";
	}
	
	@RequestMapping("updateContentProcess")
	public String updateContentProcess(BoardVo param) {
		
		boardService.updateBoard(param);
		
		return "redirect:./readContentPage?board_no=" + param.getBoard_no();
	}
	
	@RequestMapping("likeProcess")
	public String likeProcess(BoardLikeVo param , HttpSession session) {
		
		// 행위자 정보는 세션에서 꼭 뽑아오자...
		MemberVo sessionUser = (MemberVo)session.getAttribute("sessionUser");
		int memberNo = sessionUser.getMember_no();
		param.setMember_no(memberNo);
		
		boardService.dolike(param);
		
		return "redirect:./readContentPage?board_no=" + param.getBoard_no();
	}		
}
