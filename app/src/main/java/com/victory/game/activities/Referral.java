package com.victory.game.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.victory.game.R;
import com.victory.game.RetrofitClientWithToken;
import com.victory.game.adapters.RecordWinAdapter;
import com.victory.game.interfaces.ApiService;
import com.victory.game.models.UserRecordResponseModel;
import com.victory.game.utils.AppDataUtil;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Referral extends AppCompatActivity {

    private AppDataUtil appDataUtil;
    TextView balance, totalp,contrb, code, link;
    Button applyBalance, copylink, openLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);
        ImageView back=findViewById(R.id.r_back);
        appDataUtil = AppDataUtil.getInstance(getApplicationContext());
        int money = appDataUtil.getIntData("user_refer_amount");
        int  total =0 ;
        if(appDataUtil.getIntData("total_referal")!=0){
            total=appDataUtil.getIntData("total_referal");
        }
        applyBalance=findViewById(R.id.refApplybttn);
        copylink=findViewById(R.id.refcopybtn);
        openLink=findViewById(R.id.refopenlink);


        balance=findViewById(R.id.refViewBalanceTv);
        totalp=findViewById(R.id.reftotal);
        contrb=findViewById(R.id.refcontib);

        code=findViewById(R.id.refcode);
        link=findViewById(R.id.reflink);

        totalp.setText(""+total);
        contrb.setText(""+total);
        balance.setText("Balance: "+money);

        applyBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        copylink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             copyToClipboard(Referral.this,link.getText().toString());
            }
        });
        openLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               shareText(Referral.this,link.getText().toString());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Clipboard not available", Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareText(Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        Intent chooser = Intent.createChooser(intent, "Share using");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(chooser);
        }
    }
}