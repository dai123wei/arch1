package com.dw.architecture1.goodsmgr.dao;

import com.dw.architecture1.goodsmgr.vo.GoodsModel;
import com.dw.architecture1.goodsmgr.vo.GoodsQueryModel;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GoodsMapperDAO extends GoodsDAO{
    public List<Integer> getIdsByConditionPage(GoodsQueryModel gqm);
    public List<GoodsModel> getByIds(String ids);
}
