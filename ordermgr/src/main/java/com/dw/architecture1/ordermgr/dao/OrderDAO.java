package com.dw.architecture1.ordermgr.dao;

import org.springframework.stereotype.Repository;
import com.dw.common.dao.BaseDAO;

import com.dw.architecture1.ordermgr.vo.OrderModel;
import com.dw.architecture1.ordermgr.vo.OrderQueryModel;

@Repository
public interface OrderDAO extends BaseDAO<OrderModel,OrderQueryModel>{
	
}
