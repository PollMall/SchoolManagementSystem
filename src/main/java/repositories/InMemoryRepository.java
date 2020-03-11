package repositories;

import domains.Entity;
import exceptions.ValidationException;
import validators.Validator;

import java.util.ArrayList;

public class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E> {
    private Validator<E> validator;
    private ArrayList<E> arrayList;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        this.arrayList=new ArrayList<>();
    }

    public ArrayList<E> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<E> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public E findOne(ID id) {
        if(id==null)
            throw new IllegalArgumentException();
        for (E element:
                arrayList) {
            if(element.getId().equals(id))
                return element;
        }
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return arrayList;
    }

    @Override
    public E save(E entity) throws ValidationException {
        if(entity==null) throw new IllegalArgumentException();
        validator.validate(entity);
        if(findOne(entity.getId())!=null)
            return entity;
        ArrayList newArrayList=this.getArrayList();
        newArrayList.add(entity);
        this.setArrayList(newArrayList);
        return null;
    }

    @Override
    public E delete(ID id) {
        if(id==null)
            throw new IllegalArgumentException();
        for (E element:
                arrayList) {
            if(element.getId().equals(id)) {
                arrayList.remove(element);
                return element;
            }
        }
        return null;
    }

    @Override
    public E update(E entity){
        if(entity==null) throw new IllegalArgumentException();
        validator.validate(entity);
        if(findOne(entity.getId())==null)
            return entity;
        delete(entity.getId());
        //save(entity);
        arrayList.add(entity);
        return null;
    }
}
