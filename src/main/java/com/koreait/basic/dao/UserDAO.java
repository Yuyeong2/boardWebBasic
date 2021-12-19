package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.user.model.LoginResult;
import com.koreait.basic.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    public static int join(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_user (uid, upw, nm, gender) " +
                "VALUES (?, ?, ?, ?)";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getUid());
            ps.setString(2, entity.getUpw());
            ps.setString(3, entity.getNm());
            ps.setInt(4, entity.getGender());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static LoginResult login(UserEntity param) {
        int result = 0;
        UserEntity loginUser = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT iuser, upw, nm, gender, profileImg FROM t_user WHERE uid = ?";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, param.getUid());
            rs = ps.executeQuery();
            if(rs.next()){
                String dbPw = rs.getString("upw");
                if(BCrypt.checkpw(param.getUpw(), dbPw)) {
                    result = 1;
                    loginUser = new UserEntity();
                    loginUser.setIuser(rs.getInt("iuser"));
                    loginUser.setUid(param.getUid());
                    loginUser.setNm(rs.getString("nm"));
                    loginUser.setGender(rs.getInt("gender"));
                    loginUser.setProfileImg(rs.getString("profileImg"));
                } else {
                    result = 3;
                }
            } else {
                result = 2;
            }
        } catch (Exception e) {

        } finally {
            DbUtils.close(con, ps, rs);
        }
        return new LoginResult(result, loginUser);
    }

    public static int updUser(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " UPDATE t_user SET profileImg = ? WHERE iuser = ? ";
        String changeVal = entity.getProfileImg();

        if(entity.getUpw() != null && !"".equals(entity.getUpw())) {
            sql = sql.replace("profileImg", "upw");
            changeVal = entity.getUpw();
        }
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, changeVal);
            ps.setInt(2, entity.getIuser());
            return ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace();
        } finally { DbUtils.close(con, ps); }
        return 0;
    }
    public static UserEntity selUser (UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT iuser, uid, upw, nm, gender, rdt, profileImg FROM t_user WHERE ";

        if(entity.getIuser() > 0) {
            sql += "iuser = " + entity.getIuser();
        } else {
            sql += "uid = '" + entity.getUid() + "'";
        }
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()) {
                UserEntity vo = new UserEntity();
                vo.setIuser(rs.getInt("iuser"));
                vo.setUid(rs.getString("uid"));
                vo.setUpw(rs.getString("upw"));
                vo.setNm(rs.getString("nm"));
                vo.setGender(rs.getInt("gender"));
                vo.setRdt(rs.getString("rdt"));
                vo.setProfileImg(rs.getString("profileImg"));
                return vo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return null;
    }
}
