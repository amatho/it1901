package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * A single hole in a golf course.
 */
public class Hole {

  private double length;
  private int par;
  private double height;

  /**
   * Create a new hole.
   *
   * @param length the length to the hole
   * @param par    the par of the hole
   * @param height the hole's height
   */
  public Hole(double length, int par, double height) {
    this.length = length;
    this.par = par;
    this.height = height;
  }

  /**
   * Create a new hole. Meant as a creator for Jackson.
   *
   * @param length the length to the hole
   * @param par    the par of the hole
   * @param height the hole's height
   * @return a new hole
   */
  @JsonCreator
  public static Hole createHole(@JsonProperty("length") double length, @JsonProperty("par") int par,
      @JsonProperty("height") double height) {
    return new Hole(length, par, height);
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public int getPar() {
    return par;
  }

  public void setPar(int par) {
    this.par = par;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
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
