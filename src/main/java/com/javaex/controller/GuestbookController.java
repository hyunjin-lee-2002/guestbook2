package com.javaex.controller;

import com.javaex.dao.GuestbookDAO;
import com.javaex.vo.GuestbookVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("list".equals(action)) {
            // 리스트 출력
            List<GuestbookVO> list = new GuestbookDAO().getList();
            request.setAttribute("gList", list);

            RequestDispatcher rd = request.getRequestDispatcher("/addList.jsp");
            rd.forward(request, response);

        } else if ("deleteform".equals(action)) {
            // 삭제폼 요청
            String no = request.getParameter("no");

            // 예외 처리
            if (no == null || no.equals("")) {
                response.sendRedirect("gbc?action=list");
                return;
            }

            request.setAttribute("no", no);

            RequestDispatcher rd = request.getRequestDispatcher("/deleteForm.jsp");
            rd.forward(request, response);

        } else {
            // 기본값 -> 리스트
            response.sendRedirect("gbc?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // 등록 처리
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String content = request.getParameter("content");

            GuestbookVO vo = new GuestbookVO();
            vo.setName(name);
            vo.setPassword(password);
            vo.setContent(content);

            new GuestbookDAO().insert(vo);

            response.sendRedirect("gbc?action=list");

        } else if ("delete".equals(action)) {
            // 삭제 처리
            String noStr = request.getParameter("no");
            String password = request.getParameter("password");

            // 예외 처리
            if (noStr == null || noStr.equals("") || password == null || password.equals("")) {
                response.sendRedirect("gbc?action=list");
                return;
            }

            try {
                int no = Integer.parseInt(noStr);
                new GuestbookDAO().delete(no, password);
            } catch (NumberFormatException e) {
                System.out.println("delete 오류: 숫자 변환 실패 → " + noStr);
            }

            response.sendRedirect("gbc?action=list");
        }
    }
}
