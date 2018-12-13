package com.mybatis.study;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import com.mybatis.generator.pojo.QueryExample;
import com.mybatis.study.mapper.UserMapper;
import com.mybatis.study.pojo.User;
import com.mybatis.study.util.MybatisUtils;

/**
 * 测试QueryExample的使用
 * @file_comment 其他功能可根据需要在QueryExample自行添加使用(如： find_in_set())
 * 注：复杂sql，关联sql还是要自己手动写的
 * @author zhangxing
 * @date 2018年12月12日
 */
public class TestMybatisThree {
	//测试判等
	@Test
	public void testOne(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andEqualTo("age", 2).andNotEqualTo("is_del", 1);
		List<User> users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		MybatisUtils.closeSqlSession();
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.age = ? and t.is_del <> ? ) 
		//DEBUG - ==> Parameters: 2(Integer), 1(Integer)
	}
	
	//测试 大于>、大于或等于>=、小于<、小于或等于<=
	@Test
	public void testTwo(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andGreaterThanOrEqualTo("age", 0).orLessThan("id", 10);
		List<User> users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		MybatisUtils.closeSqlSession();
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.age >= ? or t.id < ? ) 
		//DEBUG - ==> Parameters: 0(Integer), 10(Integer)
	}
	
	//测试模糊查询  like、not like
	@Test
	public void testThree(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andLike("name","1").orNotLike("name", "2");
		List<User> users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		MybatisUtils.closeSqlSession();
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.name like ? or t.name not like ? ) 
		//DEBUG - ==> Parameters: %1%(String), %2%(String)
	}
	
	//测试in、notIn
	@Test
	public void testFour(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andIn("id",Arrays.asList(1,2,3,4,5,6));
		List<User> users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.id in ( ? , ? , ? , ? , ? , ? ) ) 
		//DEBUG - ==> Parameters: 1(Integer), 2(Integer), 3(Integer), 4(Integer), 5(Integer), 6(Integer)
		example.clear();//清除上面的查询条件
		example.or().andNotIn("id",Arrays.asList(12,343,545,57));
		users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.id not in ( ? , ? , ? , ? ) ) 
		//DEBUG - ==> Parameters: 12(Integer), 343(Integer), 545(Integer), 57(Integer)
		MybatisUtils.closeSqlSession();
	}
	
	//测试分页、不分页
	@Test
	public void testFive(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		QueryExample example = new QueryExample();
		example.and().andGreaterThan("age", 10);
		List<User> users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.age > ? ) 
		//DEBUG - ==> Parameters: 10(Integer)
		
		//limit #{offset},#{limit}
		example.setOffset(0);//设置分页起始位子 (page-1)*limit
		example.setLimit(10);//设置页大小 limit
		users = mapper.selectByExample(example);
		users.stream().forEach(c->System.err.println(c.getId()));
		//select t.id, t.user_name, t.password, t.age, t.cell_phone, t.is_del, t.cost from user t WHERE ( t.age > ? ) limit ?,? 
		//DEBUG - ==> Parameters: 10(Integer), 0(Integer), 10(Integer)
	}
	
	//测试自己手写的sql
	@Test
	public void testSix(){
		SqlSession sqlSession = MybatisUtils.getSqlsession();
		UserMapper mapper = sqlSession.getMapper(UserMapper.class);
		Map<String,Object> map = new HashMap<>();
		map.put("age", 4);
		List<Map<String, Object>> list = mapper.queryByWrite(map);
		//select sum(age),user_name from user where 1=1 and age < ? group by user_name 
		//DEBUG - ==> Parameters: 4(Integer)
		//DEBUG - <==      Total: 1
		//		2	zhangsan	
		//		-------------------------
		list.stream().forEach(c->{
			if(!c.isEmpty()){
				c.keySet().stream().forEach(t->{
					System.err.print(c.get(t)+"\t");
				});
				System.err.println();
			}
		});
		System.err.println("-------------------------");
		map.clear();
		list = mapper.queryByWrite(map);
		//select sum(age),user_name from user where 1=1 group by user_name 
		//DEBUG - ==> Parameters: 
		//DEBUG - <==      Total: 4
		//		5	test1	
		//		4	wewq	
		//		2	zhangsan	
		//		12	修改Id为1 的用户名字	
		list.stream().forEach(c->{
			if(!c.isEmpty()){
				c.keySet().stream().forEach(t->{
					System.err.print(c.get(t)+"\t");
				});
				System.err.println();
			}
		});
	}
}
