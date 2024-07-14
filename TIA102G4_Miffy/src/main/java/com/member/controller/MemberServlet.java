package com.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.member.model.Member;
import com.member.model.MemberService;

@WebServlet("/member/member.do")
public class MemberServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("getOne_For_Display".equals(action)) {
            MemberService memberService = new MemberService();
            Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
            req.setAttribute("errorMsgs", errorMsgs);

            String memberIdStr = req.getParameter("memberId");
            if (memberIdStr == null || (memberIdStr.trim()).length() == 0) {
                errorMsgs.put("memberId", "�п�J�|��ID");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/member/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            Long memberId = null;
            try {
                memberId = Long.valueOf(memberIdStr);
            } catch (Exception e) {
                errorMsgs.put("memberId", "�|��ID�榡�����T");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/member/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            MemberService memberSvc = new MemberService();
            Member member = memberSvc.getOneMember(memberId);
            if (member == null) {
                errorMsgs.put("memberId", "�|�����s�b");
            }
            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req
                        .getRequestDispatcher("/member/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            req.setAttribute("member", member);
            String url = "/member/listOneMem.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("getOne_For_Update".equals(action)) {

            Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Long memberId = Long.valueOf(req.getParameter("memberId"));

            MemberService memberSvc = new MemberService();
            Member member = memberSvc.getOneMember(memberId);

            req.setAttribute("member", member);
            String url = "/member/update_mem_input.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("update".equals(action)) {

            Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
            req.setAttribute("errorMsgs", errorMsgs);

            MemberService memberSvc = new MemberService();
            Member member = new Member();

            Long memberId = Long.valueOf(req.getParameter("memberId"));
            String regDateStr = req.getParameter("regDate");
            Timestamp regDate = null;
            String regDateReg = "^(\\d{4})-(\\d{2})-(\\d{2})";
            if (!Pattern.matches(regDateReg, regDateStr)) {
                errorMsgs.put("regDate", "���U����榡�����T�A���� YYYY-MM-DD");
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    java.util.Date parsedDate = sdf.parse(regDateStr);
                    regDate = new Timestamp(parsedDate.getTime());
                    member.setRegisDate(regDate);
                } catch (ParseException e) {
                    errorMsgs.put("regDate", "���U����榡�����T");
                }
            }

            String name = req.getParameter("name");
            String nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
            if (name == null || name.trim().length() == 0) {
                errorMsgs.put("name", "�|���W�ٽФŪť�");
            } else if (!name.trim().matches(nameReg)) {
                errorMsgs.put("name", "�|���W�ٮ榡�����T�A�ݬ� 2-10 �Ӧr��");
            }
            member.setName(name);

            String genderStr = req.getParameter("gender");
            String genderReg = "^[�k�k]$";
            if (genderStr != null && genderStr.trim().length() != 0 && genderStr.trim().matches(genderReg)) {
                if ("�k".equals(genderStr)) {
                    member.setGender((byte) 1);
                } else {
                    member.setGender((byte) 0);
                }
            } else {
                errorMsgs.put("gender", "�ʧO�榡�����T");
            }

            String account = req.getParameter("account");
            String accountReg = "^[a-zA-Z0-9_]{10,20}$";
            if (account == null || account.trim().length() == 0) {
                errorMsgs.put("account", "�b���ФŪť�");
            } else if (!account.trim().matches(accountReg)) {
                errorMsgs.put("account", "�b���榡�����T�A�ݬ� 10-20 �Ӧr��");
            }
            if (!memberSvc.isAccountExist(account, memberId)) {
                errorMsgs.put("account", "�b���w�s�b");
            } else {
                member.setAccount(account);
            }

            String password = req.getParameter("password");
            String passwordReg = "^[a-zA-Z0-9_]{10,20}$";
            if (password == null || password.trim().length() == 0) {
                errorMsgs.put("password", "�K�X�ФŪť�");
            } else if (!password.trim().matches(passwordReg)) {
                errorMsgs.put("password", "�K�X�榡�����T�A�ݬ� 10-20 �Ӧr��");
            }
            member.setPassword(password);

            String mobileNo = req.getParameter("mobileNo");
            String mobileNoReg = "^\\d{10}$";
            if (mobileNo == null || mobileNo.trim().length() == 0) {
                errorMsgs.put("mobileNo", "������X�ФŪť�");
            } else if (!mobileNo.trim().matches(mobileNoReg)) {
                errorMsgs.put("mobileNo", "������X�榡�����T�A�ݬ� 10 ��Ʀr");
            }
            member.setMobileNo(mobileNo);

            String email = req.getParameter("email");
            String emailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (email == null || email.trim().length() == 0) {
                errorMsgs.put("email", "�q�l�l��ФŪť�");
            } else if (!email.trim().matches(emailReg)) {
                errorMsgs.put("email", "�q�l�l��榡�����T");
            }
            member.setEmail(email);

            Part stickerPart = req.getPart("sticker");
            byte[] sticker = null;
            if (stickerPart != null && stickerPart.getSize() > 0) {
                try {
                    InputStream personalPhotosInputStream = stickerPart.getInputStream();
                    sticker = personalPhotosInputStream.readAllBytes();
                    int maxSize = 10 * 1024 * 1024; // 10 MB
                    if (sticker.length > maxSize) {
                        errorMsgs.put("sticker", "�Y���j�p�W�L 10 MB");
                    }
                    member.setSticker(sticker);
                } catch (Exception e) {
                    errorMsgs.put("sticker", "�Y���W�ǥ���");
                }
            }

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("member", member);
                RequestDispatcher failureView = req.getRequestDispatcher("/member/update_mem_input.jsp");
                failureView.forward(req, res);
                return;
            }

            member = memberSvc.updateMember(
                    member.getRegisDate(),
                    member.getName(),
                    member.getGender(),
                    member.getAccount(),
                    member.getPassword(),
                    member.getMobileNo(),
                    member.getEmail(),
                    member.getSticker(),
                    memberId
            );

            req.setAttribute("member", member);
            String url = "/member/listOneMem.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("insert".equals(action)) {

            Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
            req.setAttribute("errorMsgs", errorMsgs);

            MemberService memberSvc = new MemberService();
            Member member = new Member();

            String regDateStr = req.getParameter("regDate");
            Timestamp regDate = null;
            String regDateReg = "^(\\d{4})-(\\d{2})-(\\d{2})";
            if (!Pattern.matches(regDateReg, regDateStr)) {
                errorMsgs.put("regDate", "���U����榡�����T�A���� YYYY-MM-DD");
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    java.util.Date parsedDate = sdf.parse(regDateStr);
                    regDate = new Timestamp(parsedDate.getTime());
                    member.setRegisDate(regDate);
                } catch (ParseException e) {
                    errorMsgs.put("regDate", "���U����榡�����T");
                }
            }

