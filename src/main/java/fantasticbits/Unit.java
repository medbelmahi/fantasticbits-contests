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
    
    public void update(final int id, final int x, final int y, final int vx, final int vy, final int state) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.state = state;
    }
    
    double speedTo(final Point p) {
        final double d = 1.0 / Utilitaire.dist(this, x, y);

        // vitesse dans la direction du checkpoint - (vitesse orthogonale)^2/dist au cheeckpoint
        final double dx = (p.x - this.x) * d;
        final double dy = (p.y - this.y) * d;
        final double nspeed = vx * dx + vy * dy;
        final double ospeed = dy * vx - dx * vy;

        return nspeed - (5 * ospeed * ospeed * d);
    }

    double speed() {
        return Math.sqrt(vx*vx + vy*vy);
    }
    
    void thrust(final double thrust, final Point p, final double distance) {
        final double coef = (thrust / m) / distance;
        vx += (p.x - x) * coef;
        vy += (p.y - y) * coef;
    }
    
    void thrust(final double thrust, final double x, final double y, final double distance) {
        final double coef = (thrust / m) / distance;
        vx += (x - this.x) * coef;
        vy += (y - this.y) * coef;
    }
    
    void move(final double t) {
        x += vx * t;
        y += vy * t;
    }
    
    Collision collision(final double from) {
        double tx = 2.0;
        double ty = tx;
        
        if (x + vx < r) {
            tx = (r - x) / vx;
        } else if (x + vx > Utilitaire.WIDTH - r) {
            tx = (Utilitaire.WIDTH - r - x) / vx;
        }
        
        if (y + vy < r) {
            ty = (r - y) / vy;
        } else if (y + vy > Utilitaire.HEIGHT - r) {
            ty = (Utilitaire.HEIGHT - r - y) / vy;
        }
        
        final int dir;
        final double t;
        
        if (tx < ty) {
            dir = Utilitaire.HORIZONTAL;
            t = tx + from;
        } else {
            dir = Utilitaire.VERTICAL;
            t = ty + from;
        }
        
        if (t <= 0.0 || t > 1.0) {
            return null;
        }
        
        return Utilitaire.collisionsCache[Utilitaire.collisionsCacheFE++].update(t, this, dir);
    }
    
    Collision collision(final Unit u, final double from) {
        final double x2 = x - u.x;
        final double y2 = y - u.y;
        final double r2 = r + u.r;
        final double vx2 = vx - u.vx;
        final double vy2 = vy - u.vy;
        final double a = vx2 * vx2 + vy2 * vy2;
        
        if (a < Utilitaire.E) {
            return null;
        }
        
        final double b = -2.0 * (x2 * vx2 + y2 * vy2);
        final double delta = b * b - 4.0 * a * (x2 * x2 + y2 * y2 - r2 * r2);
        
        if (delta < 0.0) {
            return null;
        }
        
        // double sqrtDelta = sqrt(delta);
        // double d = 1.0/(2.0*a);
        // double t1 = (b + sqrtDelta)*d;
        // double t2 = (b - sqrtDelta)*d;
        // double t = t1 < t2 ? t1 : t2;
        
        double t = (b - Math.sqrt(delta)) * (1.0 / (2.0 * a));
        
        if (t <= 0.0) {
            return null;
        }
        
        t += from;
        
        if (t > 1.0) {
            return null;
        }
        
        return Utilitaire.collisionsCache[Utilitaire.collisionsCacheFE++].update(t, this, u);
    }
    
    void bounce(final Unit u) {
        final double mcoeff = (m + u.m) / (m * u.m);
        final double nx = x - u.x;
        final double ny = y - u.y;
        final double nxnydeux = nx * nx + ny * ny;
        final double dvx = vx - u.vx;
        final double dvy = vy - u.vy;
        final double product = (nx * dvx + ny * dvy) / (nxnydeux * mcoeff);
        double fx = nx * product;
        double fy = ny * product;
        final double m1c = 1.0 / m;
        final double m2c = 1.0 / u.m;
        
        vx -= fx * m1c;
        vy -= fy * m1c;
        u.vx += fx * m2c;
        u.vy += fy * m2c;
        
        // Normalize vector at 100
        final double impulse = Math.sqrt(fx * fx + fy * fy);
        if (impulse < 100.0) {
            final double min = 100.0 / impulse;
            fx = fx * min;
            fy = fy * min;
        }
        
        vx -= fx * m1c;
        vy -= fy * m1c;
        u.vx += fx * m2c;
        u.vy += fy * m2c;
    }
    
    void bounce(final int dir) {
        if (dir == Utilitaire.HORIZONTAL) {
            vx = -vx;
        } else {
            vy = -vy;
        }
    }
    
    void end() {
        x = Math.round(x);
        y = Math.round(y);
        vx = Math.round(vx * f);
        vy = Math.round(vy * f);
    }
    
    boolean can(final Unit u) {
        if (type == Utilitaire.SNAFFLE) {
            return !carrier && !dead && !u.snaffle && !u.grab;
        } else if (u.type == Utilitaire.SNAFFLE) {
            return !u.carrier && !u.dead && !snaffle && !grab;
        }
        
        return true;
    }
    
    void print() {
    }
    
    void save() {
        sx = x;
        sy = y;
        svx = vx;
        svy = vy;
    }
    
    void reset() {
        x = sx;
        y = sy;
        vx = svx;
        vy = svy;
    }
}
