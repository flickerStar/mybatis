package com.mybatis.cache.mapper;

import com.mybatis.cache.pojo.UserTwo;
import com.mybatis.generator.pojo.QueryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTwoMapper {
    int countByExample(QueryExample example);

    int deleteByExample(QueryExample example);

    int deleteByPrimaryKey(Long id);

    int insertSelective(UserTwo record);

    List<UserTwo> selectByExample(QueryExample example);

    int updateByExampleSelective(@Param("record") UserTwo record, @Param("example") QueryExample example);
}