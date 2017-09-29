package com.ilyplay.charge.dao;

import com.ilyplay.charge.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by sunxianping on 2017/9/28.
 */

public interface IOrderDao extends PagingAndSortingRepository<Order, Long> {

}
