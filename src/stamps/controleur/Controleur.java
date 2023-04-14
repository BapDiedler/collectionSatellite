package stamps.controleur;

import stamps.model.CollectionSatellites;
import stamps.model.Obervateur;


/**
 * classe abstraite permettant de représenter les contrôleurs de notre collection
 */
public abstract class Controleur implements Obervateur {

    /**
     * collection de satellites
     */
    public CollectionSatellites collectionSatellites;

    /**
     * constructeur principal de la classe
     *
     * @param collectionSatellites collection manipulée par la classe
     */
    public Controleur(CollectionSatellites collectionSatellites){
        this.collectionSatellites = collectionSatellites;
        collectionSatellites.ajouterObservateur(this);
    }
}
