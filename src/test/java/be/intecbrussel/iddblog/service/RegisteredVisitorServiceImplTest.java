package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.command.RegisteredVisitorCommand;
import be.intecbrussel.iddblog.converter.RegisteredVisitorCommandToRegisteredVisitor;
import be.intecbrussel.iddblog.converter.RegisteredVisitorToRegisteredVisitorCommand;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisteredVisitorServiceImplTest {

    RegisteredVisitorServiceImpl visitorService;

    @Mock
    RegisteredVisitorRepository visitorRepository;

    @Mock
    RegisteredVisitorToRegisteredVisitorCommand visitorToRegisteredVisitorCommand;

    @Mock
    RegisteredVisitorCommandToRegisteredVisitor registeredVisitorCommandToRegisteredVisitor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        visitorService = new RegisteredVisitorServiceImpl(visitorRepository, registeredVisitorCommandToRegisteredVisitor, visitorToRegisteredVisitorCommand);
    }

    @Test
    void saveVisitor() {
        //given
        RegisteredVisitor visitor = RegisteredVisitor.builder().id(2L).username("akyare")
                .firstName("Abdel").lastName("Khyare").password("123456").confirmPassword("123456")
                .emailAddress("akhyare@gmail.com").gender("Male").build();

        when(visitorRepository.save(any())).thenReturn(visitor);

        //when
        RegisteredVisitor savedVisitor = visitorService.saveVisitor(visitor);

        //then
        assertEquals(Long.valueOf(2L), savedVisitor.getId());
        verify(visitorRepository, times(1)).save(any(RegisteredVisitor.class));

    }

    @Test
    void findById() {
        //given
        RegisteredVisitor returnVisitor = RegisteredVisitor.builder().id(2L).build();

        when(visitorRepository.findById(anyLong())).thenReturn(Optional.of(returnVisitor));

        //when
        RegisteredVisitor visitor = visitorService.findById(2L);

        //then
        assertEquals(Long.valueOf(2L), visitor.getId());
    }


}