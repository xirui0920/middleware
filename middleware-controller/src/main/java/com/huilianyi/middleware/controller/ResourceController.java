package com.huilianyi.middleware.controller;

import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.vo.ResultStringVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * ResourceController.java
 *
 * @author : Gooliang Young
 * @date : 2018/6/1 下午7:31
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private LocaleMessageSource localeMessageSource;

    /**
     * 获取国际化资源
     *
     * @param key key
     * @return str
     */
    @ResponseBody
    @RequestMapping("/getI18nResource")
    public ResultStringVo getI18nResource(@RequestBody String key) {
        ResultStringVo returnValue = new ResultStringVo();
        returnValue.setCode(CommonValue.SUCCESS_CODE);
        returnValue.setSuccess(true);
        returnValue.setString(localeMessageSource.getMessage(key));
        return returnValue;
    }
}
