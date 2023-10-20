package com.victory.game.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.victory.game.R;

public class CustomProgressDialog {
    private Dialog dialog;

    public CustomProgressDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
//        LottieAnimationView progressImageView = view.findViewById(R.id.progressAnimationView);

        dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

