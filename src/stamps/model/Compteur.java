package stamps.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * la classe permet de compter les satellites de la collection
 */
public class Compteur {

    /**
     * conteur de la classe
     */
    private final IntegerProperty valeur ;

    /**
     * constructeur de la classe
     */
    public Compteur() {
        this.valeur = new SimpleIntegerProperty(0) ;
    }

    /**
     * setter sur la valeur du compteur
     *
     * @param valeur nouvelle valeur du compteur
     */
    public void setValeur(int valeur) {
        this.valeur.set(valeur);
    }

    /**
     * setter de la classe permettant d'ajouter 1 au compteur
     */
    public void incrementer() {
        valeur.setValue(valeur.intValue()+1);
    }

    /**
     * méthode qui permet de retirer 1 au compteur
     */
    public void decrementer() {
        valeur.setValue(valeur.intValue()-1);
    }

    /**
     * getter de la classe
     *
     * @return IntegerProperty
     */
    public IntegerProperty getPropertyValue() {
        return valeur ;
    }
}

