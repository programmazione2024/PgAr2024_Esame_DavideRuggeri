package it.unib.fp.Esame;

import it.kibo.fp.lib.InputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


class Partita {
    private List<Persona> listaGiocatori;
    private List<Carta> listaMazzo;
    private List<Carta> listaScarti;
    private int giocatoreCorrente;
    int numGiocatori;

    public Partita(Mazzo mazzo) {
        this.listaGiocatori = new ArrayList<>();
        this.listaMazzo = mazzo.creaMazzo();
        this.giocatoreCorrente = 0;
    }

    public List<Carta> getListaMazzo() {
        return listaMazzo;
    }

    public int getGiocatoreCorrente() {
        return giocatoreCorrente;
    }

    public void regole() {
        System.out.println("Sceriffo: il suo compito è di eliminare tutti i Fuorilegge ed il Rinnegato, riportando così l’ordine in città.");
        System.out.println("Fuorilegge: vogliono eliminare lo Sceriffo, ma non hanno scrupoli ad eliminarsi l’un l’altro per incassare le taglie sulle loro teste!");
        System.out.println("Rinnegato: vuole diventare il nuovo Sceriffo; il suo compito è di rimanere l’ultimo personaggio in gioco.");
        System.out.println("Vice: aiutano e proteggono lo Sceriffo, e perseguono i suoi stessi obiettivi, anche a costo della loro vita!");
    }

    public List<Carta> getListaScarti() {
        return listaScarti;
    }

    public void inizializzaGiocatori() {

        numGiocatori = InputData.readIntegerBetween("Inserire il numero di giocatori che intendono giocare la partita (minimo 4 massimo 7): ", 4, 7);
        List<String> ruoli = new ArrayList<>();

        ruoli.add("Sceriffo");
        ruoli.add("Fuorilegge");
        ruoli.add("Fuorilegge");
        ruoli.add("Rinnegato");

        if (numGiocatori >= 5) {
            ruoli.add("Vice");
        }
        if (numGiocatori >= 6) {
            ruoli.add("Fuorilegge");
        }
        if (numGiocatori == 7) {
            ruoli.add("Vice");
        }

        Collections.shuffle(ruoli.subList(1, ruoli.size()));

        for (String ruolo : ruoli) {
            int vita;
            if (ruolo.equals("Sceriffo"))
                vita = 5;
            else {
                vita = 4;
            }
            listaGiocatori.add(new Persona(ruolo, vita));
            System.out.println(listaGiocatori.size() + " " + ruolo);
        }
    }

    public void inizioPartita() {
        int carteDaPescare;

        for (Persona giocatore : listaGiocatori) {
            for (int i = 0; i < giocatore.getVita(); i++) {
                Carta cartaPescata = pescaCarta(giocatore);
            }
        }
    }

    public void cambioTurno() {
        giocatoreCorrente = (giocatoreCorrente + 1);
    }

    public void setListaScarti(List<Carta> listaScarti) {
        this.listaScarti = listaScarti;
    }

    public boolean verificaVittoria() {
        Iterator<Persona> iterator = listaGiocatori.iterator();
        while (iterator.hasNext()) {
            Persona giocatore = iterator.next();
            if (giocatore.getVita() <= 0) {
                System.out.println(giocatore.getNome() + " è stato eliminato dalla partita.");
                iterator.remove();
            }
        }

        if (listaGiocatori.size() == 1) {
            Persona vincitore = listaGiocatori.get(0);
            System.out.println("Il giocatore " + vincitore.getNome() + " ha vinto la partita!");
            return true;
        }
        return false;
    }


    public void eliminaPersona(Persona persona) {
        System.out.println(giocatoreCorrente + " è stato eliminato dalla partita");
        persona.getCarteInMano();
        listaGiocatori.remove(persona);
    }

    public List<Persona> getListaGiocatori() {
        return listaGiocatori;
    }

    public boolean controllaVita(Persona persona) {
        if (persona.getVita() > 0) {
            return true;
        }
        return false;
    }

    public Carta pescaCarta(Persona persona) {
        if (listaMazzo.isEmpty()) {
            throw new IllegalStateException("Il mazzo è vuoto, impossibile pescare una carta.");
        }
        Carta cartaPescata = listaMazzo.remove(0);
        persona.getCarteInMano().add(cartaPescata);
        return cartaPescata;
    }
}
