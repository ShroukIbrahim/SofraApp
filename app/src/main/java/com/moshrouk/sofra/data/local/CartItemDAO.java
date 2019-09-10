package com.moshrouk.sofra.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface CartItemDAO {

        @Insert
        void insert(Item... items);

        @Query("update itemsOrder set quantity = :q  where idItems = :id")
        void update(int id,int q);


        @Query("DELETE FROM itemsOrder ")
        void deleteAll();

        @Query("DELETE FROM itemsOrder where idItems = :idItems")
        void delete(int idItems);


        @Query("SELECT * FROM itemsOrder")
        List<Item> getItems();

        @Query("SELECT * FROM itemsOrder WHERE state = '0'")
        List<Item> getItemByIdRestaurant();



    }

