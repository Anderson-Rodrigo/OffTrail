package br.edu.unoesc.webmobi.offtrail.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import java.sql.SQLException;
import java.util.List;

import br.edu.unoesc.webmobi.offtrail.R;
import br.edu.unoesc.webmobi.offtrail.adapter.TrilheiroAdapter;
import br.edu.unoesc.webmobi.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmobi.offtrail.model.Cidade;
import br.edu.unoesc.webmobi.offtrail.model.Usuario;
import br.edu.unoesc.webmobi.offtrail.rest.CidadeClient;
import br.edu.unoesc.webmobi.offtrail.rest.CidadeClient_;
import br.edu.unoesc.webmobi.offtrail.rest.Endereco;

@EActivity(R.layout.activity_principal)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    ListView lstTrilheiros;

    @Bean
    TrilheiroAdapter trilheiroAdapter;
    @Pref
    Configuracao_ configuracao;
    @RestService
    CidadeClient cidadeClient;

    ProgressDialog pd;

    @Bean
    DatabaseHelper dh;

    @AfterViews
    public void inicializar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            */

                Intent itCadastro = new Intent(
                        PrincipalActivity.this, CadastroTrilheiroActivity_.class
                );
                startActivity(itCadastro);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Usuario u = (Usuario) getIntent().getSerializableExtra("usuario");
        Toast.makeText(this, "Seja bem-vindo! - " + u.getEmail(), Toast.LENGTH_LONG).show();


        Toast.makeText(this, configuracao.parametro().get(), Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        atualizaListaTrilheiros();
    }

    public void atualizaListaTrilheiros() {
        Toast.makeText(this, "Atualizando lista de trilheiros...", Toast.LENGTH_LONG).show();

        trilheiroAdapter.updateListTrilheiros();
        trilheiroAdapter.notifyDataSetChanged();

        lstTrilheiros.setAdapter(trilheiroAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_sincronizar) {
            //Inicializar progress dialog
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setTitle("Consultado...");
            pd.setIndeterminate(true);
            pd.show();

            consultarCidadesPorNome();

        } else if (id == R.id.nav_preferencias) {

            Intent itConfig = new Intent(
                    PrincipalActivity.this, PreferenciasActivity_.class
            );
            startActivity(itConfig);
        } else if (id == R.id.nav_sobre) {
            Intent itSobre = new Intent(
                    this, SobreActivity.class
            );
            startActivity(itSobre);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Background(delay = 2000)
    public void consultarCidadesPorNome() {

        List<Endereco> e = cidadeClient.getEndereco("São");
        if (e != null && e.size() > 0) {
            for (Endereco end : e) {
                try {
                    Cidade c = new Cidade();
                    c.setNome(end.getLocalidade());
                    dh.getCidadeDao().createIfNotExists(c);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                mostrarResultado(String.valueOf(dh.getCidadeDao().queryForAll().size()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @UiThread
    public void mostrarResultado(String resultado) {
        pd.dismiss();
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }

}
