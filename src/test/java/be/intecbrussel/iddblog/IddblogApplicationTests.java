package be.intecbrussel.iddblog;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
class IddblogApplicationTests {

    @Test
    void contextLoads() {
    }

}
