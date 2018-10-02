package com.dw.common.service;


import com.dw.common.dao.BaseDAO;
import com.dw.common.pageutil.Page;
import com.dw.common.vo.BaseModel;

import java.util.List;

public class BaseService<M, QM extends BaseModel> implements IBaseService<M, QM> {
    private BaseDAO dao = null;

    public void setDAO(BaseDAO dao){
        this.dao = dao;
    }

    public void create(M m) {
        dao.create(m);
    }

    @Override
    public void update(M m) {
        dao.update(m);
    }

    @Override
    public void delete(int uuid) {
        dao.delete(uuid);
    }

    @Override
    public M getByUuid(int uuid) {
        return (M)dao.getByUuid(uuid);
    }

    @Override
    public Page<M> getByConditionPage(QM qm) {
        List<M> list = dao.getByConditionPage(qm);
        qm.getPage().setResult(list);
        return qm.getPage();
    }
}
