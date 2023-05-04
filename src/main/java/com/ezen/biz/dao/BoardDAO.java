package com.ezen.biz.dao;

import java.sql.*;
import java.util.*;

import org.springframework.stereotype.Repository;

import com.ezen.biz.common.JDBCUtil;
import com.ezen.biz.dto.BoardVO;

@Repository("boardDAO")
public class BoardDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private static final String BOARD_INSERT = 
			"INSERT INTO board(seq, title, writer, content) "
			+ "VALUES(board_seq.NEXTVAL, ?, ?, ?)";
	private static final String BOARD_UPDATE = 
			"update board set title=?,writer=?,content=? where seq=?";
	
	private static final String BOARD_DELETE = 
			"delete board where seq=?";
	private static final String GET_BOARD = 
			"select * from board where seq =?";
	private static final String GET_BOARD_LIST=
			"select * from board order by seq desc";

	// 게시글 insert
	public void insertBoard(BoardVO board) {
		System.out.println("===> JDBC로 insertBoard() 처리");
		try {

			// 1.데이터 베이스 연결
			conn = JDBCUtil.getConnection();
			// 2.sql 구문 준비
			pstmt = conn.prepareStatement(BOARD_INSERT);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			// 3.sql 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 4.데이터베이스 연결 해제
			JDBCUtil.close(conn, pstmt);
		}
	}

	// 게시글 수정
	public void updateBoard(BoardVO board) {
		System.out.println("===> JDBC로 updateBoard() 처리");

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement(BOARD_UPDATE);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getSeq());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
	}
	
	//게시글 삭제
	public void deleteBoard(BoardVO board) {
		System.out.println("===> JDBC로 deleteBoard() 처리");
		
		try {
			conn = JDBCUtil.getConnection();
			
			pstmt = conn.prepareStatement(BOARD_DELETE);
			pstmt.setInt(1, board.getSeq());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}	
	}
	
	//게시글 조회
	public BoardVO getBoard(BoardVO board) {
		System.out.println("===> JDBC로 getBoard() 처리");
		BoardVO result = null;
		try {
			conn = JDBCUtil.getConnection();
			
			pstmt = conn.prepareStatement(GET_BOARD);
			pstmt.setInt(1, board.getSeq());
			
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				result = new BoardVO();
				result.setSeq(rs.getInt("seq"));
				result.setTitle(rs.getString("title"));
				result.setWriter(rs.getString("writer"));
				result.setContent(rs.getString("content"));
				result.setRegDate(rs.getDate("regDate"));
				result.setCnt(rs.getInt("cnt"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	
	public List<BoardVO> getBoardList() {
		System.out.println("===> JDBC로 getBoardList() 처리");
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		try {
			conn = JDBCUtil.getConnection();
			
			pstmt = conn.prepareStatement(GET_BOARD_LIST);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO result = new BoardVO();
				
				result.setSeq(rs.getInt("seq"));
				result.setTitle(rs.getString("title"));
				result.setWriter(rs.getString("writer"));
				result.setContent(rs.getString("content"));
				result.setRegDate(rs.getDate("regDate"));
				result.setCnt(rs.getInt("cnt"));
				
				boardList.add(result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return boardList;
	}
}
