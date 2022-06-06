package com.example.estimotedrawer.ui.invitar;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.estimotedrawer.MainActivity;
import com.example.estimotedrawer.R;
import com.example.estimotedrawer.databinding.FragmentHomeBinding;
import com.example.estimotedrawer.databinding.FragmentInvitarBinding;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Invitar extends Fragment implements View.OnClickListener {

    private FragmentInvitarBinding binding;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInvitarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bluetoothChecker();
        binding.bEnviarInvitacion.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        int n = v.getId();
        switch (n){
            case R.id.b_enviarInvitacion:
            enviarEmailStack();
        }
    }
    public void botonEnviar(String email){

        Intent intent= new Intent();

        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities;
        boolean isIntentSafe;

        intent.setAction(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));

        String para[] = {
                binding.txEmail.getText().toString()
        };

        intent.putExtra(Intent.EXTRA_EMAIL, para);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Alguiler de bicicleta");

        intent.setType("message/rfc822");

        intent.createChooser(intent, "Enviar email");

        activities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        isIntentSafe = activities.size() > 0;

        if (isIntentSafe)
            // Paso 5: Arrancar la actividad
            startActivity(intent);
        else Toast.makeText(getContext(), "¡No dispondes de una app para enviar un email!",
                Toast.LENGTH_LONG).show();

        Toast.makeText(getContext(), "Amigo invitado", Toast.LENGTH_LONG).show();
    }
    private void enviarEmailStack(){

        if(validarEmail()){
            String subject = "¡Eres muy afortunado!";
            String message = "Tu amigo "+ MainActivity.emailUsuario +" quiere invitarte a usar esta App \n \n"+
                    "https://mega.nz/folder/r2gxCCiD#zPTmGtd8OaadofUI_0kGvQ";

            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{binding.txEmail.getText().toString()});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Invitacion"));
        }else{
            Toast.makeText(getContext(), "Email formato no valido", Toast.LENGTH_LONG).show();
        }
    }

    public void bluetoothChecker(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(getContext(), "Dispositivo sin bluetooth", Toast.LENGTH_LONG).show();
        }else if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    public boolean validarEmail(){
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = binding.txEmail.getText().toString();
        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }
}