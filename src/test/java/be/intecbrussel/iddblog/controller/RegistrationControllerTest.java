package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.email.EmailService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RegistrationControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @Mock
    EmailService emailService;

    @InjectMocks
    RegistrationController visitorController;

    RegisteredVisitor savedVisitor;

    MockMvc mockMvc;

    Authentication authentication;

    @BeforeEach
    void setUp() {
        authentication = Mockito.mock(Authentication.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitorController)
                .build();

        savedVisitor = RegisteredVisitor.builder().firstName("Abdel").lastName("Khy")
                .username("akhyare").emailAddress("ak@hotmail.com").password("uD45Pj6J*@cH$u")
                .confirmPassword("uD45Pj6J*@cH$u").gender("Male").isWriter(false)
                .build();
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
    void confirmRegistrationTest() throws Exception{
        savedVisitor.setId(1L);

        when(visitorService.findById(any())).thenReturn(savedVisitor);

        mockMvc.perform(get("/registeredvisitor/confirm/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("email-sent"));
    }

    @Test
    void registrationConfirmedTest() throws Exception{

        VerificationToken token = new VerificationToken("test");

        when(visitorService.getVerificationToken(any())).thenReturn(token);

        mockMvc.perform(get("/registeredvisitor/confirmRegistration")
                .param("token", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmation-registration"));
    }

}