package br.edu.unoesc.webmobi.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Cidade implements Serializable {
    @DatabaseField(generatedId = true)
    private Integer codigo;
    @DatabaseField(canBeNull = false)
    private String nome;

    public Cidade(){

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
