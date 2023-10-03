package com.victory.game.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.victory.game.R;
import com.victory.game.adapters.HomeGridAdapter;
import com.victory.game.models.HomeItemModel;
import com.victory.game.adapters.ImagePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private ViewPager2 viewPager2;
    private List<Integer> imageList=new ArrayList<>();
    private List<HomeItemModel> homeItemModels=new ArrayList<>();
    private GridView homeRecycler;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2=view.findViewById(R.id.viewPager2);
        homeRecycler=view.findViewById(R.id.home_recycler);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);

//        homeRecycler.setLayoutManager(gridLayoutManager);
//        homeRecycler.addItemDecoration(new GridItemDecoration(8));
        homeItemModels.add(new HomeItemModel("falksdjljasdj fjdsjasdjl alfsdkljsadl alkfsdjufweir ","3999",R.drawable.w1));
        homeItemModels.add(new HomeItemModel("falksdjljasdj fjdsjasdjl alfsdkljsadl alkfsdjufweir ","3999",R.drawable.w2));
        homeItemModels.add(new HomeItemModel("falksdjljasdj fjdsjasdjl alfsdkljsadl alkfsdjufweir ","3999",R.drawable.w3));
        homeItemModels.add(new HomeItemModel("falksdjljasdj fjdsjasdjl alfsdkljsadl alkfsdjufweir ","3999",R.drawable.w4));
        homeItemModels.add(new HomeItemModel("falksdjljasdj fjdsjasdjl alfsdkljsadl alkfsdjufweir ","3999",R.drawable.w5));
//        HomeItemAdapter homeItemAdapter=new HomeItemAdapter(getContext(),homeItemModels);
//        homeRecycler.setAdapter(homeItemAdapter);

        HomeGridAdapter adapter=new HomeGridAdapter(getContext(),homeItemModels);
        homeRecycler.setAdapter(adapter);

        imageList.add(R.drawable.p1);
        imageList.add(R.drawable.p2);
        imageList.add(R.drawable.p3);
        imageList.add(R.drawable.p4);
        ImagePageAdapter imagePageAdapter=new ImagePageAdapter(getContext(),imageList);
        viewPager2.setAdapter(imagePageAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

    }
}