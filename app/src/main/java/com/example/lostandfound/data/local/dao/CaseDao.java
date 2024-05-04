package com.example.lostandfound.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lostandfound.data.local.entity.CaseEntity;

import java.util.List;

@Dao
public interface CaseDao {
    @Query("SELECT * FROM `case`")
    List<CaseEntity> getAll();

    @Insert
    void insert(CaseEntity entity);

    @Delete
    void delete(CaseEntity entity);

    @Query("DELETE FROM `case` WHERE name = :name")
    void deleteById(String name);

    // You can add more queries here as needed
}