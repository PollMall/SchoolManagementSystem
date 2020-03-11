package repositories;

import domains.Tema;
import exceptions.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import validators.ValidatorTema;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class TemaXmlRepository extends TemaRepository {
    private File xmlFile;

    public TemaXmlRepository(File file){
        this.xmlFile = file;
        loadData();
    }

    private void loadData() {
        try{
            Document document= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.xmlFile);
            document.getDocumentElement().normalize();
            Element root=document.getDocumentElement();

            NodeList nodeList=root.getElementsByTagName("tema");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Tema tema=elementToTema((Element)nodeList.item(i));
                super.save(tema);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeToFile(){
        try{
            Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root=document.createElement("teme");
            super.findAll().forEach(tema -> {
                Element element=tematoElement(document,tema);
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

    private Tema elementToTema(Element element){
        Integer id=Integer.parseInt(element.getAttribute("id"));
        String descriere=element.getElementsByTagName("descriere").item(0).getTextContent();
        Integer startWeek=Integer.parseInt(element.getElementsByTagName("startWeek").item(0).getTextContent());
        Integer deadlineWeek=Integer.parseInt(element.getElementsByTagName("deadlineWeek").item(0).getTextContent());
        Tema tema=new Tema(id,descriere,startWeek,deadlineWeek);
        return tema;
    }

    private Element tematoElement(Document document,Tema tema){
        Element element=document.createElement("tema");
        element.setAttribute("id",tema.getId().toString());

        Element descriere=document.createElement("descriere");
        descriere.setTextContent(tema.getDescriere());
        element.appendChild(descriere);

        Element startWeek=document.createElement("startWeek");
        startWeek.setTextContent(tema.getStartWeek().toString());
        element.appendChild(startWeek);

        Element deadlineWeek=document.createElement("deadlineWeek");
        deadlineWeek.setTextContent(tema.getDeadlineWeek().toString());
        element.appendChild(deadlineWeek);

        return element;
    }

    @Override
    public Tema save(Tema entity) throws ValidationException {
        Tema tema=super.save(entity);
        if(tema==null){
            writeToFile();
        }
        return tema;
    }

    @Override
    public Tema delete(Integer integer) {
        Tema tema=super.delete(integer);
        if(tema!=null){
            writeToFile();
        }
        return tema;
    }

    @Override
    public Tema update(Tema entity) {
        Tema tema=super.update(entity);
        if(tema==null){
            writeToFile();
        }
        return tema;
    }
}
