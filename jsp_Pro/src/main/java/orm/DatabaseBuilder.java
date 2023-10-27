package orm;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DatabaseBuilder {
	//DB 관리 객체
	private static SqlSessionFactory factory;
	//mybatis 경로
	private static final String config = "orm/mybatisConfig.xml";
	
	static {
		try {
			//SqlSessionFactoryBuilder 객체 생성 후,
			//SqlSessionFactory 생성
			factory = new SqlSessionFactoryBuilder().build(
					Resources.getResourceAsReader(config));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//factory 공유
	public static SqlSessionFactory getFactory() {
		return factory;
	}
}
