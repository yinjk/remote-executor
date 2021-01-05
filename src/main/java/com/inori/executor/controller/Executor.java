package com.inori.executor.controller;

import com.inori.executor.service.ExecutorService;
import com.inori.executor.service.compiler.CompileException;
import com.inori.executor.service.executor.ExecuteException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Executor
 *
 * @author inori
 * @date 2020/12/18
 */
@Controller
public class Executor {

    private final ExecutorService service;

    public Executor(ExecutorService service) {
        this.service = service;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/")
    public String index() {
        return "index";
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/execute")
    public String execute(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        if (StringUtils.isEmptyOrWhitespace(code)) {
            return "index";
        }
        String out;
        int status = 0;
        String msg = "执行成功";
        try {
            out = service.execute(code);
        } catch (ExecuteException e) {
            msg = "执行出错";
            out = e.getMessage();
            status = -2;
        } catch (CompileException e) {
            msg = "编译出错";
            out = e.getMessage();
            status = -1;
        }
        model.addAttribute("code", code);
        model.addAttribute("status", status);
        model.addAttribute("msg", msg);
        model.addAttribute("output", out);
        return "index";
    }

}
