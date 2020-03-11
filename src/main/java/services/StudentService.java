package services;

import domains.Student;
import repositories.CrudRepository;
import repositories.StudentFileRepository;
import repositories.StudentRepository;
import repositories.StudentXmlRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService implements Service<Integer, Student>{
    private StudentRepository studentRepository;//=new StudentXmlRepository(new File("src/main/resources/studenti.xml"));

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentRepository getStudentRepository() {
        return studentRepository;
    }

    public Student serviceFindOne(Integer id){
        return studentRepository.findOne(id);
    }

    public Iterable<Student> serviceFindAll(){
        return studentRepository.findAll();
    }

    public Student serviceSave(Student entity){
        return studentRepository.save(entity);
    }

    public Student serviceDelete(Integer id){
        return studentRepository.delete(id);
    }

    public Student serviceUpdate(Student entity){
        return studentRepository.update(entity);
    }

    //=====================FILTRARI=======================
    public List<Student> filterByGroup(Integer grupa){
        ArrayList<Student> students= (ArrayList<Student>) studentRepository.findAll();
        return students
                .stream()
                .filter(s-> s.getGrupa().equals(grupa))
                .collect(Collectors.toList());
    }
}
