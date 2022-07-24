package br.com.javafy.service;


import br.com.javafy.dto.UsuarioDTO;
import br.com.javafy.enums.TipoDeMensagem;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(UsuarioDTO usuarioDTO, String tipoMensagem) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(usuarioDTO.getEmail());
            if(tipoMensagem.equals(TipoDeMensagem.CREATE.getTipoDeMensagem())){
                mimeMessageHelper.setSubject("Cadastro realizado");
            }else if (tipoMensagem.equals(TipoDeMensagem.UPDATE.getTipoDeMensagem())){
                mimeMessageHelper.setSubject("Cadastro atualizado");
            }else{
                mimeMessageHelper.setSubject("Cadastro deletado");
            }
            mimeMessageHelper.setText(geContentFromTemplate(usuarioDTO,tipoMensagem), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    public String geContentFromTemplate(UsuarioDTO usuarioDTO, String tipoMensagem) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNome());
        dados.put("id", usuarioDTO.getIdUsuario());
        dados.put("email", from);
        Template template;

        if(tipoMensagem.equals(TipoDeMensagem.CREATE.getTipoDeMensagem())){
            template = fmConfiguration.getTemplate("email_boas_vindas-template.ftl");
        }else if (tipoMensagem.equals(TipoDeMensagem.UPDATE.getTipoDeMensagem())){
            template = fmConfiguration.getTemplate("email_atualizar_endereco-template.ftl");
        }else{
            template = fmConfiguration.getTemplate("email_deletar_endereco-template.ftl");
        }

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

}
