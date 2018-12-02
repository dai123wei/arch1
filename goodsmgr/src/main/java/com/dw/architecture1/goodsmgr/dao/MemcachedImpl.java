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
    private final String MEM_PRE = "Goods";
    @Autowired
    private MemCachedClient memCachedClient;
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
        Object obj = memCachedClient.get(MEM_PRE + goodsModel.getUuid());
        if(obj != null){
            memCachedClient.set(MEM_PRE + goodsModel.getUuid(), goodsModel);
        }
    }

    @Override
    public void delete(int uuid) {
        mapperDAO.delete(uuid);
        //查询缓存中有该条数据，存在删除
        Object obj = memCachedClient.get(MEM_PRE + uuid);
        if(obj != null){
            memCachedClient.delete(MEM_PRE + uuid);
        }
    }

    @Override
    public GoodsModel getByUuid(int uuid) {
        GoodsModel goodsModel = null;
        //1.先查缓存，有则从缓存中取值
        Object obj = memCachedClient.get(MEM_PRE + uuid);
        if(obj != null){
            goodsModel = (GoodsModel) obj;
            return goodsModel;
        }
        //2.缓存无，从数据库取
        goodsModel = mapperDAO.getByUuid(uuid);
        //3.将数据存入缓存
        memCachedClient.set(MEM_PRE + uuid, goodsModel);
        return goodsModel;
    }

    @Override
    public List<GoodsModel> getByConditionPage(GoodsQueryModel goodsQueryModel) {
        //1.根据条件查询所有的ids
        List<Integer> ids = mapperDAO.getIdsByConditionPage(goodsQueryModel);
        //2.在内存中找这些id的对应对象
        List<GoodsModel> listGm1 = new ArrayList<>();
        //String noIds = "";
        List<Integer> noIds = new ArrayList<Integer>();
        for(Integer id : ids){
            Object obj = memCachedClient.get(MEM_PRE + id);
            if(obj != null){
                GoodsModel goodsModel = (GoodsModel) obj;
                listGm1.add(goodsModel);
            }else{
                //noIds += id + ",";
                noIds.add(id);
            }
        }
        System.out.println("noIds = " + noIds);
        //3.找不到，从数据库取并设置到缓存中
        List<GoodsModel> listGm2 = null;
        if(noIds.size() > 0){
            for(Integer id : noIds){
				GoodsModel gm = mapperDAO.getByUuid(id);
				memCachedClient.set(MEM_PRE+id,gm);
				listGm1.add(gm);
			}
        }
        return listGm1;
    }
}
