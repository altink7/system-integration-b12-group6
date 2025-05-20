package fh.technikum.soaptransactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreTransactionRequestDto {

    private Integer id;
    private String name;
    private String timestamp;
    private BigDecimal amount;
}