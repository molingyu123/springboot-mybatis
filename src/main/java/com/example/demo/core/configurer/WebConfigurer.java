package com.example.demo.core.configurer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.core.ret.RetCode;
import com.example.demo.core.ret.RetResult;
import com.example.demo.core.ret.ServiceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * 自定义消息转换器，控制从数据库中查询到数据为null 的值，
 * 将null 的值转换为 " " --空字符串
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

    /**
     * 修改自定义消息转换器
     * @param converterList
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converterList){
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 可以设置多个解决数据返回的null 类型的条件---根据实际的需求而定
        fastJsonConfig.setSerializerFeatures(
                //处理string ---null
                SerializerFeature.WriteNullStringAsEmpty,
                //处理 number---null
                SerializerFeature.WriteNullNumberAsZero,
                //禁止循环引用
                SerializerFeature.DisableCircularReferenceDetect
        );
//        WriteNullListAsEmpty  ：List字段如果为null,输出为[],而非null
//        WriteNullStringAsEmpty ： 字符类型字段如果为null,输出为"",而非null
//        DisableCircularReferenceDetect ：消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
//        WriteNullBooleanAsFalse：Boolean字段如果为null,输出为false,而非null
//        WriteMapNullValue：是否输出值为null的字段,默认为false

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        converterList.add(fastJsonHttpMessageConverter);
    }

    public List<MediaType> getSupportedMediaTypes(){
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypeList.add(MediaType.APPLICATION_ATOM_XML);
        mediaTypeList.add(MediaType.APPLICATION_FORM_URLENCODED);
        mediaTypeList.add(MediaType.APPLICATION_OCTET_STREAM);
        mediaTypeList.add(MediaType.APPLICATION_PDF);
        mediaTypeList.add(MediaType.APPLICATION_RSS_XML);
        mediaTypeList.add(MediaType.APPLICATION_XHTML_XML);
        mediaTypeList.add(MediaType.APPLICATION_XML);
        mediaTypeList.add(MediaType.IMAGE_GIF);
        mediaTypeList.add(MediaType.IMAGE_JPEG);
        mediaTypeList.add(MediaType.IMAGE_PNG);
        mediaTypeList.add(MediaType.TEXT_EVENT_STREAM);
        mediaTypeList.add(MediaType.TEXT_HTML);
        mediaTypeList.add(MediaType.TEXT_MARKDOWN);
        mediaTypeList.add(MediaType.TEXT_PLAIN);
        mediaTypeList.add(MediaType.TEXT_XML);
        return mediaTypeList;
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers){
        exceptionResolvers.add(getHandlerExceptionResolver());
    }

    private HandlerExceptionResolver getHandlerExceptionResolver(){
        HandlerExceptionResolver handlerExceptionResolver = new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                RetResult<Object> retResult =getResultByHandleException(request,handler,ex);
                responseResult(response,retResult);
                return new ModelAndView();
            }
        };
        return handlerExceptionResolver;
    }

    /**
     * 根据异常的类型返回响应的数据
     */
    private RetResult<Object> getResultByHandleException(HttpServletRequest request,Object handler,Exception e){
        RetResult<Object> retResult = new RetResult<>();
        // 判断异常的异常类型
        if(e instanceof ServiceException){
            retResult.setCode(RetCode.FAIL).setMessage(e.getMessage()).setData(null);
            return  retResult;
        }
        // 判断是否是未处理异常
        if(e instanceof NoHandlerFoundException){
            retResult.setCode(RetCode.NOT_FOUND).setMessage("接口["+request.getRequestURI()+"]不存在");
            return retResult;
        }
        retResult.setCode(RetCode.INTERNAL_SERVER_ERROR).setMessage("接口["+request.getRequestURI()+"]内部错误，请联系管理员");
        String message;
        // 判断处理的方法
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            message=String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",request.getRequestURI(),
                    handlerMethod.getBean().getClass().getName(),handlerMethod.getMethod().getName());
        }else{
            message=e.getMessage();
        }
//        LOGGER.info(message);
        System.out.println(message);
        return retResult;
    }

    /**
     * 响应结果
     * @param httpServletResponse
     * @param retResult
     */
    private void responseResult(HttpServletResponse httpServletResponse ,RetResult<Object> retResult){
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
        httpServletResponse.setStatus(200);
        try{
            httpServletResponse.getWriter().write(JSON.toJSONString(retResult,SerializerFeature.WriteMapNullValue));
        }catch (Exception e){
//            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
