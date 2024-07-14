package com.member.model;

import java.util.List;


public interface MemberDAO {
    int insert(Member member);
    int update(Member member);
    int deleteByID(Long memberID) ;
    Member getMemberByID(Long memberID);
    List<Member> getMemberList();
    Integer getMemberCount();
    boolean isAccountExist(String account);
    boolean isAccountExist(String account, Long memberId);
}