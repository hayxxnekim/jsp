package handler;

import domain.PagingVO;

public class PagingHandler {
	//화면 하단에서 보여줄 시작 페이지 번호
	private int startPage;
	//화면 하단에서 보여줄 끝 페이지 번호
	private int endPage;
	//실제 끝 페이지 번호
	private int realEndPage;
	//이전, 다음 버튼 존재 여부
	private boolean prev, next;
	//전체 게시글 수
	private int totalCount;
	private PagingVO pgvo;
	
	//매개변수로 받은 pgvo, totalCount로 페이지 번호 계산
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		//끝 페이지 번호 계산
		//1~10 : 10, 11~20 : 20, 21~30 : 30
		// 7/10.0 = 0.7 = 1, 1*10 = 10
		this.endPage = (int)(Math.ceil(pgvo.getPageNo()/(double)pgvo.getQty())
				*pgvo.getQty());
		
		//시작 페이지 번호 계산
		this.startPage = this.endPage-9;
		
		//전체 끝 페이지 번호 계산
		// 125/10.0 = 12.5 = 13
		this.realEndPage = (int)Math.ceil(totalCount/(double)pgvo.getQty());
		// 13<20
		if(this.realEndPage<this.endPage) {
			this.endPage = this.realEndPage;
		}
		
		//시작 페이지가 11이상부터 존재
		this.prev = this.startPage>1;
		//실제 끝 페이지보다 작으면 존재
		this.next = this.endPage<realEndPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getRealEndPage() {
		return realEndPage;
	}

	public void setRealEndPage(int realEndPage) {
		this.realEndPage = realEndPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public PagingVO getPgvo() {
		return pgvo;
	}

	public void setPgvo(PagingVO pgvo) {
		this.pgvo = pgvo;
	}

}
