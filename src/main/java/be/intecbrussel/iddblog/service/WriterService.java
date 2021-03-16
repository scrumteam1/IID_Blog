package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.WriterPost;

import java.util.Date;
import java.util.List;

public interface WriterService {


    List<WriterPost> findWriterPostsByUserId(long userId);
    List<WriterPost> findAll();
    WriterPost save(WriterPost post);
    List<WriterPost> findOrderByCreationDate(Date date);
    WriterPost findByTitle(String title);
    void deleteById(Long writerPostId);
}
