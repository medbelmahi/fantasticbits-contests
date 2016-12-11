package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Obliviate extends Spell {

    public Obliviate(Wizard caster, int type) {
        duration = 0;
        this.type = type;
        this.caster = caster;
    }
}
