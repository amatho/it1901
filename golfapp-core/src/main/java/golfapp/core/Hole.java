package golfapp.core;

import java.util.Objects;

public class Hole {

  private double length;
  private int par;
  private double height;

  /**
   * Create a new Hole.
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

  // Creator for Jackson
  private Hole() {
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
