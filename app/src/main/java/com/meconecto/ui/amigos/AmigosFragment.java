package com.meconecto.ui.amigos;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.meconecto.CustomAdapter;
import com.meconecto.FirstFragment;
import com.meconecto.R;
import com.meconecto.data.Actividad;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentAmigosBinding;
import com.meconecto.ui.amigos.AmigosViewModel;
import com.meconecto.ui.home.HomeViewModel;

import java.util.ArrayList;

public class AmigosFragment extends Fragment {

    private FragmentAmigosBinding binding;
    RecyclerView lista;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> amigos;
    private String userId;



    class AmigosObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            System.out.println("Si se disparó amigoobserver");
            UserGameData uGD = (UserGameData)o;
            for(String a: uGD.getAmigos().values()){
                amigos.add(a);
            }
            refreshList();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        amigos = new ArrayList<String>();

        binding = FragmentAmigosBinding.inflate(inflater, container, false);

        lista = binding.listaAmigos;
        lista.setLayoutManager((layoutManager));

        lista.setAdapter(new AmigosListAdapter(amigos, new AmigosListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String a) {
                System.out.println("Dio click a la fila "+a);
            }
        }));

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMDynaLink();
            }
        });

        View root = binding.getRoot();
        System.out.println("Estara escuchando el userGData");
        homeModel.getuserGData().observe(getViewLifecycleOwner(), new AmigosObserver());
        homeModel.getUserId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                userId = s;
            }
        });
        return root;
    }

    public void refreshList(){
        System.out.println("Refrescará lista");
        if(amigos.size()<=0){
            final TextView textView = binding.textView5;
            textView.setText(R.string.txtNoAmigos);
        }
    }

    public void createMDynaLink(){
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://meconectosinclavos.net.gt/addamigo/"+userId))
                .setDomainUriPrefix("https://meconecto.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            System.out.println(shortLink);
                            LayoutInflater vista = requireActivity().getLayoutInflater();
                            View form = vista.inflate(R.layout.alert_shortlink,null);
                            EditText et = form.findViewById(R.id.textLink);
                            et.setText(shortLink.toString());
                            AlertDialog.Builder bd = new AlertDialog.Builder(requireActivity());
                            bd.setTitle("Invita a tus amigos");
                            bd.setView(form)
                            .setPositiveButton(R.string.txtCopiar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("amigosLink", shortLink.toString());
                                    clipboard.setPrimaryClip(clip);
                                }
                            })
                            .setNegativeButton(R.string.txtCerrar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            bd.show();
                        } else {
                            System.out.println(task.getException());
                            Toast.makeText(requireActivity().getBaseContext(),R.string.errorDynamicLink, Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
