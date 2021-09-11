//package com.yzm.oauth.controller;
//
//import com.yzm.oauth.config.OAuth2ServerConfig2;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@Slf4j
//@RestController
//@RequestMapping("/qq")
//public class QQController {
//
//    private final RestTemplate restTemplate;
//
//    public QQController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @GetMapping(OAuth2ServerConfig2.CODE_REDIRECT)
//    public String getToken(@RequestParam String code) {
//        log.info("receive code {}", code);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", OAuth2ServerConfig2.AUTH_CODE);
//        params.add("client_secret", OAuth2ServerConfig2.PASSWORD);
//        params.add("grant_type", "authorization_code");
//        params.add("code", code);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
//        String token = response.getBody();
//        log.info("token => {}", token);
//        return token;
//    }
//
//    @GetMapping(OAuth2ServerConfig2.CODE_REDIRECT2)
//    public String getToken2(@RequestParam String code) {
//        log.info("receive code {}", code);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", OAuth2ServerConfig2.AUTH_CODE2);
//        params.add("client_secret", OAuth2ServerConfig2.PASSWORD);
//        params.add("grant_type", "authorization_code");
//        params.add("code", code);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
//        String token = response.getBody();
//        log.info("token => {}", token);
//        return token;
//    }
//
//    @GetMapping("/info")
//    public String info() {
//        return "info";
//    }
//
//    @GetMapping("/info2")
//    public String info2() {
//        return "info2";
//    }
//
//    @GetMapping(OAuth2ServerConfig2.EASY_REDIRECT)
//    public String easy() {
//        return "easy";
//    }
//
//}
