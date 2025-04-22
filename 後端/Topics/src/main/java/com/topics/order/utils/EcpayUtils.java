package com.topics.order.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

public class EcpayUtils {
    public static String generateCheckMacValue(Map<String, String> params, String hashKey, String hashIV) {
        Map<String, String> sortedParams = new TreeMap<>(params);

        StringBuilder sb = new StringBuilder();
        sb.append("HashKey=").append(hashKey);
        sortedParams.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
        sb.append("&HashIV=").append(hashIV);

        try {
            String urlEncoded = URLEncoder.encode(sb.toString(), StandardCharsets.UTF_8.toString())
                    .toLowerCase()
                    .replaceAll("%21", "!")
                    .replaceAll("%2a", "*")
                    .replaceAll("%28", "(")
                    .replaceAll("%29", ")")
                    .replaceAll("%20", "+");
            return encrypt(urlEncoded).toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }

    private static String encrypt(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexStr = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexStr.append('0');
            hexStr.append(hex);
        }
        return hexStr.toString();
    }
}
