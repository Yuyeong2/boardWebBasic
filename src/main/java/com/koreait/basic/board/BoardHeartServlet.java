package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardHeartEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardHeartDAO;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/heart")
public class BoardHeartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String proc = req.getParameter("proc");
        int iboard = Utils.getParameterInt(req, "iboard");

        BoardHeartEntity param = new BoardHeartEntity();
        param.setIuser(Utils.getLoginUserPk(req));
        param.setIboard(iboard);

        switch (proc) {
            case "1":
                BoardHeartDAO.insBoardHeart(param);
                break;
            case "2":
                BoardHeartDAO.delBoardHeart(param);
                break;
        }
        res.sendRedirect("/board/detail?iboard=" + iboard);
    }
}
