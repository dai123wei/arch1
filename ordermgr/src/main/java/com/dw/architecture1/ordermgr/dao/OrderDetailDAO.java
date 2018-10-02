package com.dw.architecture1.ordermgr.dao;

import org.springframework.stereotype.Repository;
import com.dw.common.dao.BaseDAO;

import com.dw.architecture1.ordermgr.vo.OrderDetailModel;
import com.dw.architecture1.ordermgr.vo.OrderDetailQueryModel;

@Repository
public interface OrderDetailDAO extends BaseDAO<OrderDetailModel,OrderDetailQueryModel>{
	
}
