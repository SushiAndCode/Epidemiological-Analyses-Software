package progetto.Exception;

public class illegalRoleException extends Exception{

    public illegalRoleException(){

        super("L'attuale ruolo non ti permette di modificare questo file \n");

    }

}
