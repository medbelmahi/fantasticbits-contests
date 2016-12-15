package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Utilitaire {
    
    public static final double WIDTH = 16000.0;
    public static final double HEIGHT = 7500.0;
    
    public static final int OBLIVIATE = 0;
    public static final int PETRIFICUS = 1;
    public static final int ACCIO = 2;
    public static final int FLIPENDO = 3;
    public static final int SPELL_DURATIONS[] = { 3, 1, 6, 3 };
    public static final int SPELL_COSTS[] = { 5, 10, 20, 20 };
    
    public static final double E = 0.00001;
    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;
    public static final double INF = 16000 * 16000 + 7500 * 7500;
    public static final int WIZARD = 1;
    public static final int SNAFFLE = 2;
    public static final int BLUDGER = 3;
    public static final int POLE = 4;
    public static final double TO_RAD = M_PI / 180.0;
    
    public static final double ANGLES[] = { 0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0, 100.0, 110.0,
            120.0, 130.0, 140.0, 150.0, 160.0, 170.0, 180.0, 190.0, 200.0, 210.0, 220.0, 230.0, 240.0, 250.0, 260.0,
            270.0, 280.0, 290.0, 300.0, 310.0, 320.0, 330.0, 340.0, 350.0 };
    public static final int ANGLES_LENGTH = 36;
    
    public static final int DEPTH = 4;
    public static final int SPELL_DEPTH = 8;
    public static final int POOL = 50;
    public static final double MUTATION = 2;
    
    public static Collision[] collisionsCache;
    public static int collisionsCacheFE = 0;
    
    public static Collision[] collisions;
    public static int collisionsFE = 0;
    
    public static Collision[] tempCollisions;
    public static int tempCollisionsFE = 0;
    
    public static Collision fake;
    
    public static double[] cosAngles = new double[ANGLES_LENGTH];
    public static double[] sinAngles = new double[ANGLES_LENGTH];
    
    /**********************************************************/
    
    public static Wizard myWizard1;
    public static Wizard myWizard2;
    public static Wizard hisWizard1;
    public static Wizard hisWizard2;
    public static Point myGoal;
    public static Point hisGoal;
    public static Point mid;
    
    public static Unit[] units = new Unit[20];
    public static int unitsFE = 0;
    
    public static Unit[] unitsById = new Unit[24];
    
    public static Snaffle[] snaffles = new Snaffle[20];
    public static int snafflesFE = 0;
    
    public static Wizard[] wizards = new Wizard[4];
    public static Bludger[] bludgers = new Bludger[2];
    public static Pole[] poles = new Pole[4];
    
    public static Unit[][] spellTargets = new Unit[4][20];
    public static int[] spellTargetsFE = new int[4];
    
    public static boolean doLog = false;
    
    public static Spell[] spells = new Spell[16];
    
    public static int victory;
    
    /*********************************************************/
    
    public static int myTeam;
    public static int mana = 0;
    public static int turn = 0;
    public static int myScore = 0;
    public static int hisScore = 0;
    public static double energy = 0;
    public static int depth = 0;
    
    public static int smana;
    public static int smyScore;
    public static int shisScore;
    
    /*********************************************************/
    
    public static int g_seed;
    
    public static boolean fastRandInt(final int maxSize) {
        final int mod = fastrand() % maxSize;
        return mod == 0 ? false : true;
    }

    public static int fastrand() {
        //fastrand routine returns one integer, similar output value range as C lib.
        g_seed = (214013*g_seed+2531011);
        return (g_seed>>16)&0x7FFF;
    }
    
    public static void fast_srand(final int seed) {
        //Seed the generator
        g_seed = seed;
    }
    
    public static double dist(final double x1, final double y1, final double x2, final double y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }
    
    public static double dist2(final double x1, final double y1, final double x2, final double y2) {
        return (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
    }
    
    public static double dist(final Point p, final double x2, final double y2) {
        return dist(p.x, p.y, x2, y2);
    }
    
    public static double dist2(final Point p, final double x2, final double y2) {
        return dist2(p.x, p.y, x2, y2);
    }
    
    public static double dist(final Point u1, final Point u2) {
        return dist(u1.x, u1.y, u2.x, u2.y);
    }
    
    public static double dist2(final Point u1, final Point u2) {
        return dist2(u1.x, u1.y, u2.x, u2.y);
    }
}
