package br.edu.unoesc.webmobi.offtrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.sql.SQLException;

import br.edu.unoesc.webmobi.offtrail.R;
import br.edu.unoesc.webmobi.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmobi.offtrail.model.Usuario;

@EActivity(R.layout.activity_login)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class LoginActivity extends AppCompatActivity {

    private final String TAG = "OlaAndroid TAG";

    @Bean
    DatabaseHelper dh;

    @ViewById
    EditText edtLogin;
    @ViewById
    EditText edtSenha;

    public void finalizarApp(View v) {
        Log.d(TAG, "Encerrando aplicação...");
        finish();
        System.exit(0);
    }

    //Método exibir mensagem
    public void login(View v) {

        String strLogin = edtLogin.getText().toString();
        String strSenha = edtSenha.getText().toString();



        if (strLogin != null && strSenha != null &&
                !strLogin.trim().equals("") &&
                !strSenha.trim().equals("")) {

            Usuario u = dh.validaLogin(strLogin, strSenha);

            if (u != null){
                //params: de onde sai(this), para onde vai(.class)
                Intent itPrincipal = new Intent(
                        this, PrincipalActivity_.class
                );

                itPrincipal.putExtra("usuario", u);
                startActivity(itPrincipal);
                finish();
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
            edtLogin.setText("");
            edtSenha.setText("");
            edtLogin.requestFocus();
        }
        ;
    }

}
