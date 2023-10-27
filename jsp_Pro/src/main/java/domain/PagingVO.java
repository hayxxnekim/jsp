package domain;

public class PagingVO {
	//현재 페이지 번호
	private int pageNo;
	//게시글 수
	private int qty;
	//검색 대상
	private String type;
	//검색어
	private String keyword;
	
	//초기 페이지 위한 기본 생성자
	public PagingVO() {
		this(1, 10);
	}
	
	//페이지 클릭 시 pageNo, qty 받아오기 위한 생성자
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	//DB에서 조회할 시작 페이지 계산
	//현재 페이지 2 => 10번째 게시글부터
	public int getPageStart() {
		return (pageNo-1)*qty;
	}
	
	//검색 대상이 없을 때는 빈 문자열 배열 반환, 있을 때는 한 글자씩 분리한 문자열 배열 반환
	public String[] getTypeToArray() {
		return (this.type==null) ? new String[] {} : this.type.split("");
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "PagingVO [pageNo=" + pageNo + ", qty=" + qty + ", type=" + type + ", keyword=" + keyword + "]";
	}
	
}
