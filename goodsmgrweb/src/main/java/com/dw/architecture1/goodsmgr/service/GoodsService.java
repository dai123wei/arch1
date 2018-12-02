package com.dw.architecture1.goodsmgr.service;


import com.dw.architecture1.goodsmgr.dao.GoodsDAO;
import com.dw.architecture1.goodsmgr.vo.GoodsModel;
import com.dw.architecture1.goodsmgr.vo.GoodsQueryModel;
import com.dw.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodsService extends BaseService<GoodsModel,GoodsQueryModel> implements IGoodsService{
	private GoodsDAO dao = null;
	@Autowired
	private void setDao(GoodsDAO dao){
		this.dao = dao;
		super.setDAO(dao);
	}
	
}