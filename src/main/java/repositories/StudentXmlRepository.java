package repositories;

import domains.Student;
import exceptions.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class StudentXmlRepository extends StudentRepository {
    private File xmlFile;

    public StudentXmlRepository(File file){
        this.xmlFile = file;
        loadData();
    }

    private void loadData(){
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document document = dBuilder.parse(this.xmlFile);
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("student");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                Student student = elementToStudent(element);
                super.save(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeToFile(){
        try{
            Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root=document.createElement("studenti");
            document.appendChild(root);
            super.findAll().forEach(student->{
                Element element=studentToElement(document,student);
                root.appendChild(element);
            });


            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            Source source=new DOMSource(document);
            transformer.transform(source,new StreamResult(this.xmlFile));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Element studentToElement(Document document,Student student){
        Element element=document.createElement("student");
        element.setAttribute("id",student.getId().toString());
        Element nume=document.createElement("nume");
        nume.setTextContent(student.getNume());
        element.appendChild(nume);

        Element prenume=document.createElement("prenume");
        prenume.setTextContent(student.getPrenume());
        element.appendChild(prenume);

        Element grupa=document.createElement("grupa");
        grupa.setTextContent(student.getGrupa().toString());
        element.appendChild(grupa);

        Element email=document.createElement("email");
        email.setTextContent(student.getEmail());
        element.appendChild(email);

        Element cadruDidacticIndrumatorLab=document.createElement("cadruDidacticIndrumatorLab");
        cadruDidacticIndrumatorLab.setTextContent(student.getCadruDidacticIndrumatorLab());
        element.appendChild(cadruDidacticIndrumatorLab);

        return element;
    }

    private Student elementToStudent(Element element){
        Integer id=Integer.parseInt(element.getAttribute("id"));
        String nume=element.getElementsByTagName("nume").item(0).getTextContent();
        String prenume=element.getElementsByTagName("prenume").item(0).getTextContent();
        Integer grupa=Integer.parseInt(element.getElementsByTagName("grupa").item(0).getTextContent());
        String email=element.getElementsByTagName("email").item(0).getTextContent();
        String cadruDidacticIndrumatorLab=element.getElementsByTagName("cadruDidacticIndrumatorLab").item(0).getTextContent();
        Student student=new Student(id,nume,prenume,grupa,email,cadruDidacticIndrumatorLab);
        return student;
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        Student student=super.save(entity);
        if(student==null){
            writeToFile();
        }
        return student;
    }

    @Override
    public Student delete(Integer integer) {
        Student student=super.delete(integer);
        if(student!=null){
            writeToFile();
        }
        return student;
    }

    @Override
    public Student update(Student entity) {
        Student student=super.update(entity);
        if(student==null){
            writeToFile();
        }
        return student;
    }
}