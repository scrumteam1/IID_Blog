package be.intecbrussel.iddblog.service;

import be.intecbrussel.iddblog.domain.Tag;
import be.intecbrussel.iddblog.domain.Tagname;

import java.util.List;
import java.util.Set;

public interface TagService {

    List<Tag> findAll();
}
