package com.sugood.app.entity;

/**
 * 足疗 按摩 实体类
 */
public class ZuLiaoFun {
	private String headPic;
	private String shopName;
	private String comment;
	private String soldNum;
	private String distance;
	private String timeToGet;
	/**起送*/
	private String deliveryStartFrom;
	/**配送*/
	private String delivery;
	/**减免*/
	private String discoutJian;
	/**新用户首次购买*/
	private String discoutShou;
	/**折扣*/
	private String discoutZhe;
	/**领代金券*/
	private String discoutDai;
	/**可开发票*/
	private String discoutInvoice;


	public ZuLiaoFun(){}

	public ZuLiaoFun(String headPic, String shopName, String comment, String soldNum, String distance, String timeToGet, String deliveryStartFrom, String delivery, String discoutJian, String discoutShou, String discoutZhe, String discoutDai, String discoutInvoice) {
		this.headPic = headPic;
		this.shopName = shopName;
		this.comment = comment;
		this.soldNum = soldNum;
		this.distance = distance;
		this.timeToGet = timeToGet;
		this.deliveryStartFrom = deliveryStartFrom;
		this.delivery = delivery;
		this.discoutJian = discoutJian;
		this.discoutShou = discoutShou;
		this.discoutZhe = discoutZhe;
		this.discoutDai = discoutDai;
		this.discoutInvoice = discoutInvoice;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(String soldNum) {
		this.soldNum = soldNum;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTimeToGet() {
		return timeToGet;
	}

	public void setTimeToGet(String timeToGet) {
		this.timeToGet = timeToGet;
	}

	public String getDeliveryStartFrom() {
		return deliveryStartFrom;
	}

	public void setDeliveryStartFrom(String deliveryStartFrom) {
		this.deliveryStartFrom = deliveryStartFrom;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getDiscoutJian() {
		return discoutJian;
	}

	public void setDiscoutJian(String discoutJian) {
		this.discoutJian = discoutJian;
	}

	public String getDiscoutShou() {
		return discoutShou;
	}

	public void setDiscoutShou(String discoutShou) {
		this.discoutShou = discoutShou;
	}

	public String getDiscoutZhe() {
		return discoutZhe;
	}

	public void setDiscoutZhe(String discoutZhe) {
		this.discoutZhe = discoutZhe;
	}

	public String getDiscoutDai() {
		return discoutDai;
	}

	public void setDiscoutDai(String discoutDai) {
		this.discoutDai = discoutDai;
	}

	public String getDiscoutInvoice() {
		return discoutInvoice;
	}

	public void setDiscoutInvoice(String discoutInvoice) {
		this.discoutInvoice = discoutInvoice;
	}

}
