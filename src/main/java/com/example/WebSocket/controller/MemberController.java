//package com.example.WebSocket.controller;
//
//import java.util.Base64;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.servlet.http.HttpSession;
//import tw.brad.spring05.dto.MemberForm;
//import tw.brad.spring05.entity.Hotel;
//import tw.brad.spring05.entity.Member;
//import tw.brad.spring05.repo.HotelRepo;
//import tw.brad.spring05.repo.MemberRepo;
//import tw.brad.spring05.service.MemberService;
//
//@Controller
//@RequestMapping("/member")
//public class MemberController {
//
//    private final MemberRepo memberRepo;
//
//	@Autowired
//	private MemberService memberService;
//
//    MemberController(MemberRepo memberRepo) {
//        this.memberRepo = memberRepo;
//    }
//	
//	@GetMapping("/register")
//	public String register(Model model) {
//		model.addAttribute("memberForm", new MemberForm());
//		return "register";
//	}
//	
//	@PostMapping("/register")
//	public String doRegister(@ModelAttribute MemberForm memberForm,
//			Model model) {
//		try {
//			memberService.register(memberForm);
//			return "redirect:/member/login";
//		}catch(Exception e) {
//			System.out.println(e);
//			model.addAttribute("error", "帳號已註冊或其他");
//			
//			return "register";
//		}
//	}
//	
//	@PostMapping("/register2")
//	public String doRegister2(
//			@RequestParam String email,
//			@RequestParam String passwd,
//			@RequestParam String name,
//			@RequestParam MultipartFile iconFile,
//			Model model) {
//		return "login";
//	}
//	
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
//	
//	@PostMapping("/login")
//	public String doLogin(@RequestParam String email, 
//			@RequestParam String passwd,
//			HttpSession session) {
//		Member member = memberService.login(email, passwd);
//		if (member != null) {
//			session.setAttribute("member", member);
//			return "redirect:/member/home";
//		}
//		return "login";
//	}
//	
//	@Autowired
//	private HotelRepo hotelRepo;
//	
//	@RequestMapping("/home")
//	public String home(HttpSession session, Model model,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size) {
//		Object obj = session.getAttribute("member");
//		if (obj == null) return "redirect:/member/login";
//		Member member = (Member)obj;
//		
//		model.addAttribute("member", member);
//		model.addAttribute("icon", "data:image/*; base64, " + Base64.getEncoder().encodeToString(member.getIcon()));
//		
//		Pageable pageable = PageRequest.of(page, size);
//		Page<Hotel> pageHotel = hotelRepo.findAll(pageable);
//		model.addAttribute("hotels", pageHotel);
//		model.addAttribute("page", page);
//		
//		
//		return "home";
//	}
//	
//	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/member/login";
//	}
//	
//	
//}