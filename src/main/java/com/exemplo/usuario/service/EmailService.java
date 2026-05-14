package com.exemplo.usuario.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String remetente;

    @Value("${app.url:http://localhost:3000}")
    private String appUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envia email de confirmação de cadastro.
     * Se o email não estiver configurado, ignora silenciosamente.
     */
    public void enviarConfirmacao(String destinatario, String nome, String token) {
        if (remetente == null || remetente.isBlank()) {
            System.out.println("⚠️ Email não configurado — confirmação ignorada para: " + destinatario);
            return;
        }

        try {
            String link = appUrl + "/Pages/Login/confirmar-email.html?token=" + token;

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(remetente);
            msg.setTo(destinatario);
            msg.setSubject("FinWise — Confirme seu e-mail");
            msg.setText(
                "Olá, " + nome + "!\n\n" +
                "Obrigado por se cadastrar no FinWise.\n\n" +
                "Clique no link abaixo para confirmar seu e-mail:\n" +
                link + "\n\n" +
                "Se você não criou uma conta, ignore este e-mail.\n\n" +
                "Equipe FinWise"
            );

            mailSender.send(msg);
            System.out.println("✅ Email de confirmação enviado para: " + destinatario);

        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }

    /**
     * Envia email de redefinição de senha.
     */
    public void enviarRedefinicaoSenha(String destinatario, String nome, String token) {
        if (remetente == null || remetente.isBlank()) return;

        try {
            String link = appUrl + "/Pages/Login/redefinir-senha.html?token=" + token;

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(remetente);
            msg.setTo(destinatario);
            msg.setSubject("FinWise — Redefinição de senha");
            msg.setText(
                "Olá, " + nome + "!\n\n" +
                "Recebemos uma solicitação para redefinir sua senha.\n\n" +
                "Clique no link abaixo (válido por 1 hora):\n" +
                link + "\n\n" +
                "Se não foi você, ignore este e-mail.\n\n" +
                "Equipe FinWise"
            );

            mailSender.send(msg);
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }
}
