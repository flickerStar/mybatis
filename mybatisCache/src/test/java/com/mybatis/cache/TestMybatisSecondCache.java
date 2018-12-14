package com.mybatis.cache;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.mybatis.cache.mapper.UserMapper;
import com.mybatis.cache.mapper.UserTwoMapper;
import com.mybatis.cache.util.MybatisUtils;
import com.mybatis.generator.pojo.QueryExample;

/**
 * 测试mybatis自带二级缓存
 * 作用域:sqlSessionFactory
 * @file_comment 
 * @author zhangxing
 * @date 2018年12月13日
 */
public class TestMybatisSecondCache {
	
	/**
	 * 测试xml的二级缓存
	 * 1.配置mybatis-config.xml中开启二级缓存
	 * 2.配置指定开启二级缓存的UserMapper.xml中开启二级缓存<cache></cache>
	 * 3.要求Mapper.java中不能使用注解@CacheNameSpace,使用的是全xml方式
	 * 4.注： 
	 ****此例子中使用的Mapper是xml格式的mybatis映射文件***
	 */
	@Test
	public void testOne(){
		//第一个SqlSession查询
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper userMapperXml = sqlSession.getMapper(UserMapper.class);
		userMapperXml.countByExample(new QueryExample());
		MybatisUtils.closeSqlSession();
		//第二个SqlSession查询
		sqlSession = MybatisUtils.getSqlsession();
		userMapperXml = sqlSession.getMapper(UserMapper.class);
		userMapperXml.countByExample(new QueryExample());
		MybatisUtils.closeSqlSession();
		
		//不是同一个Session，并且Session关闭了，总计进行了一次数据库查询，日志只有一次数据查询，第二次使用了二级缓存，缓存命中率打印：0.5
	}
	
	/**
	 * 测试注解的二级缓存
	 * 1.配置mybatis-config.xml中开启二级缓存
	 * 2.配置指定开启二级缓存的UserMapper.java中开启二级缓存@CacheNamespace
	 * 3.全注解模式，userMapper.java没有对应的xml文件，sql通过注解写在userMapper.java中
	 */
	@Test
	public void testTwo(){
		//第一个SqlSession查询
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		com.mybatis.cache.annotation.mapper.UserMapper userMapperAnnotation = sqlSession.getMapper(com.mybatis.cache.annotation.mapper.UserMapper.class);
		userMapperAnnotation.getUser(new Long(1));
		MybatisUtils.closeSqlSession();
		//第二个SqlSession查询
		sqlSession = MybatisUtils.getSqlsession();
		userMapperAnnotation = sqlSession.getMapper(com.mybatis.cache.annotation.mapper.UserMapper.class);
		userMapperAnnotation.getUser(new Long(1));
		MybatisUtils.closeSqlSession();
		
		//不是同一个Session，并且Session关闭了，总计进行了一次数据库查询，日志只有一次数据查询，第二次使用了二级缓存，缓存命中率打印：0.5
	}
	
	/**
	 * 1.配置mybatis-config.xml中开启二级缓存
	 * 2.testOne测出userMapper的查询使用了二级缓存
	 * 3.此方法测试userTwoMapper没有配置<cache></cache>，即userTwo没有开启二级缓存
	 */
	@Test
	public void testThree(){
		//第一个SqlSession查询
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserTwoMapper userTwoMapperXml = sqlSession.getMapper(UserTwoMapper.class);
		userTwoMapperXml.countByExample(new QueryExample());
		MybatisUtils.closeSqlSession();
		
		//第二个SqlSession查询
		sqlSession = MybatisUtils.getSqlsession();
		userTwoMapperXml = sqlSession.getMapper(UserTwoMapper.class);
		userTwoMapperXml.countByExample(new QueryExample());
		MybatisUtils.closeSqlSession();
		//可以看出此方法和testOne比较，只是userMapper比userTwoMapper中多配置一个<cache></cache>,此方法没有使用二级缓存，
		//日志打印了两次数据访问sql，与数据库交互了两次。
	}
	
	/**
	 * testOne，userMapper.xml中配置了开启二级缓存，因此其中的所有查询都是可以进行二级缓存的
	 * 此方法测试，关闭某个方法的二级缓存
	 * 单独在userMapper.xml 的 selectByExample 中配置了 useCache = false
	 */
	@Test
	public void testFour(){
		//第一个SqlSession查询
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper userMapperXml = sqlSession.getMapper(UserMapper.class);
		userMapperXml.selectByExample(new QueryExample());
		userMapperXml.countByExample(new QueryExample());
		MybatisUtils.closeSqlSession();
		//第二个SqlSession查询
		sqlSession = MybatisUtils.getSqlsession();
		userMapperXml = sqlSession.getMapper(UserMapper.class);
		userMapperXml.selectByExample(new QueryExample());
		userMapperXml.countByExample(new QueryExample());
		MybatisUtils.closeSqlSession();
		
		//可以看到，selectByExample方法sql打印了两次访问sql日志，而countByExample只打印了一次访问日志，打印了一次访问命中率
		//可以看到 selectByExample 二级缓存没开启，countByExample 开启了二级缓存 	useCache = false 关闭了此方法的二级缓存
	}
}
