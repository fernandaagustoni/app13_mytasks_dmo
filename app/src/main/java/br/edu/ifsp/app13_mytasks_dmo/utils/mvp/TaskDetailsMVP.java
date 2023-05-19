package br.edu.ifsp.app13_mytasks_dmo.utils.mvp;

import android.os.Bundle;

public interface TaskDetailsMVP {

    interface View{
        void updateUI(String title, String description, String creationdate);

        Bundle getBundle();

        void showToast(String message);

        void close();
    }

    interface Presenter{
        void deatach();

        void verifyUpdate();

        void saveTask(String title, String description,String creationdate);
    }
}
