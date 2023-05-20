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
        this("titre");
    }



    /**
     * constructeur principal de la classe
     *
     * @param titre élément qui représente le titre de l'info
     */
    public Information(String titre){
        this.titre = titre;
        this.texte = "zone de texte";
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



    /**
     * méthode qui permet de récupérer le texte de l'information
     * @return les informations
     */
    public String getTexte() {
        return texte;
    }



    /**
     * méthode qui permet d'obtenir le titre de l'information
     * @return le titre de l'information
     */
    public String getTitre() {
        return titre;
    }
}
