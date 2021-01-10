package com.soumyadeeppradhan.basicbankingapp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.soumyadeeppradhan.basicbankingapp.Data.DataBaseHandler;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import es.dmoral.toasty.Toasty;


public class BankHolderDetails extends AppCompatActivity {

    private TextView holderName, holderNumber, holderEmail, holderAccNum, holderBank, ifscCode, holderBalance,holderImage;
    private Button transactButton, addBalanceButton;
    private EditText moneyToTransact;
    private AlertDialog dialog;
    private Bundle bundle;
    private Double accBalance;
    private String holderPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_holder_details);

        holderName = (TextView) findViewById(R.id.holderNameDet);
        holderNumber =  (TextView) findViewById(R.id.holderPhoneNumberDet);
        holderEmail = (TextView) findViewById(R.id.holderEmailDet);
        holderAccNum = (TextView) findViewById(R.id.accountNumDet);
        ifscCode = (TextView) findViewById(R.id.ifscCode);
        holderBalance = (TextView) findViewById(R.id.transactAmount);
        holderBank = (TextView) findViewById(R.id.bankName);
        transactButton = (Button) findViewById(R.id.selectUserButton);
        addBalanceButton = (Button) findViewById(R.id.addMoneyButton) ;
        moneyToTransact = (EditText) findViewById(R.id.enter_money);
        holderImage = (TextView) findViewById(R.id.holderImageDet);

        bundle = getIntent().getExtras();
        if(bundle != null){
            holderPhoneNumber = bundle.getString("holderNumber");
            showData(holderPhoneNumber);
        }

        transactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactAmount();
            }
        });

        addBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBalance();
            }
        });
    }

    private void showData(String holderPhoneNumber) {
        Cursor cursor = new DataBaseHandler(this).holderdata(holderPhoneNumber);
        while(cursor.moveToNext()) {

            holderNumber.setText("+"+cursor.getString(0));
            holderName.setText(cursor.getString(1));
            holderImage.setText(String.valueOf(cursor.getString(1).charAt(0)));
            holderEmail.setText(cursor.getString(2));
            holderAccNum.setText("Account Number: "+cursor.getString(3));
            holderBank.setText("Bank: "+cursor.getString(4));
            ifscCode.setText("IFSC Code: "+cursor.getString(5));
            accBalance = Double.parseDouble(cursor.getString(6));

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setGroupingUsed(true);
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);

            holderBalance.setText("Account Balance: ₹"+nf.format(accBalance));
        }

    }

    private void transactAmount() {
        if(moneyToTransact.getText().toString().isEmpty()){
            Toasty.info(BankHolderDetails.this,
                    "Amount Can't be Empty", Toast.LENGTH_LONG, true).show();
        }else if(Double.parseDouble(moneyToTransact.getText().toString()) > accBalance){
            Toasty.warning(BankHolderDetails.this,
                    "You don't have enough Balance to Transact", Toast.LENGTH_LONG, true).show();
        }else{
            Intent intent = new Intent(BankHolderDetails.this, TransactMoney.class);
            intent.putExtra("holderPhoneNum", holderNumber.getText().toString());
            intent.putExtra("holderName", holderName.getText().toString());
            intent.putExtra("holderAccBalance", accBalance.toString());
            intent.putExtra("transferAmount", moneyToTransact.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    private void addBalance() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BankHolderDetails.this);
        View view = getLayoutInflater().inflate(R.layout.activity_add_balance, null);
        builder.setTitle("Enter the Amount to Add").setView(view).setCancelable(false);

        final EditText add_money = (EditText) view.findViewById(R.id.add_money_editText);

        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(add_money.getText().toString().isEmpty()){
                    Toasty.warning(BankHolderDetails.this, "Amount Can't be Empty",
                            Toast.LENGTH_LONG, true).show();
                } else {
                    Double currentBalance = Double.parseDouble(accBalance.toString());
                    Double addAmount = Double.parseDouble(add_money.getText().toString());
                    Double totalBalance = currentBalance + addAmount;

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a");

                    long TransactionID = new Random().nextInt((9999999 - 1000000) + 1) + 1000000;
                    new DataBaseHandler(BankHolderDetails.this).updateBalance(holderPhoneNumber, totalBalance.toString());
                    new DataBaseHandler(BankHolderDetails.this).transactionStatement(
                            simpleDateFormat.format(calendar.getTime()),
                            holderName.getText().toString(), "Add Balance",
                            totalBalance.toString(), "Add", TransactionID);
                    startActivity(getIntent());
                    finish();
                    Toasty.success(BankHolderDetails.this,
                            "₹"+addAmount+" is Successfully Added to your Account.",
                            Toast.LENGTH_LONG, true).show();
                }
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
