package com.huilianyi.middleware.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.huilianyi.middleware.enumeration.EEmailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * AsyncMailUtil.java
 * 异步发送邮件工具类
 *
 * @author : Gooliang Young
 * @date : 2018/4/19 上午10:00
 */
@Component
public class AsyncMailUtil {

    private static final Logger logger = LoggerFactory.getLogger(AsyncMailUtil.class);

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private MailProperties mailProperties;

    /**
     * 发送邮件
     *
     * @param emailType       邮件类型
     * @param receivers       收件人列表
     * @param carbonCopies    抄送人列表
     * @param subject         主题
     * @param content         邮件正文
     * @param isHtml          是否是HTML
     * @param attachmentFiles 附件列表
     */
    @Async
    public void sendEmail(EEmailType emailType, String[] receivers, String[] carbonCopies, String subject,
                          String content, Boolean isHtml, File[] attachmentFiles) {
        switch (emailType) {
            case TEXT:
                sendMailText(receivers, carbonCopies, subject, content);
                break;
            case HTML:
                sendMailAttachmentFile(receivers, carbonCopies, subject, content, isHtml, null);
                break;
            case ATTACHMENT:
                sendMailAttachmentFile(receivers, carbonCopies, subject, content, isHtml, attachmentFiles);
                break;
            case TEMPLATE:
                break;
            default:
                break;
        }
    }

    /**
     * 发送文本内容的邮件
     *
     * @param receivers    收件人列表
     * @param carbonCopies 抄送人列表
     * @param subject      主题
     * @param content      邮件内容
     */
    private void sendMailText(String[] receivers, String[] carbonCopies, String subject, String content) {
        long startTimestamp = System.currentTimeMillis();
        logger.info("开始发送邮件...");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(mailProperties.getUsername());
            mailMessage.setTo(receivers);
            mailMessage.setCc(carbonCopies);
            mailMessage.setSubject(subject);
            mailMessage.setText(content);
            mailMessage.setSentDate(DateUtil.date());

            mailSender.send(mailMessage);
            logger.info("发送邮件成功，耗时{}毫秒", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            logger.error("发送邮件失败，邮件内容：{}，失败原因：{}", JSON.toJSONString(mailMessage), e.getMessage());
        }
    }

    /**
     * 发送HTML或附件类型的邮件
     *
     * @param receivers       收件人列表
     * @param carbonCopies    抄送人列表
     * @param subject         主题
     * @param content         邮件内容
     * @param attachmentFiles 附件列表
     */
    private void sendMailAttachmentFile(String[] receivers, String[] carbonCopies, String subject,
                                        String content, Boolean isHtml, File[] attachmentFiles) {
        long startTimestamp = System.currentTimeMillis();
        logger.info("开始发送邮件...");

        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        try {
            messageHelper.setFrom(mailProperties.getUsername());
            messageHelper.setTo(receivers);
            messageHelper.setCc(carbonCopies);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            messageHelper.setSentDate(DateUtil.date());
            if (null != attachmentFiles) {
                for (File attachmentFile : attachmentFiles) {
                    messageHelper.addAttachment(FileUtil.mainName(attachmentFile), attachmentFile);
                }
            }

            mailSender.send(mailMessage);
            logger.info("发送邮件成功，耗时{}毫秒", System.currentTimeMillis() - startTimestamp);
        } catch (MessagingException e) {
            logger.error("发送邮件失败，邮件内容：{}，失败原因：{}", JSON.toJSONString(mailMessage), e.getMessage());
        }
    }
}
