package repositories;

import domains.Nota;
import exceptions.ValidationException;
import validators.ValidatorNota;

public class NotaRepository extends InMemoryRepository<String, Nota> {
    public NotaRepository() {
        super(new ValidatorNota());
    }

    @Override
    public Nota findOne(String s) {
        return super.findOne(s);
    }

    @Override
    public Iterable<Nota> findAll() {
        return super.findAll();
    }

    @Override
    public Nota save(Nota entity) throws ValidationException {
        return super.save(entity);
    }

    @Override
    public Nota delete(String s) {
        return super.delete(s);
    }

    @Override
    public Nota update(Nota entity) {
        return super.update(entity);
    }
}
