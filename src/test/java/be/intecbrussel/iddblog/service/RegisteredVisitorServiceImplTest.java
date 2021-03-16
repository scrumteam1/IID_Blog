package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.repository.AuthRepository;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import be.intecbrussel.iddblog.repository.VerifTokenRepository;
import be.intecbrussel.iddblog.repository.WriterPostRepository;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisteredVisitorServiceImplTest {

    RegisteredVisitorServiceImpl visitorService;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RegisteredVisitorRepository visitorRepository;

    @Mock
    WriterService writerService;

    @Mock
    AuthService authService;

    @Mock
    VerifTokenRepository verifTokenRepository;

    RegisteredVisitor visitor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        visitorService = new RegisteredVisitorServiceImpl(jdbcTemplate,visitorRepository,passwordEncoder,authService,
                verifTokenRepository,writerService);


        visitor = RegisteredVisitor.builder().id(2L).username("akyare")
                .firstName("Abdel").lastName("Khyare").password("123456").confirmPassword("123456")
                .emailAddress("akhyare@gmail.com").gender("Male").build();
    }

    @Test
    void saveVisitor() {
        //given the visitor declare on setup()

        when(passwordEncoder.encode(any())).thenReturn("encoded password");
        when(visitorRepository.save(any())).thenReturn(visitor);

        //when
        RegisteredVisitor savedVisitor = visitorService.saveVisitor(visitor);

        //then
        assertEquals(Long.valueOf(2L), savedVisitor.getId());
        verify(visitorRepository, times(1)).save(any());
    }

    @Test
    void saveVisitorEmailExists(){
        //given the visitor declare on setup()

        when(visitorRepository.findByEmailAddress(any())).thenReturn(RegisteredVisitor.builder()
                .emailAddress("blabla").build());

        //then
        Throwable exception = assertThrows(UserAlreadyExistException.class,
                () -> visitorService.saveVisitor(visitor));

        assertTrue(exception.getMessage().contains("There is an user with that email address"));

    }

    @Test
    void saveVisitorUsernameExists(){
        //given the visitor declare on setup()

        when(visitorRepository.findByUsername(any())).thenReturn(RegisteredVisitor.builder()
                .username("blabla").build());

        //then
        Throwable exception = assertThrows(UserAlreadyExistException.class,
                () -> visitorService.saveVisitor(visitor));

        assertTrue(exception.getMessage().contains("There is an user with that username"));

    }

    @Test
    void findById() {
        //given
        RegisteredVisitor returnVisitor = RegisteredVisitor.builder().id(2L).build();

        when(visitorRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnVisitor));

        //when
        RegisteredVisitor visitor = visitorService.findById(2L);

        //then
        assertEquals(Long.valueOf(2L), visitor.getId());
    }


}