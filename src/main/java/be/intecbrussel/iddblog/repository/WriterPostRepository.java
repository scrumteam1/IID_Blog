package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WriterPostRepository extends JpaRepository<WriterPost, Long> {

    List<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor);

    @Query("select p from WriterPost p where " +
            "concat(p.title)" +
            " like %:keyword% and p.registeredVisitor = :visitor")
    Page<WriterPost> findWriterPostsByRegisteredVisitor(@Param("visitor") RegisteredVisitor visitor, @Param("keyword") String keyword, Pageable pageable);

    Page<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor, Pageable pageable);

    List<WriterPost> findAll();
    WriterPost save(WriterPost post);
    List<WriterPost> findOrderByCreationDate(Date creationDate);
    WriterPost findByTitle(String title);

    @Override
    void deleteById(Long writerPostId);
}
