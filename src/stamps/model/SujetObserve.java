package stamps.model;

import java.util.ArrayList;

/**
 * classe qui permet la manipulation des Observateurs
 *
 * @author baptistedie
 */
public class SujetObserve {

    /**
     * collection des observateurs
     */
    private final ArrayList<Obervateur> obervateurs;


    /**
     * constructeur principal de la classe
     */
    public SujetObserve(){
        obervateurs = new ArrayList<>(10);
    }

    /**
     * méthode qui permet d'ajouter un observateur à la collection
     * @param obs observateur ajouté
     */
    public void ajouterObservateur(Obervateur obs){
        obervateurs.add(obs);
    }
}
