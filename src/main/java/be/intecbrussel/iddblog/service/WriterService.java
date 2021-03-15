package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.WriterPost;

import java.util.Date;
import java.util.List;

public interface WriterService {


    List<WriterPost> findWriterPostsByUserId(long userId);
    List<WriterPost> findAll();
    List<WriterPost> findOrderByCreationDate(Date date);
}
