package com.dw.architecture1.goodsmgr.dao;

import java.util.ArrayList;
import java.util.List;

import com.dw.architecture1.goodsmgr.vo.GoodsModel;
import com.dw.architecture1.goodsmgr.vo.GoodsQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;

@Service
@Primary
public class MemcachedImpl implements GoodsDAO{

    @Autowired
    private GoodsMapperDAO mapperDAO = null;

    @Override
    public void create(GoodsModel goodsModel) {
        mapperDAO.create(goodsModel);
    }

    @Override
    public void update(GoodsModel goodsModel) {
        mapperDAO.update(goodsModel);
        //查询缓存中有该条数据，存在更新
    }

    @Override
    public void delete(int uuid) {
        mapperDAO.delete(uuid);
        //查询缓存中有该条数据，存在删除
    }

    @Override
    public GoodsModel getByUuid(int uuid) {
        GoodsModel goodsModel = null;
        //1.先查缓存，有则从缓存中取值

        //2.缓存无，从数据库取
        goodsModel = mapperDAO.getByUuid(uuid);
        //3.将数据存入缓存

        return goodsModel;
    }

    @Override
    public List<GoodsModel> getByConditionPage(GoodsQueryModel goodsQueryModel) {
        //1.根据条件查询所有的ids
        //2.在内存中找这些id的对应对象
        //3.找不到，从数据库取并设置到缓存中
        return null;
    }
}
