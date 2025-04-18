package com.topics.order.controller.front;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.topics.member.model.dto.MemberDto;
import com.topics.member.security.AuthHolder;
import com.topics.order.model.bean.ShoppingcartItemBean;
import com.topics.order.model.service.ShoppingcartItemService;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingcartItemController {
	
	@Autowired
	private ShoppingcartItemService shoppingcartItemService;
	
	//一次新增多筆
	@PostMapping
	public ResponseEntity<String> addShoppingcart(@RequestBody List<ShoppingcartItemBean> shoppingcartItemlist) {
		for (ShoppingcartItemBean item : shoppingcartItemlist) {
			shoppingcartItemService.inserShoppingcart(item);
		}
		return ResponseEntity.ok("加入成功");
	}
	
	
	//刪除
	@DeleteMapping("/{cartItemId}")
	public void deleteShoppingcart(Integer cartItemId) {
		shoppingcartItemService.deleteShoppingcartById(cartItemId);
	}
	
	//修改
	@PutMapping
	public ShoppingcartItemBean updateItemQuantity(@RequestBody Map<String, Object> data) {
		Integer cartItemId = (Integer)data.get("cartItemId");
		Integer quantity = (Integer)data.get("quantity");
		
		return shoppingcartItemService.updateItemQuantity(cartItemId, quantity);
	}
	
	//查詢
	@GetMapping
	public List<ShoppingcartItemBean> getShoppingcart() {
		MemberDto member = AuthHolder.getMember();
	    if (member == null) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登入");
	    }
		Integer memberId = member.getMemberId();
		return shoppingcartItemService.findItemsByMemberId(memberId);
	}
	

}
