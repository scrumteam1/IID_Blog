package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface WriterService {


    List<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor);

    Page<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor,String keyword, int pageNumber, String sortField, String sortDir);

    WriterPost save(WriterPost post);
    List<WriterPost> findOrderByCreationDate(Date date);
    void deleteById(Long writerPostId);

    void updateWriterPost(WriterPost post);

    WriterPost findById(Long id);
}
