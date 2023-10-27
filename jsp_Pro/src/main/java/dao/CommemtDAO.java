package dao;

import java.util.List;

import domain.CommentVO;

public interface CommemtDAO {

	int insert(CommentVO cvo);

	List<CommentVO> getList(int bno);

	int update(CommentVO cvo);

	int delete(int cno);

	int commentCount(int bno);

	int deleteAll(int bno);

}
