package com.topics.order.controller.front;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EcpayEmapController {
	
	
	@PostMapping("/ecpay/cvsMapReply")
	public void handleCvsMapReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("==== 綠界門市回傳進來了 ====");
	    request.getParameterMap().forEach((k, v) -> {
	        System.out.println(k + ": " + Arrays.toString(v));
	    });
	    
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();

	    out.println("<script>");
	    out.println("window.opener.postMessage({");
	    out.println("  storeID: '" + request.getParameter("CVSStoreID") + "',");
	    out.println("  storeName: '" + request.getParameter("CVSStoreName") + "',");
	    out.println("  storeAddress: '" + request.getParameter("CVSAddress") + "'");
	    out.println("}, '*');");
	    out.println("window.close();");
	    out.println("</script>");
	}


}
