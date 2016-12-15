package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Wizard extends Unit {
	public int team;
	public Spell[] spells = new Spell[4];
	
	public int sgrab;
	public Snaffle ssnaffle;
	
	public int spell;
	public Unit spellTarget;
	
	public Wizard(final int team) {
		this.team = team;
		this.r = 400.0;
		this.m = 1;
		this.f = 0.75;
		
		snaffle = null;
		grab = 0;
		type = Utilitaire.WIZARD;
		
		spells[Utilitaire.OBLIVIATE] = new Obliviate(this, Utilitaire.OBLIVIATE);
		spells[Utilitaire.PETRIFICUS] = new Petrificus(this, Utilitaire.PETRIFICUS);
		spells[Utilitaire.ACCIO] = new Accio(this, Utilitaire.ACCIO);
		spells[Utilitaire.FLIPENDO] = new Flipendo(this, Utilitaire.FLIPENDO);
		
		spellTarget = null;
	}
	
	void grabSnaffle(Snaffle snaffle);
	
	void apply(int move);
	
	void output(final int move, final int spellTurn, final int spell, Unit*target) {
		if (!spellTurn && spells[spell].duration == SPELL_DURATIONS[spell]) {
			if (spell == OBLIVIATE) {
				cout << "OBLIVIATE ";
			} else if (spell == PETRIFICUS) {
				cout << "PETRIFICUS ";
			} else if (spell == ACCIO) {
				cout << "ACCIO ";
			} else if (spell == FLIPENDO) {
				cout << "FLIPENDO ";
			}
			
			cout << target.id << endl;
			
			return;
		}
		
		// Adjust the targeted point for this angle
		// Find a point with the good angle
		final double px = x + cosAngles[move] * 10000.0;
		final double py = y + sinAngles[move] * 10000.0;
		
		if (snaffle) {
			cout << "THROW " << round(px) << " " << round(py) << " 500";
		} else {
			cout << "MOVE " << round(px) << " " << round(py) << " 150";
		}
		
		cout << endl;
	}
	
	boolean cast(final int spell, Unit*target) {
		final int cost = SPELL_COSTS[spell];
		
		if (mana < cost || target.dead) {
			return false;
		}
		
		mana -= cost;
		
		this.spell = spell;
		spellTarget = target;
		
		return true;
	}
	
	Collision collision(Unit u, double from) {
		if (u.type == SNAFFLE) {
			u.r = -1.0;
			Collision * result = Unit::collision (u, from);
			u.r = 150.0;
			
			return result;
		} else {
			return Unit::collision (u, from);
		}
	}
	
	void bounce(Unit u);
	
	void play();
	
	void end();
	
	void save() {
		Unit::save ();
		
		sgrab = grab;
		ssnaffle = snaffle;
	}
	
	void reset() {
		super.reset();
		
		grab = sgrab;
		snaffle = ssnaffle;
	}
	
	void print();
	
	void updateSnaffle();
	
	void apply(final Solution solution, final int turn, final int index) {
		if (index == 1) {
			if (solution.spellTurn1 == turn) {
				if (!myWizard1.cast(solution.spell1, solution.spellTarget1)) {
					myWizard1.apply(solution.moves1[turn]);
				}
			} else {
				myWizard1.apply(solution.moves1[turn]);
			}
		} else {
			if (solution.spellTurn2 == turn) {
				if (!myWizard2.cast(solution.spell2, solution.spellTarget2)) {
					myWizard2.apply(solution.moves2[turn]);
				}
			} else {
				myWizard2.apply(solution.moves2[turn]);
			}
		}
	}
}
