package be.intecbrussel.iddblog;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@SpringBootApplication
public class IddblogApplication {

    @Autowired
    RegisteredVisitorService visitorService;

    @Autowired
    WriterService writerService;

    public static void main(String[] args) {
        SpringApplication.run(IddblogApplication.class, args);
    }

}
