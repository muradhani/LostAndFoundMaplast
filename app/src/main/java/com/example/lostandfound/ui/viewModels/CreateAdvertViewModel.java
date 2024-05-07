package com.example.lostandfound.ui.viewModels;

import android.os.AsyncTask;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lostandfound.data.local.dao.CaseDao;
import com.example.lostandfound.data.local.database.AppDatabase;
import com.example.lostandfound.data.local.entity.CaseEntity;

public class CreateAdvertViewModel extends ViewModel {
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> date = new MutableLiveData<>();
    public MutableLiveData<String> location = new MutableLiveData<>();
    CaseDao dao ;
    private OnEntitySavedListener listener;
    public void initiate(Context context,OnEntitySavedListener listener) {
        dao = AppDatabase.getDatabase(context).caseDao();
        this.listener = listener;
    }

    public void saveEntity(final CaseEntity entity) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.insert(entity);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (listener != null) {
                    listener.onEntitySaved();
                }
            }
        }.execute();
    }
}
