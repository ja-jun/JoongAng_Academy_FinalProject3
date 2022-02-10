package com.ja.finalproject.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ja.finalproject.board.mapper.BoardSQLMapper;
import com.ja.finalproject.member.mapper.MemberSQLMapper;
import com.ja.finalproject.vo.BoardImageVo;
import com.ja.finalproject.vo.BoardLikeVo;
import com.ja.finalproject.vo.BoardVo;
import com.ja.finalproject.vo.CommentVo;
import com.ja.finalproject.vo.MemberVo;

@Service
public class BoardService {

	@Autowired
	private BoardSQLMapper boardSQLMapper; 
	@Autowired
	private MemberSQLMapper memberSQLMapper;
	
	
	public void writeContent(BoardVo vo , ArrayList<BoardImageVo> boardImageVoList) {
		// 취미쪽과 똑같음
		int boardNo = boardSQLMapper.createBoardPk();
		
		vo.setBoard_no(boardNo);		
		boardSQLMapper.insertBoard(vo);
		
		for(BoardImageVo boardImageVo : boardImageVoList) {
			
			boardImageVo.setBoard_no(boardNo);
			boardSQLMapper.insertImage(boardImageVo);
		}
	}

	public ArrayList<HashMap<String, Object>> getBoardList(String searchOption , String searchWord , int pageNum) {
		
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>(); 
		
		ArrayList<BoardVo> boardVoList = boardSQLMapper.getBoardList(searchOption,searchWord,pageNum); //SELECT * FROM FP_Board ORDER BY board_no DESC
		
		for(BoardVo boardVo : boardVoList) {
			
			int memberNo = boardVo.getMember_no(); //작성자 번호...
			MemberVo memberVo = memberSQLMapper.getMemberByNo(memberNo); //SELECT * FROM FP_MEMBER WHERE member_no = #{no}
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("memberVo", memberVo);
			map.put("boardVo", boardVo);
			
			//현재 글이...지금시간 -3 시간 보다 클때...
			Date writeDate = boardVo.getBoard_writedate();
			long writeTime = writeDate.getTime();
			
			long currentTime = System.currentTimeMillis();
			long targetTime = currentTime - 1000*60*60*3;
			
			if(writeTime > targetTime) {
				map.put("newKeyword", true);
			}
			
			
			
			
			dataList.add(map);
		}
		
		return dataList;
		
	}
	
	public int getBoardCount(String searchOption , String searchWord) {
		
		return boardSQLMapper.getBoardCount(searchOption, searchWord);		
	}
	
	
	
	public HashMap<String,Object> getBoard(int board_no , boolean isEscape){
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		BoardVo boardVo = boardSQLMapper.getBoardByNo(board_no);
		
		//html escape...
		if(isEscape) {
			String content = boardVo.getBoard_content();
			content = StringEscapeUtils.escapeHtml4(content);
			content = content.replaceAll("\n", "<br>");	
			content = content.replaceAll(" ", "&nbsp;");
			boardVo.setBoard_content(content);
		}
		
		
		int memberNo = boardVo.getMember_no();
		MemberVo memberVo = memberSQLMapper.getMemberByNo(memberNo);
		
		ArrayList<BoardImageVo> boardImageVoList = 
				boardSQLMapper.getImageListByBordNo(board_no);
		
		map.put("memberVo", memberVo);
		map.put("boardVo", boardVo);
		map.put("boardImageVoList", boardImageVoList);
		
		return map;
	}
	
	
	public void increaseReadCount(int board_no) {
		boardSQLMapper.increaseReadCount(board_no);
	}
	
	public void deleteBoard(int board_no) {
		//예외.. 처리..
		
		boardSQLMapper.deleteBoard(board_no);
	}
	
	public void updateBoard(BoardVo vo) {
		boardSQLMapper.updateBoard(vo);
	}
	
	// 좋아요...
	public void dolike(BoardLikeVo vo) {
		
		int count = boardSQLMapper.getMyLikeCount(vo);
		
		if(count > 0) {
			boardSQLMapper.deleteLike(vo);
		} else {
			boardSQLMapper.insertLike(vo);
		}
		
	}
	
	public int getTotalLikecount(int boardNo) {
		
		return boardSQLMapper.getTotalLikeCount(boardNo);
	}
	
	public int getMyLikeCount(BoardLikeVo vo) {
		
		return boardSQLMapper.getMyLikeCount(vo);				
	}
	
	//리플...
	public void insertComment(CommentVo vo) {
		boardSQLMapper.insertComment(vo);		
	}
	
	public void deleteComment(int commentNo) {
		boardSQLMapper.deleteComment(commentNo);
	}
	
	public void updateComment(CommentVo vo) {
		boardSQLMapper.updateComment(vo);
	}
	
	public ArrayList<HashMap<String, Object>> getCommentList(int boardNo){
		
		ArrayList<HashMap<String, Object>> list = boardSQLMapper.getCommentListByBoardNo(boardNo);
		
		return list; 
	}
	
	
	
	
	
}