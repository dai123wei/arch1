package com.dw.architecture1.ordermgr.service;


import com.dw.architecture1.cartmgr.service.ICartService;
import com.dw.architecture1.cartmgr.vo.CartModel;
import com.dw.architecture1.cartmgr.vo.CartQueryModel;
import com.dw.architecture1.ordermgr.queue.QueueSender;
import com.dw.architecture1.ordermgr.vo.OrderDetailModel;
import com.dw.architecture1.storemgr.service.IStoreService;
import com.dw.architecture1.storemgr.vo.StoreModel;
import com.dw.common.pageutil.Page;
import com.dw.common.util.format.DateFormatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dw.common.service.BaseService;

import com.dw.architecture1.ordermgr.dao.OrderDAO;
import com.dw.architecture1.ordermgr.vo.OrderModel;
import com.dw.architecture1.ordermgr.vo.OrderQueryModel;

@Service
@Transactional
public class OrderService extends BaseService<OrderModel,OrderQueryModel> implements IOrderService{
	private OrderDAO dao = null;
	@Autowired
	private ICartService cartService = null;
	@Autowired
	private IOrderDetailService orderDetailService = null;
	@Autowired
	private IStoreService storeService = null;

	@Autowired
	private void setDao(OrderDAO dao){
		this.dao = dao;
		super.setDAO(dao);
	}

	public void order(int customerId) {
		//消息生产者
		QueueSender.sendMsg(customerId);

		//消费者完成的功能

	}
}