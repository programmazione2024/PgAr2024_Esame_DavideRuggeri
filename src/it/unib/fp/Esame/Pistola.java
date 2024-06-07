package it.unib.fp.Esame;

import java.util.List;

public class Pistola extends Carta {
    private int distanza;

    public Pistola(String nome, String descrizione, boolean equipaggiabile, int copieTotali, String valori, String semi, int distanza) {
        super(nome, descrizione, equipaggiabile, copieTotali, valori, semi);
        this.distanza = distanza;
    }

    public int getDistanza() {
        return distanza;
    }

    public void setDistanza(int distanza) {
        this.distanza = distanza;
    }

    public void riduciDistanza(Persona persona) {
        if (persona.haMirino()) {
            distanza--;
        }
    }

    public void aumentaDistanza(Persona persona) {
        if (persona.haMustung()) {
            distanza++;
        }
    }

    @Override
    public String toString() {
        return "Pistola{" + getNome() + '\'' +
                ", distanza=" + distanza +
                '}';
    }
}
