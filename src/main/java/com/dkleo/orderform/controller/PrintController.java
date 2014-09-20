package com.dkleo.orderform.controller;

/**
 * Created with IntelliJ IDEA.
 * User: Derek
 * Date: 1/19/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.Map;

@Controller
@RequestMapping(value = "/print")
public class PrintController extends AbstractController {
    @Override
    @RequestMapping(value = "/{book}.pdf")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = null;

        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        mav = new ModelAndView(pathVariables.get("book").toString());
        return mav;
    }
}
