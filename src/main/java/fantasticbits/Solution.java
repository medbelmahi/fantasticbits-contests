package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Solution {

    public static final int DEPTH = 4;

    public double energy;
    public int[] moves1;
    public int[] moves2;

    public int spellTurn1;
    public Unit spellTarget1;
    public int spell1;

    public int spellTurn2;
    public Unit spellTarget2;
    public int spell2;


    public Solution merge(Solution solution) {
        Solution child = new Solution();

        for (int i = 0; i < DEPTH; ++i) {
            if (Utilitaire.fastRandInt(2)) {
                child.moves1[i] = solution.moves1[i];
                child.moves2[i] = solution.moves2[i];
            } else {
                child.moves1[i] = moves1[i];
                child.moves2[i] = moves2[i];
            }
        }

        if (Utilitaire.fastRandInt(2)) {
            child.spellTurn1 = solution.spellTurn1;
            child.spellTarget1 = solution.spellTarget1;
            child.spell1 = solution.spell1;
        } else {
            child.spellTurn1 = spellTurn1;
            child.spellTarget1 = spellTarget1;
            child.spell1 = spell1;
        }

        if (Utilitaire.fastRandInt(2)) {
            child.spellTurn2 = solution.spellTurn2;
            child.spellTarget2 = solution.spellTarget2;
            child.spell2 = solution.spell2;
        } else {
            child.spellTurn2 = spellTurn2;
            child.spellTarget2 = spellTarget2;
            child.spell2 = spell2;
        }


        return child;
    }

    public void copy(Solution solution) {
        for (int i = 0; i < DEPTH; ++i) {
            moves1[i] = solution.moves1[i];
            moves2[i] = solution.moves2[i];
        }

        spellTurn1 = solution.spellTurn1;
        spell1 = solution.spell1;
        spellTarget1 = solution.spellTarget1;
        spellTurn2 = solution.spellTurn2;
        spell2 = solution.spell2;
        spellTarget2 = solution.spellTarget2;

        this.energy = solution.energy;
    }


    //void mutate();

    //void randomize();
}
