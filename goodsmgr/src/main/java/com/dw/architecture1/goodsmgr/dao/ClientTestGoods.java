package com.dw.architecture1.goodsmgr.dao;

import com.dw.architecture1.goodsmgr.vo.GoodsModel;
import com.dw.architecture1.goodsmgr.vo.GoodsQueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientTestGoods {
	@Autowired
	private GoodsDAO goodsDAO;

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClientTestGoods ct = (ClientTestGoods)ctx.getBean("clientTestGoods");
		/*GoodsModel goodsModel = ct.goodsDAO.getByUuid(3);
		System.out.println(goodsModel);*/

		GoodsQueryModel goodsQueryModel = new GoodsQueryModel();
		goodsQueryModel.getPage().setPageShow(100);

		List<GoodsModel> list = ct.goodsDAO.getByConditionPage(goodsQueryModel);
		System.out.println(list);

	}
}
