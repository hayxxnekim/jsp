package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.MemberVO;
import service.BoardServiceImpl;
import service.MemberService;
import service.MemberServiceImpl;

@WebServlet("/mem/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	private RequestDispatcher rdp;
	private String destPage;
	private int isOk;
	private MemberService msv;
       
    public MemberController() { 
       msv = new MemberServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		switch (path) {
		case "join":
			destPage = "/member/join.jsp";
			break;
		
		case "register":
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				MemberVO mvo = new MemberVO(id, pwd, email, age);
				isOk = msv.register(mvo);
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		
		case "login":
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				//로그인 시 입력한 id, pwd
				MemberVO mvo = new MemberVO(id, pwd);
				//DB의 id, pwd 일치시 객체 MemberVO 반환
				MemberVO loginmvo = msv.login(mvo);
				//해당 id, pwd 일치하는 회원이 있을 경우
				if(loginmvo!=null) {
					//세션 생성
					HttpSession ses = request.getSession();
					//loginmvo 객체 저장
					ses.setAttribute("ses", loginmvo);
					//세션 유효 시간
					ses.setMaxInactiveInterval(10*60);
				} else {
					//일치하지 않을 경우, msg_login = 0 설정
					request.setAttribute("msg_login", 0);
				}
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "logout":
			try {
				//현재 세션 가져오기
				HttpSession ses = request.getSession();
				//세션에 ses라는 이름으로 저장된 속성 가져온 후(loginmvo)
				//MemberVO 형변환
				MemberVO mvo = (MemberVO)ses.getAttribute("ses");
				//현재 로그인한 id 가져오기
				String id = mvo.getId();
				isOk = msv.lastLogin(id);
				//세션 끊기
				ses.invalidate();
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list":
			try {
				//회원 리스트를 저장하기 위한 MemberVO 타입 리스트 생성
				List <MemberVO> list = msv.getList();
				request.setAttribute("list", list);
				destPage = "/member/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "modify":
			destPage = "/member/modify.jsp";
			break;
			
		case "update":
			try {
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String email = request.getParameter("email");
				int age = Integer.parseInt(request.getParameter("age"));
				//가져온 값으로 수정하기 위한 MemberVO 객체 생성
				MemberVO mvo = new MemberVO(id, pwd, email, age);
				isOk = msv.update(mvo);
				destPage = "logout";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove":
			try {
				//세션 끊기 위해 가져오기
				HttpSession ses = request.getSession();
				//세션에 ses라는 이름으로 저장된 속성 가져온 후(loginmvo)
				//MemberVO 형변환
				MemberVO mvo = (MemberVO)ses.getAttribute("ses");
				String id = mvo.getId();
				isOk = msv.remove(id);
				ses.invalidate();
				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "removeAdVer":
			String id = request.getParameter("id");
			isOk = msv.remove(id);
			destPage = "list";
			break;
		}
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
