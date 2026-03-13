package com.example.WebSocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.WebSocket.dto.EcpayReturnDto;
import com.example.WebSocket.entity.Users;
import com.example.WebSocket.entity.WalletLogs;
import com.example.WebSocket.repo.WalletLogsRepo;
import jakarta.transaction.Transactional;

@Service
public class WalletLogsService {

	@Autowired
    private WalletLogsRepo walletLogsRepo;
	@Autowired
    private UsersRepo usersRepo;

    @Transactional
    public void processWalletDeposit(EcpayReturnDto dto) {
        // 1. 取得使用者
        Long userId = Long.parseLong(dto.getCustomField1());
        Users user = usersRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("找不到使用者 ID: " + userId));

        // 2. 建立錢包日誌
        WalletLogs log = new WalletLogs();
        log.setUser(user);
        log.setAmount(Long.parseLong(dto.getTradeAmt()));
        log.setTransactionType((byte) 1); // 假設 1: 儲值, 2: 消費
        log.setMerchantTradeNo(dto.getMerchantTradeNo());
        
        // 如果有相關類型需求可設定，如：儲值來自綠界
        log.setRelatedType((byte) 1); 

        walletLogsRepo.save(log);
    }
}

