package com.example.lostandfound.ui.viewModels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.lostandfound.data.local.dao.CaseDao;
import com.example.lostandfound.data.local.database.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoveItemViewModel extends ViewModel {
    CaseDao dao ;
    private OnItemRemovedListener listener;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void initiate(Context context, OnItemRemovedListener listener) {
        dao = AppDatabase.getDatabase(context).caseDao();
        this.listener = listener;
    }
    public void removeItemByNameAsync(String name) {
        executorService.execute(() -> {
            dao.deleteById(name);
            // Notify listener that item is removed
            if (listener != null) {
                listener.onItemRemoved();
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // Shutdown the ExecutorService when the ViewModel is no longer used
        executorService.shutdown();
    }
}
