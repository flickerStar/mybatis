package com.mybatis.cache.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述:user表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2018-12-13
 */
public class User implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String cellPhone;

    /**
     * 
     */
    private Integer isDel;

    /**
     * 
     */
    private BigDecimal cost;

    /**
     * user
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
     * 用户名
     * @return user_name 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 年龄
     * @return age 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 年龄
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 手机号
     * @return cell_phone 手机号
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * 手机号
     * @param cellPhone 手机号
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone == null ? null : cellPhone.trim();
    }

    /**
     * 
     * @return is_del 
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 
     * @param isDel 
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 
     * @return cost 
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * 
     * @param cost 
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}