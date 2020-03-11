import org.junit.Assert;
import org.junit.jupiter.api.Test;
import domains.Student;
import exceptions.ValidationException;
import repositories.StudentRepository;

class StudentRepositoryTest {

    private StudentRepository studentRepository = new StudentRepository();

    @Test
    void save() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        Student student4=new Student(4,"Huzum","Cosmin",225,"cosmin_huzum@yahoo.com","Eva Loghin");
        Student student5=new Student(5,"Pop","Ionel",221,"pop.ionel@yahoo.com","Marcel Iosif");
        //salvare studenti
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        //assert-uri
        Assert.assertEquals(studentRepository.getArrayList().size(),4);
        Assert.assertEquals(studentRepository.save(student1),student1);
        Assert.assertNull(studentRepository.save(student5));
        try{
            Student student7=new Student(-1,"Mandruta","Andreea",223,"andreea.m@yahoo.com","Cosmina Malea");
            studentRepository.save(student7);
            Assert.fail();
        }catch (ValidationException e){
            Assert.assertTrue(true);
        }
        try {
            studentRepository.save(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    void update() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        Student student4=new Student(4,"Huzum","Cosmin",225,"cosmin_huzum@yahoo.com","Eva Loghin");
        //salvare studenti
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        //assert-uri
        Student student5=new Student(1,"Avram A.","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student6=new Student(10,"Avram A.","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Assert.assertNull(studentRepository.update(student5));
        Assert.assertNotNull(studentRepository.update(student6));
        try{
            Student student7=new Student(-1,"Mandruta","Andreea",223,"andreea.m@yahoo.com","Cosmina Malea");
            studentRepository.update(student7);
            Assert.fail();
        }catch (ValidationException e){
            Assert.assertTrue(true);
        }

        try {
            studentRepository.update(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    void findOne() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        Student student4=new Student(4,"Huzum","Cosmin",225,"cosmin_huzum@yahoo.com","Eva Loghin");
        Student student5=new Student(5,"Pop","Ionel",221,"pop.ionel@yahoo.com","Marcel Iosif");
        //salvare studenti
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        //assert-uri
        Assert.assertNull(studentRepository.findOne(6));
        Assert.assertNotNull(studentRepository.findOne(1));
        try {
            studentRepository.findOne(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    void findAll() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        Student student4=new Student(4,"Huzum","Cosmin",225,"cosmin_huzum@yahoo.com","Eva Loghin");
        Student student5=new Student(5,"Pop","Ionel",221,"pop.ionel@yahoo.com","Marcel Iosif");
        //salvare studenti
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        //assert-uri
        Assert.assertEquals (studentRepository.findAll(),studentRepository.getArrayList());
    }

    @Test
    void delete() {
        //creare studenti
        Student student1=new Student(1,"Avram","Iancu",222,"aiancu@yahoo.com","Ana Maria");
        Student student2=new Student(2,"Avram","Mirel",223,"amirel@yahoo.com","Cosmina Malea");
        Student student3=new Student(3,"Ion","Dan",222,"ion_dan@yahoo.com","Ana Maria");
        Student student4=new Student(4,"Huzum","Cosmin",225,"cosmin_huzum@yahoo.com","Eva Loghin");
        Student student5=new Student(5,"Pop","Ionel",221,"pop.ionel@yahoo.com","Marcel Iosif");
        //salvare studenti
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        //assert-uri
        Assert.assertNotNull(studentRepository.delete(4));
        Assert.assertNull(studentRepository.delete(10));
        try{
            studentRepository.delete(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }
}