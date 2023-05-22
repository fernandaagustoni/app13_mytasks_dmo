package br.edu.ifsp.app13_mytasks_dmo.model.dao;

import android.content.Context;

import java.util.List;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Tag;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Task;
public interface ITaskDao {
    void create(Task task);
    boolean update(String oldTitle, Task task);
    boolean delete(Task task);
    Task findByTitle(String title);
    List<Task> findByTag(Tag tag);
    List<Task> findAll();
    void setContext (Context context);
}
