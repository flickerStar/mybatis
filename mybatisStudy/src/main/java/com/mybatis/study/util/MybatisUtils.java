package com.mybatis.study.util;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @ClassName: MyBatisUtil
 * @Description: 创建单例工厂，可以获得线程session
 * @author peace w_peace12@163.com
 * @date 27 Mar 2016 10:01:45 pm
 *
 */
public class MybatisUtils {
	// 创建线程独立的session
	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
	// sqlsession 工厂
	private static SqlSessionFactory sqlSessionFactory;

	/**
	 * 加载位于src/mybatis.xml配置文件
	 */
	static {
		try {
			// 获得配置文件字符流
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			// 通过配置文件创建工厂
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:禁止外界new 该工具类
	 * </p>
	 */
	private  MybatisUtils() {

	}

	/**
	 * 获取session,线程独立
	 */
	public static  SqlSession getSqlsession() {
		// 从当前线程中获取SqlSession对象
		SqlSession sqlSession = threadLocal.get();
		// 如果SqlSession对象为空
		if (sqlSession == null) {
			// 在SqlSessionFactory非空的情况下，获取SqlSession对象
			sqlSession = sqlSessionFactory.openSession();
			// 将SqlSession对象与当前线程绑定在一起
			threadLocal.set(sqlSession);
		}
		// 返回SqlSession对象
		return sqlSession;
	}

	/**
	 * 关闭SqlSession与当前线程分开
	 */
	public static  void closeSqlSession() {
		// 从当前线程中获取SqlSession对象
		SqlSession sqlSession = threadLocal.get();
		// 如果SqlSession对象非空
		if (sqlSession != null) {
			// 关闭SqlSession对象
			sqlSession.close();
			// 分开当前线程与SqlSession对象的关系，目的是让GC尽早回收
			threadLocal.remove();
		}
	}
	public static  void main(String[] args) {
		Connection conn = MybatisUtils.getSqlsession().getConnection();
		System.out.println(conn!=null?"连接成功":"连接失败");
	}
}
