package it.unib.fp.Esame;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LettoreXML {
    XMLInputFactory xmlif = null;
    XMLStreamReader xmlr = null;

    public List<Pistola> leggiXMLArmi() {
        List<Pistola> listaArmi = new ArrayList<>();
        try (FileInputStream reader = new FileInputStream("listaCarte.xml")) {
            XMLInputFactory xmlif = XMLInputFactory.newInstance();
            XMLStreamReader xmlr = xmlif.createXMLStreamReader(reader);

            String nome = null;
            String descrizione = null;
            boolean equipaggiabile = false;
            int distanza = 0;
            int copieTotali = 0;
            String valori = null;
            String semi = null;

            boolean isArma = false;

            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    String tag = xmlr.getLocalName();
                    switch (tag) {
                        case "arma":
                            isArma = true;
                            equipaggiabile = Boolean.parseBoolean(xmlr.getAttributeValue(null, "equipaggiabile"));
                            break;
                        case "nome":
                            if (isArma) {
                                nome = xmlr.getElementText();
                            }
                            break;
                        case "descrizione":
                            if (isArma) {
                                descrizione = xmlr.getElementText();
                            }
                            break;
                        case "distanza":
                            if (isArma) {
                                distanza = Integer.parseInt(xmlr.getElementText());
                            }
                            break;
                        case "copie":
                            if (isArma) {
                                copieTotali = Integer.parseInt(xmlr.getAttributeValue(null, "totale"));
                            }
                            break;
                        case "valore":
                            if (isArma) {
                                valori = xmlr.getElementText();
                            }
                            break;
                        case "seme":
                            if (isArma) {
                                semi = xmlr.getElementText();
                            }
                            break;
                    }
                } else if (xmlr.isEndElement()) {
                    String tag = xmlr.getLocalName();
                    if (tag.equals("copia") && isArma) {
                        listaArmi.add(new Pistola(nome, descrizione, equipaggiabile, copieTotali, valori, semi, distanza));
                    } else if (tag.equals("arma")) {
                        isArma = false;
                    }
                }
            }
        } catch (FactoryConfigurationError | XMLStreamException | IOException e) {
            System.out.println("Errore durante la lettura del XML");
            System.out.println(e.getMessage());
        }
        return listaArmi;
    }


    public List<Carta> leggiXMLcarte() {
        List<Carta> listaCarte = null;
        try (FileInputStream reader = new FileInputStream("listaCarte.xml")) {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(reader);

            listaCarte = new ArrayList<>();
            String nome = null;
            String descrizione = null;
            boolean equipaggiabile = false;
            int copieTotali = 0;
            String valori = null;
            String semi = null;
            boolean isCarta = false;

            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                    String tag = xmlr.getLocalName();
                    switch (tag) {
                        case "carta":
                            isCarta = true;
                            equipaggiabile = Boolean.parseBoolean(xmlr.getAttributeValue(null, "equipaggiabile"));
                            break;
                        case "nome":
                            nome = xmlr.getElementText();
                            break;
                        case "descrizione":
                            descrizione = leggiDescrizione();
                            break;
                        case "copie":
                            copieTotali = Integer.parseInt(xmlr.getAttributeValue(null, "totale"));
                            break;
                        case "valore":
                            valori = xmlr.getElementText();
                            break;
                        case "seme":
                            semi = xmlr.getElementText();
                            break;
                    }
                } else if (xmlr.isEndElement()) {
                    String tag = xmlr.getLocalName();
                    if (tag.equals("copia") && isCarta) {
                        listaCarte.add(new Carta(nome, descrizione, equipaggiabile, copieTotali, valori, semi));
                    } else if (tag.equals("arma")) {
                        isCarta = false;
                    }
                }
            }
        } catch (FactoryConfigurationError | XMLStreamException | IOException e) {
            System.out.println("Errore durante la lettura del XML");
            System.out.println(e.getMessage());
        }
        return listaCarte;
    }


    private String leggiDescrizione() throws XMLStreamException {
        StringBuilder descrizione = new StringBuilder();
        while (xmlr.hasNext()) {
            xmlr.next();
            if (xmlr.isCharacters()) {
                descrizione.append(xmlr.getText());
            } else if (xmlr.isEndElement() && xmlr.getLocalName().equals("descrizione")) {
                break;
            }
        }
        return descrizione.toString();
    }
}