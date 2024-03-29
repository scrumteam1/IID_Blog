package be.intecbrussel.iddblog.repository;

import be.intecbrussel.iddblog.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag save(Tag tag);

    List<Tag> findAll();
}
