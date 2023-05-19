package br.edu.ifsp.app13_mytasks_dmo.model.dao;

import java.util.List;

import br.edu.ifsp.app13_mytasks_dmo.model.entities.Tag;

public interface ITagDao{

    void create(Tag tag);

    boolean delete(Tag tag);

    Tag find(String tagName);

    List<Tag> findAll();
}