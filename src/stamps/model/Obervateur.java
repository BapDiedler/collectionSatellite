package stamps.model;

/**
 * classe Observateur qui permet de faire le lien entre la partie model et controller du model MVC
 *
 * @author baptistedie
 */
public interface Obervateur {

    /**
     * méthode réagir qui sera activée à chaque action
     */
    void reagir();
}
