package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import service.CommentService;
import service.CommentServiceImpl;

@WebServlet("/cmt/*")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(CommentController.class); 
	private CommentService csv;
	private int isOk;
	//댓글 : 비동기(페이지 이동X = destPage, requsetdispatcher X)
	
    public CommentController() {
    	csv = new CommentServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//페이지를 띄우는게 아니기 때문에 setContentType X
		
		// get => 리스트 보여줄 때, post => 등록 시, put => update, delete => delete??
		String uri = request.getRequestURI();
		// /cmt/를 제외한 나머지 추출
		String pathUri = uri.substring("/cmt/".length());
		// list
		String path = pathUri;
		//path에 달고 오는 값 저장
		// cmt/list/10, pathVar = 10;
		String pathVar = "";
		
		if(pathUri.contains("/")) {
			//cmt
			path = pathUri.substring(0, pathUri.lastIndexOf("/"));
			//10
			pathVar = pathUri.substring(pathUri.lastIndexOf("/")+1);
		}
		
		switch (path) {
		//post request의 body 데이터를 읽어와(json string 형식) json 객체로 파싱(변환)
		case "post":
			try {
				//StringBuffer : 문자열 조작 클래스
				StringBuffer sb = new StringBuffer();
				//문자열을 저장하기 위한 변수
				String line = "";
				//body의 데이터를 읽기 위한 BufferedReader 객체 생성
				BufferedReader br = request.getReader();
				//한 줄씩 읽은 값을 line에 저장,
				//line이 null이 아닐 경우
				while((line = br.readLine())!=null) {
					//sb에 추가
					sb.append(line);
				}
				
				//읽어온 jsonString을 json 객체로 파싱하기 위해 JSONParser 객체 생성
				JSONParser parser = new JSONParser();
				//sb 저장 데이터(json string 형식)를 문자열 변환 후,
				//parse() : json 문자열을 객체로 변환
				//parse() : json 파싱, (JSONObject) 형변환
				//parse : Object 타입 반환, (JSONObject) 형변환
				JSONObject jsonObj = (JSONObject)parser.parse(sb.toString());
				
				//json 객체의 값 가져오기
				int bno = Integer.parseInt(jsonObj.get("bno").toString());
				String writer = jsonObj.get("writer").toString();
				String content = jsonObj.get("content").toString();
				
				//가져온 값으로 CommentVO 객체 생성
				CommentVO cvo = new CommentVO(bno, writer, content);
				isOk = csv.post(cvo);
				
				//PrintWriter : 텍스트 데이터 출력 클래스
				//서버로 응답 전송
				PrintWriter out = response.getWriter();
				out.print(isOk);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		
		// list/151
		case "list":
			try {
				//bno = getCommentListFromServer의 bno
				int bno = Integer.parseInt(pathVar);
				//댓글을 저장하기 위한 list 생성
				List<CommentVO> list = csv.getList(bno);
				
				//list 길이에 따른 빈 JSONObject 배열 생성(객체)
				//댓글 객체가 여러 개이기 때문에 json 객체 배열로 생성
				//배열의 각 인덱스 : 댓글 하나의 정보를 담음
				JSONObject[] jsonObjArr = new JSONObject[list.size()];
				// [{...}, {...}, {...}, {...}, {...}, {...}, {...}, {...}]
				// {bno: 136, cno: 18, regdate: '2023-09-20 19:43:44', writer: 'admin', content: '1'}
				
				//JSON 배열 생성 : [    ]
				//각각의 댓글 객체를 json 배열에 담음
				JSONArray jsonList = new JSONArray();
				for(int i=0; i<list.size(); i++) {
					//jsonObjArr의 각 요소마다
					//json 객체({bno: 136, cno: 18, regdate: '2023-09-20 19:43:44', writer: 'admin', content: '1'}) 생성
					// {   }
					//JSONObject : 키-값
					jsonObjArr[i] = new JSONObject();
					//리스트의 각 요소를 {  }에 추가
					jsonObjArr[i].put("cno", list.get(i).getCno());
					jsonObjArr[i].put("bno", list.get(i).getBno());
					jsonObjArr[i].put("writer", list.get(i).getWriter());
					jsonObjArr[i].put("content", list.get(i).getContent());
					jsonObjArr[i].put("regdate", list.get(i).getRegdate());
					
					//json 배열([  ])에 추가
					jsonList.add(jsonObjArr[i]);
				}
				//서버 전송용
				//jsonList를 json 형식 문자열로 변환
				String jsonData = jsonList.toJSONString();
				//응답(댓글 리스트)를 전송
				PrintWriter out = response.getWriter();
				out.print(jsonData);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "modify":
			try {
				//StringBuffer : 문자열 조작 클래스
				StringBuffer sb = new StringBuffer();
				//문자열을 저장하기 위한 변수
				String line = "";
				
				//body의 수정된 댓글 데이터를 읽기 위한 BufferedReader 객체 생성
				BufferedReader br = request.getReader();
				//한 줄씩 읽은 값을 line에 저장,
				//line이 null이 아닐 경우
				while((line = br.readLine())!=null) {
					//sb에 추가
					sb.append(line);
				}
				
				//읽어온 jsonString을 json 객체로 파싱하기 위해 JSONParser 객체 생성
				JSONParser parser = new JSONParser();
				//sb.toString() : sb 저장 데이터(json string 형식)를 문자열 변환
				//parser.parse(sb.toString()) : json 문자열을 json 객체로 파싱
				//parse : Object 타입 반환
				//(JSONObject) : JSONObject 형변환
				JSONObject jsonobj = (JSONObject)parser.parse(sb.toString());
				
				//json 객체의 값 가져오기
				int cno = Integer.parseInt(jsonobj.get("cno").toString());
				String writer = jsonobj.get("writer").toString();
				String content = jsonobj.get("content").toString();
				//가져온 값으로 CommentVO 객체 생성
				CommentVO cvo = new CommentVO(cno, content);
				isOk = csv.modify(cvo);
				
				//PrintWriter : 텍스트 데이터 출력 클래스
				//서버로 응답 전송
				PrintWriter out = response.getWriter();
				out.print(isOk);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove":
			try {
				int cno = Integer.parseInt(pathVar);
				isOk = csv.remove(cno);
				PrintWriter out = response.getWriter();
				out.print(isOk);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
