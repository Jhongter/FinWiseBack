package com.exemplo.usuario.service;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${resend.api.key:}")
    private String apiKey;

    @Value("${resend.from:onboarding@resend.dev}")
    private String remetente;

    @Value("${app.url:http://localhost:3000}")
    private String appUrl;

    /**
     * Envia email de confirmação de cadastro via Resend (HTTP API).
     * Se a API Key não estiver configurada, ignora silenciosamente.
     */
    public void enviarConfirmacao(String destinatario, String nome, String token) {
        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("⚠️ Resend não configurado — confirmação ignorada para: " + destinatario);
            return;
        }
        try {
            String link = appUrl + "/Pages/Login/confirmar-email.html?token=" + token;
            String corpo =
                "Olá, " + nome + "!\n\n" +
                "Obrigado por se cadastrar no FinWise.\n\n" +
                "Clique no link abaixo para confirmar seu e-mail:\n" +
                link + "\n\n" +
                "Se você não criou uma conta, ignore este e-mail.\n\n" +
                "Equipe FinWise";

            Resend resend = new Resend(apiKey);
            CreateEmailOptions params = CreateEmailOptions.builder()
                .from(remetente)
                .to(destinatario)
                .subject("FinWise — Confirme seu e-mail")
                .text(corpo)
                .build();

            resend.emails().send(params);
            System.out.println("✅ Email de confirmação enviado para: " + destinatario);

        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }

    /**
     * Envia email de redefinição de senha via Resend (HTTP API).
     */
    public void enviarRedefinicaoSenha(String destinatario, String nome, String token) {
        if (apiKey == null || apiKey.isBlank()) return;
        try {
            String link = appUrl + "/Pages/Login/redefinir-senha.html?token=" + token;
            String corpo =
                "Olá, " + nome + "!\n\n" +
                "Recebemos uma solicitação para redefinir sua senha.\n\n" +
                "Clique no link abaixo (válido por 1 hora):\n" +
                link + "\n\n" +
                "Se não foi você, ignore este e-mail.\n\n" +
                "Equipe FinWise";

            Resend resend = new Resend(apiKey);
            CreateEmailOptions params = CreateEmailOptions.builder()
                .from(remetente)
                .to(destinatario)
                .subject("FinWise — Redefinição de senha")
                .text(corpo)
                .build();

            resend.emails().send(params);
            System.out.println("✅ Email de redefinição enviado para: " + destinatario);

        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }
}
