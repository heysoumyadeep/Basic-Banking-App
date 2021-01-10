package com.soumyadeeppradhan.basicbankingapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soumyadeeppradhan.basicbankingapp.UI.BankHolderListRecyclerViewAdapter;
import com.soumyadeeppradhan.basicbankingapp.Data.DataBaseHandler;
import com.soumyadeeppradhan.basicbankingapp.Model.BankHolders;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.util.ArrayList;
import java.util.List;

public class BankHolderList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BankHolderListRecyclerViewAdapter recyclerViewAdapter;
    private List<BankHolders> holders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_holder_list);

        recyclerView = findViewById(R.id.recyclerViewBankHolder);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        holderRowShow();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BankHolderList.this, Passbook.class));
            }
        });

    }

    private void holderRowShow() {
        holders.clear();
        Cursor cursor = new DataBaseHandler(this).showAllHolders();

        while(cursor.moveToNext()){
            BankHolders holder = new BankHolders(cursor.getString(0), cursor.getString(1));
            this.holders.add(holder);
        }

        recyclerViewAdapter = new BankHolderListRecyclerViewAdapter(BankHolderList.this, holders);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
