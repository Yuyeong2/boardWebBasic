package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.dao.BoardDAO;
import com.koreait.basic.user.model.UserEntity;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del")
public class BoardDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int writer = Utils.getLoginUserPk(req);
        int iboard = Utils.getParameterInt(req, "iboard");

        BoardEntity entity = new BoardEntity();
        entity.setIboard(iboard);
        entity.setWriter(writer);

        int result = BoardDAO.delBoard(entity);
        if(result == 1) {
            res.sendRedirect("/board/list");
            return;
        } else {
            req.setAttribute("err", "삭제 실패");
            req.getRequestDispatcher("/board/detail?iboard=" + iboard).forward(req, res);
            return;
        }
    }
}
