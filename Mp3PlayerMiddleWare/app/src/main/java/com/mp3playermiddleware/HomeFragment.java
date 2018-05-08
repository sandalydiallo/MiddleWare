package com.mp3playermiddleware;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by alpha on 27/04/2018.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private ListView list_music;
    private TextView test ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final String[] items = {"http://mic.duytan.edu.vn:86/ncs.mp3","Second item"};
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        addItemsInlistMusic(items,view);

        test = (TextView)getActivity().findViewById(R.id.textViewTest);

        list_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //view.setSelected(true);
                test.setText(items[position]);
            }
        });


        return view;
    }

    public void addItemsInlistMusic(String[] items, View view){
        list_music = (ListView) view.findViewById(R.id.list_music);

        ArrayAdapter<String> listMusicAdapter = new ArrayAdapter<String>(
                getActivity(),
               android.R.layout.simple_list_item_1,
               // R.layout.text_view_item,
                items
        );

        list_music.setAdapter(listMusicAdapter);
    }
}
