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

    /**
     * méthode qui permet de trier les satellites en fonction de leur nom
     */
    public void trierNom(){
        satellites.sort(Comparator.comparing(Satellite::getNom));
    }

    @Override
    public Iterator<Satellite> iterator() {
        return satellites.iterator();
    }

    /**
     * getter sur le nombre d'éléments dans la collection
     *
     * @return le nombre de satellites dans la collection
     */
    public int nbSatellites(){
        return satellites.size();
    }

    /**
     * getter sur la collection de satellites qui permet de récupérer le satellite en fonction de sa position dans arrayList
     *
     * @param pos position du satellite dans arrayList
     * @return le satellite correspondant à la position
     */
    public Satellite getSatellite(int pos){
        return satellites.get(pos);
    }

    /**
     * le toString de la classe permet de voir les satellites dans l'ordre de la collection
     *
     * @return collection des satellites
     */
    public String toString(){
        StringBuilder affichage = new StringBuilder();
        for (Satellite satellite: satellites){
            affichage.append(satellite.getNom()).append("-");
        }
        return affichage.toString();
    }
}
