package fantasticbits;

/**
 * Created by Mohamed BELMAHI on 11/12/2016.
 */
public class Pole extends Unit {
	
	Pole(final int id, final double x, final double y) {
		this.id = id;
		r = 300;
		m = Utilitaire.INF;
		type = Utilitaire.POLE;
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		dead = false;
		f = 0.0;
	}
	
	void move(final double t) {
	}
	
	void save() {
	}
	
	void reset() {
	}
	
	Collision collision(final double from) {
		return null;
	}
}
