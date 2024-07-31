package io.microservices.demo.Product.Integration.service;

import io.microservices.demo.Product.Integration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommonService {


    @Autowired
    RestTemplate restTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

    public User findUserById(Long userId) {

        LOGGER.info("inside findUserById id : {} ",userId);

        ResponseEntity<User> response =
                restTemplate.exchange(
                        "http://user-service/user/" + userId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});
        return response.getBody();
    }
}
