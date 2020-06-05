package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * User Information Controller
 */

@Controller
public class UserController {

    /**
     * User Information Service
     */
    @Autowired
    UserService userService;

    /**
     * ユーザー情報一覧画面の表示
     * @param model Model
     * @return ユーザー情報一覧のHTML
     */

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<User> userlist = userService.searchAll();
        model.addAttribute("userlist", userlist);
        return "user/list";
    }

    /**
     * ユーザー新規登録画面の表示
     * @param model Model
     * @return ユーザー新規登録のHTML
     */
    @RequestMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "user/add";
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute UserRequest userRequest, BindingResult result, Model model) {
        /**
         * エラーチェック
         */
        if(result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for(ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }

            model.addAttribute("validationError", errorList);
            return "user/add";
        }

        /*
         * ユーザー情報の登録
         */
        userService.create(userRequest);
        return "redirect:/user/list";
    }
}
