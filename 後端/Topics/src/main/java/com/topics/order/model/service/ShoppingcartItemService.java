package com.topics.order.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topics.order.model.bean.ShoppingcartItemBean;
import com.topics.order.model.repository.ShoppingcartItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ShoppingcartItemService {
	
	@Autowired
	private ShoppingcartItemRepository shoppingcartItemRepository;

	
	//新增
	@Transactional
	public ShoppingcartItemBean inserShoppingcart(ShoppingcartItemBean insertBean) {
		insertBean.setCreatedTime(LocalDateTime.now().withNano(0));
		return shoppingcartItemRepository.save(insertBean);
	}
	
	//刪除
	@Transactional
	public void deleteShoppingcartById(Integer cartItemId) {
		shoppingcartItemRepository.deleteById(cartItemId);
	}
	
	//修改
	@Transactional
	public ShoppingcartItemBean updateItemQuantity(Integer cartItemId, Integer quantity) {
		Optional<ShoppingcartItemBean> op = shoppingcartItemRepository.findById(cartItemId);
		if (op.isPresent()) {
			ShoppingcartItemBean shoppingcartItemBean = op.get();
			shoppingcartItemBean.setQuantity(quantity);
			return shoppingcartItemRepository.save(shoppingcartItemBean);
		}
		return null;
	}
	
	
	//查詢
	public List<ShoppingcartItemBean> findItemsByMemberId(Integer memberId){
		return shoppingcartItemRepository.findByMember_MemberId(memberId);
	}
	

}
