package com.mybatis.study;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import com.mybatis.generator.pojo.QueryExample;
import com.mybatis.study.mapper.UserMapper;
import com.mybatis.study.pojo.User;
import com.mybatis.study.util.MybatisUtils;
/**
 * mybatis基础功能测试类
 * @file_comment 
 * @author zhangxing
 * @date 2018年12月11日
 */
public class TestMyBatisOne {
	//mybatis 获取会话工厂的两种方式
	@Test
	public void testOne(){
		try {
			//1.获取Reader 或者  获取InputStream,构建SqlSession工厂，并从工厂里打开一个SqlSession
			//(1)获取Reader 
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

			//(2)获取InputStream
//			InputStream in = TestMyBatisOne.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
//			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
			
			//2.获取sqlSession对象
			SqlSession sqlSession = sqlSessionFactory.openSession(true);
			//3.获取mapper
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			//4.条件查询（id是1,2的用户信息）
			QueryExample example = new QueryExample();
			example.and().andIn("id", Arrays.asList(1,2));
			List<User> users = mapper.selectByExample(example);
			//5.使用SqlSession执行完SQL之后需要关闭SqlSession
			sqlSession.close();
			//打印查询数据
			users.stream().forEach(c->{
				System.err.println("用户名称:"+c.getUserName()+"\t 用户Id:"+c.getId());
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//mybatis 获取sqlSession 是否自动提价事务
	@Test
	public void testTwo(){
		try {
			//1.构建SqlSession工厂，并从工厂里打开一个SqlSession
			InputStream in = TestMyBatisOne.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

			//2.获取sqlSession数据库连接操作会话对象
			//(1)true表示自动提交事务，不需要commit
			SqlSession sqlSession = sqlSessionFactory.openSession(true);
			
			//(2)false表示手动提交事务，增删改需要commit一下 -->不写默认false
//			SqlSession sqlSession = sqlSessionFactory.openSession();
//			SqlSession sqlSession = sqlSessionFactory.openSession(false);
			
			//3.修改用户信息（example存放修改的条件，user存放要修改的字段值）
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			QueryExample example = new QueryExample();
			example.and().andEqualTo("id",1);
			User user = new User();
			user.setUserName("修改Id为1 的用户名字");
			int num = mapper.updateByExampleSelective(user, example);
			
			//手动提交时，即false时，进行增删改以后需要commit才能将修改结果成功提交到数据库
//			sqlSession.commit();
			//4.关闭session会话对象
			sqlSession.close();
			System.err.println(num == 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//封装sqlSession的获取和关闭
	@Test
	public void testThree(){
		try {
			//1.获取sqlSession数据库连接操作会话对象
			SqlSession sqlSession = MybatisUtils.getSqlsession();
			//2.修改用户信息（example存放修改的条件，user存放要修改的字段值）
			UserMapper mapper = sqlSession.getMapper(UserMapper.class);
			QueryExample example = new QueryExample();
			example.and().andEqualTo("id",1);
			User user = new User();
			user.setUserName("修改Id为1 的用户名字");
			int num = mapper.updateByExampleSelective(user, example);
			//3.执行事务的提交(查询可省)
			sqlSession.commit();
			//4.关闭session
			MybatisUtils.closeSqlSession();
			System.err.println(num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
