package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.WriterPost;

import java.util.List;

public interface WriterService {


    List<WriterPost> findWriterPostsByUserId(long userId);
    List<WriterPost> findAll();
}
