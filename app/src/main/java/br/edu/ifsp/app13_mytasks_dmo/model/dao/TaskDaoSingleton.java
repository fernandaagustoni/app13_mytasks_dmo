package br.edu.ifsp.app13_mytasks_dmo.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.app13_mytasks_dmo.model.entities.Tag;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Task;

public class TaskDaoSingleton implements ITaskDao{
    private static TaskDaoSingleton instance = null;
    private List<Task> dataset;

    private TaskDaoSingleton() {
        dataset = new ArrayList<>();
    }

    public static TaskDaoSingleton getInstance(){
        if(instance == null)
            instance = new TaskDaoSingleton();
        return instance;
    }

    @Override
    public void create(Task task) {
        if(task != null){
            dataset.add(task);
        }
    }

    @Override
    public boolean update(String oldTitle, Task task) {
        Task inDataset;
        inDataset = dataset.stream()
                .filter(article1 -> article1.getTitle().equals(oldTitle))
                .findAny()
                .orElse(null);
        if(inDataset != null){
            inDataset.setTitle(task.getTitle());
            inDataset.setDescription(task.getDescription());
            inDataset.setCreationDate(task.getCreationDate());
            inDataset.setPriority(task.isPriority());
            inDataset.getTags().clear();
            inDataset.getTags().addAll(task.getTags());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Task task) {
        return dataset.remove(task);
    }

    @Override
    public Task findByTitle(String title) {
        return dataset.stream()
                .filter(article -> article.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Task> findByTag(Tag tag) {
        List<Task> selection = new ArrayList<>();
        for(Task a : dataset){
            for(Tag t : a.getTags()){
                if(t.getTagName().equals(tag.getTagName())){
                    selection.add(a);
                }
            }
        }
        return selection;
    }

    @Override
    public List<Task> findAll() {
        return dataset;
    }
}
