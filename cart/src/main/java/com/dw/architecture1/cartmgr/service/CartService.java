package com.dw.architecture1.cartmgr.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dw.common.service.BaseService;

import com.dw.architecture1.cartmgr.dao.CartDAO;
import com.dw.architecture1.cartmgr.vo.CartModel;
import com.dw.architecture1.cartmgr.vo.CartQueryModel;

@Service
@Transactional
public class CartService extends BaseService<CartModel,CartQueryModel> implements ICartService{
	private CartDAO dao = null;
	@Autowired
	private void setDao(CartDAO dao){
		this.dao = dao;
		super.setDAO(dao);
	}
	
}