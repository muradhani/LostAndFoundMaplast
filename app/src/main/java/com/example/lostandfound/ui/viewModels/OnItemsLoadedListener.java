package com.example.lostandfound.ui.viewModels;

import com.example.lostandfound.data.local.entity.CaseEntity;

import java.util.List;

public interface OnItemsLoadedListener {
    void onItemsLoaded(List<CaseEntity> items);
}
