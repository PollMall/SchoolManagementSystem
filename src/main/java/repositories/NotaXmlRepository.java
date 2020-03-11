package repositories;

import domains.Nota;
import domains.Student;
import domains.Tema;
import exceptions.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.structures.StructuraAnUniversitar;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotaXmlRepository extends NotaRepository {
    private File xmlFile;
    //private String toFile;
    private CrudRepository<Integer,Student> studentRepository;//=new StudentXmlRepository(new File("src/main/resources/studenti.xml"));
    private CrudRepository<Integer,Tema> temaRepository;//=new TemaXmlRepository(new File("src/main/resources/teme.xml"));
    private StructuraAnUniversitar anUniversitar;

    public NotaXmlRepository(File xmlFile,CrudRepository<Integer,Student> studentRepository,CrudRepository<Integer,Tema> temaRepository,StructuraAnUniversitar anUniversitar) {
        this.xmlFile = xmlFile;
        this.studentRepository=studentRepository;
        this.temaRepository=temaRepository;
        //this.toFile=toFile;
        this.anUniversitar=anUniversitar;
        loadData();
        writeToFileNumeStudent(super.findAll());
    }

    private void loadData() {
        try{
            Document document= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.xmlFile);
            document.getDocumentElement().normalize();
            Element root=document.getDocumentElement();

            NodeList nodeList=root.getElementsByTagName("nota");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Nota nota=elementToNota((Element)nodeList.item(i));
                super.save(nota);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeToFile(){
        try{
            Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root=document.createElement("note");
            super.findAll().forEach(nota->{
                Element element=notaToElement(document,nota);
                root.appendChild(element);
            });
            document.appendChild(root);

            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            Source source=new DOMSource(document);
            transformer.transform(source,new StreamResult(this.xmlFile));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Nota elementToNota(Element element){
        String id=element.getAttribute("id");
        Integer idStudent=Integer.parseInt(id.split(" ")[0]);
        Integer idTema=Integer.parseInt(id.split(" ")[1]);
        LocalDate data=LocalDate.parse(element.getElementsByTagName("data").item(0).getTextContent(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String profesor=element.getElementsByTagName("profesor").item(0).getTextContent();
        Integer valoare=Integer.parseInt(element.getElementsByTagName("valoare").item(0).getTextContent());
        String feedback=element.getElementsByTagName("feedback").item(0).getTextContent();
        Nota nota=new Nota(idStudent,idTema,data,profesor,valoare,feedback);
        return nota;
    }

    private Element notaToElement(Document document,Nota nota){
        Element element=document.createElement("nota");
        element.setAttribute("id",nota.getId());

        Element data=document.createElement("data");
        data.setTextContent(nota.getData().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        element.appendChild(data);

        Element profesor=document.createElement("profesor");
        profesor.setTextContent(nota.getProfesor());
        element.appendChild(profesor);

        Element valoare=document.createElement("valoare");
        valoare.setTextContent(nota.getNota().toString());
        element.appendChild(valoare);

        Element feedback=document.createElement("feedback");
        feedback.setTextContent(nota.getFeedback());
        element.appendChild(feedback);
        return element;
    }

    private void writeToFileNumeStudent(Iterable<Nota> list) {
        for (Nota entity:list) {
            Student student=studentRepository.findOne(Integer.parseInt(entity.getId().split(" ")[0]));
            Tema tema = temaRepository.findOne(Integer.parseInt(entity.getId().split(" ")[1]));
            try {
                File file=new File("src/main/resources/AnUniversitar2019-2020/"+student.getNume()+"_"+student.getPrenume()+".txt");
                FileWriter fileWriter=new FileWriter(file);
                file.createNewFile();

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
    }

    @Override
    public Nota save(Nota entity) throws ValidationException {
        Nota nota=super.save(entity);
        if(nota==null){
            writeToFile();
            writeToFileNumeStudent(super.findAll());
        }
        return nota;
    }

    @Override
    public Nota delete(String s) {
        Nota nota=super.delete(s);
        if(nota!=null){
            writeToFile();
            writeToFileNumeStudent(super.findAll());
        }
        return nota;
    }

    @Override
    public Nota update(Nota entity) {
        Nota nota=super.update(entity);
        if(nota==null){
            writeToFile();
            writeToFileNumeStudent(super.findAll());
        }
        return nota;
    }
}
