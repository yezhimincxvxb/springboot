package com.yzm.utils.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("restTemp")
public class RestTempController {

    private static final String baseUrl = "http://localhost:8090/restTemp/";

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping(path = "/restA")
    public String restA(@RequestParam("name") String name, @RequestParam("password") String password) {
        System.out.println(name);
        System.out.println(password);
        return name + "：" + password;
    }

    @GetMapping(path = "/restA2/{name}/{password}")
    public String restA2(@PathVariable("name") String name, @PathVariable("password") String password) {
        System.out.println(name);
        System.out.println(password);
        return name + "：" + password;
    }

    @GetMapping(path = "/restB")
    public String restB(String name, String password) {
        String object = restTemplate.getForObject(baseUrl + "restA?name={name}&password={password}", String.class, name, password);
        System.out.println(object);
        String object2 = restTemplate.getForObject(baseUrl + "restA2/{name}/{password}", String.class, name, password);
        System.out.println(object2);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        String object3 = restTemplate.getForObject(baseUrl + "restA?name={name}&password={password}", String.class, map);
        System.out.println(object3);
        String object4 = restTemplate.getForObject(baseUrl + "restA2/{name}/{password}", String.class, map);
        System.out.println(object4);
        return "ok";
    }

    @GetMapping(path = "/restC")
    public String restC(String name, String password) {
        ResponseEntity<String> entity = restTemplate.getForEntity(baseUrl + "restA?name={name}&password={password}", String.class, name, password);
        System.out.println(entity.getBody());
        ResponseEntity<String> entity2 = restTemplate.getForEntity(baseUrl + "restA2/{name}/{password}", String.class, name, password);
        System.out.println(entity2.getBody());

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        ResponseEntity<String> entity3 = restTemplate.getForEntity(baseUrl + "restA?name={name}&password={password}", String.class, map);
        System.out.println(entity3.getBody());
        ResponseEntity<String> entity4 = restTemplate.getForEntity(baseUrl + "restA2/{name}/{password}", String.class, map);
        System.out.println(entity4.getBody());

        return "ok";
    }

    @PostMapping(path = "/restD")
    public String restD(@RequestParam("name") String name, @RequestParam("password") String password) {
        System.out.println(name);
        System.out.println(password);
        return name + "：" + password;
    }

    @PostMapping(path = "/restD2/{name}/{password}")
    public String restD2(@PathVariable("name") String name, @PathVariable("password") String password) {
        System.out.println(name);
        System.out.println(password);
        return name + "：" + password;
    }

    @PostMapping(path = "/restD3")
    public String restD3(@RequestBody MyRequestParam param) {
        System.out.println(param.getName());
        System.out.println(param.getPassword());
        return param.getName() + "：" + param.getPassword();
    }

    @PostMapping(path = "/restE")
    public String restE(@RequestBody MyRequestParam param) {
        String result = restTemplate.postForObject(baseUrl + "restD?name={name}&password={password}", null, String.class, param.getName(), param.getPassword());
        System.out.println("result = " + result);
        String result2 = restTemplate.postForObject(baseUrl + "restD2/{name}/{password}", null, String.class, param.getName(), param.getPassword());
        System.out.println("result2 = " + result2);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", param.getName());
        map.put("password", param.getPassword());
        String result3 = restTemplate.postForObject(baseUrl + "restD?name={name}&password={password}", null, String.class, map);
        System.out.println("result3 = " + result3);
        String result4 = restTemplate.postForObject(baseUrl + "restD2/{name}/{password}", null, String.class, map);
        System.out.println("result4 = " + result4);

        HttpHeaders headers = new HttpHeaders();
        // 在header里面设置编码方式
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("name", param.getName());
        body.put("password", param.getPassword());
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        String result5 = restTemplate.postForObject(baseUrl + "restD3", httpEntity, String.class);
        System.out.println("result5 = " + result5);

        return null;
    }

    @PostMapping(path = "/restF")
    public String restF(@RequestBody MyRequestParam param) {
        ResponseEntity<String> entity = restTemplate.postForEntity(baseUrl + "restD?name={name}&password={password}", null, String.class, param.getName(), param.getPassword());
        System.out.println("entity = " + entity.getBody());
        ResponseEntity<String> entity2 = restTemplate.postForEntity(baseUrl + "restD2/{name}/{password}", null, String.class, param.getName(), param.getPassword());
        System.out.println("entity2 = " + entity2.getBody());

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", param.getName());
        map.put("password", param.getPassword());
        ResponseEntity<String> entity3 = restTemplate.postForEntity(baseUrl + "restD?name={name}&password={password}", null, String.class, map);
        System.out.println("entity3 = " + entity3.getBody());
        ResponseEntity<String> entity4 = restTemplate.postForEntity(baseUrl + "restD2/{name}/{password}", null, String.class, map);
        System.out.println("entity4 = " + entity4.getBody());

        HttpHeaders headers = new HttpHeaders();
        // 在header里面设置编码方式
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 请求body
        Map<String, Object> body = new HashMap<>();
        body.put("name", param.getName());
        body.put("password", param.getPassword());
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> entity5 = restTemplate.postForEntity(baseUrl + "restD3", httpEntity, String.class);
        System.out.println("entity5 = " + entity5.getBody());

        HttpEntity<MyRequestParam> httpEntity2 = new HttpEntity<>(param, headers);
        ResponseEntity<String> entity6 = restTemplate.postForEntity(baseUrl + "restD3", httpEntity2, String.class);
        System.out.println("entity6 = " + entity6.getBody());

        return null;
    }

    @GetMapping(path = "/restG")
    public String restG() {
        ResponseEntity<String> exchange = restTemplate.exchange(baseUrl + "restA?name={name}&password={password}", HttpMethod.GET, null, String.class, "ccc", "888");
        System.out.println("exchange = " + exchange.getBody());

        ResponseEntity<String> exchange1 = restTemplate.exchange(baseUrl + "restD?name={name}&password={password}", HttpMethod.POST, null, String.class, "ddd", "666");
        System.out.println("exchange1 = " + exchange1.getBody());

        HttpHeaders headers = new HttpHeaders();
        // 在header里面设置编码方式
        headers.setContentType(MediaType.APPLICATION_JSON);
        MyRequestParam param = new MyRequestParam();
        param.setName("www");
        param.setPassword("ppp");
        HttpEntity<MyRequestParam> httpEntity = new HttpEntity<>(param, headers);
        // 定义返回参数泛型，Class<T> class只能定义一层泛型，多层泛型则需要ParameterizedTypeReference<List<User>>
        ParameterizedTypeReference<String> type = new ParameterizedTypeReference<String>() {
        };
        ResponseEntity<String> exchange2 = restTemplate.exchange(baseUrl + "restD3", HttpMethod.POST, httpEntity, type);
        System.out.println("exchange2 = " + exchange2.getBody());

        return null;
    }

}
