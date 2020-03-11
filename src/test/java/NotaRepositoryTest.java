import org.junit.Assert;
import org.junit.jupiter.api.Test;
import domains.Nota;
import domains.Student;
import domains.Tema;
import exceptions.ValidationException;
import repositories.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class NotaRepositoryTest {
    private NotaRepository notaRepository=new NotaRepository();
    private StudentRepository studentRepository=new StudentRepository();
    private TemaRepository temaRepository=new TemaRepository();

    @Test
    void findOne() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        studentRepository.save(student1);
        studentRepository.save(student2);
        //creare teme
        Tema tema1=new Tema(1,"usor",5,6);
        Tema tema2=new Tema(2,"mediu",6,7);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        notaRepository.save(nota1);
        //assert-uri
        Assert.assertEquals(notaRepository.findOne("1 1"),nota1);
        Assert.assertNull(notaRepository.findOne("1 2"));
        try{
            notaRepository.findOne(null);
            Assert.fail();
        }catch (IllegalArgumentException iae){
            Assert.assertTrue(true);
        }
    }

    @Test
    void findAll() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        studentRepository.save(student1);
        studentRepository.save(student2);
        //creare teme
        Tema tema1=new Tema(1,"usor",5,6);
        Tema tema2=new Tema(2,"mediu",6,7);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        notaRepository.save(nota1);
        //assert-uri
        Assert.assertEquals(notaRepository.findAll(),notaRepository.getArrayList());
    }

    @Test
    void save() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        studentRepository.save(student1);
        studentRepository.save(student2);
        //creare teme
        Tema tema1=new Tema(1,"usor",5,6);
        Tema tema2=new Tema(2,"mediu",6,7);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        notaRepository.save(nota1);
        //assert-uri
        Assert.assertEquals(notaRepository.getArrayList().size(),1);
        Assert.assertEquals(notaRepository.findOne("1 1"),nota1);
        Nota nota2=new Nota(1,2, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",9,"b");
        Assert.assertNotNull(notaRepository.save(nota1));
        Assert.assertNull(notaRepository.save(nota2));
        Nota nota3=new Nota(2,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Ion10",8,"b");
        try{
            notaRepository.save(nota3);
            Assert.fail();
        }catch (ValidationException ve){
            Assert.assertTrue(true);
        }
        try{
            notaRepository.save(null);
            Assert.fail();
        }catch (IllegalArgumentException iae){
            Assert.assertTrue(true);
        }
    }

    @Test
    void delete() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        studentRepository.save(student1);
        studentRepository.save(student2);
        //creare teme
        Tema tema1=new Tema(1,"usor",5,6);
        Tema tema2=new Tema(2,"mediu",6,7);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        notaRepository.save(nota1);
        //assert-uri
        Assert.assertEquals(notaRepository.delete("1 1"),nota1);
        Assert.assertNull(notaRepository.delete("1 2"));
        try{
            notaRepository.delete(null);
            Assert.fail();
        }catch (IllegalArgumentException iae){
            Assert.assertTrue(true);
        }
    }

    @Test
    void update() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        studentRepository.save(student1);
        studentRepository.save(student2);
        //creare teme
        Tema tema1=new Tema(1,"usor",5,6);
        Tema tema2=new Tema(2,"mediu",6,7);
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        //creare note
        Nota nota1=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        notaRepository.save(nota1);
        //assert-uri
        Nota nota2=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",9,"b");
        Nota nota3=new Nota(1,2, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea",10,"fb");
        Assert.assertNull(notaRepository.update(nota2));
        Assert.assertNotNull(nota3);
        try{
            Nota nota4=new Nota(1,1, LocalDate.parse("08.11.2019", DateTimeFormatter.ofPattern("dd.MM.yyyy")),"Mircea10",10,"fb");
            notaRepository.update(nota4);
            Assert.fail();
        }catch (ValidationException ve){
            Assert.assertTrue(true);
        }
        try{
            notaRepository.update(null);
            Assert.fail();
        }catch (IllegalArgumentException iae){
            Assert.assertTrue(true);
        }
    }
}