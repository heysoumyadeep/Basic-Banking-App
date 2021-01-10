package com.soumyadeeppradhan.basicbankingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.soumyadeeppradhan.basicbankingapp.R;

public class TransactionSuccessPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_success_page);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(TransactionSuccessPage.this, Passbook.class));
                finish();
            }
        }, 2300);
    }


}