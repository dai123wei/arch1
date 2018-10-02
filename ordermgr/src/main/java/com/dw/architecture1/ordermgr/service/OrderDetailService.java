package com.dw.architecture1.ordermgr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dw.common.service.BaseService;

import com.dw.architecture1.ordermgr.dao.OrderDetailDAO;
import com.dw.architecture1.ordermgr.vo.OrderDetailModel;
import com.dw.architecture1.ordermgr.vo.OrderDetailQueryModel;

@Service
@Transactional
public class OrderDetailService extends BaseService<OrderDetailModel,OrderDetailQueryModel> implements IOrderDetailService{
	private OrderDetailDAO dao = null;
	@Autowired
	private void setDao(OrderDetailDAO dao){
		this.dao = dao;
		super.setDAO(dao);
	}
	
}