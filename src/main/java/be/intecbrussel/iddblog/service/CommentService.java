package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Comment;
import be.intecbrussel.iddblog.domain.WriterPost;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);
    List<Comment> findCommentsByWriterPost(WriterPost writerPost);
    List<Comment> findCommentsByWriterPostOrderByCreationDate(WriterPost writerPost);
    void deleteById(Long id);
}
