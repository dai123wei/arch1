package com.dw.architecture1.storemgr.vo;

import com.dw.common.vo.BaseModel;

public class StoreModel extends BaseModel{
	private Integer goodsUuid;
	private Integer storeNum;
	
	public void setGoodsUuid(Integer obj){
		this.goodsUuid = obj;
	}
	public Integer getGoodsUuid(){
		return this.goodsUuid;
	}
	
	public void setStoreNum(Integer obj){
		this.storeNum = obj;
	}
	public Integer getStoreNum(){
		return this.storeNum;
	}
	
	
	
	public String toString(){
		return "Model"+this.getClass().getName()+"[goodsUuid=" + this.getGoodsUuid() + ",storeNum=" + this.getStoreNum() + ",]";
	}	
}
