package stamps.exception;

/**
 * la classe hérite d'Exception et elle est déclenchée en cas d'une date nom valide pour le satellite
 *
 * @author baptistedie
 */
public class CollectionExceptionDate extends Exception{

    /**
     * constructeur de la classe
     *
     * @param message message d'erreur pour la date
     */
    public CollectionExceptionDate(String message){
        super(message);
    }
}
