package com.dw.common.service;


import com.dw.common.pageutil.Page;
import com.dw.common.vo.BaseModel;

public interface IBaseService<M, QM extends BaseModel> {
    public void create(M m);
    public void update(M m);
    public void delete(int uuid);

    public M getByUuid(int uuid);
    public Page<M> getByConditionPage(QM qm);
}
