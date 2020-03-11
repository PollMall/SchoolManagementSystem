package repositories;

import domains.Student;
import exceptions.ValidationException;
import validators.ValidatorStudent;

public class StudentRepository extends InMemoryRepository<Integer, Student> {

    public StudentRepository(){
        super(new ValidatorStudent());
    }

    @Override
    public Student findOne(Integer integer) {
        return super.findOne(integer);
    }

    @Override
    public Iterable<Student> findAll() {
        return super.findAll();
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        return super.save(entity);
    }

    @Override
    public Student delete(Integer integer) {
        return super.delete(integer);
    }

    @Override
    public Student update(Student entity) {
        return super.update(entity);
    }
}