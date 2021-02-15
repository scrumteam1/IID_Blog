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
        when(visitorService.saveVisitor(ArgumentMatchers.any())).thenReturn(RegisteredVisitor.builder().id(1L).build());

        mockMvc.perform(post("/registeredvisitor"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"))
                .andExpect(model().attributeExists("registeredvisitor"));

        verify(visitorService).saveVisitor(ArgumentMatchers.any());
    }
}