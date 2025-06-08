package fh.technikum.restaccountbalance.controller;

import fh.technikum.restaccountbalance.dto.AccountBalanceResponse;
import fh.technikum.restaccountbalance.service.AccountBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/account-balance")
@RequiredArgsConstructor
public class AccountBalanceController {
    private final AccountBalanceService accountBalanceService;

    @GetMapping
    public AccountBalanceResponse getAccountBalance(
            @RequestParam("id") int id,
            @RequestParam(value = "timestamp", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timestamp
    ) {
        return accountBalanceService.calculateBalance(id, timestamp);
    }
}
