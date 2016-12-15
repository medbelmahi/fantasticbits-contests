package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Snaffle extends Unit {
	
	Wizard scarrier;
	
	Snaffle() {
		this.r = 150.0;
		this.m = 0.5;
		this.f = 0.75;
		
		carrier = null;
		type = Utilitaire.SNAFFLE;
	}
	
	Collision collision(final double from) {
		if (carrier || dead) {
			return null;
		}
		
		double tx = 2.0;
		double ty = tx;
		
		if (x + vx < 0.0) {
			tx = -x / vx;
		} else if (x + vx > Utilitaire.WIDTH) {
			tx = (Utilitaire.WIDTH - x) / vx;
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
		if (u.type == Utilitaire.WIZARD) {
			r = -1.0;
			final Collision result = super.collision(u, from);
			r = 150.0;
			
			return result;
		} else {
			return super.collision(u, from);
		}
	}
	
	void bounce(Unit u) {
		if (u.type == Utilitaire.WIZARD) {
			Wizard * target = (Wizard *) u;
			if (!target.snaffle && !target.grab && !dead && !carrier) {
				target.grabSnaffle(this);
			}
		} else {
			Unit::bounce (u);
		}
	}
	
	void bounce(final int dir) {
		if (dir == Utilitaire.HORIZONTAL && y >= 1899.0 && y <= 5599.0) {
			dead = true;
			
			if (!myTeam) {
				if (x > 8000) {
					myScore += 1;
				} else {
					hisScore += 1;
				}
			} else {
				if (x > 8000) {
					hisScore += 1;
				} else {
					myScore += 1;
				}
			}
		} else {
			super.bounce(dir);
		}
	}
	
	void move(final double t) {
		if (!dead && !carrier) {
			super.move(t);
		}
	}
	
	void end() {
		if (!dead && !carrier) {
			Unit::end ();
		}
	}
	
	void save() {
		super.save();
		
		scarrier = carrier;
	}
	
	void reset() {
		super.reset();
		
		carrier = scarrier;
		dead = false;
	}
	
	void print() {
		if (dead) {
			System.err.println("Snaffle " + id + " dead");
		} else {
			System.err.println("Snaffle " + id + " " + x + " " + y + " " + vx + " " + vy + " " + speed() + " " + " | ");
			
			if (carrier) {
				System.err.println("Carrier " + carrier.id + " | ");
			}
		}
		
		System.err.println(endl);
	}
}
