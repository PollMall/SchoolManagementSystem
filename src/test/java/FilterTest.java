import domains.Nota;
import domains.Student;
import domains.Tema;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import repositories.NotaRepository;
import repositories.StudentRepository;
import repositories.TemaRepository;
import services.NotaService;
import services.StudentService;
import services.TemaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilterTest {
    private StudentService studentService=new StudentService(new StudentRepository());
    private TemaService temaService=new TemaService(new TemaRepository());
    private NotaService notaService=new NotaService(new NotaRepository(),studentService.getStudentRepository());

    @Test
    void filterByGroup(){
        //crearea studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        //adaugare studenti
        studentService.serviceSave(student1);
        studentService.serviceSave(student2);
        studentService.serviceSave(student3);
        //teste
        Assert.assertEquals(studentService.filterByGroup(222).size(),2);
    }

    @Test
    void filterByHomework(){
        //crearea studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        Nota nota2=new Nota(2,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Iovan",10,"fb");
        //adaugare studenti
        studentService.serviceSave(student1);
        studentService.serviceSave(student2);
        studentService.serviceSave(student3);
        //adaugare teme
        temaService.serviceSave(tema1);
        temaService.serviceSave(tema2);
        //adaugare note
        notaService.serviceSave(nota1);
        notaService.serviceSave(nota2);
        //teste
        Assert.assertEquals(notaService.filterByHomework(1).size(),2);
    }

    @Test
    void filterByHomeworkAndTeacher(){
        //crearea studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        Nota nota2=new Nota(2,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Iovan",10,"fb");
        //adaugare studenti
        studentService.serviceSave(student1);
        studentService.serviceSave(student2);
        studentService.serviceSave(student3);
        //adaugare teme
        temaService.serviceSave(tema1);
        temaService.serviceSave(tema2);
        //adaugare note
        notaService.serviceSave(nota1);
        notaService.serviceSave(nota2);
        //teste
        Assert.assertEquals(notaService.filterByHomeworkAndTeacher(1,"Iovan").size(),1);
    }

    @Test
    void filterByHomeworkAndWeek(){
        //crearea studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        Nota nota2=new Nota(2,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Iovan",10,"fb");
        //adaugare studenti
        studentService.serviceSave(student1);
        studentService.serviceSave(student2);
        studentService.serviceSave(student3);
        //adaugare teme
        temaService.serviceSave(tema1);
        temaService.serviceSave(tema2);
        //adaugare note
        notaService.serviceSave(nota1);
        notaService.serviceSave(nota2);
        //teste
        Assert.assertEquals(notaService.filterByHomeworkAndWeek(1,6).size(),2);
    }
}
