package fh.technikum.soaptransactionservice.controller;

import fh.technikum.soaptransactionservice.dto.StoreTransactionRequestDto;
import fh.technikum.soaptransactionservice.dto.StoreTransactionResponseDto;
import fh.technikum.soaptransactionservice.service.TransactionService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.Instant;

@Endpoint
public class TransactionEndpoint {

    private static final String NAMESPACE_URI = "http://fhtechnikum-wien.at/transactions";

    private final TransactionService transactionService;

    public TransactionEndpoint(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StoreTransactionRequest")
    @ResponsePayload
    public StoreTransactionResponseDto storeTransaction(@RequestPayload StoreTransactionRequestDto request) {
        transactionService.saveTransaction(request.getId(), request.getName(), Instant.parse(request.getTimestamp()), request.getAmount());
        StoreTransactionResponseDto response = new StoreTransactionResponseDto();
        response.setStatus("SAVED");
        return response;
    }
}