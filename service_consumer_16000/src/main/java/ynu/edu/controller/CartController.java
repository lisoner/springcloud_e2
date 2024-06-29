package ynu.edu.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ynu.edu.ConsumerApplication16000;
import ynu.edu.entity.CommonResult;
import ynu.edu.entity.User;
import ynu.edu.feign.ServiceProviderService;

import java.sql.SQLDataException;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ServiceProviderService serviceProviderService;
    @Resource
    private RestTemplate restTemplate;

    private static final String PROVIDER_SERVER_URL = "http://provider-server/user";

    @GetMapping("/getById/{userId}")
    public CommonResult<User> getService(@PathVariable("userId") Integer userId){
        CommonResult result=serviceProviderService.getUserById(userId);
        return  result;
    }

    //GET方法
    @GetMapping("/getCartById/{userId}")
    @LoadBalanced
    public CommonResult<User> getCartById(@PathVariable("userId") Integer userId) {
        CommonResult result=restTemplate.getForObject(
                "http://provider-server/user/getUserById/"+userId,CommonResult.class);
        return  result;

    }



    //post方法
    @PostMapping("/getCartById/{userId}")
    public CommonResult<User> postCartById(@PathVariable("userId") Integer userId) {
        CommonResult<User> result = restTemplate.postForObject(
                PROVIDER_SERVER_URL + "/getUserById", userId, CommonResult.class);
        return result;
    }

    //put方法
    @PutMapping("/updateUserById")
    public CommonResult<User> updateUserById(@RequestBody User user) {
        String url = PROVIDER_SERVER_URL + "/updateUserById";
        HttpEntity<User> requestEntity = new HttpEntity<>(user);
        ResponseEntity<CommonResult> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonResult.class);
        return response.getBody();
    }

    //delete方法
    @DeleteMapping("/deleteUser/{userId}")
    public CommonResult<Void> deleteUser(@PathVariable("userId") Integer userId) {
        return new CommonResult<>(200, "User deleted successfully", null);
    }
}
