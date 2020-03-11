package services;

import domains.Tema;
import repositories.TemaRepository;
import utils.ChangeOperation;
import utils.events.TemaChangeEvent;
import utils.observers.Observable;
import utils.observers.Observer;
import repositories.CrudRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TemaService implements Service<Integer,Tema>, Observable<TemaChangeEvent> {
    private List<Observer<TemaChangeEvent>> observers=new ArrayList<>();
    private TemaRepository temaRepository;//=new TemaXmlRepository(new File("src/main/resources/teme.xml"));

    public TemaService(TemaRepository temaRepository) {
        this.temaRepository = temaRepository;
    }

    public TemaRepository getTemaRepository() {
        return temaRepository;
    }

    public Integer determinare_saptamana(LocalDate dataCurenta) {
        Integer saptamana = null;
        LocalDate dataInceput=LocalDate.parse("30-09-2019", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate dataInceputVacanta=LocalDate.parse("23-12-2019", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate dataSfarsitVacanta=LocalDate.parse("05-01-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if(dataCurenta.compareTo(dataInceputVacanta)>=0){
            if(dataCurenta.compareTo(dataSfarsitVacanta.plusWeeks(1))<=0){
                saptamana=13;
            }
            else if(dataCurenta.compareTo(dataSfarsitVacanta.plusWeeks(2))<=0){
                saptamana=14;
            }
        }
        else{
            saptamana=(dataCurenta.getDayOfYear()-dataInceput.getDayOfYear())/7+1;
        }
        return saptamana;
    }

    public Tema serviceFindOne(Integer id){
        return temaRepository.findOne(id);
    }

    public Iterable<Tema> serviceFindAll(){
        return temaRepository.findAll();
    }

    public Tema serviceSave(Tema entity){
        Tema tema=temaRepository.save(entity);
        if(tema==null){
            notifyAll(new TemaChangeEvent(ChangeOperation.ADD,entity));
        }
        return tema;
    }

    public Tema serviceDelete(Integer id){
        Tema tema = temaRepository.delete(id);
        if(tema!=null){
            notifyAll(new TemaChangeEvent(ChangeOperation.DELETE,tema));
        }
        return tema;
    }

    public Tema serviceUpdate(Tema entity){
        Tema oldData=temaRepository.findOne(entity.getId());
        Tema tema=temaRepository.update(entity);
        if(tema==null){
            notifyAll(new TemaChangeEvent(ChangeOperation.UPDATE,oldData,entity));
        }
        return tema;
    }

    @Override
    public void addObserver(Observer<TemaChangeEvent> event) {
        observers.add(event);
    }

    @Override
    public void removeObserver(Observer<TemaChangeEvent> event) {
        observers.remove(event);
    }

    @Override
    public void notifyAll(TemaChangeEvent event) {
        observers.stream().forEach(x->x.update(event));
    }
}
