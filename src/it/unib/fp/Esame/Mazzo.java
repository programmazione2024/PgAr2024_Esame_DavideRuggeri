package it.unib.fp.Esame;

import java.util.*;

public class Mazzo {
    private List<Carta> mazzo;

    public Mazzo() {
        this.mazzo = new ArrayList<>();
    }

    public List creaMazzo() {
        Set<Carta> combinazioniCarteStampate = new HashSet<>();
        LettoreXML lettore = new LettoreXML();
        List<Carta> carteDalXML = lettore.leggiXMLcarte();
        List<Pistola> pistoleDalXML = lettore.leggiXMLArmi();
        mazzo.addAll(carteDalXML);
        mazzo.addAll(pistoleDalXML);

        Collections.shuffle(mazzo);
        return mazzo;
    }

    public void stampaMazzo() {
        Map<String, Integer> combinazioniCarteStampate = new HashMap<>();

        for (Carta carta : mazzo) {
            String combinazione = carta.getValori() + "-" + carta.getSemi();
            int conteggio = combinazioniCarteStampate.getOrDefault(combinazione, 0);

            if (conteggio < 2) {
                if (carta instanceof Pistola) {
                    Pistola pistola = (Pistola) carta;
                    System.out.println("Carta{" + pistola.toString() + "} " + pistola.getValori() + " di " + pistola.getSemi());
                } else {
                    System.out.println("Carta{" + carta.toString() + "} " + carta.getValori() + " di " + carta.getSemi());
                }
                combinazioniCarteStampate.put(combinazione, conteggio + 1);

            }
        }
    }

    public List<Carta> getMazzo() {
        return mazzo;
    }
}
