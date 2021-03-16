package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriterPostRepository extends CrudRepository<WriterPost, Long> {
    List<WriterPost> findWriterPostsByUserId(Long userId);
    List<WriterPost> findAll();

    @Override
    void deleteById(Long writerPostId);
}
