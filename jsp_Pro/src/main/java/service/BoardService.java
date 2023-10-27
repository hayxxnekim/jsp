package service;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardService {

	int register(BoardVO bvo);

	int getTotalCount(PagingVO pgvo);

	List<BoardVO> getPageList(PagingVO pgvo);

	BoardVO getDetail(int bno);

	int readCount(int bno);

	int modify(BoardVO bvo);

	int remove(int bno);

	String getFileName(int bno);

	List<BoardVO> myBoard(String id);

}
