package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import domain.CommentVO;
import orm.DatabaseBuilder;

public class CommentDAOImpl implements CommemtDAO {
	//DB 연결
	private SqlSession sql;
	private final String NS = "CommentMapper.";
	int isOk;
	
	public CommentDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}
	
	@Override
	public int insert(CommentVO cvo) {
		isOk = sql.insert(NS+"add", cvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<CommentVO> getList(int bno) {
		return sql.selectList(NS+"list", bno);
	}

	@Override
	public int update(CommentVO cvo) {
		isOk = sql.update(NS+"up", cvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int delete(int cno) {
		isOk = sql.delete(NS+"del", cno);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int commentCount(int bno) {
		return sql.selectOne(NS+"cnt", bno);
	}

	@Override
	public int deleteAll(int bno) {
		isOk = sql.delete(NS+"delAll", bno);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}
	
}
