package com.topics.order.controller.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	// 查詢全部
	@GetMapping("/order/allactiveorders")
	public List<OrderBean> getAllActiveOrders() {
		return orderService.findAllActiveOrders();
	}
	
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
	@DeleteMapping("/order/delete/{orderId}")
	public void deleteOrder(@PathVariable Integer orderId) {
		if (orderId != null) {
			orderService.deleteOrderByOrderId(orderId);
		}
	}

	// 修改
	@GetMapping("/order/queryone/{orderId}")
	public OrderBean findOrderByOrderId(@PathVariable Integer orderId) {
		if (orderId != null) {
			return orderService.findOrderByOrderId(orderId);
		}
		return null;
	}

	@PutMapping("/order/update/{orderId}")
	public OrderBean updateOrderByOrderId(@PathVariable Integer orderId, @RequestBody OrderBean orderBean) {
		String memberidrule = "^[0-9]{1,3}$";
		String totalpricerule = "^[0-9]+$";
		String trackingnumrule = "^[0-9]{10}$";

		String memberid = String.valueOf(orderBean.getMemberId());
		String totalprice = String.valueOf(orderBean.getPriceTotal());
		String trackingNum = orderBean.getTrackingNum();

		if (memberid.matches(memberidrule) && totalprice.matches(totalpricerule) &&
				(trackingNum != "") ? trackingNum.matches(trackingnumrule) : true) {
			orderBean.setOrderId(orderId);
			orderBean.setUpdateTime(LocalDateTime.now().withNano(0));
			return orderService.updateOrderByOrderId(orderBean);
		} else {
			return null;
		}
	}
	

	//總金額變動
	@PutMapping("/order/updatePriceTotal")
	public ResponseEntity<String> resetOrderPriceTotal(@RequestBody Map<String, Object> data) {
		Integer orderId = (Integer) data.get("orderId");
		Integer priceTotal = (Integer) data.get("priceTotal");
		
		OrderBean selectedOrder = orderService.findOrderByOrderId(orderId);
		if (selectedOrder != null) {
			selectedOrder.setPriceTotal(priceTotal);
			orderService.updateOrderByOrderId(selectedOrder);
			return ResponseEntity.ok("ok");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("找不到訂單");
		
		
		
	}

}
