package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtj.travel.Service.UserService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping(value = "login")
    public Result login(@RequestBody User user, HttpServletRequest request) {
        log.info("login收到请求");
        log.info(user.toString());
        // 对密码进行MD5加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        // 构造查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername()).eq(User::getPassword, encryptedPassword);
        // 查询用户信息
        List<User> userList = userService.list(queryWrapper);
        log.info("查到东西了捏：" + Arrays.toString(userList.toArray()));

        if (userList.isEmpty()) {
            // 如果查询结果为空，则返回错误响应
            Result result = new Result("400", null, "用户名或密码错误");
            return result;
        } else {
            // 如果查询结果非空，则把当前用户信息存入session并返回成功响应
            request.getSession().setAttribute("userInfo", userList.get(0));
            Result result = new Result("200", userList, "登录成功");
            log.info(result.toString());
            // 将响应数据转换为JSON格式
            return result;
        }
    }

    //用户注册
    @PostMapping(value = "register")
    public Result register(@ModelAttribute User user, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // 对密码进行MD5加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

        // 构造查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername())
                .or()
                .eq(User::getPhoneNumber, user.getPhoneNumber());

        // 查询是否存在重复记录
        List<User> userList = userService.list(queryWrapper);
        if (!userList.isEmpty()) {
            // 如果查询结果不为空，则返回错误响应
            String errorMessage = "";
            for (User u : userList) {
                if (u.getUsername().equals(user.getUsername())) {
                    errorMessage += "用户名已存在；";
                }
                if (u.getPhoneNumber().equals(user.getPhoneNumber())) {
                    errorMessage += "手机号码已存在；";
                }
            }
            Result result = new Result("400", null, errorMessage);
            return result;
        } else {
            // 如果查询结果为空，则把当前用户信息存入数据库
            user.setPassword(encryptedPassword);
            user.setIdentity("用户");
            try {
                // 检查保存头像的目录是否存在，不存在则创建
                String uploadDir = "C:/intel/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 生成随机 UUID 文件名
                String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                String filePath = uploadDir + fileName;
                File dest = new File(filePath);
                file.transferTo(dest);
                user.setAvatarUrl(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                Result result = new Result("500", null, "服务器内部错误");
                return result;
            }

            userService.save(user);
            // 把当前用户信息存入session
            request.getSession().setAttribute("userInfo", user);
            Result result = new Result("200", user, "注册成功");
            log.info(result.toString());
            return result;
        }
    }

    //查找用户
    @GetMapping(value = "/{id}")
    public Result getUser(@PathVariable("id") String id) {
        // 根据ID查询用户信息
        User user = userService.getById(id);
        Result result;
        if (user == null) {
            // 如果查询结果为空，则返回错误响应
            result = new Result("404", null, "用户不存在");
        } else {
            // 如果查询结果不为空，则返回成功响应
            result = new Result("200", user, "查询成功");
        }
        return result;
    }
    @PutMapping(value = "")
    public Result updateUser(@ModelAttribute User user, @RequestParam(value = "avatar", required = false) MultipartFile file) {
        log.info("update is running");
        // 根据ID查询用户信息
        User existingUser = userService.getById(user.getId());
        if (existingUser == null) {
            return new Result("404", null, "用户不存在");
        }

        // 构造查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(User::getId, user.getId()) // 排除当前用户
                .and(wq -> wq.eq(User::getUsername, user.getUsername()).or().eq(User::getPhoneNumber, user.getPhoneNumber()));

        // 查询是否存在重复记录
        List<User> userList = userService.list(queryWrapper);
        if (!userList.isEmpty()) {
            // 如果查询结果不为空，则返回错误响应
            String errorMessage = "";
            for (User u : userList) {
                if (u.getUsername().equals(user.getUsername())) {
                    errorMessage += "用户名已存在；";
                }
                if (u.getPhoneNumber().equals(user.getPhoneNumber())) {
                    errorMessage += "手机号码已存在；";
                }
            }
            Result result = new Result("400", null, errorMessage);
            return result;
        } else {
            // 如果查询结果为空，则更新用户信息
            String encryptedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            BeanUtils.copyProperties(user, existingUser); // 将表单数据复制到已有对象中
            existingUser.setPassword(encryptedPassword);

            try {
                // 更新头像文件（如果有上传）
                if (file != null && !file.isEmpty()) {
                    String uploadDir = "C:/intel/";
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                    String filePath = uploadDir + fileName;
                    File dest = new File(filePath);
                    file.transferTo(dest);

                    // 删除原有头像文件
                    if (existingUser.getAvatarUrl() != null && !existingUser.getAvatarUrl().isEmpty()) {
                        File oldFile = new File(existingUser.getAvatarUrl());
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }

                    existingUser.setAvatarUrl(fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Result result = new Result("500", null, "服务器内部错误");
                return result;
            }

            userService.updateById(existingUser);
            Result result = new Result("200", existingUser, "更新成功");
            log.info(result.toString());
            return result;
        }
    }
    //用户删除
    @DeleteMapping("/users")
    public Result deleteUser(@RequestBody String[] userIds) {
        log.info("deleteUser收到请求");
        log.info("要删除的用户ID列表：" + Arrays.toString(userIds));
        if (userIds == null || userIds.length == 0) {
            // 如果用户ID为空，则返回错误响应
            Result result = new Result("400", null, "用户ID不能为空");
            return result;
        } else {
            // 调用userService的removeByIds()方法删除指定的用户
            userService.removeByIds(Arrays.asList(userIds));
            Result result = new Result("200", null, "删除成功");
            log.info(result.toString());
            // 返回成功响应
            return result;
        }
    }

    /**
     * 更新用户身份信息
     *
     * @param user 包含要更新的用户ID和身份信息的对象
     * @return 返回操作结果的JSON对象
     */
    @PostMapping("/identity")
    public Result changeIdentity(@RequestBody User user) {
        User existingUser = userService.getById(user.getId());
        if (existingUser == null) {
            return new Result("400", null, "用户不存在");
        }
        existingUser.setIdentity(user.getIdentity());
        userService.updateById(existingUser);
        return new Result("200", null, "更新成功");
    }
}