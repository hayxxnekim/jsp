package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import domain.BoardVO;
import domain.PagingVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	//DB 연결 객체
	private SqlSession sql;
	private final String NS = "BoardMapper.";
	
	public BoardDAOImpl() {
		//DatabaseBuilder 객체 생성
		new DatabaseBuilder();
		//factory로 SqlSession 객체 생성
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(BoardVO bvo) {
		//insert, update, delete commit 필수
		int isOk = sql.insert(NS+"add", bvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return sql.selectOne(NS+"cnt", pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
		return sql.selectList(NS+"page", pgvo);
	}

	@Override
	public BoardVO selectOne(int bno) {
		return sql.selectOne(NS+"detail", bno);
	}

	@Override
	public int readCount(int bno) {
		int isOk = sql.update(NS+"count", bno);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int update(BoardVO bvo) {
		int isOk = sql.update(NS+"up", bvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int delete(int bno) {
		int isOk = sql.delete(NS+"del", bno);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public String getFileName(int bno) {
		return sql.selectOne(NS+"fileName", bno);
	}

	@Override
	public List<BoardVO> myBoard(String id) {
		return sql.selectList(NS+"myBoard", id);
	}
}
