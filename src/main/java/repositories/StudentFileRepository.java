package repositories;

import domains.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class StudentFileRepository extends StudentRepository{
    private String fileName;

    public StudentFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path p= Paths.get(fileName);
        try{
            List<String> lines= Files.readAllLines(p);
            lines.forEach(this::parseLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLine(String s) {
        String[] aux=s.split((";"));
        int id=Integer.parseInt(aux[0]);
        String nume=aux[1];
        String prenume=aux[2];
        int grupa=Integer.parseInt(aux[3]);
        String email=aux[4];
        String profesor=aux[5];
        Student student=new Student(id,nume,prenume,grupa,email,profesor);
        super.save(student);
    }

    @Override
    public Student save(Student entity){
        Student s=super.save(entity);
        writeToFile(super.findAll());
        return s;
    }

    @Override
    public Student delete(Integer id){
        Student s=super.delete(id);
        writeToFile(super.findAll());
        return s;
    }

    @Override
    public Student update(Student entity){
        Student s=super.update(entity);
        writeToFile(super.findAll());
        return s;
    }

    private void writeToFile(Iterable<Student> list) {
        Path p=Paths.get(fileName);
        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.TRUNCATE_EXISTING)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.WRITE)) {
            int i=0;
            for (Student entity:list) {
                i++;
                if(i!=1)
                    bw.newLine();
                bw.write(entity.getId()+";"+entity.getNume()+";"+entity.getPrenume()+";"+entity.getGrupa()+";"+entity.getEmail()+";"+entity.getCadruDidacticIndrumatorLab());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}