package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public abstract class Spell {


    public Wizard caster;
    public int duration;
    public Unit target;
    public int type;

    public int sduration;
    public Unit starget;

    public abstract void cast(Unit target);

    public abstract void apply();

    public abstract void save();

    public void reset() {
        duration = sduration;
        target = starget;
    }

    public abstract void reloadTarget();

    public void effect(){

    }

    public void print() {}
}
