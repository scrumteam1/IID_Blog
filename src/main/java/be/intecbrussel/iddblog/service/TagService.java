package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findAll();
}
