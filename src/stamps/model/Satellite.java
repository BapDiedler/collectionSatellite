package stamps.model;

import java.util.*;

/**
 * classe représentant l'objet de collection qui est un satellite
 *
 * @author baptistedie
 */
public class Satellite {

    /**
     * identifiant unique du satellite
     */
    private final String identifiant;

    /**
     * collection de string qui représente les mots clefs du satellite
     * utilisation d'ensemble pour éviter les répétitions
     */
    private Set<String> motsClefs;

    /**
     * nom du satellite
     */
    private String nom;

    /**
     * date de création du satellite
     */
    private int date;

    /**
     * constructeur vide de la classe
     */
    public Satellite(){
        this("satellite");
    }

    /**
     * constructeur principal de la classe
     *
     * @param nom nom du satellite
     */
    public Satellite(String nom){
        this.identifiant = FabriqueIdentifiants.getInstance().getIdentifiant();
        this.nom = nom;
        this.motsClefs = new HashSet<>(10);
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

    @Override
    public String toString() {
        return nom+identifiant;
    }
}
