package be.intecbrussel.iddblog;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.Tag;
import be.intecbrussel.iddblog.domain.Tagname;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.WriterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class IddblogApplication {
    @Autowired
    WriterService writerService;

    public static void main(String[] args) {
        SpringApplication.run(IddblogApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerWhenStarts() {
        WriterPost post = writerService.findById(131L);
        Tag tag = Tag.builder().tag(Tagname.Comedy).build();
        post.getTags().add(tag);

        writerService.save(post);

    }

}
