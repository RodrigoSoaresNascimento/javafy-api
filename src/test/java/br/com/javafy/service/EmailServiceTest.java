package br.com.javafy.service;

import br.com.javafy.dto.usuario.UsuarioDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import static org.junit.Assert.*;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender emailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private freemarker.template.Configuration fmConfiguration;

    @Before
    public void init(){
        ReflectionTestUtils.setField(emailService, "from", "meuteste@teste.com");
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        doNothing().when(emailSender).send(any(MimeMessage.class));
    }

    @Test
    public void deveGetContentFromTemplateCreate() throws TemplateException, IOException {
        String tipoDeMensagem = "create";
        when(fmConfiguration.getTemplate(anyString()))
                .thenReturn(new Template("Para TESTAR", "CREATE", new Configuration()));
        emailService.sendEmail(getUsuarioDTO(), tipoDeMensagem);
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void deveGetContentFromTemplateUpdate() throws TemplateException, IOException {
        String tipoDeMensagem = "update";
        when(fmConfiguration.getTemplate(anyString()))
                .thenReturn(new Template("Teste", "UPDATE", new Configuration()));
        emailService.sendEmail(getUsuarioDTO(), tipoDeMensagem);
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void deveGetContentFromTemplateDelete() throws  IOException {
        String tipoDeMensagem = "delete";
        when(fmConfiguration.getTemplate(anyString()))
                .thenReturn(new Template("Teste", "DELETE", new Configuration()));
        emailService.sendEmail(getUsuarioDTO(), tipoDeMensagem);
        verify(emailSender, times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void deveDaExceptionAoEnviarOEmail() {

        UsuarioDTO usuarioDTO = getUsuarioDTO();
        usuarioDTO.setEmail("");

        emailService.sendEmail(usuarioDTO, "create");
        verify(emailSender, times(0)).send(any(MimeMessage.class));
        assertEquals("Error ao enviar o email", emailService.getMensagem());
    }

    private UsuarioDTO getUsuarioDTO(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(123);
        usuarioDTO.setNome("Cleber");
        usuarioDTO.setEmail("faker@faker.com");
        return usuarioDTO;
    }

}
