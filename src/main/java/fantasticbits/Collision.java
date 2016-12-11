package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Collision {
    public double t;
    public int dir;
    public Unit a;
    public Unit b;

    public Collision() {}

    public Collision update(double t, Unit a, int dir) {
        b = null;
        this.t = t;
        this.a = a;
        this.dir = dir;

        return this;
    }

    public Collision update(double t, Unit a, Unit b) {
        dir = 0;
        this.t = t;
        this.a = a;
        this.b = b;

        return this;
    }
}
