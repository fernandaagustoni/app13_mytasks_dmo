package br.edu.ifsp.app13_mytasks_dmo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.edu.ifsp.app13_mytasks_dmo.R;
import br.edu.ifsp.app13_mytasks_dmo.presenter.TaskDetailsPresenter;
import br.edu.ifsp.app13_mytasks_dmo.mvp.TaskDetailsMVP;

public class TaskDetailsActivity extends AppCompatActivity
        implements TaskDetailsMVP.View, View.OnClickListener {
    private TaskDetailsMVP.Presenter presenter;
    private EditText descriptionEditText;
    private EditText titleEditText;
    private EditText creationdateEditText;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        presenter = new TaskDetailsPresenter(this);
        findViews();
        setListener();
        setToolbar();
    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter.verifyUpdate();
    }
    @Override
    protected void onDestroy() {
        presenter.deatach();
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        if(v == saveButton){
            presenter.saveTask(
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    creationdateEditText.getText().toString());
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void updateUI(String title, String description, String creationdate) {
        titleEditText.setText(title);
        descriptionEditText.setText(description);
        creationdateEditText.setText(creationdate);
    }
    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void close() {
        presenter.deatach();
        finish();
    }
    private void findViews(){
        titleEditText = findViewById(R.id.edittext_title_details);
        descriptionEditText = findViewById(R.id.edittext_description_details);
        creationdateEditText = findViewById(R.id.edittext_creationdate_details);
        saveButton = findViewById(R.id.button_save_task);
    }
    private void setListener(){
        saveButton.setOnClickListener(this);
    }
    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}