            String name = req.getParameter("name");
            String nameReg = "^[(\\u4e00-\\u9fa5)(a-zA-Z0-9_)]{2,10}$";
            if (name == null || name.trim().length() == 0) {
                errorMsgs.put("name", "�|���W�ٽФŪť�");
            } else if (!name.trim().matches(nameReg)) {
                errorMsgs.put("name", "�|���W�ٮ榡�����T�A�ݬ� 2-10 �Ӧr��");
            }
            member.setName(name);

            String genderStr = req.getParameter("gender");
            String genderReg = "^[�k�k]$";
            if (genderStr != null && genderStr.trim().length() != 0 && genderStr.trim().matches(genderReg)) {
                if ("�k".equals(genderStr)) {
                    member.setGender((byte) 1);
                } else {
                    member.setGender((byte) 0);
                }
            } else {
                errorMsgs.put("gender", "�ʧO�榡�����T");
            }

            String account = req.getParameter("account");
            String accountReg = "^[a-zA-Z0-9_]{10,20}$";
            if (account == null || account.trim().length() == 0) {
                errorMsgs.put("account", "�b���ФŪť�");
            } else if (!account.trim().matches(accountReg)) {
                errorMsgs.put("account", "�b���榡�����T�A�ݬ� 10-20 �Ӧr��");
            }
            if (memberSvc.isAccountExist(account)) {
                errorMsgs.put("account", "�b���w�s�b");
            } else {
                member.setAccount(account);
            }

            String password = req.getParameter("password");
            String passwordReg = "^[a-zA-Z0-9_]{10,20}$";
            if (password == null || password.trim().length() == 0) {
                errorMsgs.put("password", "�K�X�ФŪť�");
            } else if (!password.trim().matches(passwordReg)) {
                errorMsgs.put("password", "�K�X�榡�����T�A�ݬ� 10-20 �Ӧr��");
            }
            member.setPassword(password);

            String mobileNo = req.getParameter("mobileNo");
            String mobileNoReg = "^\\d{10}$";
            if (mobileNo == null || mobileNo.trim().length() == 0) {
                errorMsgs.put("mobileNo", "������X�ФŪť�");
            } else if (!mobileNo.trim().matches(mobileNoReg)) {
                errorMsgs.put("mobileNo", "������X�榡�����T�A�ݬ� 10 ��Ʀr");
            }
            member.setMobileNo(mobileNo);

            String email = req.getParameter("email");
            String emailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (email == null || email.trim().length() == 0) {
                errorMsgs.put("email", "�q�l�l��ФŪť�");
            } else if (!email.trim().matches(emailReg)) {
                errorMsgs.put("email", "�q�l�l��榡�����T");
            }
            member.setEmail(email);

            Part stickerPart = req.getPart("sticker");
            byte[] sticker = null;
            if (stickerPart != null && stickerPart.getSize() > 0) {
                try {
                    InputStream personalPhotosInputStream = stickerPart.getInputStream();
                    sticker = personalPhotosInputStream.readAllBytes();
                    int maxSize = 10 * 1024 * 1024; // 10 MB
                    if (sticker.length > maxSize) {
                        errorMsgs.put("sticker", "�Y���j�p�W�L 10 MB");
                    }
                    member.setSticker(sticker);
                } catch (Exception e) {
                    errorMsgs.put("sticker", "�Y���W�ǥ���");
                }
            }

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("member", member);
                RequestDispatcher failureView = req.getRequestDispatcher("/member/addMem.jsp");
                failureView.forward(req, res);
                return;
            }
            
   
        
            memberSvc.addMember(
                    member.getRegisDate(),
                    member.getName(),
                    member.getGender(),
                    member.getAccount(),
                    member.getPassword(),
                    member.getMobileNo(),
                    member.getEmail(),
                    member.getSticker()
            );

            String url = "/member/listAllMem.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("delete".equals(action)) {

            Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
            req.setAttribute("errorMsgs", errorMsgs);

            Long memberId = Long.valueOf(req.getParameter("memberId"));

            MemberService memberSvc = new MemberService();
            memberSvc.deleteMember(memberId);

            String url = "/member/listAllMem.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }
    }
}
