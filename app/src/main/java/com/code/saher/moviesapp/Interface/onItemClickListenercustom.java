package com.code.saher.moviesapp.Interface;

import android.view.View;
import android.widget.AdapterView;

import com.code.saher.moviesapp.Model.Result;

/**
 * Created by saher on 10/17/2016.
 */

public interface onItemClickListenercustom extends AdapterView.OnItemClickListener {
    @Override
    void onItemClick(AdapterView<?> parent, View view, int position, long id);

    void onItemClick(Result result);
}
