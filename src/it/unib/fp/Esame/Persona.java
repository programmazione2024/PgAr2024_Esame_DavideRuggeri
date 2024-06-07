    package it.unib.fp.Esame;

    import it.kibo.fp.lib.InputData;
    import it.kibo.fp.lib.Menu;

    import java.util.ArrayList;
    import java.util.List;

    public class Persona {
        private String nome;
        private String ruolo;
        private int vita;
        private List<Carta> carteInMano;
        private List<Carta> carteInGioco;
        private Pistola pistola;

        public Persona(String ruolo, int vita) {
            this.nome = InputData.readString("Inserire il nome del giocatore: ", true);
            this.ruolo = ruolo;
            this.vita = vita;
            this.carteInMano = new ArrayList<>();
            this.carteInGioco = new ArrayList<>();
            this.pistola = new Pistola("Colt .45", null, true, 0, null, null, 1);
        }

        public String getNome() {
            return nome;
        }

        public String getRuolo() {
            return ruolo;
        }
        /**
         * Gestisce il turno del giocatore corrente, permettendo di eseguire azioni come pescare carte, giocare carte, scartare carte o terminare il turno.
         * Durante il turno, il giocatore può scegliere tra diverse opzioni tramite un menu.
         * Le azioni disponibili dipendono dalle carte che il giocatore ha in mano e dalle condizioni di gioco attuali.
         */
        public void turnoGiocatore() {
            Partita partita = new Partita(new Mazzo());
            for (int i = 0; i < 2; i++) {
                Carta cartaPescata = partita.pescaCarta(this);
                System.out.println("Hai pescato una carta: " + cartaPescata);
            }
            System.out.println("Le carte che hai in mano sono: " + carteInMano);
            System.out.println("Le carte che hai in gioco sono: " + carteInGioco);

            int scelta;
            List<Persona> copiaGiocatori = new ArrayList<>(partita.getListaGiocatori());
            for (Persona persona : copiaGiocatori) {
                if (persona.getVita() <= 0) {
                    partita.getListaGiocatori().remove(persona);
                    System.out.println(persona.getNome() + " è stato eliminato dalla partita");
                }
            }
            do {
                Menu menu = new Menu("Scgli un'opzione", new String[]{"Gioca una carta", "Scarta una carta", "Termina turno"}, false, false, false);
                scelta = menu.choose();

                switch (scelta) {
                    case 1:
                        System.out.println("Le carte che hai in mano sono: ");
                        for (int i = 0; i < carteInMano.size(); i++) {
                            System.out.println((i + 1) + ". " + carteInMano.get(i).getNome());
                        }
                        int sceltaCarta = InputData.readIntegerBetween("Scegli il numero della carta che vuoi utilizzare: ", 1, carteInMano.size());

                        Carta cartaScelta = carteInMano.get(sceltaCarta - 1);

                        switch (cartaScelta.getNome()) {
                            case "BANG!":
                                String nomeBersaglio = InputData.readString("Inserire il nome del giocatore bersaglio: ", true);
                                Persona bersaglio = null;
                                for (Persona persona : partita.getListaGiocatori()) {
                                    if (persona.getNome().equals(nomeBersaglio)) {
                                        bersaglio = persona;
                                        break;
                                    }
                                }
                                if (bersaglio != null) {
                                    cartaScelta.usaBang(bersaglio);
                                } else {
                                    System.out.println("Giocatore bersaglio non trovato.");
                                }
                                break;
                            case "Birra":
                                cartaScelta.usaBirra(this);
                                break;
                            case "Saloon":
                                cartaScelta.usaSaloon(partita.getListaGiocatori());
                                break;
                            case "Diligenza":
                                cartaScelta.usaDiligenza(partita.getListaMazzo(), this);
                                break;
                            case "Wells Fargo":
                                cartaScelta.usaWellsFargo(partita.getListaMazzo(), this);
                                break;
                            case "Panico!":
                                String nomeBersaglio2 = InputData.readString("Inserire il nome del giocatore bersaglio: ", true);
                                Persona bersaglio2 = null;
                                for (Persona persona : partita.getListaGiocatori()) {
                                    if (persona.getNome().equals(nomeBersaglio2)) {
                                        bersaglio2 = persona;
                                        break;
                                    }
                                }
                                if (bersaglio2 != null) {
                                    cartaScelta.usaPanico(bersaglio2, this);
                                } else {
                                    System.out.println("Giocatore bersaglio non trovato.");
                                }
                                break;
                            case "Cat Balou":
                                String nomeBersaglio3 = InputData.readString("Inserire il nome del giocatore bersaglio: ", true);
                                Persona bersaglio3 = null;
                                for (Persona persona : partita.getListaGiocatori()) {
                                    if (persona.getNome().equals(nomeBersaglio3)) {
                                        bersaglio3 = persona;
                                        break;
                                    }
                                }
                                if (bersaglio3 != null) {
                                    cartaScelta.usaCatBalou(bersaglio3);
                                } else {
                                    System.out.println("Giocatore bersaglio non trovato.");
                                }
                                break;
                            case "Catling":
                                cartaScelta.usaGatling(partita.getListaGiocatori(), this);
                                break;
                            case "Barile":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                break;
                            case "Mirino":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                cartaScelta.usaMirino(this, partita.getListaGiocatori());
                                break;
                            case "Mustang":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                cartaScelta.usaMustang(this, partita.getListaGiocatori());
                                break;
                            case "Schofield":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                pistola.setDistanza(2);
                                break;
                            case "Remington":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                pistola.setDistanza(3);
                                break;
                            case "Rev. Carabine":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                pistola.setDistanza(4);
                                break;
                            case "Winchester":
                                carteInGioco.add(cartaScelta);
                                carteInMano.remove(cartaScelta);
                                pistola.setDistanza(5);
                                break;
                            default:
                                System.out.println("Questa carta non può essere utilizzata in questo momento.");
                        }
                        break;

                    case 2:
                        int sceltaCarta2 = InputData.readIntegerBetween("Scegli il numero della carta che vuoi scartare: ", 1, carteInMano.size());

                        Carta cartaScelta2 = carteInMano.get(sceltaCarta2 - 1);
                        if (partita.getListaScarti() == null) {
                            partita.setListaScarti(new ArrayList<>());
                        }
                        carteInMano.remove(cartaScelta2);
                        partita.getListaScarti().add(cartaScelta2);
                        break;
                    case 3:
                        if (carteInMano.size() < vita) {
                            scelta = 4;
                        } else {
                            int daScartare = carteInMano.size() - vita;
                            System.out.println("Devi prima scartare" + daScartare + "carte!");
                            break;
                        }
                }
            } while (scelta != 4);
        }

        @Override
        public String toString() {
            return "Ruolo: " + ruolo + ", Vita: " +
                    vita + ", Carte in Mano: " +
                    carteInMano.size() + ", Carte in Gioco: " +
                    carteInGioco.size();
        }

        public boolean haBarileEquipaggiato(){
            if (carteInGioco.equals("Barile")) {
                return true;
            }
            return false;

        }
        public boolean haMancato(){
            if (carteInMano.equals("Mancato!")) {
                return true;
            }
            return false;
        }
        public boolean haMirino(){
            if (carteInMano.equals("Mirino")) {
                return true;
            }
            else
                return false;
        }
        public boolean haMustung(){
            if (carteInGioco.equals("Mustang")) {
                return true;
            } else
                return false;
        }

        public List<Carta> getCarteInGioco() {
            return carteInGioco;
        }

        public Carta rubaCarta(Persona bersaglio, Persona giocatore) {
            if (!bersaglio.carteInMano.isEmpty() && !bersaglio.carteInGioco.isEmpty()) {
                boolean manoOTavolo = InputData.readYesOrNo("Vuoi pescare le carte dalla mano del giocatore?: ");
                if (manoOTavolo) {
                    int carteInMano = bersaglio.carteInMano.size();
                    int scegliCarta = InputData.readIntegerBetween("Scegli il numero della carta che vuoi rubare all'avversario", 1, carteInMano);
                    Carta cartaRubata = bersaglio.getCarteInMano().remove(scegliCarta - 1);
                    giocatore.getCarteInMano().add(cartaRubata);
                    System.out.println("Hai rubato la carta " + cartaRubata.getNome() + " a " + bersaglio.getNome() + ".");
                } else {
                    int carteInTavolo = bersaglio.carteInGioco.size();
                    int scegliCarta = InputData.readIntegerBetween("Scegli il numero della carta che vuoi rubare all'avversario", 1, carteInTavolo);
                    Carta cartaRubata = bersaglio.getCarteInMano().remove(scegliCarta - 1);
                    giocatore.getCarteInMano().add(cartaRubata);
                    System.out.println("Hai rubato la carta " + cartaRubata.getNome() + " a " + bersaglio.getNome() + ".");
                }
            }

            return null;
        }

        public int getVita() {
            return vita;
        }

        public void setVita(int vita) {
            this.vita = vita;
        }

        public List<Carta> getCarteInMano() {
            return carteInMano;
        }

        public Pistola getPistola() {
            return pistola;
        }
    }
