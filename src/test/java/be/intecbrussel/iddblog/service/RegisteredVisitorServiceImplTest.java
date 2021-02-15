package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisteredVisitorServiceImplTest {

    RegisteredVisitorServiceImpl visitorService;

    @Mock
    RegisteredVisitorRepository visitorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        visitorService = new RegisteredVisitorServiceImpl(visitorRepository);
    }

    @Test
    void saveVisitor() {
        //given
        RegisteredVisitor visitor = new RegisteredVisitor();
        visitor.setId(2L);

        when(visitorRepository.save(any())).thenReturn(visitor);

        //when
        RegisteredVisitor savedVisitor = visitorService.saveVisitor(visitor);

        //then
        assertEquals(Long.valueOf(2L), savedVisitor.getId());
        verify(visitorRepository, times(1)).save(any(RegisteredVisitor.class));

    }


}