package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.Comment;
import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByWriterPost(WriterPost writerPost);

    List<Comment> findCommentsByRegisteredVisitor();

    Comment save(Comment comment);


    @Override
    void deleteById(Long id);
}
