package com.topics.order.controller.front;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.topics.order.model.dto.OrderRequest;
import com.topics.order.utils.EcpayUtils;

@RestController
@RequestMapping("/api/ecpay")
public class EcpayPaymentController {

    @PostMapping("/checkout")
    public String checkout(@RequestBody OrderRequest order) {
        Map<String, String> params = new HashMap<>();
        params.put("MerchantID", "3002607");
        params.put("MerchantTradeNo", "TS" + System.currentTimeMillis());
        params.put("MerchantTradeDate", new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        params.put("PaymentType", "aio");
        params.put("TotalAmount", String.valueOf((int)order.getAmount()));
        params.put("TradeDesc", "PawShop付款");
        params.put("ItemName", order.getItemName());
        params.put("ReturnURL", "https://6cc5-111-249-4-158.ngrok-free.app/api/ecpay/return");
        params.put("ChoosePayment", "Credit");
        //params.put("OrderResultURL", "http://localhost:5173/orderResult");
        params.put("ClientBackURL", "http://localhost:5173/orderResult");
        params.put("CustomField1", order.getOrderId().toString());


        String checkMacValue = EcpayUtils.generateCheckMacValue(params, "pwFHCqoQZGmho4w6", "EkRm7iFT261dpevs");
        params.put("CheckMacValue", checkMacValue);

        StringBuilder sb = new StringBuilder();
        sb.append("<form id='ecpayForm' method='post' action='https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5'>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(String.format("<input type='hidden' name='%s' value='%s'/>", entry.getKey(), entry.getValue()));
        }
        sb.append("<script>document.getElementById('ecpayForm').submit();</script>");
        sb.append("</form>");

        return sb.toString();
    }
}

