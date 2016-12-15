package fantasticbits;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Grab Snaffles and try to throw them through the opponent's goal!
 * Move towards a Snaffle and use your team id to determine where you need to throw it.
 **/
class Player {
	
	public static void main(final String[] args) {
		
		Utilitaire.fast_srand(42);
		
		Utilitaire.fake = new Collision();
		Utilitaire.fake.t = 1000.0;
		
		for (int i = 0; i < Utilitaire.ANGLES_LENGTH; ++i) {
			Utilitaire.cosAngles[i] = Math.cos(Utilitaire.ANGLES[i] * Utilitaire.TO_RAD);
			Utilitaire.sinAngles[i] = Math.sin(Utilitaire.ANGLES[i] * Utilitaire.TO_RAD);
		}
		
		final Scanner in = new Scanner(System.in);
		Utilitaire.myTeam = in
				.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left
		
		//cin >> myTeam; cin.ignore();
		
		Utilitaire.collisionsCache = new Collision[100];
		Utilitaire.collisions = new Collision[100];
		Utilitaire.tempCollisions = new Collision[100];
		
		for (int i = 0; i < 100; ++i) {
			Utilitaire.collisionsCache[i] = new Collision();
		}
		
		Utilitaire.wizards[0] = new Wizard(0);
		Utilitaire.wizards[1] = new Wizard(0);
		Utilitaire.wizards[2] = new Wizard(1);
		Utilitaire.wizards[3] = new Wizard(1);
		
		Utilitaire.units[0] = Utilitaire.wizards[0];
		Utilitaire.units[1] = Utilitaire.wizards[1];
		Utilitaire.units[2] = Utilitaire.wizards[2];
		Utilitaire.units[3] = Utilitaire.wizards[3];
		
		if (Utilitaire.myTeam == 0) {
			Utilitaire.myWizard1 = Utilitaire.wizards[0];
			Utilitaire.myWizard2 = Utilitaire.wizards[1];
			Utilitaire.hisWizard1 = Utilitaire.wizards[2];
			Utilitaire.hisWizard2 = Utilitaire.wizards[3];
			Utilitaire.myGoal = new Point(16000, 3750);
			Utilitaire.hisGoal = new Point(0, 3750);
		} else {
			Utilitaire.myWizard1 = Utilitaire.wizards[2];
			Utilitaire.myWizard2 = Utilitaire.wizards[3];
			Utilitaire.hisWizard1 = Utilitaire.wizards[0];
			Utilitaire.hisWizard2 = Utilitaire.wizards[1];
			Utilitaire.myGoal = new Point(0, 3750);
			Utilitaire.hisGoal = new Point(16000, 3750);
		}
		
		Utilitaire.mid = new Point(8000, 3750);
		
		Utilitaire.bludgers[0] = new Bludger();
		Utilitaire.bludgers[1] = new Bludger();
		
		Utilitaire.units[4] = Utilitaire.bludgers[0];
		Utilitaire.units[5] = Utilitaire.bludgers[1];
		
		Utilitaire.poles[0] = new Pole(20, 0, 1750);
		Utilitaire.poles[1] = new Pole(21, 0, 5750);
		Utilitaire.poles[2] = new Pole(22, 16000, 1750);
		Utilitaire.poles[3] = new Pole(23, 16000, 5750);
		
		Utilitaire.units[6] = Utilitaire.poles[0];
		Utilitaire.units[7] = Utilitaire.poles[1];
		Utilitaire.units[8] = Utilitaire.poles[2];
		Utilitaire.units[9] = Utilitaire.poles[3];
		
		Utilitaire.unitsFE = 10;
		
		// game loop
		while (true) {
			final int myScore = in.nextInt();
			final int myMagic = in.nextInt();
			final int opponentScore = in.nextInt();
			final int opponentMagic = in.nextInt();
			final int entities = in.nextInt(); // number of entities still in game
			for (int i = 0; i < entities; i++) {
				final int entityId = in.nextInt(); // entity identifier
				final String entityType = in
						.next(); // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
				final int x = in.nextInt(); // position
				final int y = in.nextInt(); // position
				final int vx = in.nextInt(); // velocity
				final int vy = in.nextInt(); // velocity
				final int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise
			}
			for (int i = 0; i < 2; i++) {
				
				// Write an action using System.out.println()
				// To debug: System.err.println("Debug messages...");
				
				// Edit this line to indicate the action for each wizard (0 ≤ thrust ≤ 150, 0 ≤ power ≤ 500)
				// i.e.: "MOVE x y thrust" or "THROW x y power"
				System.out.println("MOVE 8000 3750 100");
			}
		}
	}
	
