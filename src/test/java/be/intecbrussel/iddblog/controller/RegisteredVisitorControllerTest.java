package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitorController)
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
    void save() throws Exception {
        RegisteredVisitor savedVisitor = RegisteredVisitor.builder().firstName("Abdel").lastName("Khy")
                .username("akhyare").emailAddress("ak@hotmail.com").password("uD45Pj6J*@cH$u")
                .confirmPassword("uD45Pj6J*@cH$u").gender("Male").isWriter(false)
                .build();

        when(visitorService.saveVisitor(ArgumentMatchers.any())).thenReturn(savedVisitor);

        mockMvc.perform(post("/registeredvisitor")
                .param("firstName","Abdel")
                .param("lastName","Khy")
                .param("username","akhyare")
                .param("emailAddress","ak@hotmail.com")
                .param("password","uD45Pj6J*@cH$u")
                .param("confirmPassword","uD45Pj6J*@cH$u"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService).saveVisitor(ArgumentMatchers.any());
    }
}