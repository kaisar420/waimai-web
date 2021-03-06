package com.waimai.model.dish;  

import java.io.Serializable;
import java.util.Date;
import com.waimai.model.IdEntity;

/**
 * ClassName: Dish
 * Desc: 菜品实体类
 * date: 2014-9-22 下午2:27:16
 * @author li.n1 
 * @since JDK 1.6
 */
public class Dish extends IdEntity implements Serializable{
	/** 
	 * serialVersionUID:序列化
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 9121389848184203713L;
	/**
	 * 菜品名称
	 */
	private String name;
	/**
	 * 菜品价格
	 */
	private String price;
	/**
	 * 菜品的提供商
	 */
	private String businessNo;

	/**
	 * 该菜几元起价
	 * 
	 */
	private int lowprice;
	/**
	 * 菜品图片
	 */
	private String img;
	/**
	 * 销售份数
	 */
	private int saleNum;
	/**
	 * 排序号
	 */
	private int sortNum;
	/**
	 *是否推荐 
	 */
	private boolean recommendOrNot;
	/**
	 * 是否热门
	 */
	private boolean hotOrNot;
	/**
	 * 菜品唯一的编码，使用uuid
	 */
	private String dishNo;
	/**
	 * 菜品创建时间
	 */
	private Date createTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessId(String businessId) {
		this.businessNo = businessId;
	}
	public String getImg() {
		return img;
	}
	public int getLowprice() {
		return lowprice;
	}
	public void setLowprice(int lowprice) {
		this.lowprice = lowprice;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public boolean isRecommendOrNot() {
		return recommendOrNot;
	}
	public void setRecommendOrNot(boolean recommendOrNot) {
		this.recommendOrNot = recommendOrNot;
	}
	public boolean isHotOrNot() {
		return hotOrNot;
	}
	public void setHotOrNot(boolean hotOrNot) {
		this.hotOrNot = hotOrNot;
	}
	public String getDishNo() {
		return dishNo;
	}
	public void setDishNo(String dishNo) {
		this.dishNo = dishNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
}
