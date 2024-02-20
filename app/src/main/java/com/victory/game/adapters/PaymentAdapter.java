package com.victory.game.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;
import com.victory.game.activities.Transaction;
import com.victory.game.models.UserPaymentModel;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>{
    List < UserPaymentModel > paymentRecord;
    Activity activity;

    public PaymentAdapter(List <UserPaymentModel > paymentRecord, Activity activity) {
        this.paymentRecord=paymentRecord;
        this.activity=activity;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new PaymentAdapter.PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
         holder.type.setText(paymentRecord.get(position).getTransactionId());
        holder.amount.setText("Amount: "+paymentRecord.get(position).getAmount());
        holder.status.setText(paymentRecord.get(position).getStatus());
        holder.time.setText(paymentRecord.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return paymentRecord.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
            public TextView amount, status, time,type;

            public PaymentViewHolder(View view) {
                super(view);
                amount = view.findViewById(R.id.rp_amount);
                status = view.findViewById(R.id.rp_status);
                time = view.findViewById(R.id.rp_time);
                type = view.findViewById(R.id.rp_type);


            }
        }

    }