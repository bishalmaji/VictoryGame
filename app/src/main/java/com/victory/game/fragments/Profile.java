package com.victory.game.fragments;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.fragment.app.Fragment;
        import androidx.navigation.NavController;
        import androidx.navigation.Navigation;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.victory.game.MainActivity;
        import com.victory.game.R;
        import com.victory.game.activities.About;
        import com.victory.game.activities.Feedback;
        import com.victory.game.activities.Recharge;
        import com.victory.game.activities.ReferHistory;
        import com.victory.game.activities.Referral;
        import com.victory.game.activities.Transaction;
        import com.victory.game.activities.Withdrawal;
        import com.victory.game.utils.AppDataUtil;

public class Profile extends Fragment {

    // Define TextViews
    private ChangeMainViewListener changeMainViewListener;
    private TextView name, id, mobile, available_balance, referral_history, referral, wallet_drop,
            recharge, withdrawal, transaction, address, app_download, reset_password, feedback,
            about, logout;

    // Define Buttons
    private Button make_recharge, change_name;

    // Define ImageView
    private ImageView alert;

    private ConstraintLayout wallet_layout;
    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    private boolean isLoggedIn() {
        AppDataUtil appDataUtil=AppDataUtil.getInstance(requireActivity().getApplicationContext());
       return appDataUtil.isLoggedIn();
        //play with time and make request current user
    }
    private String u_name="";
    private String u_id="";
    private String u_phone="";
    private int u_amount=0;

