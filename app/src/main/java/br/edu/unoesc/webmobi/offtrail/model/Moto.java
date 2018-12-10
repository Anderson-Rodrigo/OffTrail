package br.edu.unoesc.webmobi.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Moto implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField(canBeNull = false)
    private String modelo;

    @DatabaseField(canBeNull = false)
    private String marca;

    @DatabaseField(canBeNull = false)
    private String cilidrada;

    @DatabaseField(canBeNull = false)
    private String cor;

    public Moto(){

    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCilidrada() {
        return cilidrada;
    }

    public void setCilidrada(String cilidrada) {
        this.cilidrada = cilidrada;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return getModelo() + " - " + getCilidrada();
    }
}
