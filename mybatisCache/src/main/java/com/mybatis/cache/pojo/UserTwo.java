package com.mybatis.cache.pojo;

import java.io.Serializable;

/**
 * 描述:usertwo表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2018-12-14
 */
public class UserTwo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String className;

    /**
     * 
     */
    private String name;

    /**
     * 0 男  1 女
     */
    private Integer sex;

    /**
     * 
     */
    private Integer age;

    /**
     * usertwo
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return class_name 
     */
    public String getClassName() {
        return className;
    }

    /**
     * 
     * @param className 
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    /**
     * 
     * @return name 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 0 男  1 女
     * @return sex 0 男  1 女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 0 男  1 女
     * @param sex 0 男  1 女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 
     * @return age 
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 
     * @param age 
     */
    public void setAge(Integer age) {
        this.age = age;
    }
}