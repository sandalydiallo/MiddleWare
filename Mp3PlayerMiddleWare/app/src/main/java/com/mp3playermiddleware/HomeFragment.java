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

import mp3.*;

/**
 * Created by alpha on 27/04/2018.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private ListView list_music;
    private TextView selectedItem ;

    private IceClient iceClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        iceClient = new IceClient();
        String[] items = iceClient.getInstanceServer();

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        addItemsInlistMusic(items,view);



        selectedItem = (TextView)getActivity().findViewById(R.id.textViewSelectedItem);
        selectedItem.setText(items[0]);


        list_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem.setText(items[position]);
            }
        });


        return view;
    }

    public void addItemsInlistMusic(String[] items, View view){
        list_music = (ListView) view.findViewById(R.id.list_music);

        ArrayAdapter<String> listMusicAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                items
        );

        list_music.setAdapter(listMusicAdapter);

    }
}
