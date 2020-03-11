package validators;

import domains.Student;
import exceptions.ValidationException;

public class ValidatorStudent implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidationException {
        String message="";
        if(entity.getId()<0) message+="ID negativ; ";
        if(entity.getGrupa()<0) message+="Grupa negativa; ";
        if(entity.getNume().contains("0")||entity.getNume().contains("1")||entity.getNume().contains("2")||entity.getNume().contains("3")||entity.getNume().contains("4")||entity.getNume().contains("5")||entity.getNume().contains("6")||entity.getNume().contains("7")||entity.getNume().contains("8")||entity.getNume().contains("9")) message+="Numele contine cifre; ";
        if(entity.getPrenume().contains("0")||entity.getPrenume().contains("1")||entity.getPrenume().contains("2")||entity.getPrenume().contains("3")||entity.getPrenume().contains("4")||entity.getPrenume().contains("5")||entity.getPrenume().contains("6")||entity.getPrenume().contains("7")||entity.getPrenume().contains("8")||entity.getPrenume().contains("9")) message+="Prenumele contine cifre; ";
        if(!entity.getEmail().matches("\\w+\\.?\\w+@\\w+\\.?\\w+\\.(com|ro)")) message+="Email invalid; ";
        if(entity.getCadruDidacticIndrumatorLab().contains("0")||entity.getCadruDidacticIndrumatorLab().contains("1")||entity.getCadruDidacticIndrumatorLab().contains("2")||entity.getCadruDidacticIndrumatorLab().contains("3")||entity.getCadruDidacticIndrumatorLab().contains("4")||entity.getCadruDidacticIndrumatorLab().contains("5")||entity.getCadruDidacticIndrumatorLab().contains("6")||entity.getCadruDidacticIndrumatorLab().contains("7")||entity.getCadruDidacticIndrumatorLab().contains("8")||entity.getCadruDidacticIndrumatorLab().contains("9")) message+="Numele cadrului didactic indrumator lab contine cifre; ";
        if(!message.equals("")) {
            throw new ValidationException(message);
        }
    }
}
