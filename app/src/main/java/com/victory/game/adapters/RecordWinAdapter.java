package com.victory.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;

import com.victory.game.models.UserRecordModel;

import java.util.List;

public class RecordWinAdapter extends RecyclerView.Adapter<RecordWinAdapter.RecordWinViewHolder>{

    private List<UserRecordModel> recordGameList;
    private Context context;

    public RecordWinAdapter(List<UserRecordModel> recordGameList, Context context) {
        this.recordGameList = recordGameList;
        this.context = context;
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
        if(recordGameList.get(position).isWinOrLoss()){
            holder.price.setText("+"+recordGameList.get(position).getAmount());
            holder.status.setText("Success");
        }else{
            holder.price.setText("-"+recordGameList.get(position).getAmount());
            holder.status.setText("fail");
        }
        holder.no_win.setText("No Prize:\n"+recordGameList.get(position).getWinNumber());
        holder.total.setText("Total Amount\n"+recordGameList.get(position).getTotalAmount());
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
        if(recordGameList.get(position).getWinColor().length==1){
            String color=recordGameList.get(position).getColor(0);
            if(color.equals("red")){
                holder.color_win.setImageResource(R.drawable.red);
            }else{
                holder.color_win.setImageResource(R.drawable.green);
            }

        } else if (recordGameList.get(position).getWinColor().length==2) {
            String color=recordGameList.get(position).getColor(0);
            if(color.equals("red")){
                holder.color_win.setImageResource(R.drawable.v_and_r);
            }else{
                holder.color_win.setImageResource(R.drawable.v_and_g);
            }
        }
    }

    @Override
    public int getItemCount() {
        return recordGameList.size();
    }

    public static class RecordWinViewHolder extends RecyclerView.ViewHolder {
        public TextView showId, status, price,total,no_win;
        public ImageView dropdown,color_win;
        public ConstraintLayout layout;

        public RecordWinViewHolder(View view) {
            super(view);
            showId = view.findViewById(R.id.r_item_showId);
            status = view.findViewById(R.id.r_item_status);
            price = view.findViewById(R.id.r_item_amount);
            dropdown = view.findViewById(R.id.r_item_dropdown);
            total=view.findViewById(R.id.r_item_total_amount);
            no_win=view.findViewById(R.id.r_item_no_win);
            color_win=view.findViewById(R.id.r_item_color_win);
            layout=view.findViewById(R.id.r_item_layout);
        }
    }

}
