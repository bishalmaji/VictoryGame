package com.victory.game.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class  GridItemDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public GridItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left=margin;
        outRect.right=margin;
        outRect.top=margin;
        outRect.bottom=margin;
    }
}
