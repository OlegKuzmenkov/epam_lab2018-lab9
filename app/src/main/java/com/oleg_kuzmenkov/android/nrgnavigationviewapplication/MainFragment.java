package com.oleg_kuzmenkov.android.nrgnavigationviewapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainFragment extends Fragment {

    private final String IMAGE_URL = "http://st8.gismeteo.ru/static/news/img/src/18771/c9fbdbf4.jpeg";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView img = v.findViewById(R.id.main_fragment_image);
        Picasso.with(getContext()).load(IMAGE_URL).into(img);
        return v;
    }
}
