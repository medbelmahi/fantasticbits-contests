package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Utilitaire {

    public static int g_seed;

    public static boolean fastRandInt(int maxSize) {
        int mod = fastrand() % maxSize;
        return mod == 0 ? false : true;
    }

    public static int fastrand() {
        //fastrand routine returns one integer, similar output value range as C lib.
        g_seed = (214013*g_seed+2531011);
        return (g_seed>>16)&0x7FFF;
    }


    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    public static double dist2(double x1, double y1, double x2, double y2) {
        return (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
    }

    public static double dist(Point p, double x2, double y2) {
        return dist(p.x, p.y, x2, y2);
    }

    public static double dist2(Point p, double x2, double y2) {
        return dist2(p.x, p.y, x2, y2);
    }

    public static double dist(Point u1, Point u2) {
        return dist(u1.x, u1.y, u2.x, u2.y);
    }

    public static double dist2(Point u1, Point u2) {
        return dist2(u1.x, u1.y, u2.x, u2.y);
    }
}