    @Override
    public void onResume() {
        super.onResume();
        if(isLoggedIn()){
            AppDataUtil appDataUtil=AppDataUtil.getInstance(requireActivity().getApplicationContext());
            int updated_amount=  appDataUtil.getIntData("user_amount");
            available_balance.setText("Available balance: ₹"+updated_amount);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isLoggedIn()){
                AppDataUtil appDataUtil=AppDataUtil.getInstance(requireActivity().getApplicationContext());
               u_id= appDataUtil.getStringData("user_uid");
               u_name= appDataUtil.getStringData("user_name");
               u_phone= appDataUtil.getStringData("user_phone");
              u_amount=  appDataUtil.getIntData("user_amount");
        }else{
            if (changeMainViewListener!=null){
                changeMainViewListener.gotoLoginProfile();
                return;
            }
        }
        // Initialize TextViews
        name = view.findViewById(R.id.user_name);
        id = view.findViewById(R.id.user_id);
        mobile = view.findViewById(R.id.user_mobile);
        available_balance = view.findViewById(R.id.user_avl_amount);
        referral_history = view.findViewById(R.id.user_refer_history);
        referral = view.findViewById(R.id.user_refer);
        wallet_drop = view.findViewById(R.id.user_wallet);
        recharge = view.findViewById(R.id.user_recharge);
        withdrawal = view.findViewById(R.id.user_withdraw);
        transaction = view.findViewById(R.id.user_transiction);
        address = view.findViewById(R.id.user_address);
        app_download = view.findViewById(R.id.user_app_download);
        reset_password = view.findViewById(R.id.user_reset_password);
        feedback = view.findViewById(R.id.user_feedback);
        about = view.findViewById(R.id.user_about);
        logout = view.findViewById(R.id.user_logout);

        // Initialize Buttons
        make_recharge = view.findViewById(R.id.user_recharge_btn);
        change_name = view.findViewById(R.id.user_change_name_btn);

        // Initialize ImageView
        alert = view.findViewById(R.id.user_alert_iv);

        wallet_layout=view.findViewById(R.id.wallet_layout);

        setUserValue();

        // Set click listeners for TextViews
        int[] textViewIds = {
                R.id.user_name, R.id.user_id, R.id.user_mobile, R.id.user_avl_amount,
                R.id.user_refer_history, R.id.user_refer, R.id.user_wallet, R.id.user_recharge,
                R.id.user_withdraw, R.id.user_transiction, R.id.user_address, R.id.user_app_download,
                R.id.user_reset_password, R.id.user_feedback, R.id.user_about, R.id.user_logout
        };

        for (int tvId : textViewIds) {
            TextView textView = view.findViewById(tvId);
            if (textView != null) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleTextViewClick(v);
                    }
                });
            }
        }

        // Set click listeners for Buttons and ImageView
        make_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle make recharge button click here
                openActivity(Recharge.class);
            }
        });

        change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle change name button click here
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle alert button click here
            }
        });
    }

    private void setUserValue() {
        if(u_name.equals("")){
            name.setText("Name: U+91"+u_phone);
        }else{
            name.setText("Name: U"+u_name);
        }
        id.setText("this will change");
        mobile.setText("Mobile: +91"+u_phone);
        available_balance.setText("Available balance: ₹"+u_amount);

    }


    private void handleTextViewClick(View v) {
        {
            // Handle click event here
            if (v.getId() == R.id.user_name) {
                Toast.makeText(getContext(), "Username", Toast.LENGTH_SHORT).show();
                // Handle the user_name TextView click
            } else if (v.getId() == R.id.user_id) {
                Toast.makeText(getContext(), "UserID", Toast.LENGTH_SHORT).show();
                // Handle the user_id TextView click
            } else if (v.getId() == R.id.user_mobile) {
                Toast.makeText(getContext(), "User Mobile", Toast.LENGTH_SHORT).show();
                // Handle the user_mobile TextView click
            } else if (v.getId() == R.id.user_avl_amount) {
                Toast.makeText(getContext(), "Available Balance", Toast.LENGTH_SHORT).show();

                // Handle the user_avl_amount TextView click
            } else if (v.getId() == R.id.user_refer_history) {
                 openActivity(ReferHistory.class);
                // Handle the user_refer_history TextView click
            } else if (v.getId() == R.id.user_refer) {
                openActivity(Referral.class);
                // Handle the user_refer TextView click
            } else if (v.getId() == R.id.user_wallet) {
                if(wallet_layout.getVisibility()==View.VISIBLE){
                    wallet_layout.setVisibility(View.GONE);
                }else {
                    wallet_layout.setVisibility(View.VISIBLE);
                }
                // Handle the user_wallet TextView click
            } else if (v.getId() == R.id.user_recharge) {
                openActivity(Recharge.class);

                // Handle the user_recharge TextView click
            } else if (v.getId() == R.id.user_withdraw) {
                openActivity(Withdrawal.class);

                // Handle the user_withdraw TextView click
            } else if (v.getId() == R.id.user_transiction) {
                openActivity(Transaction.class);

                // Handle the user-transaction TextView click
            } else if (v.getId() == R.id.user_address) {
                openDialog();
                // Handle the user_address TextView click
            } else if (v.getId() == R.id.user_app_download) {
                openDialog();

                // Handle the user_app_download TextView click
            } else if (v.getId() == R.id.user_reset_password) {
                NavController navController= Navigation.findNavController(requireActivity(),R.id.nav_container);
                navController.navigate(R.id.resetPassword);
                // Handle the user_reset_password TextView click
            } else if (v.getId() == R.id.user_feedback) {
                openActivity(Feedback.class);
                // Handle the user_feedback TextView click
            } else if (v.getId() == R.id.user_about) {
                openActivity(About.class);
                // Handle the user_about TextView click
            } else if (v.getId() == R.id.user_logout) {

                // Handle the user_logout TextView click
            }else{

            }

        }

    }

    private void openDialog() {
    }

    private void openActivity(Class classname) {
        Intent intent=new Intent(requireActivity(), classname);
        startActivity(intent);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Profile.ChangeMainViewListener){
            changeMainViewListener=(Profile.ChangeMainViewListener) context;

        }else{
            throw new ClassCastException(context.toString());
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        changeMainViewListener=null;

    }

    public interface ChangeMainViewListener {
        void gotoLoginProfile();
    }
}

