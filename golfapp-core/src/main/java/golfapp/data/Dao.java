package golfapp.data;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

  /**
   * Try to find an object with the given {@code id}.
   *
   * @param id identifier for the object to find
   * @return the object if one was found
   */
  Optional<T> get(long id);

  /**
   * Get all the objects.
   *
   * @return all objects, or an empty list if none was found
   */
  List<ObjectContainer<T>> getAll();

  /**
   * Save an object.
   *
   * @param t the object to save
   */
  long save(T t);

  /**
   * Find the object with the given {@code id} and update it.
   *
   * @param id identifier for the object to be updated
   * @param t  the updated object
   */
  void update(long id, T t);

  /**
   * Delete the object with the given {@code id}.
   *
   * @param id identifier for the object to be deleted
   */
  void delete(long id);
}
