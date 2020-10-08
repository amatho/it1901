package golfapp.data;

import java.util.Objects;

public class ObjectContainer<T> {

  private final long id;
  private final T value;

  ObjectContainer(long id, T value) {
    this.id = id;
    this.value = value;
  }

  public long getId() {
    return id;
  }

  public T getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof ObjectContainer<?>) {
      var other = (ObjectContainer<?>) o;
      return id == other.id && value.equals(other.value);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value);
  }

  @Override
  public String toString() {
    return "ObjectContainer{"
        + "id="
        + id
        + ", value="
        + value
        + '}';
  }
}
