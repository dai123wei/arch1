package com.dw.architecture1.storemgr.dao;

import org.springframework.stereotype.Repository;
import com.dw.common.dao.BaseDAO;

import com.dw.architecture1.storemgr.vo.StoreModel;
import com.dw.architecture1.storemgr.vo.StoreQueryModel;

@Repository
public interface StoreDAO extends BaseDAO<StoreModel,StoreQueryModel>{
	
}
