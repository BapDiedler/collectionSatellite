package stamps.exception;

/**
 * la classe hérite d'Exception et elle est déclenchée en cas d'une date nom valide pour le satellite
 *
 * @author baptistedie
 */
public class CollectionExceptionValeurNonValide extends Exception{

    /**
     * constructeur de la classe
     *
     * @param message message d'erreur pour la date
     */
    public CollectionExceptionValeurNonValide(String message){
        super(message);
    }
}
