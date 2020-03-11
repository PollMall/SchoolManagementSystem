package repositories;

import domains.Tema;
import exceptions.ValidationException;
import validators.ValidatorTema;

public class TemaRepository extends InMemoryRepository<Integer, Tema> {
    public static Integer INDEX=0;

    public TemaRepository() {
        super(new ValidatorTema());
    }

    @Override
    public Tema findOne(Integer integer) {
        return super.findOne(integer);
    }

    @Override
    public Iterable<Tema> findAll() {
        return super.findAll();
    }

    @Override
    public Tema save(Tema entity) throws ValidationException {
        if(super.save(entity)==null){
            INDEX++;
            return null;
        }
        return entity;
    }

    @Override
    public Tema delete(Integer integer) {
        return super.delete(integer);
    }

    @Override
    public Tema update(Tema entity) {
        return super.update(entity);
    }
}
