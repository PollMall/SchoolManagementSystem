package validators;

import domains.Nota;
import exceptions.ValidationException;

public class ValidatorNota implements Validator<Nota> {
    @Override
    public void validate(Nota entity) throws ValidationException {
        String message="";
        if(entity.getNota()<1 || entity.getNota()>10) message+="Nota nu este intre 1 si 10; ";
        if(entity.getProfesor().contains("0") || entity.getProfesor().contains("1") || entity.getProfesor().contains("2") || entity.getProfesor().contains("3") ||
                entity.getProfesor().contains("4") || entity.getProfesor().contains("5") || entity.getProfesor().contains("6") || entity.getProfesor().contains("7") ||
                entity.getProfesor().contains("8") || entity.getProfesor().contains("9")) message+="Numele profesorului contine cifre; ";
        //to be continued
        if(!message.equals("")){
            throw new ValidationException(message);
        }
    }
}
