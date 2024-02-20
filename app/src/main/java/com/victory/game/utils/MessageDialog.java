package com.victory.game.utils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
public class MessageDialog {
    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss the dialog
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}



