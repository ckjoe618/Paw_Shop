package com.topics.order.controller.front;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.topics.order.model.bean.OrderBean;
import com.topics.order.model.repository.OrderRepository;
import com.topics.utils.EmailUtil;

@RestController
@RequestMapping("/api/ecpay")
public class EcpayCallbackController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private EmailUtil emailService;

    // 綠界會在付款完成後呼叫這個（必須是 POST）
    @PostMapping("/return")
    public String handlePaymentResult(@RequestParam Map<String, String> ecpayParams) {
        String merchantTradeNo = ecpayParams.get("MerchantTradeNo"); // 綠界訂單編號 TSxxx
        String rtnCode = ecpayParams.get("RtnCode"); // 綠界付款狀態碼
        String tradeNo = ecpayParams.get("TradeNo"); // 綠界的交易編號
        String paymentDate = ecpayParams.get("PaymentDate"); // 綠界回傳的付款完成時間
        String orderIdStr = ecpayParams.get("CustomField1"); // 我自訂的欄位（訂單編號）
        Integer orderId = Integer.parseInt(orderIdStr);

        System.out.println("🔔 綠界回傳付款結果：" + ecpayParams);

       
        OrderBean order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            if ("1".equals(rtnCode)) {
            	order.setPaymentStatus("已付款");
            	order.setMerchantTradeNo(merchantTradeNo);
                order.setEcpayTradeNo(tradeNo);
                orderRepository.save(order);
                
             // 發送 Email
                String to = order.getMember().getEmail();
                String subject = "【 🐾PawShop 】訂單已成立！付款成功通知";
                String body = String.format("""
                        親愛的會員您好，

                        您的訂單（編號：%d）已成立，並成功付款
                        金流交易編號：%s
                        交易時間：%s

                        感謝您支持 我們將盡快為您處理訂單！
                        PawShop 🐾
                        """, orderId, tradeNo, paymentDate);

                emailService.sendOrderConfirmationEmail(to, subject, body);
                
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
