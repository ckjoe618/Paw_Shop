package com.topics.order.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	// 查詢全部
	@GetMapping("/order/allorders")
	public List<OrderBean> getAllOrders() {
		return orderService.findAllOrders();
	}

	// 新增
	@PostMapping("/order/add")
	public OrderBean insertOrder(@RequestBody OrderBean orderBean) {
		String memberidrule = "^[0-9]{1,3}$";
		String totalpricerule = "^[0-9]+$";
		String trackingnumrule = "^[0-9]{10}$";

		String memberid = String.valueOf(orderBean.getMemberId());
		String totalprice = String.valueOf(orderBean.getPriceTotal());
		String trackingNum = orderBean.getTrackingNum();

		if (memberid.matches(memberidrule) && totalprice.matches(totalpricerule) &&
				(trackingNum != "") ? trackingNum.matches(trackingnumrule) : true) {

			orderBean.setTransactionTime(LocalDateTime.now().withNano(0));
			orderBean.setUpdateTime(LocalDateTime.now().withNano(0));

			return orderService.insertOrder(orderBean);
		} else {
			return null;
		}
	}

	// 刪除
	@GetMapping("/order/delete")
	public void deleteOrder(@RequestParam Integer orderId) {
		if (orderId != null) {
			orderService.deleteOrderByOrderId(orderId);
		}
	}

	// 修改
	@GetMapping("/order/queryone")
	public OrderBean findOrderByOrderId(Integer orderId) {
		if (orderId != null) {
			return orderService.findOrderByOrderId(orderId);
		}
		return null;
	}

	@PostMapping("/order/update")
	public OrderBean updateOrderByOrderId(@RequestBody OrderBean orderBean) {
		String memberidrule = "^[0-9]{1,3}$";
		String totalpricerule = "^[0-9]+$";
		String trackingnumrule = "^[0-9]{10}$";

		String memberid = String.valueOf(orderBean.getMemberId());
		String totalprice = String.valueOf(orderBean.getPriceTotal());
		String trackingNum = orderBean.getTrackingNum();

		if (memberid.matches(memberidrule) && totalprice.matches(totalpricerule) &&
				(trackingNum != "") ? trackingNum.matches(trackingnumrule) : true) {
			orderBean.setUpdateTime(LocalDateTime.now().withNano(0));
			return orderService.updateOrderByOrderId(orderBean);
		} else {
			return null;
		}
	}

}
