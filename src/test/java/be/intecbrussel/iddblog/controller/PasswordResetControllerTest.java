package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PasswordResetControllerTest {

    @Mock
    RegisteredVisitorService visitorService;

    @InjectMocks
    PasswordResetController visitorController;

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
}