package com.example.WebSocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebSocket.entity.Orders;
import com.example.WebSocket.repo.OrdersRepo;
import com.example.WebSocket.util.EcpayUtil;

@RestController
@RequestMapping("/ecpay")
public class EcpayController {
	
	@Autowired
	private OrdersRepo ordersRepo;
	
	private Long userId= 1L;

    private final String MERCHANT_ID = "3002607";
    private final String HASH_KEY = "pwFHCqoQZGmho4w6";
    private final String HASH_IV = "EkRm7iFT261dpevs";
    private final String ECPAY_URL
    =
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
    public String returnUrl(@RequestParam Map<String, String> data) throws Exception {
    	boolean isValid = EcpayUtil.verify(data, HASH_KEY, HASH_IV);
        
        if (!isValid) {
            System.out.println("警告：檢查碼驗證失敗！此請求可能是偽造的。");
            return "0|CheckMacValueVerifyFail"; 
        }

        String RtnCode = data.get("RtnCode");   // 1 = 成功
        String RtnMsg = data.get("RtnMsg"); // 交易成功
        String MerchantTradeNo = data.get("MerchantTradeNo"); //綠界單號（一筆booking 一筆單號）merchant_trade_no
        String PaymentDate = data.get("PaymentDate"); //付款成功時間created_at
        String TradeAmt = data.get("TradeAmt");//支付總金額total_amount

        System.out.println("訂單id: "+ MerchantTradeNo);
        System.out.println("回傳狀態: " + RtnCode + " - " + RtnMsg);
        System.out.println("交易時間: "+ PaymentDate);
        System.out.println("訂單金額: "+ TradeAmt);

        if ("1".equals(RtnCode)) {
        	// 1. 建立實體物件
            Orders order = new Orders();

            // 2. 填入資料 (注意：這裡的 set 方法名稱需對應你的 Entity 變數名)
            order.setMerchantTradeNo(MerchantTradeNo);
            
            order.setUserId(userId);
            
            // 將金額字串轉為 Integer
            if (TradeAmt != null) {
                order.setTotalAmount(Integer.parseInt(TradeAmt));
            }

            // 3. 處理時間 (如果想用綠界給的時間)
            // 註：綠界格式 yyyy/MM/dd HH:mm:ss
            if (PaymentDate != null) {
                java.time.format.DateTimeFormatter formatter = 
                    java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                order.setCreatedAt(java.time.LocalDateTime.parse(PaymentDate, formatter));
            }

            // 4. 執行存檔
            ordersRepo.save(order);
            
            System.out.println("訂單儲存成功！編號：" + MerchantTradeNo);
        } else {
            System.out.println("打回原形...");
        }

        // ⚠️ 綠界規定一定要回
        return "1|OK";
    }
}
