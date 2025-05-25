### How to use the application ( Test the application )

1. **Install Docker if not already installed**:
    - Ensure Docker is installed and running on your machine.
2. **Run docker-compose.yaml for the database**:
    - Navigate to the directory containing the `docker-compose.yaml` file.
    - Run the command: `docker-compose up -d`.
    - This will start a PostgreSQL database container. ( so you do not need to setup a database manually )

```bash
cd ../docker
docker compose up -d
```

3. **Start the SOAP Service**:
    - Run the `SoapTransactionServiceApplication` class to start your SOAP service.
    - Ensure the service is running by checking: `http://localhost:8080/ws/transactions.wsdl`.
4. **Start the Rest Account Balance Service**:
    - Run the `RestAccountBalanceApplication` class to start your REST service.
    - Ensure the service is running by checking: ` http://localhost:8100/swagger-ui.html`.
5. **Test the SOAP Service**:
    - install SOAP UI
    - Open SOAP UI and create a new SOAP project.
    - Set the WSDL URL to `http://localhost:8080/ws/transactions.wsdl`.
    - open transactions/TransactionPortSoap11/StoreTransaction/Request1
    - to test the SOAP Service, you can send some Transaction to the SOAP Service. like this:
    ```xml
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tran="http://fhtechnikum-wien.at/transactions">
    <soapenv:Header/>
    <soapenv:Body>
        <tran:StoreTransactionRequest xmlns:tran="http://fhtechnikum-wien.at/transactions">
            <id>1</id>
            <name>max</name>
            <amount>-100.50</amount>
        </tran:StoreTransactionRequest>
    </soapenv:Body>
   </soapenv:Envelope>
   ```

- You should receive a response with the stored transaction details. like this:

```xml

<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns3:StoreTransactionResponse xmlns:ns3="http://fhtechnikum-wien.at/transactions">
            <status>SAVED</status>
        </ns3:StoreTransactionResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

6. **Test the REST Service**:
    - Open the web browser and navigate to `http://localhost:8100/swagger-ui.html`.
    - to test the Account balance, just set the id inside parameters and execute the request. like
      this: 'http://localhost:8100/api/v1/account-balance?id=1'
    - You should receive a response with the account balance details. like this:

```json
{
  "id": 1,
  "name": "max",
  "balance": -100.50
}
```

7. **Stop the Services**:
    - To stop the services, you can run `docker-compose down` in the directory containing the `docker-compose.yaml`
      file.
    - Stop the SOAP and REST applications from your IDE or command line.
   