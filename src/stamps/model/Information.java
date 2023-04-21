package stamps.model;

/**
 * classe qui permet de représenter les informations qui compose les satellites
 *
 * @author baptistedie
 */
public class Information {

    /**
     * champ qui permet de poser un titre à l'information
     */
    private String titre;

    /**
     * champ qui permet de représenter les informations plus précises sur le satellite
     */
    private String texte;

    /**
     * constructeur principal de la classe
     */
    public Information(){
        this(null);
    }

    /**
     * constructeur principal de la classe
     *
     * @param titre élément qui représente le titre de l'info
     */
    public Information(String titre){
        this.titre = titre;
    }

    /**
     * méthode qui permet d'ajouter un texte à l'information
     *
     * @param texte texte ajouté
     */
    public void setTexte(String texte) {
        this.texte = texte;
    }

    /**
     * méthode qui permet d'ajouter un titre à l'information
     *
     * @param titre titre ajouté
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTexte() {
        return texte;
    }

    public String getTitre() {
        return titre;
    }
}