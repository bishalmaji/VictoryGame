package com.victory.game.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.victory.game.R;
import com.victory.game.models.ResultModel;

import java.util.ArrayList;
import java.util.List;
public class GameWinAdapter extends RecyclerView.Adapter<GameWinAdapter.GameViewHolder> {

    private List<ResultModel> resultModelList;
    private Context context;
    private int currentPage;
    private static final int PAGE_SIZE = 8;

    public GameWinAdapter(Context context, List<ResultModel> resultModelList) {
        this.context = context;
        this.resultModelList = resultModelList;
        this.currentPage = 0;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        notifyDataSetChanged();
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_win_list_items, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        ResultModel resultModel = resultModelList.get(currentPage * PAGE_SIZE + position);

        holder.gameId.setText(resultModel.getWinShowId());
        holder.gamePrice.setText(resultModel.getWinPrice());
        holder.gameNumber.setText(resultModel.getWinNumber());
        List<String> winColor = resultModel.getWinColor();

        // Set the appropriate image based on winColor
        if (winColor.size() == 2) {
            if (winColor.get(0).equals("red")) {
                holder.gameImage.setImageResource(R.drawable.v_and_r);
                holder.gameNumber.setTextColor(ContextCompat.getColor(context, R.color.red));
            } else {
                holder.gameNumber.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.gameImage.setImageResource(R.drawable.v_and_g);
            }
        } else {
            if (winColor.get(0).equals("red")) {
                holder.gameNumber.setTextColor(ContextCompat.getColor(context, R.color.red));
                holder.gameImage.setImageResource(R.drawable.red);
            } else {
                holder.gameNumber.setTextColor(ContextCompat.getColor(context, R.color.green));
                holder.gameImage.setImageResource(R.drawable.green);
            }
        }
    }

    @Override
    public int getItemCount() {
        int start = currentPage * PAGE_SIZE;
        int remaining = resultModelList.size() - start;
        return Math.min(remaining, PAGE_SIZE);
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        public TextView gameId, gamePrice, gameNumber;
        public ImageView gameImage;

        public GameViewHolder(View view) {
            super(view);
            gameId = view.findViewById(R.id.win_item_id);
            gamePrice = view.findViewById(R.id.win_item_price);
            gameNumber = view.findViewById(R.id.win_item_number);
            gameImage = view.findViewById(R.id.win_item_image);
        }
    }
}
