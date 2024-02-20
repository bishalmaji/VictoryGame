package com.victory.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;
import com.victory.game.models.ReferalResponseModel;

import java.security.SecureRandom;
import java.util.List;

public class ReferHistoryAdapter extends RecyclerView.Adapter<ReferHistoryAdapter.ReferHistoryViewHolder>{
    List<ReferalResponseModel> list;
    Context context;

    public ReferHistoryAdapter(List<ReferalResponseModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ReferHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refer_history_row, parent, false);
        return new ReferHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferHistoryViewHolder holder, int position) {
        holder.id.setText(generateRandomString(8));
    }
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomString(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReferHistoryViewHolder extends  RecyclerView.ViewHolder{
        TextView id;
        public ReferHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.row_rh_id);
        }
    }
}
