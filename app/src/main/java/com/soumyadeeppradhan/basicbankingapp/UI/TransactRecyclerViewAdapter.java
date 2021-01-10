package com.soumyadeeppradhan.basicbankingapp.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soumyadeeppradhan.basicbankingapp.Activities.TransactMoney;
import com.soumyadeeppradhan.basicbankingapp.Model.BankHolders;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.util.List;

public class TransactRecyclerViewAdapter extends RecyclerView.Adapter<TransactRecyclerViewAdapter.ViewHolder> {

    private List<BankHolders> holdersList;
    private TransactMoney transact;

    public TransactRecyclerViewAdapter(TransactMoney transact, List<BankHolders> holdersList) {
        this.holdersList = holdersList;
        this.transact = transact;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transact_money_holder_row,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.holderName.setText(holdersList.get(position).getHolderName());
        holder.holderPhoneNum.setText("+"+holdersList.get(position).getHolderPhoneNumber());
        holder.holderAccBalance.setText("₹"+holdersList.get(position).getHolderAccbalance());
        holder.holderImage.setText(String.valueOf(holdersList.get(position).getHolderName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return holdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView holderName, holderPhoneNum, holderAccBalance,holderImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            holderName = itemView.findViewById(R.id.holderNameDet);
            holderPhoneNum = itemView.findViewById(R.id.holderPhoneNumberDet);
            holderAccBalance = itemView.findViewById(R.id.transactBalance);
            holderImage = itemView.findViewById(R.id.holderImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transact.selectHolder(getAdapterPosition());
                }
            });

        }

    }
}
