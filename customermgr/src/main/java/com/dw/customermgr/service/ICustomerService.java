package com.dw.customermgr.service;

import com.dw.common.service.IBaseService;
import com.dw.customermgr.vo.CustomerModel;
import com.dw.customermgr.vo.CustomerQueryModel;


public interface ICustomerService extends IBaseService<CustomerModel, CustomerQueryModel>{
    public CustomerModel getByCustomerId(String customerId);
}
