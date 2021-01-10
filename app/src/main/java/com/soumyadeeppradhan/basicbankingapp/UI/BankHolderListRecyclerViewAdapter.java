package com.soumyadeeppradhan.basicbankingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soumyadeeppradhan.basicbankingapp.Activities.BankHolderDetails;
import com.soumyadeeppradhan.basicbankingapp.Model.BankHolders;
import com.soumyadeeppradhan.basicbankingapp.R;

import java.util.List;

public class BankHolderListRecyclerViewAdapter extends RecyclerView.Adapter<BankHolderListRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<BankHolders> holders;

    public BankHolderListRecyclerViewAdapter(Context context, List<BankHolders> holdersList) {
        this.context = context;
        this.holders = holdersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_holder_row, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.holderName.setText(holders.get(position).getHolderName());
        holder.holderNumber.setText("+"+holders.get(position).getHolderPhoneNumber());
        holder.holderImage.setText(String.valueOf(holders.get(position).getHolderName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return holders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView holderName,holderNumber,holderImage;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            holderName = itemView.findViewById(R.id.holderNameDet);
            holderNumber = itemView.findViewById(R.id.holderPhoneNumberDet);
            holderImage = itemView.findViewById(R.id.holderImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    BankHolders user = holders.get(position);
                    Intent intent = new Intent(context, BankHolderDetails.class);
                    intent.putExtra("holderNumber",user.getHolderPhoneNumber());
                    context.startActivity(intent);
                }
            });
        }
    }
}
