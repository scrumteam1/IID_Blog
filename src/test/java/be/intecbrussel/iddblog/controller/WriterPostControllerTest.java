package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.*;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WriterPostControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @Mock
    TagService tagService;

    @Mock
    AuthService authService;

    @Mock
    WriterService writerService;

    @Mock
    CommentService commentService;

    @InjectMocks
    WriterPostController writerPostController;

    MockMvc mockMvc;

    RegisteredVisitor savedVisitor;

    Authentication authentication;

    List<Authority> authorities;

    WriterPost post;

    @BeforeEach
    void setUp() {

        authentication = Mockito.mock(Authentication.class);

        mockMvc = MockMvcBuilders
                .standaloneSetup(writerPostController)
                .build();

        savedVisitor = RegisteredVisitor.builder().id(1L).firstName("Abdel").lastName("Khy")
                .username("akhyare").emailAddress("ak@hotmail.com").password("uD45Pj6J*@cH$u")
                .confirmPassword("uD45Pj6J*@cH$u").gender("Male").isWriter(true)
                .build();

        post = new WriterPost(1L, LocalDateTime.now(), "test", "test", "test", "test",
                savedVisitor, true, null,null);

        Authority authority = Authority.builder().id(1L).authority("WRITER").build();
        authorities = new ArrayList<>();
        authorities.add(authority);
    }


    @Test
    void showPostsListTest() throws Exception {

        Page<WriterPost> page = Mockito.mock(Page.class);
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
        when(visitorService.findById(any())).thenReturn(savedVisitor);
        when(writerService.findWriterPostsByRegisteredVisitor(savedVisitor, "", 1, "creationDate", "desc")).thenReturn(page);


        mockMvc.perform(get("/writer/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("writer/writer"));
    }

    @Test
    void showPostTest() throws Exception {

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("test");
        when(visitorService.findByUsername(ArgumentMatchers.any())).thenReturn(savedVisitor);
        when(authService.findAuthorityByUsername(ArgumentMatchers.any())).thenReturn(authorities);

        mockMvc.perform(get("/writer/post/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("writer/blogpost-view"));

    }

    @Test
    void getSavePostErrorTest() throws Exception {

        when(visitorService.findById(any())).thenReturn(savedVisitor);

        mockMvc.perform(get("/writer/1/newblogpost"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/forbidden-page"));
    }

    @Test
    void getSavePostTest() throws Exception {

        Principal mockPrincipal = Mockito.mock(Principal.class);

        when(visitorService.findById(any())).thenReturn(savedVisitor);
        Mockito.when(mockPrincipal.getName()).thenReturn("akhyare");
        when(tagService.findAll()).thenReturn(any());

        mockMvc.perform(get("/writer/1/newblogpost")
                .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(view().name("writer/newblogpost"));
    }

    @Test
    void postSavePostTest() throws Exception {

        when(visitorService.findById(any())).thenReturn(savedVisitor);

        mockMvc.perform(post("/writer/1/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/writer/1"));
    }

    @Test
    void deleteWriterPostErrorTest() throws Exception {

        mockMvc.perform(get("/deletepost/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/forbidden-page"));
    }

    @Test
    void deleteWriterPostTest() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);

        when(writerService.findById(anyLong())).thenReturn(post);
        Mockito.when(mockPrincipal.getName()).thenReturn("akhyare");

        mockMvc.perform(get("/deletepost/1")
                .principal(mockPrincipal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/writer/1"));
    }

    @Test
    void updateWriterPostErrorTest() throws Exception {

        mockMvc.perform(get("/writer/1/update/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/forbidden-page"));
    }

    @Test
    void updateWriterPostTest() throws Exception {

        Principal mockPrincipal = Mockito.mock(Principal.class);

        when(writerService.findById(anyLong())).thenReturn(post);
        Mockito.when(mockPrincipal.getName()).thenReturn("akhyare");

        mockMvc.perform(get("/writer/1/update/1")
                .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(view().name("writer/blogpost-edit"));
    }

    @Test
    void postUpdateWriterErrorPostTest() throws Exception {

        mockMvc.perform(post("/writer/1/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(3))
                .andExpect(view().name("writer/blogpost-edit"));
    }

    @Test
    void postUpdateWriterPostTest() throws Exception {
        String intro = RandomStringUtils.random(100);
        String content = RandomStringUtils.random(500);

        when(writerService.findById(anyLong())).thenReturn(post);

        mockMvc.perform(post("/writer/1/edit/1")
                .param("title","test")
                .param("intro",intro)
                .param("content",content))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/writer/1"));
    }
}