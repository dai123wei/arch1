package com.dw.architecture1;

import com.dw.architecture1.cartmgr.service.ICartService;
import com.dw.architecture1.cartmgr.vo.CartModel;
import com.dw.architecture1.cartmgr.vo.CartQueryModel;
import com.dw.architecture1.goodsmgr.service.IGoodsService;
import com.dw.architecture1.goodsmgr.vo.GoodsModel;
import com.dw.architecture1.goodsmgr.vo.GoodsQueryModel;
import com.dw.architecture1.ordermgr.service.IOrderDetailService;
import com.dw.architecture1.ordermgr.service.IOrderService;
import com.dw.architecture1.ordermgr.vo.OrderDetailModel;
import com.dw.architecture1.ordermgr.vo.OrderModel;
import com.dw.architecture1.ordermgr.vo.OrderQueryModel;
import com.dw.architecture1.storemgr.service.IStoreService;
import com.dw.architecture1.storemgr.vo.StoreModel;
import com.dw.common.pageutil.Page;
import com.dw.common.util.format.DateFormatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private IGoodsService goodsService = null;

    @Autowired
    private ICartService cartService = null;

    @Autowired
    private IOrderService orderService = null;

    @Autowired
    private IOrderDetailService orderDetailService = null;

    @Autowired
    private IStoreService storeService = null;

    @RequestMapping(value = "/toIndex", method = RequestMethod.GET)
    public String toIndex(Model model){
        GoodsQueryModel gqm = new GoodsQueryModel();
        gqm.getPage().setPageShow(100);
        Page<GoodsModel> page = goodsService.getByConditionPage(gqm);

        model.addAttribute("page", page);

        return "index";
    }

    @RequestMapping(value = "/toGoodsDesc/{goodsUuid}", method = RequestMethod.GET)
    public String toGoodDesc(Model model, @PathVariable("goodsUuid") int goodsUuid){
        GoodsModel gm = goodsService.getByUuid(goodsUuid);
        model.addAttribute("m", gm);

        return "goods/desc";
    }

    @RequestMapping(value = "/addToCart/{goodsUuid}", method = RequestMethod.GET)
    public String addToCart(Model model, @PathVariable("goodsUuid") int goodsUuid, @CookieValue("MyLogin") String myLogin){
        int customerId = Integer.parseInt(myLogin.split(",")[0]);

        CartModel cm = new CartModel();
        cm.setBuyNum(1);
        cm.setCustomerUuid(customerId);
        cm.setGoodsUuid(goodsUuid);

        cartService.create(cm);
        CartQueryModel cqm = new CartQueryModel();
        cqm.getPage().setPageShow(1000);
        cqm.setCustomerUuid(customerId);

        Page<CartModel> page = cartService.getByConditionPage(cqm);
        model.addAttribute("page", page);

        return "cart/myCart";
    }

    @RequestMapping(value = "/toCart", method = RequestMethod.GET)
    public String toCart(Model model, @CookieValue("MyLogin") String myLogin){
        int customerId = Integer.parseInt(myLogin.split(",")[0]);

        CartQueryModel cqm = new CartQueryModel();
        cqm.getPage().setPageShow(1000);
        cqm.setCustomerUuid(customerId);

        Page<CartModel> page = cartService.getByConditionPage(cqm);

        model.addAttribute("page", page);
        return "cart/myCart";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(@CookieValue("MyLogin") String myLogin){
        //1:查出这个人购物车所有的信息
        int customerId = Integer.parseInt(myLogin.split(",")[0]);
        //orderService.order(customerId);
        CartQueryModel cqm = new CartQueryModel();
        cqm.getPage().setPageShow(1000);
        cqm.setCustomerUuid(customerId);

        Page<CartModel> page = cartService.getByConditionPage(cqm);

        //2
        float totalMoney = 0.0f;
        for(CartModel cm : page.getResult()){
            totalMoney += 10;
        }
        OrderModel om = new OrderModel();
        om.setCustomerUuid(customerId);
        om.setOrderTime(DateFormatHelper.long2str(System.currentTimeMillis()));
        om.setSaveMoney(0.0F);
        om.setTotalMoney(totalMoney);
        om.setState(1);

        orderService.create(om);

        OrderQueryModel oqm = new OrderQueryModel();
        oqm.setOrderTime(om.getOrderTime());

        Page<OrderModel> omPage = orderService.getByConditionPage(oqm);
        om = omPage.getResult().get(0);

        //3
        for (CartModel cm : page.getResult()){
            OrderDetailModel odm = new OrderDetailModel();
            odm.setGoodsUuid(cm.getGoodsUuid());
            odm.setOrderNum(cm.getBuyNum());
            odm.setPrice(10.0F);
            odm.setMoney(odm.getPrice() * odm.getOrderNum());
            odm.setSaveMoney(0.0F);
            odm.setOrderUuid(om.getUuid());

            orderDetailService.create(odm);

            //4
            StoreModel sm = storeService.getByGoodsUuid(cm.getGoodsUuid());
            sm.setStoreNum(sm.getStoreNum() - odm.getOrderNum());
            storeService.update(sm);

            //5
            cartService.delete(cm.getUuid());
        }

        return "success";
    }
}
