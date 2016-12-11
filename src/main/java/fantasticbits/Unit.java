package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Unit extends Point {

    public int id;
    public int type;
    public int state;
    public boolean dead = false;
    public double r;
    public double m;
    public double f;
    public double vx;
    public double vy;

    public double sx;
    public double sy;
    public double svx;
    public double svy;


    public Wizard carrier = null;
    public Snaffle snaffle = null;

    public int grab = 0;

    //#ifndef PROD
    public double lx;
    public double ly;
    public double lvx;
    public double lvy;
    public Wizard lcarrier;
    public Snaffle lsnaffle;
    public boolean ldead;


    public void store() {
        lx = x;
        ly = y;
        lvx = vx;
        lvy = vy;
        lcarrier = carrier;
        lsnaffle = snaffle;
        ldead = dead;
    }

    //void compare();


    public void update(int id, int x, int y, int vx, int vy, int state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.state = state;
    }

    double speedTo(Point p) {
        double d = 1.0 / Utilitaire.dist(this, x, y);

        // vitesse dans la direction du checkpoint - (vitesse orthogonale)^2/dist au cheeckpoint
        double dx = (p.x - this.x) * d;
        double dy = (p.y - this.y) * d;
        double nspeed = vx*dx + vy*dy;
        double ospeed = dy*vx - dx*vy;

        return nspeed - (5 * ospeed * ospeed * d);
    }

    double speed() {
        return Math.sqrt(vx*vx + vy*vy);
    }
}
