package services;

import domains.Nota;
import domains.Student;
import exceptions.ValidationException;
import repositories.CrudRepository;
import repositories.NotaRepository;
import repositories.StudentRepository;
import utils.observers.Observable;
import utils.observers.Observer;
import utils.structures.StructuraAnUniversitar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotaService implements Service<String, Nota> {
    private StructuraAnUniversitar anUniversitar;//=new StructuraAnUniversitar();
    private NotaRepository notaRepository;//=new NotaXmlRepository(new File("src/main/resources/note.xml"),anUniversitar);
    private StudentRepository studentRepository;//=new StudentXmlRepository(new File("src/main/resources/studenti.xml"));

    public NotaService(NotaRepository notaRepository, StudentRepository studentRepository) {
        this.anUniversitar=new StructuraAnUniversitar();
        this.notaRepository = notaRepository;
        this.studentRepository = studentRepository;
    }

    public Nota serviceFindOne(String s) {
        return notaRepository.findOne(s);
    }

    public Iterable<Nota> serviceFindAll() {
        return notaRepository.findAll();
    }

    public Nota serviceSave(Nota entity) throws ValidationException {
        return notaRepository.save(entity);
    }

    public Nota serviceDelete(String s) {
        return notaRepository.delete(s);
    }

    public Nota serviceUpdate(Nota entity) {
        return notaRepository.update(entity);
    }

    //================FILTRARI===============
    public List<Student> filterByHomework(Integer idTema){
        ArrayList<Student> students= (ArrayList<Student>) studentRepository.findAll();
        return students
                .stream()
                .filter(s->{
                    String idNota=s.getId()+" "+idTema;
                    return notaRepository.findOne(idNota)!=null;
                })
                .collect(Collectors.toList());
    }

    public List<Student> filterByHomeworkAndTeacher(Integer idTema,String profesor){
        ArrayList<Student> students= (ArrayList<Student>) studentRepository.findAll();
        return students
                .stream()
                .filter(s->{
                    String idNota=s.getId()+" "+idTema;
                    Nota nota=notaRepository.findOne(idNota);
                    if(nota!=null)
                        return nota.getProfesor().equals(profesor);
                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<Nota> filterByHomeworkAndWeek(Integer idTema, Integer week){
        ArrayList<Nota> note= (ArrayList<Nota>) notaRepository.findAll();
        return note
                .stream()
                .filter(n->{
                    Integer id;
                    id=Integer.parseInt(n.getId().split(" ")[1]);
                    return id.equals(idTema) && anUniversitar.getCurrentWeek(n.getData()).equals(week);
                })
                .collect(Collectors.toList());
    }
}
