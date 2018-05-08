package com.mp3playermiddleware;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alpha on 27/04/2018.
 */

public class FavoritesFragment extends android.support.v4.app.Fragment {

    TextView test;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        test = (TextView)getActivity().findViewById(R.id.textViewTest);

        test.setText("Favories");
        View v =  inflater.inflate(R.layout.fragment_favorites, container, false);
        return v;
    }
}
