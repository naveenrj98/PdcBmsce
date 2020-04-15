package com.developer.rjtech.pdcbmsce.CodingClub;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ResumeBuild.CardItem;
import com.developer.rjtech.pdcbmsce.ResumeBuild.CardPagerAdapter;
import com.developer.rjtech.pdcbmsce.ResumeBuild.ShadowTransformer;
import com.squareup.picasso.Picasso;


public class ResumeFragment extends Fragment  {

    private Button mButton;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resume, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
//        mCardShadowTransformer.enableScaling(true);



        mCardAdapter = new CardPagerAdapter();


        mCardAdapter.addCardItem(new CardItem(R.drawable.rs1));
        mCardAdapter.addCardItem(new CardItem(R.drawable.rs2));
        mCardAdapter.addCardItem(new CardItem(R.drawable.rs3));
        mCardAdapter.addCardItem(new CardItem(R.drawable.rs3));
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);

        mViewPager.setOffscreenPageLimit(3);


        return view;
    }




}
