package com.mybatis.cache.annotation.mapper;
import com.mybatis.cache.pojo.User;
import java.util.List;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@CacheNamespace
public interface UserMapper {
	@Select("select user_name userName,password,id from user where id = #{id}")
	@Options(useCache = true)
	List<User> getUser(@Param("id") Long id);
}