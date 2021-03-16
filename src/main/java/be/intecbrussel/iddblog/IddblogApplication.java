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

    @EventListener(ApplicationReadyEvent.class)
    public void triggerWhenStarts() {
        Date date = getDate(2019, 05, 28);
        String title = "Gestion du stress";
        String intro = "Il n'y a personne qui n'aime la souffrance pour elle-même, qui ne la recherche et qui ne la veuille pour elle-même...";
        String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent quis velit maximus, cursus orci a, lobortis nulla. Maecenas vitae porta nibh. Integer sed mauris lacus. Fusce elit velit, consequat eu sapien hendrerit, tempus vulputate magna. Etiam vel lorem eros. Vestibulum in tellus sapien. Fusce id erat dignissim, congue sapien eget, sagittis metus.\n" +
                "\n" +
                "Curabitur commodo purus est, nec molestie nulla posuere in. Aenean non mattis nibh, sodales condimentum enim. Phasellus volutpat orci id diam efficitur sodales. Etiam volutpat orci nulla, vel cursus lorem sollicitudin in. Vivamus mauris mauris, lacinia nec fringilla quis, ornare eu sem. Curabitur in maximus metus. Praesent condimentum eget diam in varius. Donec pulvinar mauris viverra urna imperdiet tempus. Donec odio tortor, vestibulum ac scelerisque rhoncus, auctor in dui. Maecenas tincidunt porta odio, ac molestie mi porttitor vitae. In vel luctus mauris. Aenean lacus urna, scelerisque in nulla at, hendrerit mattis sapien. Duis vulputate congue dapibus. Etiam aliquam lorem id scelerisque sollicitudin.";

        RegisteredVisitor user = visitorService.findById(10L);

        WriterPost post = new WriterPost();
        post.setRegisteredVisitor(user);
        post.setCreationDate(date);
        post.setTitle(title);
        post.setIntro(intro);
        post.setContent(content);

        writerService.save(post);
    }

    private Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
