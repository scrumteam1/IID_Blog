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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class RegisteredVisitorControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @InjectMocks
    RegisteredVisitorController visitorController;

    RegisteredVisitor savedVisitor;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

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
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verifyNoInteractions(visitorService);
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

        when(visitorService.saveVisitor(ArgumentMatchers.any())).thenReturn(savedVisitor);
        //To improve and add the setId in the when(...)...
        savedVisitor.setId(1L);

        mockMvc.perform(post("/registeredvisitor")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/registeredvisitor/1/show"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void saveBindingErrorEmailNotValid() throws Exception {

        mockMvc.perform(post("/registeredvisitor")
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

        mockMvc.perform(post("/registeredvisitor")
                .param("password","1234"))
                .andExpect(model().attributeHasFieldErrors("registeredvisitor","password"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerform"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService, times(0)).saveVisitor(ArgumentMatchers.any());
    }

    @Test
    void saveBindingErrorPasswordNotMatch() throws Exception {

        mockMvc.perform(post("/registeredvisitor")
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

        when(visitorService.saveVisitor(any())).thenThrow(new UserAlreadyExistException("There is an user with that email address: "));

        mockMvc.perform(post("/registeredvisitor")
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

        when(visitorService.saveVisitor(any())).thenThrow(new UserAlreadyExistException("There is an user with that username: "));

        mockMvc.perform(post("/registeredvisitor")
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

        mockMvc.perform(post("/registeredvisitor")
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
                .andExpect(view().name("changepassword"))
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
                .andExpect(view().name("changepassword"))
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
                .andExpect(view().name("changepassword"))
                .andExpect(model().attributeExists("registeredvisitor"))
                .andExpect(model().attributeExists("messageInvalidOldPwd"));

        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
        verify(visitorService, times(0)).updateUserPwd(ArgumentMatchers.any(),ArgumentMatchers.any());
        verify(visitorService, times(1)).checkIfValidOldPassword(ArgumentMatchers.any(),ArgumentMatchers.any());
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/registeredvisitor/delete/1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/"));

//        verify(visitorService, times(1)).deleteVisitor();
    }
}