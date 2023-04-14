package stamps.model;

import java.util.*;

/**
 * Classe principale du model de la collection. La classe permet la gestion
 * des manipulations sur la collection
 *
 * @author baptistedie
 */
public class CollectionSatellites implements Iterable<Satellite>{

    /**
     * collection de satellites référencée par leur identifiant unique
     */
    ArrayList<Satellite> satellites;


    /**
     * constructeur principal de la classe
     */
    public CollectionSatellites(){
        satellites = new ArrayList<>(10);
    }

    /**
     * méthode qui permet l'ajout de satellite dans la collection
     *
     * @param nom nom donné au nouveau satellite
     */
    public void ajouter(String nom){
        Satellite satellite = new Satellite(nom);
        satellites.add(satellite);
    }

    /**
     * méthode qui permet de trier les satellites en fonction de leur date de création
     */
    public void trierDate(){
        satellites.sort(Comparator.comparingInt(Satellite::getDate));
    }

    /**
     * méthode qui permet de trier les satellites en fonction de leur apparition dans la collection
     */
    public void trierApparition(){
        satellites.sort(Comparator.comparing(Satellite::getIdentifiant));
    }

    public void trierNom(){
        satellites.sort(Comparator.comparing(Satellite::getNom));
    }

    @Override
    public Iterator<Satellite> iterator() {
        return satellites.iterator();
    }
}
