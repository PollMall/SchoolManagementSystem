package repositories;

import domains.Tema;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TemaFileRepository extends TemaRepository {
    private String fileName;

    public TemaFileRepository(String fileName) {
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
        String descriere=aux[1];
        int startWeek=Integer.parseInt(aux[2]);
        int deadlineWeek=Integer.parseInt(aux[3]);
        Tema tema=new Tema(id,descriere,startWeek,deadlineWeek);
        super.save(tema);
    }

    @Override
    public Tema save(Tema entity){
        Tema s=super.save(entity);
        writeToFile(super.findAll());
        return s;
    }

    @Override
    public Tema delete(Integer id){
        Tema s=super.delete(id);
        writeToFile(super.findAll());
        return s;
    }

    @Override
    public Tema update(Tema entity){
        Tema s=super.update(entity);
        writeToFile(super.findAll());
        return s;
    }

    private void writeToFile(Iterable<Tema> list) {
        Path p=Paths.get(fileName);
        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.WRITE)) {
            int i=0;
            for (Tema entity:list) {
                i++;
                if(i!=1)
                    bw.newLine();
                bw.write(entity.getId()+";"+entity.getDescriere()+";"+entity.getStartWeek()+";"+entity.getDeadlineWeek());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
