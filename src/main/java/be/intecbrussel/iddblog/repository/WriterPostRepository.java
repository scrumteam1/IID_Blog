package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WriterPostRepository extends JpaRepository<WriterPost, Long> {

    List<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor);

    @Query(value = "select distinct p.* from `writerposts` p " +
            "left join `users` u on u.id = p.user_id " +
            "left join `writerposts_tags` wt on p.id = wt.post_id " +
            "left join `tags` t on t.id = wt.tag_id " +
            "where u.id = :visitor and (p.title like concat('%',:keyword,'%') or t.tag like concat('%',:keyword,'%'))" ,
            nativeQuery = true)
    Page<WriterPost> findWriterPostsByRegisteredVisitor(@Param("visitor") RegisteredVisitor visitor, @Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select p.* from `writerposts` p left join `users` u on u.id = p.user_id where u.id = :visitor",
            nativeQuery = true)
    Page<WriterPost> findWriterPostsByRegisteredVisitor(@Param("visitor") RegisteredVisitor visitor, Pageable pageable);

    @Query(value = "select distinct p.* from `writerposts` p " +
            "left join `users` u on u.id = p.user_id " +
            "left join `writerposts_tags` wt on p.id = wt.post_id " +
            "left join `tags` t on t.id = wt.tag_id " +
            "where p.title like concat('%',:keyword,'%') or t.tag like concat('%',:keyword,'%') or u.username like concat('%',:keyword,'%')" ,
            nativeQuery = true)
    Page<WriterPost>findWriterPostsByRegisteredVisitor(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("update WriterPost u set u.title = :title, u.intro = :intro, u.content = :content where u.id = :id")
    void updateWriterPost(@Param(value = "id") Long id, @Param(value = "title") String title,
                                 @Param(value = "intro") String intro, @Param(value = "content") String content);

    List<WriterPost> findAll();
    WriterPost save(WriterPost post);
    List<WriterPost> findOrderByCreationDate(Date creationDate);
    @Override
    void deleteById(Long writerPostId);

    Optional<WriterPost> findById(Long id);
}
