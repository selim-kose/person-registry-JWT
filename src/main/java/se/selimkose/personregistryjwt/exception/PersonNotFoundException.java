package se.selimkose.personregistryjwt.exception;

import java.util.function.Supplier;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(Integer personId){
        super("The person with student id: '" + personId + " does not exist in our records");
    }

}
