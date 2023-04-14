package stamps.model;

/**
 * classe qui permet de fabriquer des identifiants uniques à chaque satellite
 *
 * @author baptistedie
 */
public class FabriqueIdentifiants {

    /**
     * identifiant unique
     */
    private int identifiant;

    /**
     * instance unique de la fabrique
     */
    private static final FabriqueIdentifiants instance = new FabriqueIdentifiants();

    /**
     * constructeur de la classe
     */
    private FabriqueIdentifiants(){
        identifiant=0;
    }


    /**
     * getter sur l'instance unique de la fabrique
     *
     * @return l'instance de la fabrique
     */
    public static FabriqueIdentifiants getInstance() {
        return instance;
    }

    /**
     * getter sur l'identifiant courant en passant au suivant
     *
     * @return l'identifiant sous forme de string
     */
    public String getIdentifiant(){
        return String.valueOf(identifiant++);
    }

    /**
     * mise à zero des identifiants
     */
    public void reset(){
        identifiant=0;
    }
}
