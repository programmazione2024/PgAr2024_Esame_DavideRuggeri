package it.unib.fp.Esame;


public class Main {
    public static void main(String[] args) {

        Mazzo mazzo = new Mazzo();

        Partita partita = new Partita(mazzo);

        partita.inizializzaGiocatori();

        partita.inizioPartita();
        for (Persona giocatore : partita.getListaGiocatori()) {
            giocatore.turnoGiocatore();
            partita.cambioTurno();
            if (partita.controllaVita(giocatore)){
                partita.eliminaPersona(giocatore);
            }
            partita.verificaVittoria();
        }

        System.out.println("Partita conclusa!");
    }
}
