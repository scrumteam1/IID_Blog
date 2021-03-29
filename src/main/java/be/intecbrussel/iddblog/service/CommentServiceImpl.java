package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Comment;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByWriterPost(WriterPost writerPost) {


        return commentRepository.findCommentsByWriterPost(writerPost);
    }

    @Override
    public List<Comment> findCommentsByWriterPostOrderByCreationDate(WriterPost writerPost) {
        List<Comment> commentsByDateReversed = commentRepository.findCommentsByWriterPost(writerPost)
                .stream()
                .sorted(Comparator.comparing(Comment::getCreationDate))
                .collect(Collectors.toList());
        Collections.reverse(commentsByDateReversed);
        return commentsByDateReversed;
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
