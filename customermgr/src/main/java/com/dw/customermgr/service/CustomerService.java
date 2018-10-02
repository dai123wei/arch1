package com.dw.customermgr.service;

import com.dw.common.service.BaseService;
import com.dw.customermgr.dao.CustomerDAO;
import com.dw.customermgr.vo.CustomerModel;
import com.dw.customermgr.vo.CustomerQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService extends BaseService<CustomerModel, CustomerQueryModel> implements ICustomerService{
    private CustomerDAO dao = null;
    @Autowired
    private void setDao(CustomerDAO dao){
        this.dao = dao;
        super.setDAO(dao);
    }
}
