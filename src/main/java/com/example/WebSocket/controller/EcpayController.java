package com.example.WebSocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebSocket.util.EcpayUtil;

@RestController
@RequestMapping("/ecpay")
public class EcpayController {

    private final String MERCHANT_ID = "3002607";
    private final String HASH_KEY = "pwFHCqoQZGmho4w6";
    private final String HASH_IV = "EkRm7iFT261dpevs";
    private final String ECPAY_URL =
            "https://payment-stage.ecpay.com.tw/Cashier/AioCheckOut/V5";

    // =========================
    // 付款入口
    // =========================
    @GetMapping("/pay")
    public String pay() throws Exception {

        Map<String, String> params = new LinkedHashMap<>();

        //商店代號
        params.put("MerchantID", MERCHANT_ID);
        //訂單編號
        params.put("MerchantTradeNo", "TEST" + System.currentTimeMillis());
        //交易時間
        params.put("MerchantTradeDate",
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        //付款型態(規定aio)
        params.put("PaymentType", "aio");
        //金額
        params.put("TotalAmount", "200");
        //交易描述
        params.put("TradeDesc", "測試交易");
        //商品名稱
        params.put("ItemName", "測試商品");
        //背景通知 URL
        params.put("ReturnURL",
                "https://subjugable-uncreditably-ignacia.ngrok-free.dev/ecpay/return");
        //前端返回 URL
        params.put("ClientBackURL",
                "https://subjugable-uncreditably-ignacia.ngrok-free.dev/");
        //付款方式
        params.put("ChoosePayment", "ALL");
        //加密方式
        params.put("EncryptType", "1");

        // 最後才算 CheckMacValue（⚠️ 一定要最後）
        String checkMacValue = EcpayUtil.generate(params, HASH_KEY, HASH_IV);
        params.put("CheckMacValue", checkMacValue);

        // 回傳自動送出的 HTML
        return buildAutoSubmitForm(ECPAY_URL, params);
    }

    // =========================
    // 組綠界付款表單
    // =========================
    private String buildAutoSubmitForm(String action, Map<String, String> params) {

        StringBuilder sb = new StringBuilder();
        sb.append("<form id='ecpay' method='post' action='")
          .append(action)
          .append("'>");

        params.forEach((k, v) ->
            sb.append("<input type='hidden' name='")
              .append(k)
              .append("' value='")
              .append(v)
              .append("'/>")
        );

        sb.append("</form>");
        sb.append("<script>document.getElementById('ecpay').submit();</script>");

        return sb.toString();
    }

    // =========================
    // 綠界背景通知
    // =========================
    @PostMapping("/return")
    public String returnUrl(@RequestParam Map<String, String> data) {

        String rtnCode = data.get("RtnCode");   // 1 = 成功
        String msg = data.get("RtnMsg"); // 交易結果
        String orderNo = data.get("MerchantTradeNo"); //訂單編號
        String PaymentDate = data.get("PaymentDate");
        String CheckMacValue = data.get("CheckMacValue");
        String TradeAmt = data.get("TradeAmt");
        String PaymentType = data.get("PaymentType");

        System.out.println(
            "訂單 " + orderNo + " 回傳狀態: " + rtnCode + " - " + msg
        );
        System.out.println("商店id: "+ MERCHANT_ID);
        System.out.println("交易時間: "+ PaymentDate);
        System.out.println("訂單金額: "+ TradeAmt);
        System.out.println("檢查碼: "+ CheckMacValue);
        System.out.println("付款方式: "+ PaymentType);

        if ("1".equals(rtnCode)) {
            System.out.println("付款成功處理...");
        } else {
            System.out.println("付款失敗處理...");
        }

        // ⚠️ 綠界規定一定要回
        return "1|OK";
    }
}
