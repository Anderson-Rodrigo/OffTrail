package br.edu.unoesc.webmobi.offtrail.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;

import br.edu.unoesc.webmobi.offtrail.R;


@EActivity(R.layout.activity_config)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class PreferenciasActivity extends AppCompatActivity {

    @ViewById
    EditText edtParametro;

    @ViewById
    Spinner spnCores;

    //Injeção de preferencias
    @Pref
    Configuracao_ configuracao;

    @AfterViews
    public void inicializar() {

        ArrayList<String> motos = new ArrayList<String>();
        motos.add("CRF");
        motos.add("Agrale");

        ArrayAdapter<String> coresAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, motos);
        spnCores.setAdapter(coresAdapter);


        spnCores.setSelection(configuracao.cor().get());
        edtParametro.setText(configuracao.parametro().get());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
    }

    public int getMotoString(String s) {
        switch (s) {
            case "CRF":
                return Color.BLUE;
            case "Agrale":
                return Color.GREEN;
            default:
                return Color.GREEN;
        }
    }
    public void salvar(View v) {

        //Escrevendo/Alterando parâmetros
        configuracao.edit().cor().put(getMotoString(spnCores.getSelectedItem().toString())).apply();

        configuracao.edit().parametro().put(edtParametro.getText().toString()).apply();

        Toast.makeText(this, "Nova Moto: " +
                configuracao.parametro().get() + " Escolher: " +
                configuracao.cor().get(), Toast.LENGTH_LONG).show();

        finish();
    }

}
