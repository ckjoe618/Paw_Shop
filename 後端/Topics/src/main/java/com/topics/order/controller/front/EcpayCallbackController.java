package com.topics.order.controller.front;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.repository.OrderRepository;

@RestController
@RequestMapping("/api/ecpay")
public class EcpayCallbackController {

    @Autowired
    private OrderRepository orderRepository;

    // 綠界會在付款完成後呼叫這個（必須是 POST）
    @PostMapping("/return")
    public String handlePaymentResult(@RequestParam Map<String, String> ecpayParams) {
        String merchantTradeNo = ecpayParams.get("MerchantTradeNo"); // TSxxxxxxxxxx
        String rtnCode = ecpayParams.get("RtnCode"); // 綠界付款狀態碼
        String tradeNo = ecpayParams.get("TradeNo"); // 綠界的交易編號
        String orderIdStr = ecpayParams.get("CustomField1");
        Integer orderId = Integer.parseInt(orderIdStr);

        System.out.println("🔔 綠界回傳付款結果：" + ecpayParams);

       
        OrderBean order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            if ("1".equals(rtnCode)) {
            	order.setPaymentStatus("已付款");
            	order.setMerchantTradeNo(merchantTradeNo);
                order.setEcpayTradeNo(tradeNo);
                orderRepository.save(order);
                return "1|OK";
            } else {
                order.setPaymentStatus("付款失敗");
                orderRepository.save(order);
                return "0|Failed";
            }
        }

        return "0|OrderNotFound";
    }
}
