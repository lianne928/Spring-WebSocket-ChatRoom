package com.example.WebSocket.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.stream.Collectors;

public class EcpayUtil {

    public static String generate(Map<String, String> params,
                                  String hashKey, String hashIV) throws Exception {

        String query = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        String raw = "HashKey=" + hashKey + "&" + query + "&HashIV=" + hashIV;
        raw = URLEncoder.encode(raw, StandardCharsets.UTF_8)
                .toLowerCase()
                .replace("%21", "!")
                .replace("%2a", "*")
                .replace("%28", "(")
                .replace("%29", ")");

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest).toUpperCase();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}