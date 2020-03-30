package com.xuecheng.order.dao.repository;

import com.xuecheng.framework.domain.order.XcOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XcOrderRepository extends JpaRepository<XcOrders, String> {
}
