package stamps.model;

/**
 * classe représentant l'objet de collection qui est un satellite
 *
 * @author baptistedie
 */
public class Satellite {

    /**
     * identifiant unique du satellite
     */
    private String identifiant;

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
}
