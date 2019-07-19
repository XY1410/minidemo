package com.gzhc365.minidemo.web.controller;

import com.gzhc365.minidemo.model.User;
import com.gzhc365.minidemo.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户相关接口
 *
 * @author chengang
 * @date 18-11-13
 * @time 下午5:40
 * @since JDK 1.8
 */
@Controller
@RequestMapping("api/user")
@Api(tags = "用户相关接口")
public class UserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 获取指定用户信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ApiOperation(value = "获取指定用户信息", response = User.class)
    public void isFollow(
            @ApiParam(value = "用户id") @RequestParam(required = true) Long userId,
            HttpServletRequest request, HttpServletResponse response) {
        User result = userService.getById(userId, getHcContext(request));
        this.sendSuccessData(response, result);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户名获取用户信息", response = User.class)
    public void getByName(
            @ApiParam(value = "用户name") @RequestParam(required = true) String name,
            HttpServletRequest request, HttpServletResponse response) {
        User result = userService.getByName(name, getHcContext(request));
        this.sendSuccessData(response, result);
    }

}
