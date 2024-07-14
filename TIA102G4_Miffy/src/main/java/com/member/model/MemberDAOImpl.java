package com.member.model;

import com.basedao.BaseDAO;
import jdbcutils.JDBCUtils;

import java.sql.Connection;
import java.util.List;

public class MemberDAOImpl extends BaseDAO<Member> implements MemberDAO {
    @Override
    public int insert(Member member) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO member (regis_date, name, gender, account, password) VALUES (?,?,?,?,?)";
            return update(conn, sql, member.getRegisDate(), member.getName(), member.getGender(), member.getAccount(), member.getPassword());
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return 0;
    }

    @Override
    public int update(Member member) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE member SET regis_date = ?, name = ?, gender = ?, account = ?, password = ?, mobile_no = ?, email = ?, sticker = ? WHERE member_id=?";
            return update(conn, sql, member.getRegisDate(), member.getName(), member.getGender(), member.getAccount(), member.getPassword(), member.getMobileNo(), member.getEmail(), member.getSticker(), member.getMemberId());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn);
        }
        return 0;
    }

    @Override
    public int deleteByID(Long memberId) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "DELETE FROM member WHERE member_id=?";
            return update(conn, sql, memberId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return 0;
    }

    @Override
    public Member getMemberByID(Long memberId) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM member WHERE member_id=?";
            return getInstance(conn, sql, memberId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return null;
    }

    @Override
    public List<Member> getMemberList() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from member";
            return getForList(conn, sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return null;
    }

    @Override
    public Integer getMemberCount() {
        return 0;
    }

    @Override
    public boolean isAccountExist(String account) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT COUNT(*) FROM member WHERE account = ?";
            Long count = getValue(conn, sql, account);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return false;
    }

    @Override
    public boolean isAccountExist(String account, Long memberId) {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT COUNT(*) FROM member WHERE account = ? AND member_id != ?";
            Long count = getValue(conn, sql, account, memberId);
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn);
        }
        return false;
    }

}
