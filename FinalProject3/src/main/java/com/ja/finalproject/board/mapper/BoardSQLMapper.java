package com.ja.finalproject.board.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.ja.finalproject.vo.BoardImageVo;
import com.ja.finalproject.vo.BoardLikeVo;
import com.ja.finalproject.vo.BoardVo;
import com.ja.finalproject.vo.CommentVo;

public interface BoardSQLMapper {
	
	// Pk 만들기
	public int createBoardPk();
	
	
	public void insertBoard(BoardVo vo);
	
	public ArrayList<BoardVo> getBoardList(
			@Param("searchOption") String searchOption,
			@Param("searchWord") String searchWord,
			@Param("pageNum") int pageNum
			); //1개의 행을 받을때는 단일 객체 리턴 , N개의 행을 받을때는 List로 받는다.
	
	public int getBoardCount(
			@Param("searchOption") String searchOption,
			@Param("searchWord") String searchWord
			);
	
	
	
	public BoardVo getBoardByNo(int no);
	
	public void increaseReadCount(int no);
	public void deleteBoard(int no);
	public void updateBoard(BoardVo vo);
	
	// 이미지T
	public void insertImage(BoardImageVo vo); 	
	public ArrayList<BoardImageVo> getImageListByBordNo(int boardNo);
	
	// 좋아요 T
	public void insertLike(BoardLikeVo vo);
	public void deleteLike(BoardLikeVo vo);
	public int getTotalLikeCount(int boardNo);
	public int getMyLikeCount(BoardLikeVo vo);
	
	// 리플 T
	public void insertComment(CommentVo vo);
	public ArrayList<HashMap<String, Object>> getCommentListByBoardNo(int boardNo);
	public void deleteComment(int commentNo);
	public void updateComment(CommentVo vo);
	
}
