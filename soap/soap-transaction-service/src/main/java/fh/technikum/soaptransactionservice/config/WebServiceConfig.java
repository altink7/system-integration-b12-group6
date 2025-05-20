package fh.technikum.soaptransactionservice.config;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> dispatcherServlet(ApplicationContext ctx) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "transactions")
    public DefaultWsdl11Definition defaultWsdl(XsdSchema schema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("TransactionPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://fhtechnikum-wien.at/transactions");
        wsdl.setSchema(schema);
        return wsdl;
    }

    @Bean
    public DispatcherServletPath dispatcherServletPath() {
        return () -> "/";
    }

    @Bean
    public XsdSchema transactionSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/transactions.xsd"));
    }
}
