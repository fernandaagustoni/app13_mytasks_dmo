package br.edu.ifsp.app13_mytasks_dmo.model.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifsp.app13_mytasks_dmo.model.entities.Tag;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Task;
import br.edu.ifsp.app13_mytasks_dmo.utils.Constant;

public class TaskDaoSingleton implements ITaskDao{
    private static TaskDaoSingleton instance = null;
    private List<Task> dataset;
    private Context context;
    private TaskDaoSingleton() {
        dataset = new ArrayList<>();
    }
    public static TaskDaoSingleton getInstance(){
        if(instance == null)
            instance = new TaskDaoSingleton();
        return instance;
    }
    public void setContext(Context context){
        this.context = context;
    }
    @Override
    public void create(Task task) {
        if(task != null){
            dataset.add(task);
            Collections.sort(dataset);
            writeDataset();
            readDatabase();
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
            Collections.sort(dataset);
            writeDataset();
            readDatabase();
            return true;
        }
        return false;
    }
    @Override
    public boolean delete(Task task) {
        dataset.remove(task);
        writeDataset();
        readDatabase();
        return true;
    }
    @Override
    public Task findByTitle(String title) {
        return dataset.stream()
                .filter(task -> task.getTitle().equals(title))
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
        readDatabase();
        return dataset;
    }

    private void writeDataset(){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        JSONObject jsonObject;
        JSONArray jsonArray;

        jsonArray = new JSONArray();
        for(Task t : dataset){
            jsonObject = new JSONObject();
            try{
                jsonObject.put(Constant.ATTR_TITLE, t.getTitle());
                jsonObject.put(Constant.ATTR_DESCRIPTION, t.getDescription());
                jsonObject.put(Constant.ATTR_DATE, t.getCreationDate());
                jsonObject.put(Constant.ATTR_PRIORITY, t.isPriority());
                jsonArray.put(jsonObject);
            }catch (JSONException e){
                Log.e("Erro", e.getMessage());
            }
        }
        preferences = context.getSharedPreferences(Constant.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(Constant.TABLE_NAME, jsonArray.toString());
        editor.commit();

    }
    private void readDatabase(){
        SharedPreferences preferences;
        String json;
        Task task;
        JSONObject jsonObject;
        JSONArray jsonArray;

        preferences = context.getSharedPreferences(Constant.DATABASE_FILE_NAME, Context.MODE_PRIVATE);
        json = preferences.getString(Constant.TABLE_NAME, "");

        if(!json.isEmpty()){
            dataset.clear();
            try{
                jsonArray = new JSONArray(json);
                for(int i=0; i < jsonArray.length(); i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    task = new Task(jsonObject.getString(Constant.ATTR_TITLE), jsonObject.getString(Constant.ATTR_DESCRIPTION), jsonObject.getString(Constant.ATTR_DATE), jsonObject.getBoolean(Constant.ATTR_PRIORITY));
                    dataset.add(task);
                }
            }catch (JSONException e){
                Log.e("TaskDAOJson", e.getMessage());
            }
        }else{
            Log.v("TaskDAOJson", "Sem dados do JSON");
        }
    }
}
