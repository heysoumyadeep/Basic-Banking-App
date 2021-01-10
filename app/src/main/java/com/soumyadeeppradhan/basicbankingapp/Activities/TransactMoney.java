package com.soumyadeeppradhan.basicbankingapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soumyadeeppradhan.basicbankingapp.UI.TransactRecyclerViewAdapter;
import com.soumyadeeppradhan.basicbankingapp.Data.DataBaseHandler;
import com.soumyadeeppradhan.basicbankingapp.Model.BankHolders;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class TransactMoney extends AppCompatActivity {

    private List<BankHolders> holdersTransact = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TransactRecyclerViewAdapter adapter;

    private String holderPhoneNumber, fromHolder, currentBalance,netBalance, transactAmount;
    private String phSelected, toHolder, holderBalanceSelected, dateOfTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact_money);

        recyclerView = findViewById(R.id.recyclerViewPassbook);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");
        dateOfTransaction = simpleDateFormat.format(calendar.getTime());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            holderPhoneNumber = bundle.getString("holderPhoneNum");
            fromHolder = bundle.getString("holderName");
            currentBalance = bundle.getString("holderAccBalance");
            transactAmount = bundle.getString("transferAmount");
            showData(holderPhoneNumber);

        }
    }

    private void showData(String phonenumber) {
        holdersTransact.clear();
        Cursor cursor = new DataBaseHandler(this).removeHolder(phonenumber);
        while(cursor.moveToNext()){

            Double holderBalance = Double.parseDouble(cursor.getString(6));

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            BankHolders holders = new BankHolders(cursor.getString(0),
                    cursor.getString(1), nf.format(holderBalance));
            holdersTransact.add(holders);
        }

        adapter = new TransactRecyclerViewAdapter(TransactMoney.this, holdersTransact);
        recyclerView.setAdapter(adapter);
    }

    public void selectHolder(int position) {
        phSelected = holdersTransact.get(position).getHolderPhoneNumber();
        Cursor cursor = new DataBaseHandler(this).holderdata(phSelected);
        while(cursor.moveToNext()) {
            toHolder = cursor.getString(1);
            holderBalanceSelected = cursor.getString(6);
            Double currentBalance = Double.parseDouble(holderBalanceSelected);
            Double transactAmount = Double.parseDouble(this.transactAmount);
            Double netBalance = currentBalance + transactAmount;

            long randomNum = new Random().nextInt((9999999 - 1000000) + 1) + 1000000;

            new DataBaseHandler(this).transactionStatement(dateOfTransaction,
                    fromHolder, toHolder, this.transactAmount, "Success",randomNum);

            /*Log.d("ABC",this.transactAmount);
            Log.d("randomNum",String.valueOf(randomNum));*/

            new DataBaseHandler(this).updateBalance(phSelected, netBalance.toString());
            calculateNetBalance();
            startActivity(new Intent(TransactMoney.this, TransactionSuccessPage.class));
            finish();
        }
    }

    private void calculateNetBalance() {
        Double currentBalance = Double.parseDouble(this.currentBalance);
        Double transactAmount = Double.parseDouble(this.transactAmount);
        Double netBalance = currentBalance - transactAmount;
        this.netBalance = netBalance.toString();
        new DataBaseHandler(this).updateBalance(holderPhoneNumber, this.netBalance);
    }

    @Override
    public void onBackPressed() {

        Random rn = new Random();
        long randomNum = rn.nextInt((9999999 - 1000000) + 1) + 1000000;

        AlertDialog.Builder closeButton = new AlertDialog.Builder(TransactMoney.this);
        closeButton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new DataBaseHandler(TransactMoney.this).transactionStatement(dateOfTransaction,
                                fromHolder, "None", transactAmount, "Failed",randomNum);
                        Toasty.error(TransactMoney.this, "Transaction Failed",
                                Toast.LENGTH_LONG, true).show();
                        startActivity(new Intent(TransactMoney.this, Passbook.class));
                        finish();
                    }
                }).setNegativeButton("NO", null);
        AlertDialog exit = closeButton.create();
        exit.show();
    }
}
