package com.victory.game.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;

import com.victory.game.models.UserRecordModel;

import java.util.List;

public class RecordWinAdapter extends RecyclerView.Adapter<RecordWinAdapter.RecordWinViewHolder>{

    private List<UserRecordModel> recordGameList;
    private Context context;

    private int currentPage;
    private static final int PAGE_SIZE = 8;
    public RecordWinAdapter(List<UserRecordModel> recordGameList, Context context) {
        this.recordGameList = recordGameList;
        this.context = context;
        this.currentPage = 0;

    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecordWinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_list_item, parent, false);
        return new RecordWinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordWinViewHolder holder, int position) {
        holder.showId.setText(recordGameList.get(position).getGameId());
        double amount= recordGameList.get(position).getAmount();
        double fee=amount*0.02;
        String  rastr="";
        if (recordGameList.get(position).isWinOrLoss()) {
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.green));
            rastr=String.format("+%s",String.format("%.2f", amount));
            holder.price.setText(rastr);
            holder.status.setText("Success");
        } else {
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.status.setTextColor(ContextCompat.getColor(context, R.color.red));
            rastr=String.format("-%s", String.format("%.2f", amount));
            holder.price.setText(rastr);
            holder.status.setText("Fail");
        }
        holder.dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.layout.getVisibility()==View.VISIBLE){
                    holder.layout.setVisibility(View.GONE);
                }else {
                    holder.layout.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.rsPeriod.setText(recordGameList.get(position).getGameId());
        holder.rsContract.setText(String.valueOf(amount));

        holder.rsDelevery.setTextColor(Color.CYAN);
        holder.rsDelevery.setText(String.valueOf(amount));
        holder.rsFee.setText(String.valueOf(fee));
        holder.rsOpenPrice.setText(String.valueOf(recordGameList.get(position).getTotalAmount()));
        String winC="";
        if(recordGameList.get(position).getWinColor().length==1){
            String color=recordGameList.get(position).getColor(0);
            if(color.equals("red")){
                winC ="Red";
                holder.rsSelect.setText("Green");
            }else{
                winC ="Green";
                holder.rsSelect.setText("Red");
            }

        }
        else if (recordGameList.get(position).getWinColor().length==2) {
            String color=recordGameList.get(position).getColor(0);
            if(color.equals("red")){
                winC="Red and Violet";
                holder.rsSelect.setText("Green and Violet");
            }else{
                winC="Green and Violet";
                holder.rsSelect.setText("Red and Violet");
            }
        }
        holder.rsResult.setText(recordGameList.get(position).getWinNumber() +" "+winC);

        String StrStatus="";
        if(recordGameList.get(position).isWinOrLoss()){
            StrStatus="Success";
            holder.rsStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.rsStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.rsAmount.setTextColor(ContextCompat.getColor(context, R.color.green));
        }else {
            StrStatus="Fail";
            holder.rsStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.rsSelect.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.rsAmount.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        holder.rsStatus.setText(StrStatus);
        holder.rsAmount.setText(rastr);
        holder.rsCreateTime.setText(recordGameList.get(position).getTimestamp());


    }

    @Override
    public int getItemCount() {
        int start = currentPage * PAGE_SIZE;
        int remaining = recordGameList.size() - start;
        return Math.min(remaining, PAGE_SIZE);
    }


    public static class RecordWinViewHolder extends RecyclerView.ViewHolder {
        public TextView showId, status, price;
        public TextView rsPeriod,rsContract,rsDelevery,rsFee,rsOpenPrice,rsResult
                ,rsSelect,rsAmount,rsStatus,rsCreateTime;
        public ImageView dropdown;
        public ConstraintLayout layout;

        public RecordWinViewHolder(View view) {
            super(view);
            showId = view.findViewById(R.id.r_item_showId);
            status = view.findViewById(R.id.r_item_status);
            price = view.findViewById(R.id.r_item_amount);
            dropdown = view.findViewById(R.id.r_item_dropdown);
            layout=view.findViewById(R.id.r_item_layout);

            rsPeriod=view.findViewById(R.id.rs_perioid_Id);
            rsContract=view.findViewById(R.id.rsContractMoney);
            rsDelevery=view.findViewById(R.id.rsDeliveryAmount);
            rsFee =view.findViewById(R.id.rsFee);
            rsOpenPrice=view.findViewById(R.id.rsTotalPrice);
            rsResult=view.findViewById(R.id.rsResult);
            rsSelect=view.findViewById(R.id.rsSelected);
            rsAmount=view.findViewById(R.id.rsAmount);
            rsStatus =view.findViewById(R.id.rsStatus);
            rsCreateTime=view.findViewById(R.id.rsTime);

        }
    }

}
