package fh.technikum.restaccountbalance.dto;

import java.math.BigDecimal;

public record AccountBalanceResponse(int id, String name, BigDecimal balance) {
}