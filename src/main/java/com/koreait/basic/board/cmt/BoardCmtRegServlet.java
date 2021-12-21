package com.koreait.basic.board.cmt;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.dao.BoardCmtDAO;
import com.koreait.basic.dao.BoardDAO;

import javax.rmi.CORBA.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/reg")
public class BoardCmtRegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int loginUserPk = Utils.getLoginUserPk(req);

        String ctnt = req.getParameter("ctnt");
        int iboard = Utils.getParameterInt(req, "iboard");

        BoardCmtEntity entity = new BoardCmtEntity();
        entity.setIboard(iboard);
        entity.setCtnt(ctnt);
        entity.setWriter(loginUserPk);

        int result = BoardCmtDAO.insBoardCmt(entity);
        switch (result) {
            case 1:
                if(entity.getIboard() != 0 ) {
                    res.sendRedirect("/board/detail2?nohits=1&iboard=" + iboard);
                    return;
                }
                break;
            default:
                req.setAttribute("err", "수정/등록에 실패하였습니다.");
                req.setAttribute("data", entity);
                doGet(req, res);
                break;
        }
        res.sendRedirect("/board/list");
    }
}