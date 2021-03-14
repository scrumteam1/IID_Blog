package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
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
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UpdateVisitorControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @InjectMocks
    UpdateVisitorController visitorController;

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
    void UpdateVisitorGetForm() throws Exception {
        RegisteredVisitor visitorFound = new RegisteredVisitor();
        visitorFound.setId(1L);

        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(visitorFound);

        mockMvc.perform(get("/registeredvisitor/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("registeredVisitor/updateprofile"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
    }

    @Test
    void UpdateVisitorPost() throws Exception {
        savedVisitor.setId(1L);

        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(savedVisitor);

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
        savedVisitor.setId(1L);

        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(savedVisitor);

        mockMvc.perform(post("/registeredvisitor/edit/1")
                .param("firstName","")
                .param("lastName","")
                .param("username","ak")
                .param("emailAddress","akhotmail")
                .param("password","123")
                .param("confirmPassword","123"))
                .andExpect(model().errorCount(7))
                .andExpect(status().isOk())
                .andExpect(view().name("registeredVisitor/updateprofile"))
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
                .andExpect(view().name("registeredVisitor/updateprofile"))
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
                .andExpect(view().name("registeredVisitor/updateprofile"))
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

}