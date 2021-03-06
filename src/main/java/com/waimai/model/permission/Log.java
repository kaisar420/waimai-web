package com.waimai.model.permission;

import java.io.Serializable;
import java.util.Date;


public class Log implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6469655628699816755L;
	private Integer id;
	/**
	 * 操作内容
	 */
	private String content;
	/**
	 * 操作类型
	 */
	private String type;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 操作人姓名
	 */
	private String operatorRealName;
	/**
	 * 操作模块
	 */
	private Menu menu;
	/**
	 * 操作时间
	 */
	private Date createTime;
	/**
	 * 备用字段
	 */
	private String temp1;
	private String temp2;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getOperatorRealName() {
		return operatorRealName;
	}
	public void setOperatorRealName(String operatorRealName) {
		this.operatorRealName = operatorRealName;
	}
}
