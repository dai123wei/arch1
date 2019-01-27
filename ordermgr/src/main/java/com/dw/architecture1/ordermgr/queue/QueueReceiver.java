package com.dw.architecture1.ordermgr.queue;

import com.dw.architecture1.cartmgr.service.ICartService;
import com.dw.architecture1.cartmgr.vo.CartModel;
import com.dw.architecture1.cartmgr.vo.CartQueryModel;
import com.dw.architecture1.ordermgr.dao.OrderDAO;
import com.dw.architecture1.ordermgr.service.IOrderDetailService;
import com.dw.architecture1.ordermgr.service.IOrderService;
import com.dw.architecture1.ordermgr.vo.OrderDetailModel;
import com.dw.architecture1.ordermgr.vo.OrderModel;
import com.dw.architecture1.ordermgr.vo.OrderQueryModel;
import com.dw.architecture1.storemgr.service.IStoreService;
import com.dw.architecture1.storemgr.vo.StoreModel;
import com.dw.common.pageutil.Page;
import com.dw.common.util.format.DateFormatHelper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.jms.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Service
public class QueueReceiver implements ServletContextListener{
    @Autowired
    private IOrderService orderService = null;
    @Autowired
    private ICartService cartService = null;
    @Autowired
    private IOrderDetailService orderDetailService = null;
    @Autowired
    private IStoreService storeService = null;
    public void acceptMsg() {
        Connection connection = null;
        final Session session;
        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
                    "tcp://192.168.0.30:61616");
            connection = cf.createConnection();
            connection.start();

            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("order-queue");
            MessageConsumer consumer = session.createConsumer(destination);

            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        int customerId = Integer.parseInt(textMessage.getText());
                        session.commit();
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
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        QueueReceiver queueReceiver = (QueueReceiver)ctx.getBean("queueReceiver");
        queueReceiver.acceptMsg();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

