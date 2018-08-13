package com.csaa.config;



import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import com.csaa.bo.*;
import com.csaa.processor.RequestProcessor;




@SpringBootApplication
@ComponentScan(basePackages="com.csaa.config")
public class DemoApplication  extends SpringBootServletInitializer  {
	
	@Value("${camel.springboot.name}")
	String contextPath;


	public static void main(String[] args) {
		System.out.println("In the main");
		SpringApplication.run(DemoApplication.class, args);
	}
	
	
	@Bean
	public org.apache.camel.component.kafka.KafkaComponent kafka(){
		org.apache.camel.component.kafka.KafkaComponent kafkaComponent = new org.apache.camel.component.kafka.KafkaComponent();
		kafkaComponent.setBrokers("n01apl4366.tent.trt.csaa.pri:9092,n01apl4414.tent.trt.csaa.pri:9092,N01APL4409.tent.trt.csaa.pri:9092");
		return kafkaComponent;
	}
	
	
	
	
	@Bean
	public com.csaa.processor.RequestProcessor requestProcessor(){
		com.csaa.processor.RequestProcessor requestProcessor = new com.csaa.processor.RequestProcessor();		
		return requestProcessor;
	}
	
	@Bean
	public org.apache.camel.component.cxf.CxfEndpoint cxfEndpoint() {
		org.apache.camel.component.cxf.CxfEndpoint cxfEndpoint=new org.apache.camel.component.cxf.CxfEndpoint() ;		 
        cxfEndpoint.setAddress("http://pit-soaservices.tent.trt.csaa.pri:41000/RetrieveConvertedPolicyInfoV2");
        
        try {
			cxfEndpoint.setServiceClass("aaancnu_wsdl_retrieveconvertedpolicyinfo_version2.com.aaancnuit.RetrieveConvertedPolicyInfoRequest");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return cxfEndpoint;
	
	}
	
	@Bean
	ServletRegistrationBean servletRegistrationBean() {// settING up camel servel
	    ServletRegistrationBean servlet = new ServletRegistrationBean
	      (new CamelHttpTransportServlet(), contextPath+"/*");
	    servlet.setName("CamelServlet");
	    return servlet;
	}
	
	@Bean
	 public  org.apache.camel.component.jackson.JacksonDataFormat  jacksonDataFormat(){
		org.apache.camel.component.jackson.JacksonDataFormat format = new org.apache.camel.component.jackson.JacksonDataFormat(com.csaa.bo.ConvertedPolicyInfoRequest.class);
     format.useList();
     return format;
	}
	
	@Bean 
	  public org.apache.camel.component.gson.GsonDataFormat formatPojo(){
		org.apache.camel.component.gson.GsonDataFormat formatPojo = new org.apache.camel.component.gson.GsonDataFormat(com.csaa.bo.ConvertedPolicyInfoRequest.class);
		return formatPojo;
	}
	  
    @Bean
    public JaxbDataFormat jaxb () {
  	JaxbDataFormat retrieveConvertedPolicy = new JaxbDataFormat();
  	retrieveConvertedPolicy.setContextPath(aaancnu_wsdl_retrieveconvertedpolicyinfo_version2.com.aaancnuit.RetrieveConvertedPolicyInfoRequest.class.getPackage().getName());
  	
  	return retrieveConvertedPolicy;
  }
	
	
	
	 @Component
	    class DemoRestApi extends RouteBuilder {

	        @Override
	        public void configure() {
	            restConfiguration()
	            		.contextPath("/camel-rest-jpa")
	            		.enableCORS(true)
	            		.apiContextPath("/api-doc")
	            		.apiProperty("api.title", "Camel REST API")
	                    .apiProperty("api.version", "1.0")
	                    .apiProperty("cors", "true")
	                    .apiContextRouteId("doc-api")
	                    .bindingMode(RestBindingMode.json);
	            	
	            rest("/say").description("Get hello REST service")
	                 .id("api-route")
	                .get("/hello").description("echo back hello service")
	                .produces(MediaType.APPLICATION_JSON_VALUE)
	                .consumes(MediaType.APPLICATION_JSON_VALUE)
	                .to("direct:hello");
	            
	            from("direct:hello")
	            .tracing()
	            .transform().constant("Hello World")	            
	            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
	            
	        }
	    }
	 
	 
	 @Component
	    class ConvertedPolicyInfo extends RouteBuilder {

	        @Override
	        public void configure() {
	            restConfiguration()
	            		.contextPath("/convertedPolicy")
	            		.enableCORS(true)
	            		.apiContextPath("/api-doc1")
	            		.apiProperty("api.title", "ConvertedPolicy REST API")
	                    .apiProperty("api.version", "1.0")
	                    .apiProperty("cors", "true")
	                    .apiContextRouteId("doc-api")
	                    .bindingMode(RestBindingMode.json);
	            	
	            rest("/policy").description("Get hello REST service")
	                 .id("api-conv")
	                 .post("/converted").description("retrieveConvertedPolicy")
	                .produces(MediaType.APPLICATION_JSON_VALUE)
	                .consumes(MediaType.APPLICATION_JSON_VALUE)
	                .bindingMode(RestBindingMode.auto)
	                .type(com.csaa.bo.ConvertedPolicyInfo.class)
	                .to("direct:forward");
	            
	            from("direct:forward")
	            .tracing()
	            .log(">>> ${body.convertedPolicyInfoRequest.policyInfo.policyNumber}")	            
	            .transform().constant("Hello World1")
	            //.to("spring-ws:http://foo.com/bar")
	            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
	            
	        }
	    }
	 
	 
	 @Component
	    class PushMessagesToKafka extends RouteBuilder {

	        @Override
	        public void configure() {

	                
	            restConfiguration()
	            		.contextPath("/kafka")
	            		.enableCORS(true)
	            		.apiContextPath("/api-doc2")
	            		.apiProperty("api.title", "REST API to Pus message to kafka")
	                    .apiProperty("api.version", "1.0")
	                    .apiProperty("cors", "true")
	                    .apiContextRouteId("doc-api")
	                    .bindingMode(RestBindingMode.json);
	            	
	            rest("/kafka").description("POST Kafka REST service")
	                 .id("api-Kafka")
	                 .post("/messge").description("Pushing messages to Kafka")
	                .to("direct:Kafka");

	            from("direct:Kafka")
	            .to("kafka:soatest")
	            .routeId("ToKafka")
	            .log(">>>Putting the message---> ${body}")
	            .transform().constant("Hello World2")
	            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
	            
	        }
	    }
	 
	 @Component
	    class ReadMessagesFromKafka extends RouteBuilder {

	        @Override
	        public void configure() {
	        	
                from("kafka:soatest?brokers=n01apl4366.tent.trt.csaa.pri:9092,n01apl4414.tent.trt.csaa.pri:9092,N01APL4409.tent.trt.csaa.pri:9092"
                        + "&maxPollRecords=10"
                        + "&consumersCount=1"
                        + "&groupId=SOAGroup")
                        .routeId("FromKafka")
                    .log("<<<<Reading the message ${body}");
	        	
              //  + "&seekTo=beginning"

	        	
	        }
	 }
	 
	 
	 
	 @Component
	    class InvoceWSConvertedPolicyInfo extends RouteBuilder {

	        @Override
	        public void configure() {
	        	//CamelContext context = new DefaultCamelContext();
	            restConfiguration()
	            		.contextPath("/convertedPolicySOAP")
	            		.enableCORS(true)
	            		.apiContextPath("/api-doc1")
	            		.apiProperty("api.title", "ConvertedPolicy REST API")
	                    .apiProperty("api.version", "1.0")
	                    .apiProperty("cors", "true")
	                    .apiContextRouteId("doc-api")
	                    .bindingMode(RestBindingMode.json);
	            	
	            rest("/policy").description("Get hello REST service")
	                 .id("api-conv")
	                 .post("/convertedSOAP").description("retrieveConvertedPolicy")
	                .produces(MediaType.APPLICATION_JSON_VALUE)
	                .consumes(MediaType.APPLICATION_JSON_VALUE)
	                .bindingMode(RestBindingMode.json)
	                .type(com.csaa.bo.ConvertedPolicyInfo.class)//of no use, nothing happening with this statement	                
	                .to("direct:forwardSOAP");
	            
	            from("direct:forwardSOAP")
	            .log(">>>0 ${body.convertedPolicyInfoRequest.policyInfo.policyNumber}")	
	            .bean(new com.csaa.processor.RequestProcessor(), "transformToConveredPolicySOAPServiceRequest")
	            .log(">>>1 ${body}")
	            .marshal(jaxb())	          
	            .log(">>>2 ${body}")
	            .to("spring-ws:http://pit-soaservices.tent.trt.csaa.pri:41000/RetrieveConvertedPolicyInfoV2?soapAction=http://www.aaancnuit.com.retrieveConvertedPolicyInfo")
	            .tracing()
	            .log(">>>3 ${body}");
	           //setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
	            
	        }
	    }
	 
	 

}
