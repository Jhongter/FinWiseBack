package com.exemplo.usuario.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.url:http://localhost:3000}")
    private String appUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarConfirmacao(String destinatario, String nome, String token) {
        try {
            String link = appUrl + "/Pages/Login/confirmar-email.html?token=" + token;
            String corpo =
                "Olá, " + nome + "!\n\n" +
                "Obrigado por se cadastrar no FinWise.\n\n" +
                "Clique no link abaixo para confirmar seu e-mail:\n" +
                link + "\n\n" +
                "Se você não criou uma conta, ignore este e-mail.\n\n" +
                "Equipe FinWise";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("FinWise — Confirme seu e-mail");
            helper.setText(corpo);
            mailSender.send(message);

            System.out.println("✅ Email de confirmação enviado para: " + destinatario);
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }

    public void enviarRedefinicaoSenha(String destinatario, String nome, String token) {
        try {
            String link = appUrl + "/Pages/Login/redefinir-senha.html?token=" + token;
            String corpo =
                "Olá, " + nome + "!\n\n" +
                "Recebemos uma solicitação para redefinir sua senha.\n\n" +
                "Clique no link abaixo (válido por 1 hora):\n" +
                link + "\n\n" +
                "Se não foi você, ignore este e-mail.\n\n" +
                "Equipe FinWise";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject("FinWise — Redefinição de senha");
            helper.setText(corpo);
            mailSender.send(message);

            System.out.println("✅ Email de redefinição enviado para: " + destinatario);
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }
}
