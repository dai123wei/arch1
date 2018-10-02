package com.dw.customermgr.dao;

import com.dw.common.dao.BaseDAO;
import com.dw.customermgr.vo.CustomerModel;
import com.dw.customermgr.vo.CustomerQueryModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO extends BaseDAO<CustomerModel, CustomerQueryModel>{
}
