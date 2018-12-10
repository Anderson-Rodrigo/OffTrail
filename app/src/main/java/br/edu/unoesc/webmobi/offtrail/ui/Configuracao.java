package br.edu.unoesc.webmobi.offtrail.ui;

import android.graphics.Color;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Configuracao {

    // The field name will have default value "John"
    @DefaultInt(Color.GREEN)
    int cor();

    @DefaultString("Valor padrão")
    String parametro();
}