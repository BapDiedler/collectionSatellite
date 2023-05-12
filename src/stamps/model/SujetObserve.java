package stamps.model;

import java.util.ArrayList;
import java.util.List;

/**
 * classe qui permet la manipulation des Observateurs
 *
 * @author baptistedie
 */
public class SujetObserve {

    /**
     * collection des observateurs
     */
    private transient final ArrayList<Obervateur> obervateurs;


    /**
     * constructeur principal de la classe
     */
    public SujetObserve(){
        obervateurs = new ArrayList<>(10);
    }

    /**
     * méthode qui permet d'ajouter un observateur à la collection
     *
     * @param obs observateur ajouté
     */
    public void ajouterObservateur(Obervateur... obs){
        obervateurs.addAll(List.of(obs));
    }


    /**
     * méthode qui fait réagir les observateurs
     */
    public void notifierObservateurs(){
        for(int i=0; i< obervateurs.size(); i++){
            obervateurs.get(i).reagir();
        }
    }

    public void clear(){
        obervateurs.clear();
    }
}
