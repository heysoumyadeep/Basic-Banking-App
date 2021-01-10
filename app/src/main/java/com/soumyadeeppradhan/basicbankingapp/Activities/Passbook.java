package com.soumyadeeppradhan.basicbankingapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.soumyadeeppradhan.basicbankingapp.UI.PassbookRecyclerViewAdapter;
import com.soumyadeeppradhan.basicbankingapp.Data.DataBaseHandler;
import com.soumyadeeppradhan.basicbankingapp.Model.BankHolders;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Passbook extends AppCompatActivity {
    private List<BankHolders> holdersPassbook = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PassbookRecyclerViewAdapter adapter;

    private TextView empty_passbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook);

        recyclerView = findViewById(R.id.recyclerViewPassbook);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        empty_passbook = findViewById(R.id.noTransaction);

        FloatingActionButton fab = findViewById(R.id.fabInfo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Passbook.this, AppInfo.class));
            }
        });

        showData();
    }

    private void showData() {

        holdersPassbook.clear();
        Cursor cursor = new DataBaseHandler(this).getPassbook();

        while (cursor.moveToNext()) {
            String balancefromdb = cursor.getString(4);
            Double balance = Double.parseDouble(balancefromdb);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            String price = nf.format(balance);

            BankHolders holders = new BankHolders(cursor.getString(2),
                    cursor.getString(3), price, cursor.getString(1),
                    cursor.getString(5), cursor.getString(6));
            holdersPassbook.add(holders);
        }

        adapter = new PassbookRecyclerViewAdapter(Passbook.this, holdersPassbook,getApplicationContext());
        recyclerView.setAdapter(adapter);

        if(holdersPassbook.size() == 0){
            empty_passbook.setVisibility(View.VISIBLE);
        }
    }
}
