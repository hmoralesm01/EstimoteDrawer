package com.example.estimotedrawer.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class DialogConfirmation extends DialogFragment {

    RespuestaDialogoConfirmation respuesta;
/* Este método es llamado al hacer el show() de la clase DialogFragment()*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Usamos la clase Builder para construir el diálogo
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //Escribimos el título
            builder.setTitle("Confirmación");
            //Escribimos la pregunta
        //setSingleChoiceItems()
        //setMultiChoiceItems()
            builder.setMessage("¿Seguro que desea continuar?");
            //añadimos el botón de Si y su acción asociada
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    respuesta.onRespuesta(true);
                    }
                });
            //añadimos el botón de No y su acción asociada
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    respuesta.onRespuesta(false);
                    }
                });
            // Crear el AlertDialog y devolverlo
            return builder.create();
    }



    //interfaz para la comunicación entre la Actividad y el Fragmento
    public interface RespuestaDialogoConfirmation {
        public void onRespuesta(boolean respond);
    }
    //Se invoca cuando el fragmento se añade a la actividad
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        respuesta=(RespuestaDialogoConfirmation)context;
    }



}
