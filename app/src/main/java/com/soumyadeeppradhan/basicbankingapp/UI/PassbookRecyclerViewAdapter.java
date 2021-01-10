package com.soumyadeeppradhan.basicbankingapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soumyadeeppradhan.basicbankingapp.Activities.Passbook;
import com.soumyadeeppradhan.basicbankingapp.Model.BankHolders;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.util.List;

public class PassbookRecyclerViewAdapter extends RecyclerView.Adapter<PassbookRecyclerViewAdapter.ViewHolder> {

    private List<BankHolders> holderList;
    private Context context;
    private Passbook passbook;

    public PassbookRecyclerViewAdapter(Passbook passbook, List<BankHolders> holdersList, Context context) {
        this.holderList = holdersList;
        this.passbook = passbook;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passbook_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.toUser.setText(holderList.get(position).getFromUserName());
        holder.fromUser.setText(holderList.get(position).getToUserName());
        holder.dateOfTransaction.setText(holderList.get(position).getDateOfTransaction());
        holder.transactionID.setText("Transaction ID: #"+ holderList.get(position).getTransactionID());

        if(holderList.get(position).getTransactionStatus().equals("Failed")){
            holder.transferAmount.setText("₹"+ holderList.get(position).getHolderAccbalance());
            holder.transferAmount.setTextColor(Color.GRAY);
            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.cross));
        }
        else if (holderList.get(position).getTransactionStatus().equals("Add")){
            holder.transferAmount.setText("+ ₹"+ holderList.get(position).getHolderAccbalance());
            holder.transferAmount.setTextColor(Color.parseColor("#4BB543"));
            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
        }
            else{
            holder.transferAmount.setText("- ₹"+ holderList.get(position).getHolderAccbalance());
            holder.transferAmount.setTextColor(Color.parseColor("#f40404"));
            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
        }
    }

    @Override
    public int getItemCount() {
        return holderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView transferAmount, toUser, fromUser, dateOfTransaction, transactionID;
        public ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                transferAmount = (TextView) itemView.findViewById(R.id.transactAmount);
                toUser = (TextView) itemView.findViewById(R.id.fromUser);
                fromUser = (TextView) itemView.findViewById(R.id.toUser);
                dateOfTransaction = (TextView) itemView.findViewById(R.id.dateOfTransaction);
                transactionID = (TextView) itemView.findViewById(R.id.transactionID);
                img = (ImageView) itemView.findViewById(R.id.statusImage);
        }
    }
}
