package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WriterPostRepository extends CrudRepository<WriterPost, Long> {
    List<WriterPost> findWriterPostsByUserId(Long userId);
    List<WriterPost> findAll();
    WriterPost save(WriterPost post);
    List<WriterPost> findOrderByCreationDate(Date creationDate);
    WriterPost findByTitle(String title);

    @Override
    void deleteById(Long writerPostId);
}
