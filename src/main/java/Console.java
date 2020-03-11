import domains.Nota;
import domains.Student;
import domains.Tema;
import exceptions.ValidationException;
import repositories.NotaXmlRepository;
import repositories.StudentXmlRepository;
import repositories.TemaXmlRepository;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import utils.structures.StructuraAnUniversitar;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Console {
    private StructuraAnUniversitar anUniversitar=new StructuraAnUniversitar();
    private StudentService studentService = new StudentService(new StudentXmlRepository(new File("src/main/resources/studenti.xml")));
    private TemaService temaService = new TemaService(new TemaXmlRepository(new File("src/main/resources/teme.xml")));
    private NotaService notaService = new NotaService(new NotaXmlRepository(new File("src/main/resources/note.xml"),studentService.getStudentRepository(),temaService.getTemaRepository(),anUniversitar),studentService.getStudentRepository());

    private void findOneStudent() throws IOException {
        System.out.println("Introduceti ID: ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        int id;
        if (!line.equals("")) {
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        } else {
            System.out.println("ID vid");
            return;
        }

        try {
            Student student = studentService.serviceFindOne(id);
            if (student != null)
                System.out.println(student);
            else
                System.out.println("ID inexistent");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void findAllStudent() {
        Iterable<Student> students = studentService.serviceFindAll();
        Boolean exist=Boolean.FALSE;
        for (Student student :
                students) {
            if(student!=null) {
                exist = Boolean.TRUE;
            }
            System.out.println(student);
        }
        if(!exist){
            System.out.println("Nu exista studenti!");
        }
    }

    private void saveStudent() throws IOException {
        System.out.println("Introduceti ID: ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        int id;
        if (!line.equals("")) {
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        } else {
            System.out.println("ID vid");
            return;
        }

        System.out.println("Introduceti nume:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String nume = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Nume vid");
            return;
        }

        System.out.println("Introduceti prenume:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String prenume = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Prenume vid");
            return;
        }

        System.out.println("Introduceti grupa:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        line = bufferedReader.readLine();
        int grupa;
        if (!line.equals("")) {
            try {
                grupa = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Grupa trebuie sa fie int");
                return;
            }
        } else {
            System.out.println("Grupa vida");
            return;
        }

        System.out.println("Introduceti email:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String email = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Email vid");
            return;
        }

        System.out.println("Introduceti nume cadru didactic indrumator lab:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String cadruDidacticIndrumatorLab = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Nume cadru didactic indrumator lab vid");
            return;
        }

        Student student = new Student(id, nume, prenume, grupa, email, cadruDidacticIndrumatorLab);
        try {
            Student aux = studentService.serviceSave(student);
            if (aux != null) {
                System.out.println("Student deja existent");
            } else {
                System.out.println("Student adaugat cu succes");
            }
        } catch (ValidationException ve) {
            System.out.println("Student nevalid");
            System.out.println(ve.getMesaj());
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }

    private void deleteStudent() throws IOException {
        System.out.println("Introduceti ID: ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        int id;
        if (!line.equals("")) {
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException nfe) {
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        } else {
            System.out.println("ID vid");
            return;
        }

        try {
            Student student = studentService.serviceDelete(id);
            if (student == null) {
                System.out.println("ID inexistent");
            } else {
                System.out.println("Student sters cu succes");
            }
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
    }

    private void updateStudent() throws IOException{
        System.out.println("Introduceti ID: ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        int id;
        if (!line.equals("")) {
            try {
                id = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        } else {
            System.out.println("ID vid");
            return;
        }

        System.out.println("Introduceti nume nou:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String nume = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Nume vid");
            return;
        }

        System.out.println("Introduceti prenume nou:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String prenume = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Prenume vid");
            return;
        }

        System.out.println("Introduceti grupa noua:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        line = bufferedReader.readLine();
        int grupa;
        if (!line.equals("")) {
            try {
                grupa = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Grupa trebuie sa fie int");
                return;
            }
        } else {
            System.out.println("Grupa vida");
            return;
        }

        System.out.println("Introduceti email nou:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String email = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Email vid");
            return;
        }

        System.out.println("Introduceti nume cadru didactic indrumator lab nou:");
        inputStreamReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputStreamReader);
        String cadruDidacticIndrumatorLab = bufferedReader.readLine();
        if (line.equals("")) {
            System.out.println("Nume cadru didactic indrumator lab vid");
            return;
        }

        Student newStudent= new Student(id,nume,prenume,grupa,email,cadruDidacticIndrumatorLab);
        try{
            if(studentService.serviceUpdate(newStudent)!=null){
                System.out.println("ID inexistent");
            }
            else{
                System.out.println("Student modificat cu succes");
            }
        }catch (ValidationException ve){
            System.out.println("Student nevalid");
            System.out.println(ve.getMesaj());
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private void findOneTema() throws IOException{
        System.out.println("Introduceti id:");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        int id;
        if(!line.equals("")){
            try {
                id = Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }
        try {
            Tema tema=temaService.serviceFindOne(id);
            if(tema==null){
                System.out.println("ID inexistent");
            }
            else{
                System.out.println(tema);
            }
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private void findAllTema(){
        Iterable<Tema> teme=temaService.serviceFindAll();
        Boolean exist=Boolean.FALSE;
        for (Tema tema:
                teme) {
            if(tema!=null){
                exist=Boolean.TRUE;
            }
            System.out.println(tema);
        }
        if(!exist){
            System.out.println("Nu exista teme inregistrate");
        }
    }

    private void saveTema() throws IOException{
        System.out.println("Introduceti id:");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        int id;
        if(!line.equals("")){
            try{
                id=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }

        System.out.println("Introduceti descriere:");
        inputStreamReader=new InputStreamReader(System.in);
        bufferedReader=new BufferedReader(inputStreamReader);
        String descriere=bufferedReader.readLine();
        if(descriere.equals("")){
            System.out.println("Descriere vida");
            return;
        }

        int startWeek=temaService.determinare_saptamana(LocalDate.now());

        System.out.println("Introduceti deadline week:");
        inputStreamReader=new InputStreamReader(System.in);
        bufferedReader=new BufferedReader(inputStreamReader);
        line=bufferedReader.readLine();
        int deadlineWeek;
        if(!line.equals("")){
            try{
                deadlineWeek=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("Deadline week trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("Deadline week vid");
            return;
        }

        Tema tema=new Tema(id,descriere,startWeek,deadlineWeek);

        try{
            if(temaService.serviceSave(tema)==null){
                System.out.println("Tema inregistrata cu succes");
            }
            else{
                System.out.println("Tema deja existenta");
            }
        }catch (ValidationException ve){
            System.out.println("Tema nevalida");
            System.out.println(ve.getMesaj());
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private void deleteTema() throws IOException{
        System.out.println("Introduceti ID: ");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        int id;
        if(!line.equals("")){
            try{
                id=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }

        try{
            if(temaService.serviceDelete(id)==null){
                System.out.println("ID inexistent");
            }
            else{
                System.out.println("Tema stearsa cu succes");
            }
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private void updateTema() throws IOException{
        System.out.println("Introduceti id:");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        int id;
        if(!line.equals("")){
            try{
                id=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }

        System.out.println("Introduceti noua descriere:");
        inputStreamReader=new InputStreamReader(System.in);
        bufferedReader=new BufferedReader(inputStreamReader);
        String descriere=bufferedReader.readLine();
        if(descriere.equals("")){
            System.out.println("Descriere vida");
            return;
        }

        System.out.println("Introduceti noul deadline week:");
        inputStreamReader=new InputStreamReader(System.in);
        bufferedReader=new BufferedReader(inputStreamReader);
        line=bufferedReader.readLine();
        int deadlineWeek;
        if(!line.equals("")){
            try{
                deadlineWeek=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("Deadline week trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("Deadline week vid");
            return;
        }

        try{
            if(temaService.serviceFindOne(id)==null){
                System.out.println("ID inexistent");
            }
            else{
                Tema newTema=new Tema(id,descriere,temaService.serviceFindOne(id).getStartWeek(),deadlineWeek);
                temaService.serviceUpdate(newTema);
            }
        }catch (ValidationException ve){
            System.out.println("Tema nevalida");
            System.out.println(ve.getMesaj());
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private void findOneNota() throws IOException {
        System.out.println("Introduceti id student:");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        int idStudent;
        if(!line.equals("")){
            try {
                idStudent = Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }
        try {
            if(studentService.serviceFindOne(idStudent)==null){
                System.out.println("ID student inexistent");
            }
            else{
                System.out.println("Introduceti id tema:");
                inputStreamReader=new InputStreamReader(System.in);
                bufferedReader=new BufferedReader(inputStreamReader);
                line=bufferedReader.readLine();
                int idTema;
                if(!line.equals("")){
                    try{
                        idTema=Integer.parseInt(line);
                    }catch (NumberFormatException nfe){
                        System.out.println("ID-ul trebuie sa fie int");
                        return;
                    }
                }
                else{
                    System.out.println("ID vid");
                    return;
                }
                if(temaService.serviceFindOne(idTema)==null)
                    System.out.println("ID tema inexistent");
                else
                    System.out.println(notaService.serviceFindOne(idStudent+" "+idTema));
            }
        }catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
    }

    private void findAllNota(){
        Iterable<Nota> note=notaService.serviceFindAll();
        Boolean exist=Boolean.FALSE;
        for (Nota nota:
                note) {
            if(nota!=null){
                exist=Boolean.TRUE;
            }
            System.out.println(nota);
        }
        if(!exist){
            System.out.println("Nu exista note inregistrate");
        }
    }

    private void deleteNota() throws IOException{
        System.out.println("Introduceti id student: ");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        int idStudent;
        if(!line.equals("")){
            try{
                idStudent=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }
        if(studentService.serviceFindOne(idStudent)==null){
            System.out.println("ID student inexistent");
        }
        else{
            System.out.println("Introduceti id tema: ");
            line=bufferedReader.readLine();
            int idTema;
            if(!line.equals("")){
                try{
                    idTema=Integer.parseInt(line);
                }catch (NumberFormatException nfe){
                    System.out.println("ID-ul trebuie sa fie int");
                    return;
                }
            }
            else{
                System.out.println("ID vid");
                return;
            }
            if(temaService.serviceFindOne(idTema)==null){
                System.out.println("ID tema inexistent");
            }
            else{
                try{
                    if(notaService.serviceDelete(idStudent+" "+idTema)==null){
                        System.out.println("Nota inexistenta");
                    }
                    else{
                        System.out.println("Nota stearsa cu succes");
                    }
                }catch (IllegalArgumentException iae){
                    iae.printStackTrace();
                }
            }
        }
    }

    private void saveNota() throws IOException{
        System.out.println("Introduceti id student:");
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        String line = br.readLine();
        Integer idStudent;
        if(!line.equals("")) {
            try {
                idStudent = Integer.parseInt(line);
            } catch (NumberFormatException nfe) {
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
            if (studentService.serviceFindOne(idStudent) == null) {
                System.out.println("ID student inexistent");
                return;
            } else {
                System.out.println("Introduceti id tema:");
                line = br.readLine();
                Integer idTema;
                if(!line.equals("")) {
                    try {
                        idTema = Integer.parseInt(line);
                    } catch (NumberFormatException nfe) {
                        System.out.println("ID-ul trebuie sa fie int");
                        return;
                    }
                    if (temaService.serviceFindOne(idTema) == null) {
                        System.out.println("ID tema inexistent");
                        return;
                    } else {
                        System.out.println("Introduceti numele profesorului:");
                        line = br.readLine();
                        String profesor;
                        if(line.equals("")) {
                            System.out.println("Nume profesor vid");
                            return;
                        }
                        else{
                            profesor=line;
                        }
                        System.out.println("Introduceti nota:");
                        line=br.readLine();
                        Integer valoare;
                        if(line.equals("")){
                            System.out.println("Nota vida");
                            return;
                        }else{
                            try{
                                valoare=Integer.parseInt(line);
                            }catch (NumberFormatException nfe){
                                System.out.println("Nota trebuie sa fie int");
                                return;
                            }
                        }

                        System.out.println("Introduceti feedback:");
                        line=br.readLine();
                        String feedback;
                        if(line.equals("")){
                            System.out.println("Feedback vid");
                            return;
                        }else{
                            feedback=line;
                        }

                        Integer penalizare=0;
                        Tema tema=temaService.serviceFindOne(idTema);
                        LocalDate data=LocalDate.now();
                        if(anUniversitar.getCurrentWeek(LocalDate.now())>tema.getDeadlineWeek()){
                            //EXISTA POSIBILE PENALIZARI
                            System.out.println("Studentul a predat tema astazi? (y/n)");
                            line=br.readLine();
                            if(line.equals("")){
                                System.out.println("Raspuns vid");
                                return;
                            }else if(line.equals("n")){
                                //A INTARZIAT PROFESORUL
                                System.out.println("Introduceti data cand studentul a predat tema (format: dd.MM.yyyy):");
                                line=br.readLine();
                                if(line.equals("")){
                                    System.out.println("Data vida");
                                    return;
                                }else{
                                    DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd.MM.yyyy");
                                    LocalDate newDate=LocalDate.parse(line,dateTimeFormatter);
                                    data=newDate;
                                    penalizare=anUniversitar.getCurrentWeek(newDate)-tema.getDeadlineWeek();
                                    System.out.println("Studentul are motivare? (y/n)");
                                    line=br.readLine();
                                    if(line.equals("")){
                                        System.out.println("Raspuns vid");
                                        return;
                                    }
                                    else{
                                        if(line.equals("y")){
                                            //ARE MOTIVARE
                                            System.out.println("Cate motivari are studentul?");
                                            Integer nrMotivari;
                                            line=br.readLine();

                                            if(line.equals("")){
                                                System.out.println("Numar motivari vid");
                                                return;
                                            }
                                            else{
                                                try{
                                                    nrMotivari=Integer.parseInt(line);
                                                    if(nrMotivari<0){
                                                        System.out.println("Numar motivari negativ");
                                                        return;
                                                    }
                                                }catch (NumberFormatException nfe){
                                                    System.out.println("Numar motivari trebuie sa fie int");
                                                    return;
                                                }
                                                LocalDate motivare;
                                                for (int i = 0; i < nrMotivari; i++) {
                                                    System.out.println("Dati data motivarii (format: dd.MM.yyyy):");
                                                    line=br.readLine();
                                                    if(line.equals("")){
                                                        System.out.println("Data motivare vida");
                                                        return;
                                                    }
                                                    else{
                                                        motivare=LocalDate.parse(line,DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                                                    }
                                                    if(anUniversitar.getCurrentWeek(motivare)>=temaService.serviceFindOne(idTema).getDeadlineWeek() && anUniversitar.getCurrentWeek(motivare)<=anUniversitar.getCurrentWeek(data)){
                                                        penalizare--;
                                                    }
                                                }
                                            }
                                        }
                                        if(penalizare>2){
                                            System.out.println("Tema nu mai poate fi predata! Nota este 1");
                                            valoare=1;
                                        }
                                        else{
                                            System.out.println("Nota maxima poate fi "+(10-penalizare));
                                            valoare-=penalizare;
                                            if(valoare<1) valoare=1;
                                        }
                                    }
                                }
                            }else if(line.equals("y")){
                                //NU A INTARZIAT PROFESORUL
                                penalizare=anUniversitar.getCurrentWeek(LocalDate.now())-tema.getDeadlineWeek();
                                System.out.println("Studentul are motivare? (y/n)");
                                line=br.readLine();
                                if(line.equals("")){
                                    System.out.println("Raspuns vid");
                                    return;
                                }
                                else{
                                    if(line.equals("y")){
                                        //ARE MOTIVARE
                                        System.out.println("Cate motivari are studentul?");
                                        Integer nrMotivari;
                                        line=br.readLine();

                                        if(line.equals("")){
                                            System.out.println("Numar motivari vid");
                                            return;
                                        }
                                        else{
                                            try{
                                                nrMotivari=Integer.parseInt(line);
                                                if(nrMotivari<0){
                                                    System.out.println("Numar motivari negativ");
                                                    return;
                                                }
                                            }catch (NumberFormatException nfe){
                                                System.out.println("Numar motivari trebuie sa fie int");
                                                return;
                                            }
                                            LocalDate motivare;
                                            for (int i = 0; i < nrMotivari; i++) {
                                                System.out.println("Dati data motivarii (format: dd.MM.yyyy):");
                                                line=br.readLine();
                                                if(line.equals("")){
                                                    System.out.println("Data motivare vida");
                                                    return;
                                                }
                                                else{
                                                    motivare=LocalDate.parse(line,DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                                                }
                                                if(anUniversitar.getCurrentWeek(motivare)>=temaService.serviceFindOne(idTema).getDeadlineWeek() && anUniversitar.getCurrentWeek(motivare)<=anUniversitar.getCurrentWeek(data)){
                                                    penalizare--;
                                                }
                                            }
                                        }
                                    }
                                    if(penalizare>2){
                                        System.out.println("Tema nu mai poate fi predata! Nota este 1");
                                        valoare=1;
                                    }
                                    else{
                                        System.out.println("Nota maxima poate fi "+(10-penalizare));
                                        valoare-=penalizare;
                                        if(valoare<1) valoare=1;
                                    }
                                }
                            }
                            else{
                                System.out.println("Nu exista acest raspuns");
                                return;
                            }
                        }
                        Nota nota=new Nota(idStudent,idTema,data,profesor,valoare,feedback);
                        try{
                            notaService.serviceSave(nota);
                        }catch (ValidationException ve){
                            System.out.println("Nota nevalida");
                            System.out.println(ve.getMesaj());
                        }catch (IllegalArgumentException iae){
                            iae.printStackTrace();
                        }
                    }
                }else {
                    System.out.println("ID tema vid");
                    return;
                }
            }
        }
        else {
            System.out.println("ID student vid");
            return;
        }
    }

    private void updateNota() throws IOException{
        System.out.println("Introduceti id student:");
        InputStreamReader inputStreamReader=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(inputStreamReader);
        String line=br.readLine();
        int idStudent;
        if(!line.equals("")){
            try{
                idStudent=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
                return;
            }
        }
        else{
            System.out.println("ID vid");
            return;
        }
        if(studentService.serviceFindOne(idStudent)==null){
            System.out.println("ID student inexistent");
        }
        else{
            System.out.println("Introduceti id tema:");
            line=br.readLine();
            int idTema;
            if(!line.equals("")){
                try{
                    idTema=Integer.parseInt(line);
                }catch (NumberFormatException nfe){
                    System.out.println("ID-ul trebuie sa fie int");
                    return;
                }
            }
            else{
                System.out.println("ID vid");
                return;
            }
            if(temaService.serviceFindOne(idTema)==null){
                System.out.println("ID tema inexistent");
            }
            else{
                System.out.println("Introduceti noul nume al profesorului:");
                line = br.readLine();
                String profesor;
                if(line.equals("")) {
                    System.out.println("Nume profesor vid");
                    return;
                }
                else{
                    profesor=line;
                }
                System.out.println("Introduceti noua nota:");
                line=br.readLine();
                Integer valoare;
                if(line.equals("")){
                    System.out.println("Nota vida");
                    return;
                }else{
                    try{
                        valoare=Integer.parseInt(line);
                    }catch (NumberFormatException nfe){
                        System.out.println("Nota trebuie sa fie int");
                        return;
                    }
                }

                System.out.println("Introduceti noul feedback:");
                line=br.readLine();
                String feedback;
                if(line.equals("")){
                    System.out.println("Feedback vid");
                    return;
                }else{
                    feedback=line;
                }
                System.out.println("Introduceti noua data cand studentul a predat tema (format: dd.MM.yyyy):");
                line=br.readLine();
                Integer penalizare;
                Tema tema=temaService.serviceFindOne(idTema);
                if(line.equals("")){
                    System.out.println("Data vida");
                    return;
                }else{
                    DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate date=LocalDate.parse(line,dateTimeFormatter);
                    penalizare=anUniversitar.getCurrentWeek(date)-tema.getDeadlineWeek();
                    if(penalizare>2) {
                        System.out.println("Tema nu mai poate fi predata");
                        return;
                    }
                    else if(penalizare>0){
                        System.out.println("Nota maxima poate fi "+(10-penalizare));
                        valoare=valoare-penalizare;
                    }
                    Nota nota=new Nota(idStudent,idTema,date,profesor,valoare,feedback);
                    try{
                        if(notaService.serviceFindOne(nota.getId())==null){
                            System.out.println("Nota inexistenta");
                        }
                        else{
                            notaService.serviceUpdate(nota);
                            System.out.println("Nota actualizata cu succes");
                        }
                    }catch (ValidationException ve){
                        System.out.println("Nota nevalida");
                        System.out.println(ve.getMesaj());
                    }catch (IllegalArgumentException iae){
                        iae.printStackTrace();
                    }
                }
            }
        }
    }

    private void filterByGroup() throws IOException {
        System.out.println("Dati grupa: ");
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        String line=br.readLine();
        Integer grupa=null;
        if(line.equals("")){
            System.out.println("Grupa vida");
            return;
        }
        else {
            try {
                grupa=Integer.parseInt(line);
            } catch (NumberFormatException nfe){
                System.out.println("Grupa trebuie sa fie int");
                return;
            }
            studentService.filterByGroup(grupa).forEach(System.out::println);
        }
    }

    private void filterByHomework() throws IOException {
        System.out.println("Dati id tema:");
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        String line=br.readLine();
        Integer idTema = null;
        if(line.equals("")){
            System.out.println("ID tema vid");
            return;
        }
        else{
            try{
                idTema=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
            }
            notaService.filterByHomework(idTema).forEach(System.out::println);
        }
    }

    private void filterByHomeworkAndTeacher() throws IOException {
        System.out.println("Dati id tema:");
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        String line=br.readLine();
        Integer idTema = null;
        if(line.equals("")){
            System.out.println("ID tema vid");
            return;
        }
        else{
            try{
                idTema=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
            }
            System.out.println("Dati numele profesorului");
            String profesor=br.readLine();
            notaService.filterByHomeworkAndTeacher(idTema,profesor).forEach(System.out::println);
        }
    }

    private void filterByHomeworkAndWeek() throws IOException {
        System.out.println("Dati id tema:");
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader br=new BufferedReader(isr);
        String line=br.readLine();
        Integer idTema = 0;
        Integer week;
        if(line.equals("")){
            System.out.println("ID tema vid");
            return;
        }
        else{
            try{
                idTema=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("ID-ul trebuie sa fie int");
            }
            System.out.println("Dati saptamana:");
            line=br.readLine();
            if(line.equals("")){
                System.out.println("Saptamana vida");
                return;
            }else{
                try{
                    week=Integer.parseInt(line);
                }catch (NumberFormatException nfe){
                    System.out.println("Saptamana trebuie sa fie int");
                    return;
                }
                notaService.filterByHomeworkAndWeek(idTema,week).forEach(System.out::println);
            }
        }
    }


    public void run() throws IOException{
        while(true){
            System.out.println("0 - Iesire");
            System.out.println("=============STUDENTI=============");
            System.out.println("1 - Cautare student");
            System.out.println("2 - Listare studenti");
            System.out.println("3 - Adaugare Student");
            System.out.println("4 - Stergere student");
            System.out.println("5 - Update student");
            System.out.println("=============TEME=============");
            System.out.println("6 - Cautare tema");
            System.out.println("7 - Listare teme");
            System.out.println("8 - Adaugare tema");
            System.out.println("9 - Stergere tema");
            System.out.println("10 - Update tema");
            System.out.println("=============NOTE=============");
            System.out.println("11 - Cautare nota");
            System.out.println("12 - Listare note");
            System.out.println("13 - Adaugare nota");
            System.out.println("14 - Stergere nota");
            System.out.println("15 - Update nota");
            System.out.println("=============FILTRARI=============");
            System.out.println("16 - Filter dupa grupa");
            System.out.println("17 - Filter dupa tema");
            System.out.println("18 - Filter dupa tema si profesor");
            System.out.println("19 - Filter dupa tema si saptamana");
            System.out.println("Alegeti optiunea: ");
            InputStreamReader inputStreamReader=new InputStreamReader(System.in);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            int op=0;
            try{
                op=Integer.parseInt(line);
            }catch (NumberFormatException nfe){
                System.out.println("Optiunea trebuie sa fie int");
            }
            switch (op){
                case 0:
                    return;
                case 1:findOneStudent();
                    break;

                case 2:findAllStudent();
                    break;

                case 3:saveStudent();
                    break;

                case 4:deleteStudent();
                    break;

                case 5:updateStudent();
                    break;

                case 6:findOneTema();
                    break;

                case 7:findAllTema();
                    break;

                case 8:saveTema();
                    break;

                case 9:deleteTema();
                    break;

                case 10:updateTema();
                    break;

                case 11:findOneNota();
                    break;

                case 12:findAllNota();
                    break;

                case 13:saveNota();
                    break;

                case 14:deleteNota();
                    break;

                case 15:updateNota();
                    break;
                case 16:filterByGroup();
                    break;
                case 17:filterByHomework();
                    break;
                case 18:filterByHomeworkAndTeacher();;
                    break;
                case 19:filterByHomeworkAndWeek();
                    break;
                default:
                    System.out.println("Optiune inexistenta");
                    break;
            }
        }
    }
}
