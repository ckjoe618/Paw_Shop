package com.topics.order.controller.back;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.topics.order.model.bean.OrderDetailBean;
import com.topics.order.model.service.back.AdminOrderDetailService;

@RestController
@RequestMapping("/api/admin/orderdetail")
public class AdminOrderDetailController {

	@Autowired
	private AdminOrderDetailService orderDetailService;

	// 新增
	@PostMapping
	public OrderDetailBean insertOrderDetail(@RequestBody OrderDetailBean orderDetailBean) {
		return orderDetailService.insertOrderDetail(orderDetailBean);
	}

	// 刪除
	@DeleteMapping("/{orderDetailId}")
	public void deleteOrderDetail(@PathVariable Integer orderDetailId) {
		if (orderDetailId != null) {
			orderDetailService.deleteByOrderDetailId(orderDetailId);
		}
	}

	// 查詢
	@GetMapping
	public List<OrderDetailBean> getOrderDetails(@RequestParam Integer orderId) {
		return orderDetailService.findByOrderId(orderId);
	}
	

}
