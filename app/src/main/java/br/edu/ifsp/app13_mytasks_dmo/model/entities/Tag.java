package br.edu.ifsp.app13_mytasks_dmo.model.entities;

public class Tag {
    private String tagName;
    public Tag(String tagName) {
        this.tagName = tagName;
    }
    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