	public static void main(final String[] args) {
		
		int spellsFE = 0;
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				spells[spellsFE++] = wizards[j]->spells[i];
			}
		}
		
		Solution * best = new Solution();
		
		int oldSnafflesFE;
		
		while (1) {
			int entities;
			cin >> entities;
			cin.ignore();
			start = NOW;
			
			int bludgersFE = 0;
			
			if (turn) {
				for (int i = 0; i < 24; ++i) {
					Unit * u = unitsById[i];
					
					if (u && u -> type == SNAFFLE) {
						u -> dead = true;
						u -> carrier = NULL;
					}
				}
			}
			
			for (int i = 0; i < entities; i++) {
				int id; // entity identifier
				string entityType; // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
				int x; // position
				int y; // position
				int vx; // velocity
				int vy; // velocity
				int state; // 1 if the wizard is holding a Snaffle, 0 otherwise
				cin >> id >> entityType >> x >> y >> vx >> vy >> state;
				cin.ignore();
				
				Unit * unit;
				
				if (entityType == "WIZARD" || entityType == "OPPONENT_WIZARD") {
					unit = wizards[id];
				} else if (entityType == "SNAFFLE") {
					if (!turn) {
						unit = new Snaffle();
					} else {
						unit = unitsById[id];
					}
					
					unit -> dead = false;
					units[unitsFE++] = unit;
					snaffles[snafflesFE++] = (Snaffle *) unit;
				} else if (entityType == "BLUDGER") {
					unit = bludgers[bludgersFE++];
				}
				
				unit -> update(id, x, y, vx, vy, state);
			}
			
			if (turn == 0) {
				victory = (snafflesFE / 2) + 1;
				
				for (int i = 0; i < unitsFE; ++i) {
					unitsById[units[i]->id] =units[i];
				}
			}
			
			// Mise à jour des carriers et des snaffles
			for (int i = 0; i < 4; ++i) {
				wizards[i]->updateSnaffle();
			}
			
			// Mise à jour du score
			if (turn && oldSnafflesFE != snafflesFE) {
				for (int i = 0; i < 24; ++i) {
					Unit * u = unitsById[i];
					
					if (u && u -> type == SNAFFLE && u -> dead) {
						if (!myTeam) {
							if (u -> x > 8000) {
								myScore += 1;
							} else {
								hisScore += 1;
							}
						} else {
							if (u -> x > 8000) {
								hisScore += 1;
							} else {
								myScore += 1;
							}
						}
						
						delete u;
						unitsById[i] = NULL;
					}
				}
			}
			
			// Cibles pour les sorts
			
			// Bludgers pour tous les sorts
			for (int i = 0; i < 4; ++i) {
				spellTargets[i][0] = bludgers[0];
				spellTargets[i][1] = bludgers[1];
				spellTargetsFE[i] = 2;
			}
			
			// Wizards ennemis pour petrificus et flipendo
			if (!myTeam) {
				spellTargets[PETRIFICUS][spellTargetsFE[PETRIFICUS]++] = wizards[2];
				spellTargets[PETRIFICUS][spellTargetsFE[PETRIFICUS]++] = wizards[3];
				spellTargets[FLIPENDO][spellTargetsFE[FLIPENDO]++] = wizards[2];
				spellTargets[FLIPENDO][spellTargetsFE[FLIPENDO]++] = wizards[3];
			} else {
				spellTargets[PETRIFICUS][spellTargetsFE[PETRIFICUS]++] = wizards[0];
				spellTargets[PETRIFICUS][spellTargetsFE[PETRIFICUS]++] = wizards[1];
				spellTargets[FLIPENDO][spellTargetsFE[FLIPENDO]++] = wizards[0];
				spellTargets[FLIPENDO][spellTargetsFE[FLIPENDO]++] = wizards[1];
			}
			
			// Snaffles pour tous les sorts sauf obliviate
			for (int i = 1; i < 4; ++i) {
				for (int j = 0; j < snafflesFE; ++j) {
					spellTargets[i][spellTargetsFE[i]++] = snaffles[j];
				}
			}
			
			for (int i = 0; i < unitsFE; ++i) {
				units[i]->save();
				
				smana = mana;
				smyScore = myScore;
				shisScore = hisScore;
			}
			
			for (int i = 0; i < 16; ++i) {
				spells[i]->reloadTarget();
				spells[i]->save();
			}

        #ifndef PROD if (turn) {
				for (int i = 0; i < unitsFE; ++i) {
					units[i]->compare();
				}
			}
		#endif

        #ifndef PROD cerr << "hisScore : " << hisScore << endl;
			cerr << "victory : " << victory << endl;
		#endif

        /*cerr << "***** State for this turn " << endl;
		cerr << "Mana: " << mana << endl;
        cerr << "My score: " << myScore << endl;
        cerr << "His score: " << hisScore << endl;
        for (int i = 0; i < unitsFE; ++i) {
            units[i]->print();
        }*/
			
			// Evolution
			
			Solution * base;
			if (turn) {
				base = new Solution();
				
				for (int j = 1; j < DEPTH; ++j) {
					base -> moves1[j - 1] = best -> moves1[j];
					base -> moves2[j - 1] = best -> moves2[j];
				}
				
				base -> spellTurn1 = best -> spellTurn1;
				base -> spell1 = best -> spell1;
				base -> spellTarget1 = best -> spellTarget1;
				base -> spellTurn2 = best -> spellTurn2;
				base -> spell2 = best -> spell2;
				base -> spellTarget2 = best -> spellTarget2;
				
				if (!base -> spellTurn1) {
					base -> spellTurn1 = SPELL_DEPTH - 1;
				} else {
					base -> spellTurn1 -= 1;
				}
				
				if (!base -> spellTurn2) {
					base -> spellTurn2 = SPELL_DEPTH - 1;
				} else {
					base -> spellTurn2 -= 1;
				}
				
				if (base -> spellTarget1 -> dead) {
					base -> spellTurn1 = SPELL_DEPTH - 1;
					base -> spellTarget1 = spellTargets[base -> spell1][fastRandInt(spellTargetsFE[base -> spell1])];
				}
				
				if (base -> spellTarget2 -> dead) {
					base -> spellTurn2 = SPELL_DEPTH - 1;
					base -> spellTarget2 = spellTargets[base -> spell2][fastRandInt(spellTargetsFE[base -> spell2])];
				}
				
				delete best;
			}
			
			Solution **pool = new Solution *[POOL];
			Solution **newPool = new Solution *[POOL];
			Solution **temp;
			int counter = POOL;
			
			best = new Solution();
			Solution * sol = new Solution();
			sol -> randomize();
			
			simulate(sol);
			pool[0] = sol;
			
			best -> copy(sol);
			
			Solution * tempBest = sol;
			
			// First generation
			int startI = 1;
			
			if (turn) {
				// Populate the POOL with some copy of the previous best one
				for (int i = startI; i < POOL / 5; ++i) {
					Solution * solution = new Solution();
					solution -> copy(base);
					
					// Add a last one random
					solution -> moves1[DEPTH - 1] = fastRandInt(ANGLES_LENGTH);
					solution -> moves2[DEPTH - 1] = fastRandInt(ANGLES_LENGTH);
					
					simulate(solution);
					
					if (solution -> energy > tempBest -> energy) {
						tempBest = solution;
					}
					
					pool[i] = solution;
				}
				
				delete base;
				
				startI = POOL / 5;
			}
			
			for (int i = startI; i < POOL; ++i) {
				Solution * solution = new Solution();
				solution -> randomize();
				
				simulate(solution);
				
				if (solution -> energy > tempBest -> energy) {
					tempBest = solution;
				}
				
				pool[i] = solution;
			}
			
			if (tempBest -> energy > best -> energy) {
				best -> copy(tempBest);
			}
			tempBest = best;
			
			double limit = turn ? 0.085 : 0.800;

        #ifdef DEBUG
			#define LIMIT counter <= 1000
		#else
			#define LIMIT TIME<limit
		#endif
			
			int generation = 1;
			int bestGeneration = 1;
			
			int poolFE;
			while (LIMIT) {
				// New generation
				
				// Force the actual best with a mutation to be in the pool
				Solution * solution = new Solution();
				solution -> copy(tempBest);
				solution -> mutate();
				simulate(solution);
				
				if (solution -> energy > tempBest -> energy) {
					tempBest = solution;
				}
				
				newPool[0] = solution;
				
				counter += 1;
				
				poolFE = 1;
				while (poolFE < POOL && LIMIT) {
					int aIndex = fastRandInt(POOL);
					int bIndex;
					
					do {
						bIndex = fastRandInt(POOL);
					} while (bIndex == aIndex);
					
					int firstIndex = pool[aIndex]->energy > pool[bIndex]->energy ? aIndex : bIndex;
					
					do {
						aIndex = fastRandInt(POOL);
					} while (aIndex == firstIndex);
					
					do {
						bIndex = fastRandInt(POOL);
					} while (bIndex == aIndex || bIndex == firstIndex);
					
					int secondIndex = pool[aIndex]->energy > pool[bIndex]->energy ? aIndex : bIndex;
					
					Solution * child = pool[firstIndex]->merge(pool[secondIndex]);
					
					if (!fastRandInt(MUTATION)) {
						child -> mutate();
					}
					
					simulate(child);
					
					if (child -> energy > tempBest -> energy) {
						tempBest = child;
					}
					
					newPool[poolFE++] = child;
					
					counter += 1;
				}
				
				// Burn previous generation !!
				for (int i = 0; i < POOL; ++i) {
					delete pool[ i];
				}
				
				temp = pool;
				pool = newPool;
				newPool = temp;
				
				if (tempBest -> energy > best -> energy) {
					best -> copy(tempBest);
					bestGeneration = generation;
				}
				tempBest = best;
				
				generation += 1;
			}

        #ifndef PROD cerr << "Counter: " << counter << endl;
			cerr << "Energy: " << best -> energy << endl;
			cerr << "Generation: " << generation << endl;
		#endif

        #ifdef DEBUG
			// Play a last time for debug
			simulate2(best);
		#endif
			
			// Play a last time to check some infos
			myWizard1 -> apply(best, 0, 1);
			myWizard2 -> apply(best, 0, 2);
			dummies();
			
			play();

