package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Bludger extends Unit {
	
	Wizard last;
	int[] ignore = new int[2];
	
	Wizard slast;
	
	Bludger() {
		this.r = 200.0;
		this.m = 8;
		this.f = 0.9;
		
		last = null;
		ignore[0] = -1;
		ignore[1] = -1;
		type = Utilitaire.BLUDGER;
	}
	
	void print() {
		System.err.println(
				"Bludger " + id + " " + x + " " + y + " " + vx + " " + vy + " " + speed() + " " + ignore[0] + " "
						+ ignore[1] + " | ");
		
		if (last != null) {
			System.err.println("Last " + last.id + " | ");
		}
		
		System.err.println("\n");
	}
	
	void save() {
		super.save();
		
		slast = last;
	}
	
	void reset() {
		super.reset();
		
		last = slast;
		ignore[0] = -1;
		ignore[1] = -1;
	}
	
	void bounce(final Unit u) {
		if (u.type == Utilitaire.WIZARD) {
			last = (Wizard) u;
		}
		
		super.bounce(u);
	}
	
	void play() {
		// Find our target
		Wizard target = null;
		double d = 0;
		
		for (int i = 0; i < 4; ++i) {
			final Wizard wizard = wizards[i];
			
			if ((last != null && last.id == wizard.id) || wizard.team == ignore[0] || wizard.team == ignore[1]) {
				continue;
			}
			
			final double d2 = Utilitaire.dist2(this, wizard);
			
			if (target == null || d2 < d) {
				d = d2;
				target = wizard;
			}
		}
		
		if (target != null) {
			thrust(1000.0, target, Math.sqrt(d));
		}
		
		ignore[0] = -1;
		ignore[1] = -1;
	}
}
