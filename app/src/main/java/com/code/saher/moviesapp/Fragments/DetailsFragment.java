package com.code.saher.moviesapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.saher.moviesapp.Model.Result;
import com.code.saher.moviesapp.R;
import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment {

    final static String IMAGE_API_URL="http://image.tmdb.org/t/p/w500";
    ImageView iv_poster;
    TextView tv_1,tv_2,tv_3,tv_4;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Result result = (Result) getActivity().getIntent().getExtras().getSerializable("result");

        iv_poster= (ImageView) view.findViewById(R.id.item_image);
        tv_1= (TextView) view.findViewById(R.id.item_text1);
        tv_2= (TextView) view.findViewById(R.id.item_text2);
        tv_3= (TextView) view.findViewById(R.id.item_text3);
        tv_4= (TextView) view.findViewById(R.id.item_text4);

        Picasso.with(getActivity()).load(IMAGE_API_URL+result.getPosterPath()).into(iv_poster);
        tv_1.setText(result.getOriginalTitle());
        tv_2.setText("ID : "+result.getId().toString());
        tv_3.setText("Overview : "+result.getOverview());
        tv_4.setText("Votecount : "+result.getVoteCount().toString());
        return view;
    }

}
