package es.studium.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // componentes de GUI
    TextView bienvenido;
    Button btnBorrarCredenciales;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // obtener los valores pasados desde Login
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre");
        boolean sharedPrefsClicked = extras.getBoolean("sharedPrefsClicked");
        btnBorrarCredenciales = findViewById(R.id.btnBorrarCredenciales);
        // si el switch fue activado, habilitar el botón de 'borrar credenciales'
        btnBorrarCredenciales.setEnabled(sharedPrefsClicked);

        btnBorrarCredenciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // si el usuario pulsa el botón de borrar credenciales
                if (v.getId() == btnBorrarCredenciales.getId()) {
                    // obtener la referencia a la colección 'LoginCredentials'
                    sharedpreferences = getSharedPreferences("LoginCredentials", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear(); // borrar todos los valores
                    editor.commit();
                    // mostrar un mensaje informativo en un toast
                    Toast toast = Toast.makeText(MainActivity.this, R.string.credencialesBorradas, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    View toastView = toast.getView();
                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                    // aplicar un estilo personalizado al toast
                    toastMessage.setTextAppearance(R.style.ToastStyle);
                    toastMessage.setGravity(Gravity.CENTER);
                    toastView.setBackgroundColor(getColor(R.color.non_photo_blue));
                    toast.show();
                    // al borrar las credenciales en las preferencias compartidas, se deshabilita el botón de 'borrar credenciales'
                    btnBorrarCredenciales.setEnabled(false);
                }
            }
        });

        bienvenido = findViewById(R.id.textViewBienvenido);
        String greeting = getResources().getString(R.string.bienvenido) + " " + nombre;
        bienvenido.setText(greeting);
    }

     /*cuando el usuario entra directamente a la pantalla principal
     con preferencias compartidas guardadas
     y luego las borra y vuelve a la pantalla de login*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!btnBorrarCredenciales.isEnabled()) {
            Intent intentLogin = new Intent(this, ActivityLogin.class);
            startActivity(intentLogin);
        }
    }
}