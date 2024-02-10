package com.victory.game.fragments;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.navigation.NavController;
        import androidx.navigation.Navigation;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.victory.game.MainActivity;
        import com.victory.game.R;
        import com.victory.game.activities.About;
        import com.victory.game.activities.AddWithdrawDetail;
        import com.victory.game.activities.Address;
        import com.victory.game.activities.Feedback;
        import com.victory.game.activities.Recharge;
        import com.victory.game.activities.ReferHistory;
        import com.victory.game.activities.Referral;
        import com.victory.game.activities.Transaction;
        import com.victory.game.activities.Withdrawal;
        import com.victory.game.utils.AppDataUtil;

        import java.util.Objects;

public class Profile extends Fragment {

    // Define TextViews
    private ChangeMainViewListener changeMainViewListener;
    private TextView name, id, mobile, available_balance, referral_history, referral,
            recharge, withdrawal, transaction, address, app_download, reset_password, feedback,
            about, logout,uPolicy,uRisk;

    // Define Buttons
    private Button make_recharge, change_name;

    // Define ImageView
    private ImageView alert;

    private ConstraintLayout wallet_layout;
    private LinearLayout about_layout;
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
        }
//        else{
//            if (changeMainViewListener!=null){
//                changeMainViewListener.gotoLoginProfile();
//                return;
//            }
//        }
        // Initialize TextViews
        name = view.findViewById(R.id.user_name);
        id = view.findViewById(R.id.user_id);
        mobile = view.findViewById(R.id.user_mobile);
        available_balance = view.findViewById(R.id.user_avl_amount);
        referral_history = view.findViewById(R.id.user_refer_history);
        referral = view.findViewById(R.id.user_refer);

        recharge = view.findViewById(R.id.user_recharge);
        withdrawal = view.findViewById(R.id.user_withdraw);
        transaction = view.findViewById(R.id.user_transiction);
        address = view.findViewById(R.id.user_address);
        app_download = view.findViewById(R.id.user_app_download);
        reset_password = view.findViewById(R.id.user_reset_password);
        feedback = view.findViewById(R.id.user_feedback);
        about = view.findViewById(R.id.user_about);
        logout = view.findViewById(R.id.user_logout);
        uPolicy=view.findViewById(R.id.u_policy);
        uRisk=view.findViewById(R.id.u_risk);


        // Initialize Buttons
        make_recharge = view.findViewById(R.id.user_recharge_btn);
        change_name = view.findViewById(R.id.user_change_name_btn);

        // Initialize ImageView
        alert = view.findViewById(R.id.user_alert_iv);

        wallet_layout=view.findViewById(R.id.wallet_layout);
        about_layout=view.findViewById(R.id.aboutLayout);

        setUserValue();

        // Set click listeners for TextViews
        int[] textViewIds = {
                R.id.user_name, R.id.user_id, R.id.user_mobile, R.id.user_avl_amount,
                R.id.user_refer_history, R.id.user_refer, R.id.user_wallet, R.id.user_recharge,
                R.id.user_withdraw, R.id.user_transiction, R.id.user_address, R.id.user_app_download,
                R.id.user_reset_password, R.id.user_feedback, R.id.user_about, R.id.user_logout,R.id.u_risk,R.id.u_policy
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
        if(u_id.equals("")){
            name.setText("Name: Memb");
        }else{
            name.setText("Name: Member"+u_id);
        }
        id.setText("this will change");
        mobile.setText("Mobile: +91"+u_phone);
        available_balance.setText("Available balance: ₹"+u_amount);

    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppDataUtil appDataUtil = AppDataUtil.getInstance(requireActivity().getApplicationContext());
                appDataUtil.setBooleanData(false,"login");
                appDataUtil.setStringData("","token");

                changeMainViewListener.gotoLoginProfile();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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
//                if(sp.get("addr").)
                     Intent i=new Intent(getContext(), Address.class);
                     startActivity(i);
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
                if(about_layout.getVisibility()==View.VISIBLE){
                    about_layout.setVisibility(View.GONE);
                }else {
                    about_layout.setVisibility(View.VISIBLE);
                }
                // Handle the user_about TextView click
            } else if (v.getId() == R.id.user_logout) {
               showLogoutDialog();
                // Handle the user_logout TextView click
            }else if(v.getId()==R.id.u_policy){
              openIntent(1);
            }
            else if(v.getId()==R.id.u_risk){
                openIntent(2);
            }

        }

    }

    private void openDialog() {
    }

    String t1="Privacy Policy";
    String d1="This Privacy Policy describes Our policies and procedures on the collection, use and disclosure of Your information when You use the Service and tells You about Your privacy rights and how the law protects You.\n" +
            "\n" +
            "Interpretation and Definitions\n" +
            "Interpretation\n" +
            "The words of which the initial letter is capitalized have meanings defined under the following conditions.\n" +
            "\n" +
            "The following definitions shall have the same meaning regardless of whether they appear in singular or in plural.\n" +
            "\n" +
            "Definitions\n" +
            "For the purposes of this Privacy Policy:\n" +
            "\n" +
            "You means the individual accessing or using the Service, or the company, or other legal entity on behalf of which such individual is accessing or using the Service, as applicable.\n" +
            "\n" +
            "Company (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this Agreement) refers to Victory Malls.\n" +
            "\n" +
            "Affiliate means an entity that controls, is controlled by or is under common control with a party, where \"control\" means ownership of 50% or more of the shares, equity interest or other securities entitled to vote for election of directors or other managing authority.\n" +
            "Account means a unique account created for You to access our Service or parts of our Service.\n" +
            "Website refers to Victory Malls, accessible from https://victorygames.com\n" +
            "Service refers to the Website.\n" +
            "Country refers to: Uttar Pradesh, India\n" +
            "Service Provider means any natural or legal person who processes the data on behalf of the Company. It refers to third-party companies or individuals employed by the Company to facilitate the Service, to provide the Service on behalf of the Company, to perform services related to the Service or to assist the Company in analyzing how the Service is used.\n" +
            "\n" +
            "Third-party Social Media Service refers to any website or any social network website through which a User can log in or create an account to use the Service.\n" +
            "Personal Data is any information that relates to an identified or identifiable individual.\n" +
            "\n" +
            "Cookies are small files that are placed on Your computer, mobile device or any other device by a website, containing the details of Your browsing history on that website among its many uses.\n" +
            "Device means any device that can access the Service such as a computer, a cellphone or a digital tablet.\n" +
            "Usage Data refers to data collected automatically, either generated by the use of the Service or from the Service infrastructure itself (for example, the duration of a page visit).\n" +
            "Collecting and Using Your Personal Data\n" +
            "Types of Data Collected\n" +
            "Personal Data\n" +
            "While using Our Service, We may ask You to provide Us with certain personally identifiable information that can be used to contact or identify You. Personally identifiable information may include, but is not limited to:\n" +
            "\n" +
            "Email address\n" +
            "First name and last name\n" +
            "Phone number\n" +
            "Address, State, Province, ZIP/Postal code, City\n" +
            "Usage Data\n" +
            "Usage Data\n" +
            "Usage Data is collected automatically when using the Service.\n" +
            "\n" +
            "Usage Data may include information such as Your Device's Internet Protocol address (e.g. IP address), browser type, browser version, the pages of our Service that You visit, the time and date of Your visit, the time spent on those pages, unique device identifiers and other diagnostic data.\n" +
            "\n" +
            "When You access the Service by or through a mobile device, We may collect certain information automatically, including, but not limited to, the type of mobile device You use, Your mobile device unique ID, the IP address of Your mobile device, Your mobile operating system, the type of mobile Internet browser You use, unique device identifiers and other diagnostic data.\n" +
            "\n" +
            "We may also collect information that Your browser sends whenever You visit our Service or when You access the Service by or through a mobile device.\n" +
            "\n" +
            "Tracking Technologies and Cookies\n" +
            "We use Cookies and similar tracking technologies to track the activity on Our Service and store certain information. Tracking technologies used are beacons, tags, and scripts to collect and track information and to improve and analyze Our Service.\n" +
            "\n" +
            "You can instruct Your browser to refuse all Cookies or to indicate when a Cookie is being sent. However, if You do not accept Cookies, You may not be able to use some parts of our Service.\n" +
            "\n" +
            "Cookies can be \"Persistent\" or \"Session\" Cookies. Persistent Cookies remain on your personal computer or mobile device when You go offline, while Session Cookies are deleted as soon as You close your web browser.\n" +
            "\n" +
            "We use both session and persistent Cookies for the purposes set out below:\n" +
            "\n" +
            "Necessary / Essential Cookies\n" +
            "\n" +
            "Type: Session Cookies\n" +
            "\n" +
            "Administered by: Us\n" +
            "\n" +
            "Purpose: These Cookies are essential to provide You with services available through the Website and to enable You to use some of its features. They help to authenticate users and prevent fraudulent use of user accounts. Without these Cookies, the services that You have asked for cannot be provided, and We only use these Cookies to provide You with those services.\n" +
            "\n" +
            "Cookies Policy / Notice Acceptance Cookies\n" +
            "\n" +
            "Type: Persistent Cookies\n" +
            "\n" +
            "Administered by: Us\n" +
            "\n" +
            "Purpose: These Cookies identify if users have accepted the use of cookies on the Website.\n" +
            "\n" +
            "Functionality Cookies\n" +
            "\n" +
            "Type: Persistent Cookies\n" +
            "\n" +
            "Administered by: Us\n" +
            "\n" +
            "Purpose: These Cookies allow us to remember choices You make when You use the Website, such as remembering your login details or language preference. The purpose of these Cookies is to provide You with a more personal experience and to avoid You having to re-enter your preferences every time You use the Website.\n" +
            "\n" +
            "For more information about the cookies we use and your choices regarding cookies, please visit our Cookies Policy.\n" +
            "\n" +
            "Use of Your Personal Data\n" +
            "The Company may use Personal Data for the following purposes:\n" +
            "\n" +
            "To provide and maintain our Service, including to monitor the usage of our Service.\n" +
            "To manage Your Account: to manage Your registration as a user of the Service. The Personal Data You provide can give You access to different functionalities of the Service that are available to You as a registered user.\n" +
            "For the performance of a contract: the development, compliance and undertaking of the purchase contract for the products, items or services You have purchased or of any other contract with Us through the Service.\n" +
            "To contact You: To contact You by email, telephone calls, SMS, or other equivalent forms of electronic communication, such as a mobile application's push notifications regarding updates or informative communications related to the functionalities, products or contracted services, including the security updates, when necessary or reasonable for their implementation.\n" +
            "To provide You with news, special offers and general information about other goods, services and events which we offer that are similar to those that you have already purchased or enquired about unless You have opted not to receive such information.\n" +
            "To manage Your requests: To attend and manage Your requests to Us.\n" +
            "We may share your personal information in the following situations:\n" +
            "\n" +
            "With Service Providers: We may share Your personal information with Service Providers to monitor and analyze the use of our Service, to contact You.\n" +
            "For Business transfers: We may share or transfer Your personal information in connection with, or during negotiations of, any merger, sale of Company assets, financing, or acquisition of all or a portion of our business to another company.\n" +
            "With Affiliates: We may share Your information with Our affiliates, in which case we will require those affiliates to honor this Privacy Policy. Affiliates include Our parent company and any other subsidiaries, joint venture partners or other companies that We control or that are under common control with Us.\n" +
            "With Business partners: We may share Your information with Our business partners to offer You certain products, services or promotions.\n" +
            "With other users: when You share personal information or otherwise interact in the public areas with other users, such information may be viewed by all users and may be publicly distributed outside. If You interact with other users or register through a Third-Party Social Media Service, Your contacts on the Third-Party Social Media Service may see You name, profile, pictures and description of Your activity. Similarly, other users will be able to view descriptions of Your activity, communicate with You and view Your profile.\n" +
            "Retention of Your Personal Data\n" +
            "The Company will retain Your Personal Data only for as long as is necessary for the purposes set out in this Privacy Policy. We will retain and use Your Personal Data to the extent necessary to comply with our legal obligations (for example, if we are required to retain your data to comply with applicable laws), resolve disputes, and enforce our legal agreements and policies.\n" +
            "\n" +
            "The Company will also retain Usage Data for internal analysis purposes. Usage Data is generally retained for a shorter period of time, except when this data is used to strengthen the security or to improve the functionality of Our Service, or We are legally obligated to retain this data for longer time periods.\n" +
            "\n" +
            "Transfer of Your Personal Data\n" +
            "Your information, including Personal Data, is processed at the Company's operating offices and in any other places where the parties involved in the processing are located. It means that this information may be transferred to — and maintained on — computers located outside of Your state, province, country or other governmental jurisdiction where the data protection laws may differ than those from Your jurisdiction.\n" +
            "\n" +
            "Your consent to this Privacy Policy followed by Your submission of such information represents Your agreement to that transfer.\n" +
            "\n" +
            "The Company will take all steps reasonably necessary to ensure that Your data is treated securely and in accordance with this Privacy Policy and no transfer of Your Personal Data will take place to an organization or a country unless there are adequate controls in place including the security of Your data and other personal information.\n" +
            "\n" +
            "Disclosure of Your Personal Data\n" +
            "Business Transactions\n" +
            "If the Company is involved in a merger, acquisition or asset sale, Your Personal Data may be transferred. We will provide notice before Your Personal Data is transferred and becomes subject to a different Privacy Policy.\n" +
            "\n" +
            "Law enforcement\n" +
            "Under certain circumstances, the Company may be required to disclose Your Personal Data if required to do so by law or in response to valid requests by public authorities (e.g. a court or a government agency).\n" +
            "\n" +
            "Other legal requirements\n" +
            "The Company may disclose Your Personal Data in the good faith belief that such action is necessary to:\n" +
            "\n" +
            "Comply with a legal obligation\n" +
            "Protect and defend the rights or property of the Company\n" +
            "Prevent or investigate possible wrongdoing in connection with the Service\n" +
            "Protect the personal safety of Users of the Service or the public\n" +
            "Protect against legal liability\n" +
            "Security of Your Personal Data\n" +
            "The security of Your Personal Data is important to Us, but remember that no method of transmission over the Internet, or method of electronic storage is 100% secure. While We strive to use commercially acceptable means to protect Your Personal Data, We cannot guarantee its absolute security.\n" +
            "\n" +
            "Children's Privacy\n" +
            "Our Service does not address anyone under the age of 13. We do not knowingly collect personally identifiable information from anyone under the age of 13. If You are a parent or guardian and You are aware that Your child has provided Us with Personal Data, please contact Us. If We become aware that We have collected Personal Data from anyone under the age of 13 without verification of parental consent, We take steps to remove that information from Our servers.\n" +
            "\n" +
            "If We need to rely on consent as a legal basis for processing Your information and Your country requires consent from a parent, We may require Your parent's consent before We collect and use that information.\n" +
            "\n" +
            "Links to Other Websites\n" +
            "Our Service may contain links to other websites that are not operated by Us. If You click on a third party link, You will be directed to that third party's site. We strongly advise You to review the Privacy Policy of every site You visit.\n" +
            "\n" +
            "We have no control over and assume no responsibility for the content, privacy policies or practices of any third party sites or services.\n" +
            "\n" +
            "Changes to this Privacy Policy\n" +
            "We may update our Privacy Policy from time to time. We will notify You of any changes by posting the new Privacy Policy on this page.\n" +
            "\n" +
            "We will let You know via email and/or a prominent notice on Our Service, prior to the change becoming effective and update the \"Last updated\" date at the top of this Privacy Policy.\n" +
            "\n" +
            "You are advised to review this Privacy Policy periodically for any changes. Changes to this Privacy Policy are effective when they are posted on this page.\n" +
            "\n" +
            "Contact Us\n" +
            "If you have any questions about this Privacy Policy, You can contact us:\n";
    String t2="Risk Disclosure Agreement";
    String d2="Chapter 1.Booking/Collection Description\n" +
            "Prepayment Booking/Recycling Customer should read and understand the business content carefully before making prepayment bookings (prepayment lock price, payment settlement and shipment) /recovery or repurchase (prepayment lock price, shipping payment) before making prepayment bookings to Victory Malls:\n" +
            "\n" +
            "1. Before making an appointment/restoring the prepayment business, the customer should complete the real name authentication in the mall and ensure that the name, ID number, bank account number, delivery address and other information filled in are true, accurate and valid; Otherwise, the user will be liable for the consequences of false information.\n" +
            "\n" +
            "2. Customers can order gold and silver products in advance at the shopping centre. Orders can be cancelled by 01:30 a.m. on the same Saturday. When the customer pays the end payment, the mall receives the final payment and arranges the delivery.\n" +
            "\n" +
            "If the customer does not pay the final pick-up by 01:30 a.m. on Saturday, the customer is deemed to have made the last offer before the inventory and the booking is cancelled.\n" +
            "\n" +
            "3. Customers can make an appointment to recycle gold and silver products purchased at the gold point. Pre-purchase recovery requires a credit margin and confirmation of actual possession of gold and silver products purchased from the mall. Customers can cancel their reservation at any time before 01:30 on Saturday and the credit mark will be refunded after deducting the increase or decrease in the value of the goods within the corresponding time.\n" +
            "\n" +
            "If the customer fails to deliver the goods to a shopping mall or shopping center at the designated collection point by Saturday within the same week, or if the goods delivered do not meet the recycling standard test, the customer will be deemed to have cancelled the reservation recovery and will bear the logistics and testing costs.\n" +
            "\n" +
            "4. Counting time: Daily 01:30-05:30 for the mall warehouse inventory time. During the inventory period, the mall stops accepting advance payments for reservations/receipts.\n" +
            "\n" +
            "5. For further details, please refer to the Business Guidelines in the front page of the mall, Understanding Victory Malls.\n" +
            "\n" +
            "\n" +
            "Chapter 2 Reveals the business model of Victory Malls\n" +
            "\n" +
            "Booking/repurchase orders, the business model for clearing balance shipments, uncertainties such as potential benefits and potential risks to the value of its merchandise due to real-time fluctuations in the gold and silver market, and the extent to which booking/repo risk stake is understood for customer booking/repo risk, Risk control ability and understanding of related products have high requirements. Customer selects pre-payment booking/repurchase, fully informed on behalf of the customer and understand the risks of prepayments/repurchase business and agree to and accept Victory Malls current and future relevant booking/repurchase business processes and management systems (collectively, the Process Systems) to develop, modify and publish. This Risk Disclosure (Disclosure) is intended to fully disclose to the Client the risk of the prepayment booking/repurchase business and is intended only to provide reference for the client to assess and determine its own risk tolerance. The risk disclosures described in this disclosure are for example only. All risk factors associated with Victory Malls Advance Booking/Repurchase are not detailed. Customers should also carefully understand and understand other possible risk factors before starting or participating in Victory Malls pre-payment booking/repurchase business. If the customer is not aware of or is not aware of this disclosure, they should consult Victory Malls Customer Service or the relevant regional service provider in a timely manner. If the Customer ultimately clicks on Risk Disclosure, it is deemed that the Customer fully agrees and accepts the full contents of this disclosure.\n" +
            "\n" +
            "\n" +
            "Warm tips\n" +
            "\n" +
            "1.Minors under the age of 18 are not permitted to participate in The Victory Malls Advance Booking/Recycling.\n" +
            "2.Victory Malls Advance Booking/Repo is only available to customers who meet all of the following criteria:\n" +
            "① Natural persons with full civil capacity, legal persons of enterprises or other economic organizations registered in accordance with the law.\n" +
            "\n" +
            "② To fully understand all risks associated with Victory Malls Advance Booking/Repurchase business and have a certain risk tolerance.\n" +
            "\n" +
            "③ Have a certain understanding of gold and silver and its products:\n" +
            "\n" +
            "A. Policy-related risk disclosure, such as changes in national laws, regulations and policies, contingency measures, implementation of appropriate regulatory measures, Victory Malls regulatory system and changes in management methods and regulations, etc., all risks that may affect customer bookings/repurchases, etc., the customer must bear the losses incurred.\n" +
            "\n" +
            "B. Price fluctuations, gold, silver and other precious metals and their accessories are affected by a variety of factors, such as the international economic situation, foreign exchange, related market trends, supply and demand, and political situation and energy prices. The pricing mechanism for gold, silver and other precious metals products is very complex, making it difficult for customers to fully grasp in practice, so decisions such as advance booking/buyback are possible Mistakes, if the risk cannot be effectively controlled, may suffer losses and the customer must bear all the losses incurred as a result.\n" +
            "\n" +
            "④ Victory Malls has enabled the provision of services through electronic communication technology and Internet technology. Communication services and hardware and software services are provided by different vendors and may be at risk in terms of quality and stability. Interruptions or delays due to communication or network failures may affect customer prepayment bookings/repurchases. In addition, the customers computer system may be attacked by viruses and/or cyber-hackers, resulting in the customers advance payment booking/repurchase not being properly and/or timely.\n" +
            "\n" +
            "There is also a risk that the above uncertainties may affect the customer’s advance payment booking/repurchase.\n" +
            "A. The price quoted by the Victory Malls Prepayment Booking/Repo System is based on the systems real-time trading price and may differ slightly from the commodity prices in other markets.\n" +
            "Victory Malls cannot guarantee that the above prepayment booking//repurchase price is fully consistent with other markets.\n" +
            "B. At Victory Malls;, once the customers pre-payment booking/repurchase application submitted through the online terminal is completed, it cannot be withdrawn and the customer must accept the risks associated with such a subscription.\n" +
            "C. Victory Malls prohibits regional service providers and their staff from providing any profit guarantee to customers, from engaging in prepaid bookings/repurchases on behalf of customers, or from sharing profits or risks with customers. Customer should be aware that any profit guarantee or commitment that Victory Malls advance booking/repurchase does not have a loss, profit share or risk-sharing is impossible, unfounded, and incorrect.\n" +
            "D. The customers pre-paid booking / repurchase application must be based on the customers own decision. Victory Malls and regional service providers and employees do not provide booking / buyback to the client, nor does it constitute any commitment if the client makes a booking / buyback decision accordingly.\n" +
            "E. In advance booking / buyback process, there may be occasional apparent errors in the offer.\n" +
            "⑤ RISK-AGREEMENT\n" +
            "Typhoons, floods, fires, wars, disturbances, rule revisions, changes or adjustments in government regulatory policies and regulatory requirements, and electricity, To ensure that you fully understand the relevant provisions and risks of booking / repurchase business, customers should be based on their own booking experience, booking / repurchase / purchase of commodities, read all the contents of the advance booking / repurchase notice carefully, and fully understand and agree to all the contents, I am willing to take all risks to start or participate in Victory Malls. In case of above mentioned condition I shall be him-self liable to any financial as well as monitory loss. By accepting this I shall be no more eligible to claims any statutory legal benefits given to Indian citizen by Law of India.\n" +
            "\n" +
            "\n" +
            "Note: I have carefully read all contents of this app including Privacy Statement, Risk Disclosure Agreement and Risk Agreement and I am agreed to continue with my own risk.\n" +
            "\n" +
            "\n" +
            "Cancellation and refundable Policy\n" +
            "In case of any discrepancy we can cancel any of the orders placed by you. A few reasons for cancellation from our end usually include limitation of the product in the inventory, error in pricing, error in product information etc. We also have the right to check out for extra information for the purpose of accepting orders in a few cases. We make sure to notify you if in case your order is cancelled partially or completely or if in case any extra data is required for the purpose of accepting your order.\n" +
            "\n" +
            "Once you place the order, such order can be cancelled from your end before the shipping is undertaken to the destination. Once the request of cancellation for ready for shipping product is received by us, we make sure to refund the amount through the same mode of payment within 5 working days. Cancellation of the order of Gold coin(exchanged by integrals) shall not be accepted as under Company’s policies.\n" +
            "\n" +
            "We don’t accept Cancellation requests for Smart Buy orders or customized jewellery orders. In specific situations when the customer wants the money back or wants to exchange it with other products, making charges of the product and stone charges, if there is any stone on the product shall be deducted from the payment and balance will be refunded back to customer account within 5 working days.\n" +
            "\n" +
            "If in case the amount is deducted from your account and the transaction has failed, the same will be refunded back to your account within 72 hours.";


    private void openIntent(int i) {
        Intent intent = new Intent(requireActivity(), About.class);
        if(i==1){
            // Put extra data with the intent
            intent.putExtra("Atitle", t1);
            intent.putExtra("Adesc", d1);
        }else{
            // Put extra data with the intent
            intent.putExtra("Atitle", t2);
            intent.putExtra("Adesc", d2);
        }
        startActivity(intent);
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

