package com.dw.architecture1.goodsmgr.dao;

import java.util.List;

import com.dw.architecture1.goodsmgr.vo.GoodsQueryModel;
import org.springframework.stereotype.Repository;


@Repository
public interface GoodsMapperDAO extends GoodsDAO{
    public List<Integer> getIdsByConditionPage(GoodsQueryModel gqm);
}