/*        cerr << "***** State for next turn " << endl;
		cerr << "Mana: " << mana << endl;
        cerr << "My score: " << myScore << endl;
        cerr << "His score: " << hisScore << endl;
        for (int i = 0; i < unitsFE; ++i) {
            units[i]->print();
        }*/
			
			smana = mana;
			bludgers[0]->slast = bludgers[0]->last;
			bludgers[1]->slast = bludgers[1]->last;
			
			for (int i = 0; i < 16; ++i) {
				spells[i]->save();
			}

        #ifndef PROD for (int i = 0; i < unitsFE; ++i) {
				units[i]->store();
			}
		#endif
			
			reset();

        #ifdef PROFILE double totalSpent = 0;
			for (int i = 0; i < DURATIONS_COUNT; i++) {
				totalSpent += durations[i];
			}
			for (int i = 0; i < DURATIONS_COUNT; i++) {
				fprintf(stderr, "Time %3d: %.6fms (%.2f%%)\n", i, durations[i] * 1000.0,
						durations[i] * 100.0 / totalSpent);
			}
			fprintf(stderr, "Total: %.6fms (%.6fms per turn)\n", totalSpent * 1000.0,
					totalSpent * 1000.0 / (double) (turn + 1));
		#endif
			
			myWizard1 -> output(best -> moves1[0], best -> spellTurn1, best -> spell1, best -> spellTarget1);
			myWizard2 -> output(best -> moves2[0], best -> spellTurn2, best -> spell2, best -> spellTarget2);
			
			// Burn last generation !!
			for (int i = 0; i < poolFE; ++i) {
				delete pool[ i];
			}
			
			delete[] pool;
			delete[] newPool;
			
			turn += 1;
			unitsFE = 10;
			
			oldSnafflesFE = snafflesFE;
			snafflesFE = 0;
		}
	}
}