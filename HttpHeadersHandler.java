package com.store.customer.handler;

import com.store.customer.DTO.AddressDTO;
import com.store.customer.DTO.CustomerDTO;
import com.store.customer.DTO.OrderDTO;
import com.store.customer.DTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class HttpHeadersHandler {

    private final RestTemplate restTemplate;

    @Autowired
    public HttpHeadersHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendCustomersToLocalhost(List<CustomerDTO> customerDTOList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<CustomerDTO>> requestEntity = new HttpEntity<>(customerDTOList, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/customers",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response from localhost: " + responseEntity.getBody());
    }

    public void sendAddressesToLocalhost(List<AddressDTO> addressDTOList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<AddressDTO>> requestEntity = new HttpEntity<>(addressDTOList, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/addresses",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response from localhost: " + responseEntity.getBody());
    }

    public void sendOrdersToLocalhost(List<OrderDTO> orderDTOList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<OrderDTO>> requestEntity = new HttpEntity<>(orderDTOList, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/orders",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response from localhost: " + responseEntity.getBody());
    }

    public void sendProductsToLocalhost(List<ProductDTO> productDTOList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<ProductDTO>> requestEntity = new HttpEntity<>(productDTOList, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/products",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Response from localhost: " + responseEntity.getBody());
    }

    public void getAllProductsFromLocalhost() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/products",
                HttpMethod.GET,
                null,
                String.class
        );

        System.out.println("Response from localhost (GET all products): " + responseEntity.getBody());
    }
}