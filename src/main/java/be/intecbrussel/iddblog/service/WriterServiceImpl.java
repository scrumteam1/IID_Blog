package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.repository.WriterPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WriterServiceImpl implements WriterService{

    private final WriterPostRepository writerPostRepository;

    public WriterServiceImpl(WriterPostRepository writerPostRepository) {
        this.writerPostRepository = writerPostRepository;
    }

    @Override
    public List<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor){
        return writerPostRepository.findWriterPostsByRegisteredVisitor(visitor);
    }

    public Page<WriterPost> findWriterPostsByRegisteredVisitor(RegisteredVisitor visitor, String keyword, int pageNumber, String sortField, String sortDir) {
        sortField = sortField.equals("creationDate") ? "creation_date" : sortField;
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1 ,6, sort);

        if(keyword != null && !keyword.equals("")) {
            return writerPostRepository.findWriterPostsByRegisteredVisitor(visitor,keyword, pageable);
        }

        return writerPostRepository.findWriterPostsByRegisteredVisitor(visitor,pageable);
    }


    @Override
    public WriterPost save(WriterPost post) {
        post.setCreationDate(LocalDate.now());
        return writerPostRepository.save(post);
    }

    @Override
    public List<WriterPost> findOrderByCreationDate(Date date) {
       List<WriterPost> list = writerPostRepository.findAll().stream().sorted(Comparator.comparing(WriterPost::getCreationDate))
                .collect(Collectors.toList());
       Collections.reverse(list);
       return list;
    }

    @Override
    public void deleteById(Long writerPostId) {
        writerPostRepository.deleteById(writerPostId);
    }

    @Override
    public WriterPost findById(Long id) {
        return writerPostRepository.findById(id).orElse(null);
    }
}
