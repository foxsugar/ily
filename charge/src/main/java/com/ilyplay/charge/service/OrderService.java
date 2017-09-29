package com.ilyplay.charge.service;

import com.ilyplay.charge.dao.IOrderDao;
import com.ilyplay.charge.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sunxianping on 2017/9/28.
 */
@Service
public class OrderService {

    @Autowired
    private IOrderDao orderDao;

    public void save(Order order){
        orderDao.save(order);
    }
}
