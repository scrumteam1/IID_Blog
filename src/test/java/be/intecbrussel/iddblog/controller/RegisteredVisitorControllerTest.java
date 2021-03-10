package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.email.EmailService;
import be.intecbrussel.iddblog.email.EmailServiceImpl;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RegisteredVisitorControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @Mock
    AuthService authService;

    @InjectMocks
    RegisteredVisitorController visitorController;

    RegisteredVisitor savedVisitor;

    MockMvc mockMvc;

    Authentication authentication;

    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private JavaMailSender javaMailSender;

    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {

        authentication = Mockito.mock(Authentication.class);

        mimeMessage = Mockito.mock(MimeMessage.class);
        javaMailSender = mock(JavaMailSender.class);
//        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//        emailServiceImpl = new EmailServiceImpl(javaMailSender);

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitorController)
                .build();

        savedVisitor = RegisteredVisitor.builder().firstName("Abdel").lastName("Khy")
                .username("akhyare").emailAddress("ak@hotmail.com").password("uD45Pj6J*@cH$u")
                .confirmPassword("uD45Pj6J*@cH$u").gender("Male").isWriter(false)
                .build();
    }

    @Test
    void getIndexTest() throws Exception {
        savedVisitor.setId(1L);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("test");
        when(visitorService.findByUsername(ArgumentMatchers.any())).thenReturn(savedVisitor);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn("ADMIN");

        mockMvc.perform(get("/index/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loggedinuser"));
    }

    @Test
    void logOutTest() throws Exception {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void newMember() throws Exception {
        mockMvc.perform(get("/registeredvisitor/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verifyNoInteractions(visitorService);
    }

    @Test
    void saveWithoutErrors() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        when(visitorService.saveVisitor(ArgumentMatchers.any())).thenReturn(savedVisitor);
        //To improve and add the setId in the when(...)...
        savedVisitor.setId(1L);

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u")
                .param("isWriter","false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/registeredvisitor/confirm/1"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void saveBindingErrorEmailNotValid() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("emailAddress","akgmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(model().attributeHasFieldErrors("registeredvisitor","emailAddress"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void saveBindingErrorPasswordNotValid() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("password","1234"))
                .andExpect(model().attributeHasFieldErrors("registeredvisitor","password"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void saveBindingErrorPasswordNotMatch() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","aD45Pj6J*@cH$u"))
                .andExpect(model().errorCount(1))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void saveErrorUserAlreadyExistEmail() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        when(visitorService.saveVisitor(any())).thenThrow(new UserAlreadyExistException("There is an user with that email address: "));

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("message"));

    }

    @Test
    void saveErrorUserAlreadyExistUsername() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        when(visitorService.saveVisitor(any())).thenThrow(new UserAlreadyExistException("There is an user with that username: "));

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("message"));

    }

    @Test
    void saveWithAllErrors() throws Exception {

        MockMultipartFile file = new MockMultipartFile("image", "other-file-name.jpg",
                "image/jpeg", "some other type".getBytes());

        mockMvc.perform(multipart("/registeredvisitor")
                .file(file)
                .param("firstName","")
                .param("lastName","")
                .param("username","ak")
                .param("emailAddress","akhotmail")
                .param("password","123")
                .param("confirmPassword","123"))
                .andExpect(model().errorCount(7))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void showById() throws Exception {
        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(savedVisitor);

        mockMvc.perform(get("/registeredvisitor/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("profileview"))
                .andExpect(model().attributeExists("registeredvisitor"));
        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void UpdateVisitorGetForm() throws Exception {
        RegisteredVisitor visitorFound = new RegisteredVisitor();
        visitorFound.setId(1L);

        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(visitorFound);

        mockMvc.perform(get("/registeredvisitor/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateprofile"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void UpdateVisitorPost() throws Exception {
        savedVisitor.setId(1L);

        mockMvc.perform(post("/registeredvisitor/edit/1")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u")
                .param("gender","Male")
                .param("isWriter","false"))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/registeredvisitor/1/show"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(1)).updateVisitorWithoutPwd(savedVisitor);
    }

    @Test
    void updateWithAllErrors() throws Exception {

        mockMvc.perform(post("/registeredvisitor/edit/1")
                .param("firstName","")
                .param("lastName","")
                .param("username","ak")
                .param("emailAddress","akhotmail")
                .param("password","123")
                .param("confirmPassword","123"))
                .andExpect(model().errorCount(7))
                .andExpect(status().isOk())
                .andExpect(view().name("updateprofile"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).updateVisitorWithoutPwd(ArgumentMatchers.any());
    }

    @Test
    void updateErrorUserAlreadyExistEmail() throws Exception {

        doThrow(new UserAlreadyExistException("There is an user with that email address: ")).when(visitorService).updateVisitorWithoutPwd(any());

        mockMvc.perform(post("/registeredvisitor/edit/1")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateprofile"))
                .andExpect(model().attributeExists("message"));

    }

    @Test
    void updateErrorUserAlreadyExistUsername() throws Exception {

        doThrow(new UserAlreadyExistException("There is an user with that username: ")).when(visitorService).updateVisitorWithoutPwd(any());

        mockMvc.perform(post("/registeredvisitor/edit/1")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateprofile"))
                .andExpect(model().attributeExists("message"));

    }

    @Test
    void UpdatePwdVisitorGetForm() throws Exception {
        RegisteredVisitor visitorFound = new RegisteredVisitor();
        visitorFound.setId(1L);

        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(visitorFound);

        mockMvc.perform(get("/registeredvisitor/update password/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/password/changepassword"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void UpdatePwdVisitorPost() throws Exception {
        RegisteredVisitor visitorFound = new RegisteredVisitor();
        visitorFound.setId(1L);

        when(visitorService.checkIfValidOldPassword(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(true);

        mockMvc.perform(post("/registeredvisitor/edit password/1")
                .param("oldPassword","uD45Pj6J*@cH$u")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com"))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
        verify(visitorService, times(1)).updateUserPwd(ArgumentMatchers.any(),ArgumentMatchers.any());
        verify(visitorService, times(1)).checkIfValidOldPassword(ArgumentMatchers.any(),ArgumentMatchers.any());

    }

    @Test
    void updateWithAllPwdErrors() throws Exception {

        mockMvc.perform(post("/registeredvisitor/edit password/1")
                .param("oldPassword","123")
                .param("password","123")
                .param("confirmPassword","")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com"))
                .andExpect(model().errorCount(3))
                .andExpect(status().isOk())
                .andExpect(view().name("/password/changepassword"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).findById(ArgumentMatchers.any());
        verify(visitorService, times(0)).updateUserPwd(ArgumentMatchers.any(),ArgumentMatchers.any());
        verify(visitorService, times(0)).checkIfValidOldPassword(ArgumentMatchers.any(),ArgumentMatchers.any());

    }

    @Test
    void updateWithOldPwdInvalid() throws Exception {

        when(visitorService.checkIfValidOldPassword(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(false);

        mockMvc.perform(post("/registeredvisitor/edit password/1")
                .param("oldPassword","123")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com"))
                .andExpect(model().errorCount(0))
                .andExpect(status().isOk())
                .andExpect(view().name("/password/changepassword"))
                .andExpect(model().attributeExists("registeredvisitor"))
                .andExpect(model().attributeExists("messageInvalidOldPwd"));

        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
        verify(visitorService, times(0)).updateUserPwd(ArgumentMatchers.any(),ArgumentMatchers.any());
        verify(visitorService, times(1)).checkIfValidOldPassword(ArgumentMatchers.any(),ArgumentMatchers.any());

    }

    @Test
    void deleteRegisteredVisitorTest() throws Exception {
        savedVisitor.setId(1L);

        when(visitorService.findById(any())).thenReturn(savedVisitor);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("akhyare");

        mockMvc.perform(get("/delete/1")
                .principal(mockPrincipal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    void deleteRegisteredVisitorForbiddenTest() throws Exception {
        savedVisitor.setId(1L);

        when(visitorService.findById(any())).thenReturn(savedVisitor);

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("hello");

        mockMvc.perform(get("/delete/1")
                .principal(mockPrincipal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/forbidden-page"));
    }

    @Test
    void showForgetPasswordTest() throws Exception {
        mockMvc.perform(get("/password/forgetPassword/"))
                .andExpect(status().isOk());
    }

    @Test
    void resetPasswordNoVisitorTest() throws Exception {
        mockMvc.perform(post("/password/forgetPassword/")
                .param("email",""))
                .andExpect(status().isOk())
                .andExpect(view().name("/password/forgetPassword"));
    }

    @Test
    void confirmResetPwdTest() throws Exception {
        VerificationToken token = new VerificationToken("test");

        when(visitorService.getVerificationToken(any())).thenReturn(token);

        mockMvc.perform(get("/resetPwdConfirm/")
                .param("token","test"))
                .andExpect(status().isOk())
                .andExpect(view().name("/password/reset-pwd"));
    }

    @Test
    void confirmResetPwdFailTokenTest() throws Exception {

        when(visitorService.getVerificationToken(any())).thenReturn(null);

        mockMvc.perform(get("/resetPwdConfirm/")
                .param("token","test"))
                .andExpect(status().isOk())
                .andExpect(view().name("/verification-link-failed"));
    }

    @Test
    void confirmResetPwdTokenExpiredTest() throws Exception {
        savedVisitor.setId(1L);
        VerificationToken token = new VerificationToken("test",savedVisitor);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(2000, 1, 1);
        token.setExpiryDate(Date.from(localDate.atStartOfDay(defaultZoneId).toInstant()));

        when(visitorService.getVerificationToken(any())).thenReturn(any());

        mockMvc.perform(get("/resetPwdConfirm/")
                .param("token","test"))
                .andExpect(status().isOk())
                .andExpect(view().name("/verification-link-failed"));
    }


//    @Test
//    void resolveException() throws Exception{
//        lenient().when(visitorService.saveVisitor(any())).thenThrow(new MaxUploadSizeExceededException(450000L));
//
//        mockMvc.perform(post("/registeredvisitor")
//                .param("firstName","Abdel")
//                .param("lastName","Khy")
//                .param("username","akhyare")
//                .param("emailAddress","ak@hotmail.com")
//                .param("password","uD45Pj6J*@cH$u")
//                .param("confirmPassword","uD45Pj6J*@cH$u"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("registerform"))
//                .andExpect(model().attributeExists("error"));
//
//        verify(visitorService, times(0)).saveVisitor(ArgumentMatchers.any());
//
//    }

}