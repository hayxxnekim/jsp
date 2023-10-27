package service;

import java.util.List;

import dao.CommemtDAO;
import dao.CommentDAOImpl;
import domain.CommentVO;

public class CommentServiceImpl implements CommentService {
	private CommemtDAO cdao;
	
	public CommentServiceImpl() {
		cdao = new CommentDAOImpl(); 
	}
	
	@Override
	public int post(CommentVO cvo) {
		return cdao.insert(cvo);
	}

	@Override
	public List<CommentVO> getList(int bno) {
		return cdao.getList(bno);
	}

	@Override
	public int modify(CommentVO cvo) {
		return cdao.update(cvo);
	}

	@Override
	public int remove(int cno) {
		return cdao.delete(cno);
	}

	//BoardServiceImpl에서 호출
	public int commentCount(int bno) {
		return cdao.commentCount(bno);
	}

	public int deleteAll(int bno) {
		return cdao.deleteAll(bno);
	}

}
