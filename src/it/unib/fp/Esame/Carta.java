package it.unib.fp.Esame;

import it.kibo.fp.lib.InputData;

import java.util.*;

class Carta {
    private String nome;
    private String descrizione;
    private boolean equipaggiabile;
    private int copieTotali;
    private String valori;
    private String semi;

    public Carta(String nome, String descrizione, boolean equipaggiabile, int copieTotali, String valori, String semi) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.equipaggiabile = equipaggiabile;
        this.copieTotali = copieTotali;
        this.valori = valori;
        this.semi = semi;
    }

    public String getNome() {
        return nome;
    }

    public boolean isEquipaggiabile() {
        return equipaggiabile;
    }

    public String getValori() {
        return valori;
    }

    public String getSemi() {
        return semi;
    }

    public void usaBang(Persona bersaglio) {
        System.out.println("Hai usato la carta BANG! su " + bersaglio.getNome());

        if (bersaglio.haBarileEquipaggiato()) {
            System.out.println(bersaglio.getNome() + " ha un Barile equipaggiato e ha evitato il colpo.");
        } else if (bersaglio.haMancato()) {
            boolean usaMancato = InputData.readYesOrNo("Vuoi usare un mancato: ");
            if (usaMancato) {
                System.out.println(bersaglio.getNome() + " ha usato un mancato e ha evitato il colpo.");
            }
            int vitaBersagio = bersaglio.getVita();
            vitaBersagio--;
            bersaglio.setVita(vitaBersagio);
            System.out.println("Il colpo ha colpito " + bersaglio.getNome() + "!");
        }
    }

    public void usaBarile() {
        System.out.println("Hai usato la carta Barile per evitare il colpo!");

    }
    public void usaBirra(Persona giocatore) {
        if (giocatore.getRuolo().equals("Sceriffo")) {
            if (giocatore.getVita() < 5) {
                int vita = giocatore.getVita();
                giocatore.setVita(vita + 1);
                System.out.println("Hai recuperato un punto ferita. Punti ferita attuali: " + giocatore.getVita());
            } else {
                System.out.println("Non hai subito danni, quindi non puoi recuperare punti ferita.");
            }
        } else {
            if (giocatore.getVita() < 4) {
                int vita = giocatore.getVita();
                giocatore.setVita(vita + 1);
                System.out.println("Hai recuperato un punto ferita. Punti ferita attuali: " + giocatore.getVita());
            } else {
                System.out.println("Non hai subito danni, quindi non puoi recuperare punti ferita.");
            }
        }
    }

    public void usaCatBalou(Persona bersaglio) {
        Partita partita = new Partita(new Mazzo());
        System.out.println("Hai usato la carta Cat Balou per scartare una carta a " + bersaglio.getNome());
        if (!bersaglio.getCarteInMano().isEmpty() && !bersaglio.getCarteInGioco().isEmpty()) {
            boolean manoOTavolo = InputData.readYesOrNo("Vuoi pescare le carte dalla mano del giocatore?: ");
            if (manoOTavolo) {
                int carteInMano = bersaglio.getCarteInMano().size();
                int scegliCarta = InputData.readIntegerBetween("Scegli il numero della carta che vuoi rubare all'avversario", 1, carteInMano);
                Carta cartaRubata = bersaglio.getCarteInMano().remove(scegliCarta - 1);
                partita.getListaScarti().add(cartaRubata);
            } else {
                int carteInMano = bersaglio.getCarteInGioco().size();
                int scegliCarta = InputData.readIntegerBetween("Scegli il numero della carta che vuoi rubare all'avversario", 1, carteInMano);
                Carta cartaRubata = bersaglio.getCarteInGioco().remove(scegliCarta - 1);
                partita.getListaScarti().add(cartaRubata);
            }
        }

    }

    public void usaDiligenza(List mazzo, Persona giocatore) {
        Partita partita = new Partita(new Mazzo());
        System.out.println("Hai usato la carta Diligenza per pescare due carte.");
        for (int i = 0; i < 2; i++) {
            partita.pescaCarta(giocatore);
        }
    }

    public void usaGatling(List<Persona> giocatori, Persona giocatoreCorrente) {
        System.out.println("Hai usato la carta Gatling per sparare un BANG! a tutti gli altri giocatori.");
        for (Persona bersaglio : giocatori) {
            if (!bersaglio.equals(giocatoreCorrente)) {
                int vitaBersagio = bersaglio.getVita();
                vitaBersagio--;
                bersaglio.setVita(vitaBersagio);
                System.out.println("Il colpo ha colpito " + bersaglio.getNome() + "!");

            }
        }
    }

    public void usaMancato(Persona giocatore) {
        System.out.println("Hai usato la carta Mancato! per annullare un colpo.");
    }

    public void usaMirino(Persona giocatore, List<Persona> giocatori) {
        System.out.println("Hai usato la carta Mirino per vedere gli altri giocatori a una distanza diminuita di 1.");
        for (Persona persona : giocatori) {
            if (!persona.equals(giocatore)) {
                giocatore.getPistola().riduciDistanza(giocatore);
            }
        }
    }

    public void usaMustang(Persona giocatore, List<Persona> giocatori) {
        System.out.println("Hai usato la carta Mustang per essere visto dagli altri a una distanza aumentata di 1.");
        giocatore.getPistola().aumentaDistanza(giocatore);
    }

    public void usaPanico(Persona bersaglio, Persona giocatore) {
        System.out.println("Hai usato la carta Panico! per pescare una carta da " + bersaglio.getNome());
        bersaglio.rubaCarta(bersaglio, giocatore);

    }

    public void usaSaloon(List<Persona> giocatori) {
        System.out.println("Hai usato la carta Saloon per far recuperare un punto ferita a tutti i giocatori.");
        for (Persona giocatore : giocatori) {
            if (giocatore.getRuolo().equals("Sceriffo")) {
                if (giocatore.getVita() < 5) {
                    int vita = giocatore.getVita();
                    giocatore.setVita(vita + 1);
                    System.out.println("Hai recuperato un punto ferita. Punti ferita attuali: " + giocatore.getVita());
                } else {
                    System.out.println("Non hai subito danni, quindi non puoi recuperare punti ferita.");
                }
            } else {
                if (giocatore.getVita() < 4) {
                    int vita = giocatore.getVita();
                    giocatore.setVita(vita + 1);
                    System.out.println("Hai recuperato un punto ferita. Punti ferita attuali: " + giocatore.getVita());
                } else {
                    System.out.println("Non hai subito danni, quindi non puoi recuperare punti ferita.");
                }
            }
        }
    }

    public void usaWellsFargo(List mazzo, Persona giocatore) {
        Partita partita = new Partita(new Mazzo());
        System.out.println("Hai usato la carta Wells Fargo per pescare tre carte.");
        for (int i = 0; i < 3; i++) {
            partita.pescaCarta(giocatore);
        }
    }

    @Override
    public String toString() {
        return "Carta{" + nome +
                '}';
    }
}
