package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import orm.DatabaseBuilder;

public class MemberDAOImpl implements MemberDAO {
	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	private SqlSession sql;
	private final String NS = "MemberMapper.";
	
	public MemberDAOImpl() {
		new DatabaseBuilder();
		//SqlSession 객체 생성
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(MemberVO mvo) {
		int isOk = sql.insert(NS+"add", mvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public MemberVO login(MemberVO mvo) {
		//id, pwd가 일치하는 객체 반환
		return sql.selectOne(NS+"login", mvo);
	}

	@Override
	public int lastLogin(String id) {
		//마지막 접속 시간 update
		int isOk = sql.update(NS+"last", id);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<MemberVO> getList() {
		return sql.selectList(NS+"list");
	}

	@Override
	public int update(MemberVO mvo) {
		int isOk = sql.update(NS+"up", mvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int delete(String id) {
		int isOk = sql.delete(NS+"del", id);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

}
