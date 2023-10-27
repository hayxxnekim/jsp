package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.MemberVO;
import domain.PagingVO;
import handler.FileHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;

@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//로그 출력
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    //destpage로 요청, 응답 전달
	private RequestDispatcher rdp;
	//목적지 저장 변수
    private String destPage;
    private int isOk;
    //인터페이스 생성
    private BoardService bsv;
    //0925 추가
    //파일 경로를 저장할 변수
    private String savePath;
   
    public BoardController() {
    	//인터페이스 구현
        bsv = new BoardServiceImpl();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//요청, 응답, 응답의 콘텐츠 타입 설정 및 인코딩
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//요청 uri
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/")+1);
		
		switch (path) {
		case "register":
			destPage = "/board/register.jsp";
			break;
			
		/*
		 * case "insert": 
		 * try { 
		 * //register.jsp의 데이터 가져오기 
		 * String title = request.getParameter("title"); 
		 * String writer = request.getParameter("writer"); 
		 * String content = request.getParameter("content");
		 * 
		 * //가져온 값으로 BoardVO 객체 생성 
		 * BoardVO bvo = new BoardVO(title, writer, content);
		 * isOk = bsv.register(bvo); 
		 * destPage = "/index.jsp"; 
		 * } catch (Exception e) {
		 * e.printStackTrace(); 
		 * } 
		 * break;
		 */
			
		case "insert":
			try {
			//파일을 업로드할 물리적인 경로 설정
			//getServletContext() : 웹 애플리케이션(톰캣 서버)의 환경 설정과 정보 관리
			//getRealPath() : _fileUpload의 실제 파일 시스템 경로
			savePath = getServletContext().getRealPath("/_fileUpload");
			//경로 기반 file 객체 생성
			File fileDir = new File(savePath);
			
			//DiskFileItemFactory : 파일 업로드와 관련된 설정 관리 객체
			//파일 저장위치, 파일 업로드 크기 설정...
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			//파일 업로드시 임시로 저장할 디렉토리 설정
			fileItemFactory.setRepository(fileDir);
			//파일의 임계값 설정, 2mb 초과 시 디스크, 이하시 메모리 저장
			fileItemFactory.setSizeThreshold(2*1024*1024);
			
			//게시글을 등록하기 위한 BoardVO 객체 생성
			BoardVO bvo = new BoardVO();
			
			//ServletFileUpload : multipart/form-data 형식의 요청을 다루기 쉽게 변환해주는 객체
			//fileItemFactory : 파일 업로드와 관련된 설정 지정
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			
			//multipart/form-data 형식 요청의 업로드 파일 및 폼 데이터를 추출 후,
			//FileItem 객체의 리스트에 저장 역할
			List<FileItem> itemList = fileUpload.parseRequest(request);
			
			for(FileItem item : itemList) {
				//getFieldName() : 필드 이름(name) 반환
				switch (item.getFieldName()) {
				//키
				case "title":
					//item.getString() : 키의 대응하는 값을 가져옴
					//키가 title인 경우 title의 값을 
					//utf-8 문자열 인코딩으로 추출 후,
					//bvo 객체의 title로 설정
					bvo.setTitle(item.getString("utf-8"));
					break;
				
				case "writer":
					bvo.setWriter(item.getString("utf-8"));
					break;
					
				case "content":
					bvo.setContent(item.getString("utf-8"));
					break;
					
				//이미지 파일 처리	
				//이미지는 필수X, 없는 경우에도 처리
				case "image_file":
					//item.getSize() : 파일, 폼 크기 반환
					//업로드 된 파일의 크기가 0보다 크다면 있다면 파일이 있는걸로 판단
					if(item.getSize()>0) {
						//item.getName() : 전체 경로, 파일 이름
						//경로를 포함하는 파일 이름의 경우
						//파일 이름만 추출
						//~~~~~/dog.jpg
						String fileName = item.getName()
								.substring(item.getName().lastIndexOf("/")+1); 
						//현재 시간_ 기반 파일 이름 설정
						fileName = System.currentTimeMillis()+"_"+fileName;
						
						//경로, 파일 이름을 포함하는 파일 객체 생성
						//D:~/fileUpload/시간_cat2.jpg
						File uploadFilePath = new File(fileDir+File.separator+fileName);
						log.info("파일 경로+이름 : "+uploadFilePath);
						
						//저장
						try {
							//item.write() : FileItem 객체의 파일 데이터를 지정된 경로(uploadFilePath)와 이름으로 저장
							item.write(uploadFilePath);
							//BoardVO 객체인 bvo의 파일 이름 설정
							bvo.setImage_File(fileName);
							
							//썸네일 작업
							//원본 파일(uploadFilePath) 기반 60*60 썸네일 생성
							//toFile() : 썸네일 이미지를 파일로 저장, 생성된 파일의 경로 반환
							//File 객체 : _th_가 붙은 파일의 경로와 이름
							//파일 디렉토리(fileDir)에 _th_가 붙은 썸네일 저장
							Thumbnails.of(uploadFilePath).size(60, 60)
							.toFile(new File(fileDir+File.separator+"_th_"+fileName)); 
						} catch (Exception e) {
							log.info(">>> file writer on disk error");
							e.printStackTrace();
						}
					}
					break;
				}
			}
			//DB에 bvo 저장 요청
			isOk = bsv.register(bvo);
			log.info(">>> insert > "+(isOk>0? "OK":"Fail"));
			destPage = "pageList";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "pageList":
			try {
				//초기 페이지 설정 위한 PagingVO 객체 생성
				PagingVO pgvo = new PagingVO();
				//list.jsp에서 페이지 번호를 클릭했을 경우
				if(request.getParameter("pageNo")!=null) {
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					//가져온 값으로 PagingVO 객체 생성
					pgvo = new PagingVO(pageNo, qty);
				}
				String type = request.getParameter("type");
				String keyword = request.getParameter("keyword");
				pgvo.setType(type);
				pgvo.setKeyword(keyword);
				int totalCount = bsv.getTotalCount(pgvo);
				List<BoardVO> list = bsv.getPageList(pgvo);
				//destPage에서 해당 속성 사용 가능
				request.setAttribute("list", list);
				PagingHandler ph = new PagingHandler(pgvo, totalCount);
				request.setAttribute("ph", ph);
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "detail":
			try {
				//list.jsp에서 전달한 bno 파라미터 String => int 형변환
				int bno = Integer.parseInt(request.getParameter("bno"));
				//조회수
				int isOk = bsv.readCount(bno);
				BoardVO bvo = bsv.getDetail(bno);
				request.setAttribute("bvo", bvo);
				destPage = "/board/detail.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "modify":
			try {
				//detail.jsp에서 전달한 bno 파라미터 String => int 형변환
				int bno = Integer.parseInt(request.getParameter("bno"));
				//modify.jsp의 초깃값(기존 게시글 정보)을 설정하기 위해 객체 생성
				BoardVO bvo = bsv.getDetail(bno);
				//bvo라는 이름의 속성을 request 객체에 추가
				//destPage에서 해당 속성 사용 가능
				request.setAttribute("bvo", bvo);
				destPage = "/board/modify.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		/*
		 * case "edit": 
		 * try { 
		 * //modify.jsp에서 전달한 bno 파라미터 String => int 형변환 
		 * int bno = Integer.parseInt(request.getParameter("bno")); 
		 * String title = request.getParameter("title"); 
		 * String content = request.getParameter("content");
		 * //가져온 값으로 변경하기 위한 BoardVO 객체 생성 
		 * BoardVO bvo = new BoardVO(bno, title, content);
		 * isOk = bsv.modify(bvo);
		 * destPage = "detail?bno="+bno;
		 * } catch (Exception e) { 
		 * e.printStackTrace(); 
		 * } 
		 * break;
		 */
		
		case "edit":
			try {
			//수정 파일을 업로드할 물리적인 경로 설정
			//getServletContext() : 웹 애플리케이션(톰캣 서버)의 환경 설정과 정보 관리
			//getRealPath() : _fileUpload의 실제 파일 시스템 경로
			savePath = getServletContext().getRealPath("/_fileUpload");
			//경로 기반 file 객체 생성
			File fileDir = new File(savePath);
			
			//DiskFileItemFactory : 파일 업로드와 관련된 설정 관리 객체
			//파일 저장위치, 파일 업로드 크기 설정...
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			//파일 업로드시 임시로 저장할 디렉토리 설정
			fileItemFactory.setRepository(fileDir);
			//파일의 임계값 설정, 2mb 초과 시 디스크, 이하시 메모리 저장
			fileItemFactory.setSizeThreshold(2*1024*1024);
			
			//게시글을 수정하기 위한 BoardVO 객체 생성
			BoardVO bvo = new BoardVO();
			
			//ServletFileUpload : multipart/form-data 형식의 요청을 다루기 쉽게 변환해주는 객체
			//fileItemFactory : 파일 업로드와 관련된 설정 지정
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			log.info(">>> update 준비 >");
			
			//리퀘스트에 실은 객체를 파일의 아이템 형태로 파싱
			//multipart/form-data 형식 요청의 업로드 파일 및 폼 데이터를 추출 후,
			//FileItem 객체의 리스트에 저장 역할
			List<FileItem> itemList = fileUpload.parseRequest(request);
			
			//수정하기 전 원래 이미지 파일
			String old_file = null;
			
			for(FileItem item : itemList) {
				//getFieldName() : 필드 이름(name) 반환
				switch (item.getFieldName()) {
				//키
				case "bno":
					bvo.setBno(Integer.parseInt(item.getString("utf-8")));
					break;
					
				case "title":
					//item.getString() : 키의 대응하는 값을 가져옴
					//키가 title인 경우 title의 값을 
					//utf-8 문자열 인코딩으로 추출 후,
					//bvo 객체의 title로 설정
					bvo.setTitle(item.getString("utf-8"));
					break;
				
				case "content":
					bvo.setContent(item.getString("utf-8"));
					break;
				
				//기존 파일
			    //<input type="hidden" name="image_file" value="${bvo.image_File }">
				case "image_file":
					old_file = item.getString("utf-8");
					break;
					
				//새로운 파일 처리
				case "new_file":
					//item.getSize() : 파일, 폼 크기 반환
					//파일의 크기가 0보다 크다면 있다면 수정 파일이 있는걸로 판단
					if(item.getSize()>0) {
						//기존 파일 존재 시
						if(old_file!=null) {
							//기존 파일 삭제 위한 FileHandler 객체 생성
							FileHandler fileHandler = new FileHandler();
							//삭제할 파일 이름, 경로 설정
							isOk = fileHandler.deleteFile(old_file, savePath);
						}
						//새로운 파일의 경로, 파일 이름 설정
						//item.getName() : 전체 경로, 파일 이름
						//경로를 포함하는 파일 이름의 경우
						//파일 이름만 추출
						//~~~~~/dog.jpg
						String fileName = item.getName()
								.substring(item.getName().lastIndexOf(File.separator)+1); 
						log.info("new_fileName"+fileName);
						
						//현재 시간_ 기반 파일 이름 설정
						fileName = System.currentTimeMillis()+"_"+fileName;
						
						//경로, 파일 이름을 포함하는 새로운 이미지 파일 객체 생성
						//D:~/fileUpload/시간_cat2.jpg
						File uploadFilePath = new File(fileDir+File.separator+fileName);
						
						//저장
						try {
							//item.write() : FileItem 객체의 파일 데이터를 지정된 경로(uploadFilePath)와 이름으로 저장
							item.write(uploadFilePath);
							//BoardVO 객체인 bvo의 파일 이름 설정
							bvo.setImage_File(fileName);
							
							//썸네일 작업
							//원본 파일(uploadFilePath) 기반 60*60 썸네일 생성
							//toFile() : 썸네일 이미지를 파일로 저장, 생성된 파일의 경로 반환
							//File 객체 : _th_가 붙은 파일의 경로와 이름
							//파일 디렉토리(fileDir)에 _th_가 붙은 썸네일 저장
							Thumbnails.of(uploadFilePath).size(60, 60)
							.toFile(new File(fileDir+File.separator+"_th_"+fileName)); 
						} catch (Exception e) {
							log.info(">>> new file save error");
							e.printStackTrace();
						}
					}else {
						//새로운 파일이 없다면, 기존 파일 다시 담기
						bvo.setImage_File(old_file);
					}
					break;
				}
			}
			//DB에 bvo 저장 요청
			isOk = bsv.modify(bvo);
			log.info((isOk>0)?"OK":"Fail");
			destPage = "pageList";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "remove":
			//detail.jsp에서 전달한 bno 파라미터 String => int 형변환
			int bno = Integer.parseInt(request.getParameter("bno"));
			//삭제할 bno의 image_file 이름 가져오기
			String fileName = bsv.getFileName(bno);
			//파일을 삭제하기 위한 savePath 설정
			savePath = getServletContext().getRealPath("/_fileUpload");
			//파일을 삭제하기 위한 FileHandler 객체 생성
			FileHandler filehandler = new FileHandler();
			isOk = filehandler.deleteFile(fileName, savePath);
			log.info((isOk>0)?"file remove OK":"file remove Fail");
			isOk = bsv.remove(bno);
			destPage = "pageList";
			break;
			
		case "myBoard":
			try {
				//현재 세션 가져오기
				HttpSession ses = request.getSession();
				//세션에 ses라는 이름으로 저장된 속성 가져온 후(loginmvo)
				//MemberVO 형변환
				MemberVO mvo = (MemberVO)ses.getAttribute("ses");
				//현재 로그인한 id 가져오기
				String id = mvo.getId();
				List<BoardVO> list = bsv.myBoard(id);
				request.setAttribute("list", list);
				destPage = "/board/myBoard.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;	
		}
		
		//요청, 응답을 destpage로 전달하기 위한 RequestDispatcher 객체 생성
		rdp = request.getRequestDispatcher(destPage);
		//요청, 응답 destpage 전달
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

}
