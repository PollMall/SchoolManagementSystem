package validators;

import domains.Tema;
import exceptions.ValidationException;

public class ValidatorTema implements Validator<Tema>{
    @Override
    public void validate(Tema entity) throws ValidationException {
        String message="";
        if(entity.getId()<0) message+="ID negativ\n";
        if(entity.getStartWeek()<1 || entity.getStartWeek()>14) message+="StartWeek nu este intre 1 si 14\n";
        if(entity.getDeadlineWeek()<=entity.getStartWeek() || entity.getDeadlineWeek()>14) message+="DeadlineWeek nu este intre StartWeek si 14\n";
        if(!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
