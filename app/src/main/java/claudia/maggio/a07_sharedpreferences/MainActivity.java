package claudia.maggio.a07_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnGuardar;
    private Button btnBorrar;
    private ImageButton btnBorrarNombre;
    private ImageButton btnBorrarrEdad;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombreMain);
        txtEdad = findViewById(R.id.txtEdadMain);
        btnGuardar = findViewById(R.id.btnGuardarMain);
        btnBorrar = findViewById(R.id.btnBorrarDatosMain);
        btnBorrarNombre = findViewById(R.id.btnEliminaNombreMain);
        btnBorrarrEdad = findViewById(R.id.btnEliminaEdadMain);

        sharedPreferences = getSharedPreferences(Constantes.PERSONAS, MODE_PRIVATE); //para que nadie toque el archivo xml

        cargaDatos();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //habria que comprobar que el usuario haya escrito algo

                String nombre = txtNombre.getText().toString();
                int edad = Integer.parseInt(txtEdad.getText().toString());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constantes.NOMBRE, nombre);
                editor.putInt(Constantes.EDAD, edad);
                //commit(sincrono) hasta que no escriba la app no continua o apply (asincrona) la escritura se hace en segundo plano.
                //el commit se hace si hay una dependencia de datos, pero de normal se hace un apply
                editor.apply();
            }
        });

        btnBorrarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(Constantes.NOMBRE);
                editor.apply();
                txtNombre.setText("");
            }
        });

        btnBorrarrEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(Constantes.EDAD);
                editor.apply();
                txtEdad.setText("");
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
            }
        });
    }

    private void cargaDatos() {
        //leer directamente con sharedpreferences, no hace falta el editor
        //"" --> lo que quiero que ponga en la variable si el getString == null!
        String nombre = sharedPreferences.getString(Constantes.NOMBRE, "");

        if (!nombre.isEmpty()){
            txtNombre.setText(nombre);
        }

        int edad = sharedPreferences.getInt(Constantes.EDAD, -1);

        if (edad != -1){
            txtEdad.setText(String.valueOf(edad));
        }
    }
}