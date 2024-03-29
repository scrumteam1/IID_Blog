package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class IndexControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @Mock
    AuthService authService;

    @Mock
    WriterService writerService;

    @InjectMocks
    IndexController visitorController;

    RegisteredVisitor savedVisitor;

    MockMvc mockMvc;

    Authentication authentication;

    List<Authority> authorities;

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

        Authority authority = Authority.builder().id(1L).authority("ADMIN").build();
        authorities = new ArrayList<>();
        authorities.add(authority);
    }

    @Test
    void LoginTest() throws Exception{

        mockMvc.perform(get("/login/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void LoginErrorTest() throws Exception{

        mockMvc.perform(get("/login-error"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginError"));
    }

    @Test
    void getIndexTest() throws Exception {

        Page<WriterPost> page = Mockito.mock(Page.class);
        savedVisitor.setId(1L);
        Authority authority = Authority.builder().id(1L).authority("ADMIN").build();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        WriterPost post = new WriterPost();
        post.setId(1L);
        List<WriterPost> posts = new ArrayList<>();
        posts.add(post);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("test");
        when(visitorService.findByUsername(ArgumentMatchers.any())).thenReturn(savedVisitor);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn(authorities);
        when(writerService.findWriterPostsByRegisteredVisitor("",1,"creationDate","desc")).thenReturn(page);

        mockMvc.perform(get("/index/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("loggedinuser"));
    }

    @Test
    void logoutTest() throws Exception {

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void showAbout() throws Exception {

        savedVisitor.setId(1L);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("test");
        when(visitorService.findByUsername(ArgumentMatchers.any())).thenReturn(savedVisitor);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn(authorities);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn(authorities);

        mockMvc.perform(get("/about/"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }
}