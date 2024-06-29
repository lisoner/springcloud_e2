package ynu.edu.controller;

import org.springframework.web.bind.annotation.*;
import ynu.edu.entity.CommonResult;
import ynu.edu.entity.User;

import java.lang.module.Configuration;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getUserById/{userId}")
    public CommonResult<User> getUserById(@PathVariable("userId") Integer userId){
        CommonResult<User> result =new CommonResult<>();
        int code=200;
        String message="success(11002)";

        try{
            User u =new User(userId,"小明","123456");
            result.setResult(u);
        }
        catch (Exception e){
            code=500;
            message="failed";
        }

        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    @PostMapping("/getUserById")
    public CommonResult<User> postUserById(@RequestBody Integer userId) {
        User user = new User(userId, "222" + userId, "555");
        return new CommonResult<>(200, "success11000", user);
    }


    @PutMapping("/updateUserById")
    public CommonResult<User> updateUserById(@RequestBody User user) {
        return new CommonResult<>(200, "success update user", user);
    }

    @DeleteMapping("/deleteUserById/{userId}")
    public CommonResult<User> deleteUserById(@RequestBody Integer userId) {
        return new CommonResult<>(200, "delete User success!",null);
    }

}
