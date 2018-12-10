package br.edu.unoesc.webmobi.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class GrupoTrilheiro implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField(foreign = true, foreignColumnName = "codigo")
    private Grupo grupo;

    @DatabaseField(foreign = true, foreignColumnName = "codigo")
    private Trilheiro trilheiro;

    @DatabaseField(canBeNull = false)
    private String data;

    public GrupoTrilheiro(){

    }
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Trilheiro getTrilheiro() {
        return trilheiro;
    }

    public void setTrilheiro(Trilheiro trilheiro) {
        this.trilheiro = trilheiro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
