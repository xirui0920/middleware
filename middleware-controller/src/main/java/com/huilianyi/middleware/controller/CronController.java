package com.huilianyi.middleware.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.URLUtil;
import com.huilianyi.middleware.common.CommonValue;
import com.huilianyi.middleware.config.LocaleMessageSource;
import com.huilianyi.middleware.util.CronUtil;
import com.huilianyi.middleware.vo.ResultListVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CronController.java
 *
 * @author : Gooliang Young
 * @date : 2018/6/6 下午4:34
 */
@Controller
@RequestMapping("/face/cron")
public class CronController {

    @Resource
    private LocaleMessageSource localeMessageSource;

    /**
     * 获取最近5次执行时间
     *
     * @param cron cron
     * @return result
     */
    @RequestMapping("/getExpression")
    @ResponseBody
    public ResultListVo<String> getExpression(@RequestBody String cron) {
        cron = URLUtil.decode(cron);
        ResultListVo<String> returnValue = new ResultListVo<>();
        List<String> list = executeTimes(CommonValue.FIVE, cron.endsWith(" ") ? cron.substring(0, cron.length() - 1) : cron);
        if (list.contains(null) || list.size() < CommonValue.FIVE) {
            returnValue.setMessage(localeMessageSource.getMessage("task.execute.cron.error"));
        } else {
            returnValue.setCode(CommonValue.SUCCESS_CODE);
            returnValue.setSuccess(true);
            returnValue.setList(list);
        }
        return returnValue;
    }

    /**
     * 获取制定次数的cron表达式
     *
     * @param times times
     * @param cron  cron表达式
     * @return list
     */
    private List<String> executeTimes(Integer times, String cron) {
        List<String> list = new ArrayList<>();
        Date date = new Date();
        for (int i = times; i > 0; i--) {
            date = CronUtil.getNextTime(date, cron);
            list.add(DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN));
        }
        return list;
    }
}
