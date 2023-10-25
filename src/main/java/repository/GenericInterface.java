package repository;

import java.util.List;

public interface GenericInterface<T, ID> {
    T getById(ID id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void deleteById(ID id);

}
