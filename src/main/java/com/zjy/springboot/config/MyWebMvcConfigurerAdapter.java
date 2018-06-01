package com.zjy.springboot.config;

import com.zjy.springboot.intercepter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*第一步创建一个类实现HandlerInterceptor接口，重写接口的方法，只是多了一个@Component注解，这个注解是为后面的使用时进行注入。*/
/*第二步在入口类的目录或者兄弟目录下创建一个类继承WebMvcConfigurerAdapter类并重写addInterceptors方法*/

@Configuration  //此注解的作用是标志这是个配置类，相当于spring里面的xml的最高层，springboot启动时会加载此配置
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {


    @Autowired
    private LoginInterceptor loginInterceptor;

    //自定义拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }
}
