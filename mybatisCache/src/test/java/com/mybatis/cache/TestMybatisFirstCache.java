package com.mybatis.cache;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.mybatis.cache.mapper.UserMapper;
import com.mybatis.cache.pojo.User;
import com.mybatis.cache.util.MybatisUtils;
import com.mybatis.generator.pojo.QueryExample;

/**
 * 测试mybatis自带一级缓存
 * 作用域:sqlSession
 * @file_comment 
 * @author zhangxing
 * @date 2018年12月13日
 */
public class TestMybatisFirstCache {

	//测试一级缓存的使用
	/*@Test
	public void testOne(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		//第一次查询
		QueryExample ex = new QueryExample();
		List<User> userOneList = userMapper.selectByExample(ex);
		//第二次查询
		List<User> userTwoList = userMapper.selectByExample(new QueryExample());
		
		userOneList.stream().forEach(c->System.err.print(c.getUserName()));
		System.err.println("\n++++++++++++++++++++++++++++");
		userTwoList.stream().forEach(c->System.err.print(c.getUserName()));
		
		//关闭sqlSession
		MybatisUtils.closeSqlSession();
		//两次查询条件一次，实际只执行了一次数据库查询操作，日志只打印了一次查询，与数据库只交互了一次，二次查询使用了一级缓存，没有真实的查询
	}*/
	
	//测试flush或者clear会是一级缓存失效
	/*@Test
	public void testTwo(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		//第一次查询
		QueryExample ex = new QueryExample();
		List<User> userOneList = userMapper.selectByExample(ex);
		//刷新一次缓存
		sqlSession.clearCache();
		//第二次查询
		List<User> userTwoList = userMapper.selectByExample(new QueryExample());
		userOneList.stream().forEach(c->System.err.print(c.getUserName()));
		
		System.err.println("\n++++++++++++++++++++++++++++");
		userTwoList.stream().forEach(c->System.err.print(c.getUserName()));
		
		//关闭sqlSession
		MybatisUtils.closeSqlSession();
		//两次查询条件一致，中间若进行了flush或者清空缓存操作，则一级缓存失效，日志打印出了两次数据库查询语句，与数据库进行了两次交互
	}*/
	
	//测试新增、修改、删除会使一级缓存失效
	/*@Test
	public void testThree(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		//第一次查询
		QueryExample ex = new QueryExample();
		List<User> userOneList = userMapper.selectByExample(ex);
		//删除操作、修改操作、新增操作
		userMapper.deleteByPrimaryKey(new Long(55));
		//第二次查询
		List<User> userTwoList = userMapper.selectByExample(new QueryExample());
		
		userOneList.stream().forEach(c->System.err.print(c.getUserName()));
		System.err.println("\n++++++++++++++++++++++++++++");
		userTwoList.stream().forEach(c->System.err.print(c.getUserName()));
		
		//增删改时，要提交事务
		sqlSession.commit();
		//关闭sqlSession
		MybatisUtils.closeSqlSession();
		//两次查询条件一致，中间做了一次删除操作，一级缓存失效，日志打印出了两次查询数据库的语句，与数据库累计进行了三次交互，两次查询一次删除
	}*/
	
	//测试一级缓存的作用域(注： 测试此方法时，要确保UserMapper.xml <cache></cache> 或者 UserMapper.java @CacheNameSpace 二级缓存没有开启)
	@Test
	public void testFour(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		//第一次查询
		QueryExample ex = new QueryExample();
		List<User> userOneList = userMapper.selectByExample(ex);
		//关闭sqlSession
		MybatisUtils.closeSqlSession();
		
		SqlSession sqlSessionTwo = MybatisUtils.getSqlsession();
		userMapper = sqlSessionTwo.getMapper(UserMapper.class);
		//第二次查询
		List<User> userTwoList = userMapper.selectByExample(ex);
		//关闭sqlSession
		MybatisUtils.closeSqlSession();
		
		userOneList.stream().forEach(c->System.err.print(c.getUserName()));
		System.err.println("\n++++++++++++++++++++++++++++");
		userTwoList.stream().forEach(c->System.err.print(c.getUserName()));
		//两次查询条件一致，但是使用的不是一个SqlSession，进行了两次数据库交互查询，说明，一级缓存的作用域是单个的SqlSession,不能跨session
	}
}
