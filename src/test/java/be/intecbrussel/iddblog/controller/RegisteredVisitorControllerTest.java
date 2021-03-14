package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
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

import java.security.Principal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RegisteredVisitorControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @InjectMocks
    RegisteredVisitorController visitorController;

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
    void showById() throws Exception {
        when(visitorService.findById(ArgumentMatchers.any())).thenReturn(savedVisitor);

        mockMvc.perform(get("/registeredvisitor/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("registeredvisitor/profileview"))
                .andExpect(model().attributeExists("registeredvisitor"));
        verify(visitorService, times(1)).findById(ArgumentMatchers.any());
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

}