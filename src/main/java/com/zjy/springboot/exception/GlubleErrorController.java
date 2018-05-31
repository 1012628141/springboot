package com.zjy.springboot.exception;

import org.springframework.boot.autoconfigure.web.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//自定义的四种方式
 /*     自定义一个bean，实现ErrorController接口，那么默认的错误处理机制将不再生效。
        自定义一个bean，继承BasicErrorController类，使用一部分现成的功能，自己也可以添加新的public方法，使用@RequestMapping及其produces属性指定新的地址映射。
        自定义一个ErrorAttribute类型的bean，那么还是默认的两种响应方式，只不过改变了内容项而已。
        继承AbstractErrorControlle*/

@Controller
public class GlubleErrorController extends BasicErrorController {


    public GlubleErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    /**
     * 覆盖默认的Json响应
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);

        //输出自定义的Json格式
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code",body.get("status"));
        map.put("msg",body.get("error"));
        map.put("data",body.get("message"));

        return new ResponseEntity<Map<String, Object>>(map, status);
    }

    /**
     * 覆盖默认的HTML响应
     */
   /* @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //请求的状态
        HttpStatus status = getStatus(request);
        response.setStatus(getStatus(request).value());

        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        //指定自定义的视图
        return (modelAndView == null ? new ModelAndView("error", model) : modelAndView);
    }*/
}



