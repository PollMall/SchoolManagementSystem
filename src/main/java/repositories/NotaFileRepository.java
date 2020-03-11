package repositories;

import domains.Nota;
import domains.Student;
import domains.Tema;
import utils.structures.StructuraAnUniversitar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotaFileRepository extends NotaRepository {
    private String fromFile;
    //private String toFile;
    private StudentFileRepository studentFileRepository=new StudentFileRepository("src/main/resources/studenti.csv");
    private TemaFileRepository temaFileRepository=new TemaFileRepository("src/main/resources/teme.csv");
    private StructuraAnUniversitar anUniversitar;

    public NotaFileRepository(String fromFile,StructuraAnUniversitar anUniversitar) {
        this.fromFile = fromFile;
        //this.toFile=toFile;
        this.anUniversitar=anUniversitar;
        loadData();
        writeToFileNumeStudent(super.findAll());
    }

    private void loadData() {
        Path p= Paths.get(fromFile);
        try{
            List<String> lines= Files.readAllLines(p);
            lines.forEach(this::parseLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLine(String s) {
        String[] aux=s.split((";"));
        int idStudent=Integer.parseInt(aux[0]);
        int idTema=Integer.parseInt(aux[1]);
        String dataPredare=aux[2];
        LocalDate data= LocalDate.parse(dataPredare, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String profesor=aux[3];
        int valoare=Integer.parseInt(aux[4]);
        String feedback=aux[5];
        Nota nota=new Nota(idStudent,idTema,data,profesor,valoare,feedback);
        super.save(nota);
    }

    @Override
    public Nota save(Nota entity){
        Nota s=super.save(entity);
        writeToFile(super.findAll());
        writeToFileNumeStudent(super.findAll());
        return s;
    }

    @Override
    public Nota delete(String id){
        Nota s=super.delete(id);
        writeToFile(super.findAll());
        writeToFileNumeStudent(super.findAll());
        return s;
    }

    @Override
    public Nota update(Nota entity){
        Nota s=super.update(entity);
        writeToFile(super.findAll());
        writeToFileNumeStudent(super.findAll());
        return s;
    }

    private void writeToFileNumeStudent(Iterable<Nota> list) {
//        Path p=Paths.get(toFile);
//        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.TRUNCATE_EXISTING)) {
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.WRITE)) {
            for (Nota entity:list) {
                Student student=studentFileRepository.findOne(Integer.parseInt(entity.getId().split(" ")[0]));
                Tema tema = temaFileRepository.findOne(Integer.parseInt(entity.getId().split(" ")[1]));
                try {
                    File file=new File("src/main/resources/AnUniversitar2019-2020/"+student.getNume()+"_"+student.getPrenume()+".txt");
                    FileWriter fileWriter=new FileWriter(file);
                    file.createNewFile();

                    //fileWriter.write("Studentul: "+student.getNume()+" "+student.getPrenume());
                    //fileWriter.write("\n");
                    fileWriter.write("Tema: "+tema.getId());
                    fileWriter.write("\n");
                    fileWriter.write("Nota: "+entity.getNota());
                    fileWriter.write("\n");
                    fileWriter.write("Predata in saptamana: "+anUniversitar.getCurrentWeek(entity.getData()));
                    fileWriter.write("\n");
                    fileWriter.write("Deadline: "+tema.getDeadlineWeek());
                    fileWriter.write("\n");
                    fileWriter.write("Feedback: "+ entity.getFeedback());
                    fileWriter.write("\n\n");
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void writeToFile(Iterable<Nota> list) {
        Path p=Paths.get(fromFile);
        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.TRUNCATE_EXISTING)) {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedWriter bw=Files.newBufferedWriter(p, StandardOpenOption.WRITE)) {
            int i=0;
            for (Nota entity:list) {
                i++;
                if(i!=1)
                    bw.newLine();
                Student student=studentFileRepository.findOne(Integer.parseInt(entity.getId().split(" ")[0]));
                Tema tema = temaFileRepository.findOne(Integer.parseInt(entity.getId().split(" ")[1]));
                bw.write(student.getId()+";"+tema.getId()+";"+entity.getData().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+";"+entity.getProfesor()+";"+entity.getNota()+";"+entity.getFeedback());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
