package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Hole {

  private final double length;
  private final int par;
  private final double height;

  /**
   * Create a new Hole.
   *
   * @param length the length to the hole
   * @param par    the par of the hole
   * @param height the hole's height
   */
  public Hole(double length, int par, double height) {
    if (length <= 0) {
      throw new IllegalArgumentException(
          "The hole length can not be negative or 0, was : " + length);
    }
    if (par <= 0) {
      throw new IllegalArgumentException("The hole par can not be negative or 0, was : " + par);
    }
    this.length = length;
    this.par = par;
    this.height = height;
  }

  // Creator for Jackson
  @JsonCreator
  public static Hole createHole(@JsonProperty("length") double length, @JsonProperty("par") int par,
      @JsonProperty("height") double height) {
    return new Hole(length, par, height);
  }

  public double getLength() {
    return length;
  }

  public int getPar() {
    return par;
  }

  public double getHeight() {
    return height;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Hole) {
      var other = (Hole) o;
      return length == other.length && par == other.par && height == other.height;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(length, par, height);
  }
}
