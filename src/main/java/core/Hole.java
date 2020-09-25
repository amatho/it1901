package core;

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
}
