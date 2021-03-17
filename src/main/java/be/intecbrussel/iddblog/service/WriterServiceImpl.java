package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.repository.WriterPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class WriterServiceImpl implements WriterService{

    private final WriterPostRepository writerPostRepository;

    public WriterServiceImpl(WriterPostRepository writerPostRepository) {
        this.writerPostRepository = writerPostRepository;
    }

    @Override
    public List<WriterPost> findWriterPostsByUserId(long userId){
        return writerPostRepository.findWriterPostsByUserId(userId);
    }

    @Override
    public List<WriterPost> findAll() {
        return writerPostRepository.findAll();
    }
    @Override
    public WriterPost save(WriterPost post) {
        return writerPostRepository.save(post);
    }
    @Override
    public List<WriterPost> findOrderByCreationDate(Date date) {
        return writerPostRepository.findAll().stream().sorted(Comparator.comparing(WriterPost::getCreationDate).reversed())
                .collect(Collectors.toList());
    }
    @Override
    public WriterPost findByTitle(String title){
        return writerPostRepository.findByTitle(title);
    }

    @Override
    public void deleteById(Long writerPostId) {
        writerPostRepository.deleteById(writerPostId);
    }
}
