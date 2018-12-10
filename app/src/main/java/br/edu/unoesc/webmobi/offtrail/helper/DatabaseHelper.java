package br.edu.unoesc.webmobi.offtrail.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.androidannotations.annotations.EBean;

import java.sql.SQLException;
import java.util.List;

import br.edu.unoesc.webmobi.offtrail.model.Cidade;
import br.edu.unoesc.webmobi.offtrail.model.Grupo;
import br.edu.unoesc.webmobi.offtrail.model.GrupoTrilheiro;
import br.edu.unoesc.webmobi.offtrail.model.Moto;
import br.edu.unoesc.webmobi.offtrail.model.Trilheiro;
import br.edu.unoesc.webmobi.offtrail.model.Usuario;

@EBean
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    // This is created in android directories;
    private static final String DATABASE_NAME = "offtrail.db";
    // TO UPDATE DATABASE: any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Cidade, Integer> cidadeDao = null;
    private Dao<Usuario, String> usuarioDao = null;
    private Dao<Grupo, Integer> grupoDao = null;
    private Dao<Trilheiro, Integer> trilheiroDao = null;
    private Dao<Moto, Integer> motoDao = null;
    private Dao<GrupoTrilheiro, Integer> grupoTrilheiroDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, Cidade.class);
            TableUtils.createTable(connectionSource, Grupo.class);
            TableUtils.createTable(connectionSource, Moto.class);
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Trilheiro.class);
            TableUtils.createTable(connectionSource, GrupoTrilheiro.class);

            Usuario u = new Usuario();
            u.setEmail("anderson");
            u.setSenha("anderson");
            getUsuarioDao().create(u);

            Cidade c = new Cidade();
            c.setNome("Maravilha");
            getCidadeDao().create(c);

            Grupo g = new Grupo();
            g.setNome("TrilheirosMH");
            g.setCidade(c);
            getGrupoDao().create(g);

            g = new Grupo();
            g.setCidade(c);
            g.setNome("Lobos do Asfalto");
            getGrupoDao().create(g);

            Moto m = new Moto();
            m.setMarca("Honda");
            m.setModelo("GC");
            m.setCilidrada("125cc");
            m.setCor("Verde");
            getMotoDao().create(m);

            m = new Moto();
            m.setMarca("Honda");
            m.setModelo("Titan");
            m.setCilidrada("150cc");
            m.setCor("Preta");
            getMotoDao().create(m);

            m = new Moto();
            m.setMarca("Honda");
            m.setModelo("CRF");
            m.setCilidrada("230cc");
            m.setCor("Vermelha");
            getMotoDao().create(m);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }


        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate!");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Cidade.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public Dao<Cidade, Integer> getCidadeDao() throws SQLException {
        if (cidadeDao == null) {
            cidadeDao = getDao(Cidade.class);
        }
        return cidadeDao;
    }

    public Dao<Grupo, Integer> getGrupoDao() throws SQLException {
        if (grupoDao == null) {
            grupoDao = getDao(Grupo.class);
        }
        return grupoDao;
    }

    public Dao<Trilheiro, Integer> getTrilheiroDao() throws SQLException {
        if (trilheiroDao == null) {
            trilheiroDao = getDao(Trilheiro.class);
        }
        return trilheiroDao;
    }

    public Dao<Usuario, String> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<Moto, Integer> getMotoDao() throws SQLException {
        if (motoDao == null) {
            motoDao = getDao(Moto.class);
        }
        return motoDao;
    }

    public Dao<GrupoTrilheiro, Integer> getGrupoTrilheiroDao() throws SQLException {
        if (grupoTrilheiroDao == null) {
            grupoTrilheiroDao = getDao(GrupoTrilheiro.class);
        }
        return grupoTrilheiroDao;
    }

    @Override
    public void close() {
        super.close();
        cidadeDao = null;
    }

    public Usuario validaLogin(String email, String senha) {

        List<Usuario> usuarios = null;
        try {
            usuarios = getUsuarioDao().queryBuilder().
                    where().
                    eq("email", email).
                    and().
                    eq("senha", senha).
                    query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (usuarios != null && usuarios.size() > 0){
            return usuarios.get(0);
        }

        return null;

    }
}