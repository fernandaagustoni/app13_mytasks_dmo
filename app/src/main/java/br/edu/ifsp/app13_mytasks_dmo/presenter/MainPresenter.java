package br.edu.ifsp.app13_mytasks_dmo.presenter;

import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.edu.ifsp.app13_mytasks_dmo.model.dao.ITaskDao;
import br.edu.ifsp.app13_mytasks_dmo.model.dao.TaskDaoSingleton;
import br.edu.ifsp.app13_mytasks_dmo.model.entities.Task;
import br.edu.ifsp.app13_mytasks_dmo.utils.mvp.MainMVP;
import br.edu.ifsp.app13_mytasks_dmo.utils.Constant;
import br.edu.ifsp.app13_mytasks_dmo.view.RecyclerViewItemClickListener;
import br.edu.ifsp.app13_mytasks_dmo.view.TaskDetailsActivity;
import br.edu.ifsp.app13_mytasks_dmo.view.adapter.ItemPocketRecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter {
    private MainMVP.View view;
    private ITaskDao dao;
    Task task;

    public MainPresenter(MainMVP.View view) {
        this.view = view;
        dao = TaskDaoSingleton.getInstance();
    }
    @Override
    public void deatach() {
        view = null;
    }

    @Override
    public void openDetails() {
        Intent intent = new Intent(view.getContext(), TaskDetailsActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openDetails(Task task) {
        Intent intent = new Intent(view.getContext(), TaskDetailsActivity.class);
        intent.putExtra(Constant.ATTR_TITLE, task.getTitle());
        view.getContext().startActivity(intent);
    }

    @Override
    public void populateList(RecyclerView recyclerView) {
        ItemPocketRecyclerAdapter adapter = new
                ItemPocketRecyclerAdapter(view.getContext(), dao.findAll(), this);
        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                task = dao.findAll().get(position);
                openDetails(task);
            }
        });
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void favoriteArticle(Task task) {
        task.setPriority(!task.isPriority());
        dao.update(task.getTitle(), task);
    }
}
