package com.topics.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.topics.exception.EmailSendException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtil {
	
	@Autowired
    private JavaMailSender mailSender;
	
	private String companyEmail = "pawshopispan@gmail.com";
	
	public void sendOrderConfirmationEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(companyEmail);
        mailSender.send(message);
    }
	
	public void sendResetPasswordEmail(String to, String resetUrl) {
        String subject = "PawShop 密碼重設通知";
        String htmlContent = "<p>親愛的用戶，您好：</p>" +
                "<p>請點擊以下連結以重設您的密碼（<strong>15 分鐘內有效</strong>）：</p>" +
                "<p><a href='" + resetUrl + "'>" + resetUrl + "</a></p>" +
                "<br>" +
                "<p>如果您沒有申請密碼重設，請忽略這封信。</p>" +
                "<p>敬祝順心，<br>PawShop 團隊敬上</p>";
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // 第二個參數 true → 支援 HTML
            helper.setFrom(companyEmail);
            mailSender.send(message);
        } catch (MessagingException e) {
        	throw new EmailSendException("寄送重設密碼信件失敗");
        }
    }

}
