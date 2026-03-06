//package com.example.WebSocket.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import tw.brad.spring05.dto.MemberForm;
//import tw.brad.spring05.entity.Member;
//import tw.brad.spring05.repo.MemberRepo;
//import tw.brad.spring05.util.BCrypt;
//
//@Service
//public class MemberService {
//	@Autowired
//	private MemberRepo memberRepo;
//	
//	public Member register(MemberForm memberForm) throws Exception {
//		Member member = new Member();
//		
//		String email = memberForm.getEmail();
//		if (memberRepo.findByEmail(email) != null) throw new Exception();
//		
//		member.setEmail(memberForm.getEmail());
//		member.setPasswd(BCrypt.hashpw(memberForm.getPasswd(), BCrypt.gensalt()));
//		member.setName(memberForm.getName());
//		
//		MultipartFile iconFile = memberForm.getIconFile();
//		byte[] icon = iconFile != null && !iconFile.isEmpty() ? iconFile.getBytes():null;
//		member.setIcon(icon);
//		
//		return memberRepo.save(member);
//	}
//	
//	public Member login(String email, String passwd) {
//		Member member = memberRepo.findByEmail(email);
//		if (member != null && BCrypt.checkpw(passwd, member.getPasswd())) {
//			return member;
//		}
//		return null;
//	}
//	
//}