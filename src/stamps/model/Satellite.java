package stamps.model;

import java.util.*;

/**
 * classe représentant l'objet de collection qui est un satellite
 *
 * @author baptistedie
 */
public class Satellite implements Iterable<Information>{

    /**
     * identifiant unique du satellite
     */
    private final String identifiant;

    /**
     * collection de string qui représente les mots clefs du satellite
     * utilisation d'ensemble pour éviter les répétitions
     */
    private final Set<String> motsClefs;

    /**
     * information sur le satellite
     */
    private ArrayList<Information> informations;

    /**
     * nom du satellite
     */
    private String nom;

    /**
     * date de création du satellite
     */
    private int date;

    /**
     * lien de l'image pour le satellite
     */
    private String url;

    /**
     * constructeur vide de la classe
     */
    public Satellite(){
        this("satellite","/pasImage.jpeg");
    }

    /**
     * constructeur principal de la classe
     *
     * @param nom nom du satellite
     */
    public Satellite(String nom, String url){
        this.identifiant = FabriqueIdentifiants.getInstance().getIdentifiant();
        this.nom = nom;
        this.motsClefs = new HashSet<>(10);
        this.informations = new ArrayList<>(10);
        this.url = url;
        this.date = Integer.MAX_VALUE;
    }

    /**
     * méthode qui permet d'ajouter des informations
     *
     * @param informations nouvelles informations
     */
    public void setInformations(Information... informations){
        this.informations.addAll(List.of(informations));
    }

    /**
     * setter de mots clefs dans notre satellite
     *
     * @param motsClefs mots clefs ajoutés
     */
    public void setMotsClefs(String... motsClefs){
        this.motsClefs.addAll(List.of(motsClefs));
    }

    /**
     * setter sur le nom du satellite
     *
     * @param nom nouveau nom du satellite
     */
    public void setNom(String nom){
        this.nom = nom;
    }

    /**
     * setter de la date du satellite
     *
     * @param date date de création du satellite
     */
    public void setDate(int date){
        this.date = date;
    }

    public void ajoutInfo(){
        informations.add(new Information());
    }

    /**
     * getter sur l'identifiant
     *
     * @return l'identifiant unique du satellite
     */
    public String getIdentifiant() {
        return identifiant;
    }

    /**
     * getter sur l'identifiant
     *
     * @return l'identifiant unique du satellite
     */
    public int getIdentifiantEntier() {
        return Integer.parseInt(identifiant);
    }

    /**
     * getter sur le nom du satellite
     *
     * @return le nom du satellite
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter sur la date de création du satellite
     *
     * @return la date du satellite
     */
    public int getDate() {
        return date;
    }

    /**
     * méthode qui permet dde savoir si notre satellite contient le mot clef
     *
     * @param motClef mots clefs testés
     * @return le nombre de mots en correspondance avec les mots clefs du satellite
     */
    public int getMotsClefs(String... motClef) {
        int cpt=0;
        for(String val: motClef) {
            cpt = motsClefs.contains(val) ? cpt+1 : cpt;
        }
        return cpt;
    }

    public Information getInformations(int pos) {
        if(pos< informations.size())
            return informations.get(pos);
        return new Information();
    }

    public int nbInformations(){
        return informations.size();
    }

    /**
     * méthode qui permet de connaitre l'url pour l'image du satellite
     *
     * @return url de l'image
     */
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return nom+identifiant;
    }

    @Override
    public Iterator<Information> iterator() {
        return informations.iterator();
    }
}
