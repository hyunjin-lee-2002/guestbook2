package com.javaex.dao;

import com.javaex.vo.GuestbookVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestbookDAO {

    
    
    
    private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/guestbook_db";
	private String id = "guestbook";
	private String pw = "guestbook";

    // DB 연결 메서드
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패: " + e.getMessage());
        }
        return DriverManager.getConnection(url, id, pw);
    }

    // 자원 정리 메서드
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("자원 정리 오류: " + e.getMessage());
        }
    }

    // 전체 리스트 가져오기
    public List<GuestbookVO> getList() {
        List<GuestbookVO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            String sql = "SELECT no, name, password, content, reg_date FROM guestbook ORDER BY no DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                GuestbookVO vo = new GuestbookVO();
                vo.setNo(rs.getInt("no"));
                vo.setName(rs.getString("name"));
                vo.setPassword(rs.getString("password"));
                vo.setContent(rs.getString("content"));
                vo.setRegDate(rs.getString("reg_date"));

                list.add(vo);
            }

        } catch (SQLException e) {
            System.out.println("getList 오류: " + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }

        return list;
    }

    // 글 등록
    public int insert(GuestbookVO vo) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO guestbook (name, password, content, reg_date) VALUES (?, ?, ?, NOW())";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getName());
            pstmt.setString(2, vo.getPassword());
            pstmt.setString(3, vo.getContent());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("insert 오류: " + e.getMessage());
        } finally {
            close(conn, pstmt, null);
        }

        return count;
    }

    // 글 삭제 (비밀번호 일치 시만)
    public int delete(int no, String password) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            String sql = "DELETE FROM guestbook WHERE no = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            pstmt.setString(2, password);

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("delete 오류: " + e.getMessage());
        } finally {
            close(conn, pstmt, null);
        }

        return count;
    }
}
