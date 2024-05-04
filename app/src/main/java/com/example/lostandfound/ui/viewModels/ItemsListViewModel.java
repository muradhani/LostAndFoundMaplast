package com.example.lostandfound.ui.viewModels;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.ViewModel;

import com.example.lostandfound.data.local.dao.CaseDao;
import com.example.lostandfound.data.local.database.AppDatabase;
import com.example.lostandfound.data.local.entity.CaseEntity;

import java.util.List;

public class ItemsListViewModel extends ViewModel {
    CaseDao dao ;
    private OnItemsLoadedListener listener;
    public void initiate(Context context, OnItemsLoadedListener listener) {
        dao = AppDatabase.getDatabase(context).caseDao();
        this.listener = listener;
    }
    public void getAllItemsAsync() {
        new AsyncTask<Void, Void, List<CaseEntity>>() {
            @Override
            protected List<CaseEntity> doInBackground(Void... voids) {
                return dao.getAll();
            }

            @Override
            protected void onPostExecute(List<CaseEntity> items) {
                if (listener != null) {
                    listener.onItemsLoaded(items);
                }
            }
        }.execute();
    }

}
