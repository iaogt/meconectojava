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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.meconecto.AmigosAdapter;
import com.meconecto.CustomAdapter;
import com.meconecto.FirstFragment;
import com.meconecto.MainActivity;
import com.meconecto.R;
import com.meconecto.data.Actividad;
import com.meconecto.data.GameDataFac;
import com.meconecto.data.UserGameData;
import com.meconecto.databinding.FragmentAmigosBinding;
import com.meconecto.ui.amigos.AmigosViewModel;
import com.meconecto.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AmigosFragment extends Fragment {

    private FragmentAmigosBinding binding;
    RecyclerView lista;
    RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> amigos;
    private String userId;
    private String nuevoAmigo;
    private AmigosViewModel amigosModel;
    private UserGameData uGD;
    private HomeViewModel homeModel;
    private List<HashMap<String,String>> dAmigos;
    private AmigosAdapter objAmigosAdapter;



    class AmigosObserver implements Observer {
        @Override
        public void onChanged(Object o) {
            System.out.println("Si se disparó amigoobserver");
            uGD = (UserGameData)o;
            makeFriendsList();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dAmigos = new ArrayList<>();
        homeModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        amigosModel =
                new ViewModelProvider(requireActivity()).get(AmigosViewModel.class);

        amigos = new ArrayList<String>();

        binding = FragmentAmigosBinding.inflate(inflater, container, false);

        lista = binding.listaAmigos;
        layoutManager = new LinearLayoutManager(getContext());
        lista.setLayoutManager((layoutManager));

        objAmigosAdapter = new AmigosAdapter(dAmigos);
        lista.setAdapter(objAmigosAdapter);

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
        amigosModel.getNuevoAmigo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                nuevoAmigo = s;
                cargarNuevo();
            }
        });
        return root;
    }

    public void makeFriendsList(){
        for(String a: uGD.getAmigos().values()){
            amigos.add(a);
            GameDataFac.cargaDataUsuario(((MainActivity)getActivity()).getDB(),a,new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String,Integer> arrAvatares = new HashMap<>();
                    arrAvatares.put("ana",R.drawable.ana1);
                    arrAvatares.put("jose",R.drawable.jose1);
                    arrAvatares.put("susan",R.drawable.susan1);
                    arrAvatares.put("mama",R.drawable.mama1);
                    arrAvatares.put("papa",R.drawable.papa1);
                    arrAvatares.put("abuelo",R.drawable.abuelo1);
                    arrAvatares.put("afro",R.drawable.afro1);
                    UserGameData userGData = dataSnapshot.getValue(UserGameData.class);
                    HashMap<String,String> ams = new HashMap<>();
                    ams.put("nombre",arrAvatares.get(userGData.getNomAvatar()).toString());
                    ams.put("punteo","Punteo: "+userGData.getPunteo().toString());
                    ams.put("img",arrAvatares.get(userGData.getAvatar()).toString());
                    dAmigos.add(ams);
                    objAmigosAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    System.out.println("Sucedió un error con la bd al cargar el usuario");
                }
            });
        }
        refreshList();
    }

    public void cargarNuevo(){
        uGD.addAmigo(nuevoAmigo);
        makeFriendsList();
        GameDataFac.setUserGameData(((MainActivity)getActivity()).getDB(),userId,uGD);
        Toast toast = Toast.makeText(this.getContext(),R.string.nuevoAmigo,Toast.LENGTH_LONG);
        toast.show();
    }

    public void refreshList(){
        System.out.println("Refrescará lista");
        if(amigos.size()<=0){
            final TextView textView = binding.textView5;
            textView.setText(R.string.txtNoAmigos);
        }else{
            final TextView textView = binding.textView5;
            textView.setText("");
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
