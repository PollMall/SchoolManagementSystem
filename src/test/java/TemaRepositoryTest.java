import org.junit.Assert;
import org.junit.jupiter.api.Test;
import domains.Tema;
import exceptions.ValidationException;
import repositories.TemaRepository;

class TemaRepositoryTest {
    private TemaRepository temaRepository=new TemaRepository();

    @Test
    void save() {
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        Tema tema3=new Tema(3,"proiect",5,7);
        Tema tema4=new Tema(4,"proiect2",8,10);
        //save teme
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        temaRepository.save(tema3);
        //assert-uri
        Assert.assertEquals(temaRepository.getArrayList().size(),3);
        Assert.assertEquals (temaRepository.save(tema1),tema1);
        Assert.assertNull(temaRepository.save(tema4));
        try{
            Tema tema7=new Tema(-1,"nimic",2,3);
            temaRepository.save(tema7);
            Assert.fail();
        }catch (ValidationException e){
            Assert.assertTrue(true);
        }

        try {
            temaRepository.save(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    void update() {
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        Tema tema3=new Tema(3,"proiect",5,7);
        Tema tema4=new Tema(4,"proiect2",8,10);
        //save teme
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        temaRepository.save(tema3);
        //assert-uri
        Tema tema5=new Tema(1,"urgent",1,2);
        Tema tema6=new Tema(10,"nimic",2,3);
        Assert.assertNull(temaRepository.update(tema5));
        Assert.assertNotNull(temaRepository.update(tema6));
        try{
            Tema tema7=new Tema(-1,"nimic",2,3);
            temaRepository.update(tema7);
            Assert.fail();
        }catch (ValidationException e){
            Assert.assertTrue(true);
        }

        try {
            temaRepository.update(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    void findOne() {
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        Tema tema3=new Tema(3,"proiect",5,7);
        Tema tema4=new Tema(4,"proiect2",8,10);
        //save teme
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        temaRepository.save(tema3);
        //assert-uri
        Assert.assertNull (temaRepository.findOne(6));
        Assert.assertNotNull (temaRepository.findOne(1));
        try {
            temaRepository.findOne(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    void findAll() {
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        Tema tema3=new Tema(3,"proiect",5,7);
        Tema tema4=new Tema(4,"proiect2",8,10);
        //save teme
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        temaRepository.save(tema3);
        //assert-uri
        Assert.assertEquals (temaRepository.findAll(),temaRepository.getArrayList());
    }

    @Test
    void delete() {
        //creare teme
        Tema tema1=new Tema(1,"usor",1,2);
        Tema tema2=new Tema(2,"mediu",3,4);
        Tema tema3=new Tema(3,"proiect",5,7);
        Tema tema4=new Tema(4,"proiect2",8,10);
        //save teme
        temaRepository.save(tema1);
        temaRepository.save(tema2);
        temaRepository.save(tema3);
        //assert-uri
        Assert.assertNotNull(temaRepository.delete(3));
        Assert.assertNull(temaRepository.delete(10));
        try{
            temaRepository.delete(null);
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }
    }
}