package br.edu.ifsp.app13_mytasks_dmo.presenter;

import android.os.Bundle;
import br.edu.ifsp.app13_mytasks_dmo.model.dao.ITaskDao;
import br.edu.ifsp.app13_mytasks_dmo.model.dao.TaskDaoSingleton;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Task;
import br.edu.ifsp.app13_mytasks_dmo.utils.Constant;
import br.edu.ifsp.app13_mytasks_dmo.utils.mvp.TaskDetailsMVP;

public class TaskDetailsPresenter implements TaskDetailsMVP.Presenter {
    private TaskDetailsMVP.View view;
    private Task task;
    private ITaskDao dao;

    public TaskDetailsPresenter(TaskDetailsMVP.View view) {
        this.view = view;
        task = null;
        dao = TaskDaoSingleton.getInstance();
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate() {
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constant.ATTR_TITLE);
            task = dao.findByTitle(title);
            view.updateUI(task.getTitle(), task.getDescription(), task.getCreationDate());
        }
    }

    @Override
    public void saveTask(String title, String description, String creationdate) {

        if(task == null){
            //New task
            task = new Task(title, description, creationdate);
            dao.create(task);
            view.showToast("Nova tarefa adicionada com sucesso.");
            view.close();
        }else{
            //Update a existent task
            String oldTitle = task.getTitle();
            Task newTask = new Task(title, description, creationdate, task.isPriority());
            if(dao.update(oldTitle, newTask)){
                view.showToast("Tarefa atualizada com sucesso.");
                view.close();
            }else{
                view.showToast("Erro ao atualizar a tarefa.");
            }
        }
    }
}