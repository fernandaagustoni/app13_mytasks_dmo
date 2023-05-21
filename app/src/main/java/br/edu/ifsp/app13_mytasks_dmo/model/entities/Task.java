package br.edu.ifsp.app13_mytasks_dmo.model.entities;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task implements Comparable<Task>{
    private String title;
    private String description;
    private String creationDate;
    private boolean priority;
    //Relationships
    private List<Tag> tags;
    private void init(){
        tags = new ArrayList<>();
    }
    public Task(String title, String description, String creationDate) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        init();
    }
    public Task(String title, String description, String creationDate, boolean priority) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.priority = priority;
        init();
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String url) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCreationDate(){ return creationDate; }
    public void setCreationDate(String creationDate){ this.creationDate = creationDate; }
    public boolean isPriority() {
        return priority;
    }
    public void setPriority(boolean priority) {
        this.priority = priority;
    }
    public void addTag(Tag tag){
        this.tags.add(tag);
    }
    public boolean removeTag(Tag tag){
        return this.tags.remove(tag);
    }
    public List<Tag> getTags(){
        return tags;
    }
    @NonNull
    @Override
    public String toString() {
        return "Title: " + title;
    }
    @Override
    public int compareTo(Task task) {
        return Comparator.comparing(Task::isPriority).reversed().compare(this, task);
    }
}
