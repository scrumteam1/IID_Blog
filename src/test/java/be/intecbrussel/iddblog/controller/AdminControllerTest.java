package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AdminControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @Mock
    AuthService authService;

    @InjectMocks
    AdminController adminController;

    RegisteredVisitor savedVisitor;

    MockMvc mockMvc;

    Authentication authentication;

    @BeforeEach
    void setUp() {

        authentication = Mockito.mock(Authentication.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(adminController)
                .build();

        List<Authority> authorities = new ArrayList<>();
        authorities.add(Authority.builder().id(1L).authority("USER").build());

        savedVisitor = RegisteredVisitor.builder().id(1L).firstName("Abdel").lastName("Khy")
                .username("akhyare").emailAddress("ak@hotmail.com").password("uD45Pj6J*@cH$u")
                .confirmPassword("uD45Pj6J*@cH$u").gender("Male").isWriter(false).enabled(true)
                .authority(authorities)
                .build();
    }

    @Test
    void listAdminByPageTest() throws Exception {

        Authority authority = Authority.builder().id(1L).authority("ADMIN").build();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        Page<RegisteredVisitor> page = Mockito.mock(Page.class);
        when(visitorService.findAllExceptAdmin("",1,"username","asc")).thenReturn(page);

        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("test");
        when(visitorService.findByUsername(ArgumentMatchers.any())).thenReturn(savedVisitor);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn(authorities);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn(authorities);

        mockMvc.perform(get("/admin/page/1")
                .param("keyword","")
                .param("sortField","username")
                .param("sortDir","asc"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"));
    }

    @Test
    void banMemberTest() throws Exception {

        when(visitorService.findById(any())).thenReturn(savedVisitor);

        mockMvc.perform(get("/ban/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        verify(visitorService, times(1)).updateUserEnabled(savedVisitor,false);
    }
}