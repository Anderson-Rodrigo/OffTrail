package br.edu.unoesc.webmobi.offtrail.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.j256.ormlite.stmt.DeleteBuilder;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.sql.SQLException;
import java.util.List;

import br.edu.unoesc.webmobi.offtrail.helper.DatabaseHelper;
import br.edu.unoesc.webmobi.offtrail.model.Trilheiro;
import br.edu.unoesc.webmobi.offtrail.ui.CadastroTrilheiroActivity_;
import br.edu.unoesc.webmobi.offtrail.ui.PrincipalActivity;
import br.edu.unoesc.webmobi.offtrail.ui.TrilheiroItemView;
import br.edu.unoesc.webmobi.offtrail.ui.TrilheiroItemView_;

@EBean
public class TrilheiroAdapter
        extends BaseAdapter {
    List<Trilheiro> trilheiros;

    @Bean
    DatabaseHelper dh;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        try {
            trilheiros = dh.getTrilheiroDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return trilheiros.size();
    }

    @Override
    public Trilheiro getItem(int position) {
        return trilheiros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrilheiroItemView trilheiroItemView;
        if (convertView == null) {
            trilheiroItemView = TrilheiroItemView_.build(context);
        } else {
            trilheiroItemView = (TrilheiroItemView) convertView;
        }

        trilheiroItemView.bind(getItem(position));

        return trilheiroItemView;
    }

    public void updateListTrilheiros(){
        try {
            // trilheiros = dh.getTrilheiroDao().queryForAll();

            trilheiros = dh.getTrilheiroDao().queryBuilder().orderBy("nome", false).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirTrilheiro(Trilheiro t){
        try {
            DeleteBuilder deleteBuilder = dh.getGrupoTrilheiroDao().deleteBuilder();

            deleteBuilder.where().eq("codigo", t.getCodigo());
            deleteBuilder.delete();

            DeleteBuilder deleteBuilderT = dh.getTrilheiroDao().deleteBuilder();

            deleteBuilderT.where().eq("codigo", t.getCodigo());
            deleteBuilderT.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}