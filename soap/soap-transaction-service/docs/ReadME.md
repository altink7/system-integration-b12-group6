## How to Test

### Start the SOAP Service

1. Run the `SoapTransactionServiceApplication` class to start your SOAP service.
2. Ensure the service is running on `http://localhost:8080/ws/transactions.wsdl`.

### Use `curl` to Send the Request

1. Save the `example-request.xml` file locally.
2. Use the following `curl` command to send the SOAP request:
   ```bash
   curl -X POST -H "Content-Type: text/xml" --data @example-request.xml http://localhost:8080/ws
    ```
3. You should receive a response in XML format.
4. Check the console output of your Spring Boot application for any logs or errors.
5. if using SOAP UI, make sure to set the endpoint URL to `http://localhost:8080/ws/transactions.wsdl` and import the
   WSDL file to test
   the service.
6. If you encounter any issues, check the logs for errors and ensure that the service is running correctly.