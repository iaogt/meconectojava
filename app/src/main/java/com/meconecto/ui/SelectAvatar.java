package com.meconecto.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.meconecto.R;
import com.meconecto.data.Avatar;
import com.meconecto.data.UserGameData;
import com.meconecto.ui.home.HomeFragment;
import com.meconecto.ui.home.HomeViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private ArrayList<List> arrAvatares;
    private HomeViewModel homeViewModel;

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
        arrAvatares.add(UserGameData.getAvatar1());
        arrAvatares.add(UserGameData.getAvatar2());
        arrAvatares.add(UserGameData.getAvatar3());
        arrAvatares.add(UserGameData.getAvatar4());
        arrAvatares.add(UserGameData.getAvatar5());
        arrAvatares.add(UserGameData.getAvatar6());
        arrAvatares.add(UserGameData.getAvatar7());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        View v =  inflater.inflate(R.layout.fragment_select_avatar, container, false);
        EditText txtNomAvatar = v.findViewById(R.id.inputNomAvatar);
        objImg = (ImageView) v.findViewById(R.id.imageView10);
        ImageButton bl = (ImageButton) v.findViewById(R.id.imageButton7);
        ImageButton br = (ImageButton) v.findViewById(R.id.imageButton8);
        Button btn = (Button)v.findViewById(R.id.button4);
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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> lstAvatares = UserGameData.getNomAvatars();
                Avatar a = new Avatar();
                a.setImgAvatar(lstAvatares.get(numImg.intValue()));
                a.setNombre(txtNomAvatar.getText().toString().replace(getResources().getString(R.string.txtNomAvatar),""));
                homeViewModel.setAvatar(a);
                NavHostFragment.findNavController(SelectAvatar.this).popBackStack();

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
        List listAvatars = arrAvatares.get(numImg.intValue());
        int idAvatar = (int)listAvatars.get(0);
        objImg.setImageResource(idAvatar);
    }
}