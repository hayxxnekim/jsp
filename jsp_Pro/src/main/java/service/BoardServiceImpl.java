package service;

import java.util.List;

import dao.BoardDAO;
import dao.BoardDAOImpl;
import domain.BoardVO;
import domain.CommentVO;
import domain.PagingVO;

public class BoardServiceImpl implements BoardService {
	private BoardDAO bdao;
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int register(BoardVO bvo) {
		return bdao.insert(bvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
		return bdao.getPageList(pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		return bdao.selectOne(bno);
	}

	@Override
	public int readCount(int bno) {
		return bdao.readCount(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		return bdao.update(bvo);
	}

	@Override
	public int remove(int bno) {
		//댓글 삭제 후 게시글 삭제
		CommentServiceImpl csv = new CommentServiceImpl();
		//댓글 개수 확인
		int cnt = csv.commentCount(bno);
		//댓글 존재 시 해당 게시글의 댓글 삭제
		if(cnt>0) {
			int isOk = csv.deleteAll(bno);
		}
		return bdao.delete(bno);
	}

	@Override
	public String getFileName(int bno) {
		return bdao.getFileName(bno);
	}

	public List<BoardVO> myBoard(String id) {
		return bdao.myBoard(id);
	}
}
