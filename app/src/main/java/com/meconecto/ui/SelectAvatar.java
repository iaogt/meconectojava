package com.meconecto.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.meconecto.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectAvatar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectAvatar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer numImg;
    private ImageView objImg;
    private ArrayList arrAvatares;

    public SelectAvatar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectAvatar.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectAvatar newInstance(String param1, String param2) {
        SelectAvatar fragment = new SelectAvatar();
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
        numImg=0;
        arrAvatares = new ArrayList();
        arrAvatares.add(R.drawable.avatarsusan);
        arrAvatares.add(R.drawable.avatarjose);
        arrAvatares.add(R.drawable.avatarafro);
        arrAvatares.add(R.drawable.avatarana);
        arrAvatares.add(R.drawable.avatarmama);
        arrAvatares.add(R.drawable.avatarpapa);
        arrAvatares.add(R.drawable.avatarabuelo);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_select_avatar, container, false);
        objImg = (ImageView) v.findViewById(R.id.imageView10);
        ImageButton bl = (ImageButton) v.findViewById(R.id.imageButton7);
        ImageButton br = (ImageButton) v.findViewById(R.id.imageButton8);
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izqImg();
            }
        });

        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                derImg();
            }
        });

        return v;
    }

    public void izqImg(){
        if(numImg>0) {
            numImg = numImg - 1;
            updateImg();
        }
    }

    public void derImg(){
        if(numImg<(arrAvatares.size()-1)) {
            numImg = numImg + 1;
            updateImg();
        }
    }

    public void updateImg(){
        objImg.setImageResource((int)arrAvatares.get(numImg.intValue()));
    }
}