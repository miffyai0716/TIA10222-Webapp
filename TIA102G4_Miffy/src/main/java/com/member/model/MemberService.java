package com.member.model;

import java.sql.Timestamp;
import java.util.List;

public class MemberService {

    private MemberDAO memberDAO;

    public MemberService() {
        memberDAO = new MemberDAOImpl();
    }

    //��瘜�矽�DAO撅斤�瘜����嚗耦��閬���ㄐtia102g4鋆∠�ember.java���惇��
    public Member addMember(Timestamp regDate, String name, Byte gender, String account, String password, String mobileNo, String email, byte[] sticker) {
        Member member = new Member();
        member.setRegisDate(regDate);
        member.setName(name);
        member.setGender(gender);
        member.setAccount(account);
        member.setPassword(password);
        member.setMobileNo(mobileNo);
        member.setEmail(email);
        member.setSticker(sticker);
        memberDAO.insert(member);
        return member;
    }
    //��瘜�耨���鞈��
    public Member updateMember(Timestamp regDate, String name, Byte gender, String account, String password, String mobileNo, String email, byte[] sticker, Long memberId) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setRegisDate(regDate);
        member.setName(name);
        member.setGender(gender);
        member.setAccount(account);
        member.setPassword(password);
        member.setMobileNo(mobileNo);
        member.setEmail(email);
        member.setSticker(sticker);
        memberDAO.update(member);
        return member;
    }
    public void deleteMember(long memberId) {
        memberDAO.deleteByID(memberId);
    }
    public Member getOneMember(Long memberId) {
        return memberDAO.getMemberByID(memberId);
    }
    public List<Member> getAllMembers() {
        return memberDAO.getMemberList();
    }
    public int getTotalMembers() {
        return memberDAO.getMemberCount();
    }
    public boolean isAccountExist(String account) {
        return memberDAO.isAccountExist(account);
    }
    public boolean isAccountExist(String account, Long memberId) {
        return memberDAO.isAccountExist(account, memberId);
    }
}
