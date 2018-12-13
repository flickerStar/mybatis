package com.mybatis.study;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.mybatis.generator.pojo.QueryExample;
import com.mybatis.study.mapper.UserMapper;
import com.mybatis.study.pojo.User;
import com.mybatis.study.util.MybatisUtils;

/**
 * 
 * 测试mybatis-CURD操作
 * @file_comment 
 * @author zhangxing
 * @date 2018年12月11日
 */
public class TestMyBatisTwo {
	//测试增C
	@Test
	public void testOne(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		User user = new User();
		user.setUserName("李四");
		user.setPassword("222");
		int num = mapper.insertSelective(user);
		sqlSession.commit();
		MybatisUtils.closeSqlSession();
		System.err.println(num > 0);
	}
	
	//测试更新U
	@Test
	public void testTwo(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andBetween("age", 5, 12);
		example.and().andEqualTo("id",34);
		User user = new User();
		user.setPassword("修改age在5到12 并且id为12的用户密码");
		int num = mapper.updateByExampleSelective(user, example);
		sqlSession.commit();
		MybatisUtils.closeSqlSession();
		System.err.println(num > 0);
	}
	
	//测试查询R
	@Test
	public void testThree(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andLike("user_name","1");
		List<User> users = mapper.selectByExample(example);
		users.stream().forEach(c->{
			System.err.println(c.getUserName());
		});
		System.err.println("------------------------------------");
		example.clear();
		example.and().andNotLike("user_name","1");
		users = mapper.selectByExample(example);
		users.stream().forEach(c->{
			System.err.println(c.getUserName());
		});
	}
	
	//测试删除D
	@Test
	public void testFour(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andEqualTo("id",new Long(33));
		example.or().andGreaterThanOrEqualTo("age", 100);
		int num = mapper.deleteByExample(example);
		sqlSession.commit();
		MybatisUtils.closeSqlSession();
		System.err.println(num > 0);
	}
}
