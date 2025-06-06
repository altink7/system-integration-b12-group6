package fh.technikum.soaptransactionservice.controller;

import fh.technikum.soaptransactionservice.generated.StoreTransactionRequest;
import fh.technikum.soaptransactionservice.generated.StoreTransactionResponse;
import fh.technikum.soaptransactionservice.service.TransactionService;
import jakarta.xml.bind.JAXBElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Endpoint
public class TransactionEndpoint {
    private static final String NAMESPACE_URI = "http://fhtechnikum-wien.at/transactions";

    private final TransactionService transactionService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "StoreTransactionRequest")
    @ResponsePayload
    public JAXBElement<StoreTransactionResponse> storeTransaction(@RequestPayload JAXBElement<StoreTransactionRequest> request) {
        transactionService.saveTransaction(
                request.getValue().getId(),
                request.getValue().getName(),
                getInstant(request),
                request.getValue().getAmount()
        );
        StoreTransactionResponse response = new StoreTransactionResponse();
        response.setStatus("SAVED");
        return new JAXBElement<>(
                new javax.xml.namespace.QName(NAMESPACE_URI, "StoreTransactionResponse"),
                StoreTransactionResponse.class,
                response
        );
    }

    private Instant getInstant(JAXBElement<StoreTransactionRequest> request) {
        XMLGregorianCalendar xmlGregorianCalendar = request.getValue().getTimestamp();
        if (xmlGregorianCalendar == null) {
            return Instant.now();
        }

        return xmlGregorianCalendar.toGregorianCalendar().toInstant();
    }
}