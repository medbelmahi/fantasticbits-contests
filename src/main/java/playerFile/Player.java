import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.awt.*;
import java.util.*;






/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
abstract class BasicAction extends GameAction{
    protected Position destination;

    public BasicAction(Position destination) {
        this.destination = destination;
    }
}



/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
class MoveAction extends BasicAction {

    public static final int STANDARD_THRUST = 150;
    public static final String MOVE_ACTION = "MOVE ";

    private int thrust;


    public MoveAction(Position destination) {
        super(destination);
        thrust = STANDARD_THRUST;
    }

    public MoveAction withThrust(int thrust) {
        this.thrust = thrust;
        return this;
    }

    @Override
    public String toString() {
        return MOVE_ACTION + this.destination.toString() + " " + thrust;
    }
}



/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
class ThrowAction extends BasicAction {

    public static final int STANDARD_POWER = 500;
    public static final String THROW_ACTION = "THROW ";


    public ThrowAction(Position destination) {
        super(destination);
    }

    @Override
    public String toString() {
        return THROW_ACTION + this.destination.toString() + " " + STANDARD_POWER;
    }
}


/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
abstract class GameAction {

    public int newGaugeValue(int gauge) {
        return gauge;
    }
}



/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 * <p>
 * ACCIO id	The target entity is pulled towards the wizard.
 * Magic cost	20
 * Duration	6
 * Spell target	Bludgers, Snaffles
 * Details	For the next 6 turns, a force pulls the entity id
 * towards the wizard. This force decreases in proportion
 * to the square of the distance.
 */
class Accio extends MagicAction {

    private static final String ACCIO_POWER = "ACCIO";

    public Accio(int target) {
        super(target, ACCIO_POWER, 20, 6);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Accio && super.equals(obj);
    }
}


/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 * <p>
 * FLIPENDO id	The target entity is pushed away from the wizard.
 * Magic cost	20
 * Duration	3
 * Spell target	Bludgers, Snaffles, Opponent wizards
 * Details	For the next 3 turns, a force pushes the entity id
 * away from the wizard. This force decreases in proportion
 * to the square of the distance.
 */
class Flipendo extends MagicAction {

    private static final String FLIPENDO_POWER = "FLIPENDO";

    public Flipendo(int target) {
        super(target, FLIPENDO_POWER, 20, 3);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Flipendo && super.equals(obj);
    }
}



/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
abstract class MagicAction extends GameAction{
    protected int target;
    protected String spellPower;
    protected int magicCost;
    protected int duration;

    public MagicAction(int target, String spellPower, int magicCost, int duration) {
        this.target = target;
        this.spellPower = spellPower;
        this.magicCost = magicCost;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return this.spellPower + " " + this.target;
    }

    @Override
    public int newGaugeValue(int gauge) {
        int newGauge = gauge - this.magicCost;
        return newGauge;
    }

    public boolean canSpell(int gauge) {
        return magicCost <= gauge;
    }

    @Override
    public boolean equals(Object obj) {
        return this.target == ((MagicAction) obj).target;
    }

    public void decreaseDuration() {
        this.duration--;
    }

    public boolean stillApplied() {
        return duration >= 0;
    }
}


/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 * <p>
 * OBLIVIATE id	The wizard's team is invisible to the target bludger.
 * Magic cost	5
 * Duration	3
 * Spell target	Bludgers
 * Details	Both of your wizards can no longer be targeted by the Bludger
 * id for the next 3 turns. The Bludger will stop chasing you if it was
 * already targeting one of your wizards.
 */
class Obliviate extends MagicAction {

    private static final String OBLIVIATE_POWER = "OBLIVIATE";

    public Obliviate(int target) {
        super(target, OBLIVIATE_POWER, 5, 9);
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Obliviate && super.equals(obj);
    }

}


/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 * PETRIFICUS id	The target entity is immediately frozen to a standstill.
 * Magic cost	10
 * Duration	1
 * Spell target	Bludgers, Snaffles, Opponent wizards
 * Details	The speed vector of the entity id is set to 0 on the next turn.
 */
class Petrificus extends MagicAction {

    private static final String PETRIFICUS_POWER = "PETRIFICUS";

    public Petrificus(int target) {
        super(target, PETRIFICUS_POWER, 10, 1);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Petrificus && super.equals(obj);
    }
}



/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
class Bludger extends FantasticEntity {

    private static double BLUDGER_MASS = 1;
    private static double BLUDGER_SPEED_VECTOR = 0.9;


    public Bludger(int id, int x, int y, int vx, int vy, int state) {
        super(id, x, y, vx, vy, state);
        this.mass = BLUDGER_MASS;
        this.speedVector = BLUDGER_SPEED_VECTOR;
    }
}



/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class EntityFactory {

    public static FantasticEntity create(String entityType, int entityId, int x, int y, int vx, int vy, int state, Team myTeam, Team otherTeam) {
        switch (entityType) {
            case FantasticEntity.SNAFFLE_TYPE : return new Snaffle(entityId, x, y, vx, vy, state);
            case FantasticEntity.WIZARD_TYPE : return new Wizard(entityId, x, y, vx, vy, state).setWizardTeam(myTeam);
            case FantasticEntity.OPPONENT_WIZARD_TYPE : return new Wizard(entityId, x, y, vx, vy, state).setWizardTeam(otherTeam);
            case FantasticEntity.BLUDGER_TYPE : return new Bludger(entityId, x, y, vx, vy, state);
            default:
                throw new IllegalArgumentException("bad arguments");
        }
    }
}



/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class FantasticEntity {

    public static final String WIZARD_TYPE = "WIZARD";
    public static final String OPPONENT_WIZARD_TYPE = "OPPONENT_WIZARD";
    public static final String SNAFFLE_TYPE = "SNAFFLE";
    public static final String BLUDGER_TYPE = "BLUDGER";

    protected int id;
    public Position position;
    //protected Position nextPosition;
    protected int inputState;
    protected Vector2D vector;
    protected double mass;
    protected double speedVector;

    public void setNearestGoal(Goal nearestGoal) {
        this.nearestGoal = nearestGoal;
    }

    private Goal nearestGoal;

    private MagicAction appliedPower;
    protected int radius;
    
    public FantasticEntity(final int id, final int x, final int y, final int vx, final int vy, final int state) {
        this.id = id;
        this.inputState = state;
        this.position = new Position(x, y);
        this.vector = new Vector2D(vx, vy, this.position);
        this.position.nextPosition(this.vector);
    }

    @Override
    public String toString() {
        return "id=" + id + " inputState=" + inputState + " position: " + position.toString() + " " + vector.toString();
    }
    
    public int distanceFromOther(final Position position) {
        return new Double(this.position.distance(position)).intValue();
    }
    
    public void applySpell(final MagicAction magicAction) {

        this.appliedPower = magicAction;
    }
    
    @Override public boolean equals(final Object obj) {
        return this.id == ((FantasticEntity) obj).id;
    }

    public void nextTurn() {
        if (appliedPower != null) {
            appliedPower.decreaseDuration();
        }
    }

    public boolean alreadyApplied() {
        final boolean alreadyApplied = appliedPower != null && appliedPower.stillApplied();
        return alreadyApplied;
    }
    
    public void update(final int x, final int y, final int vx, final int vy, final int state) {
        this.inputState = state;
        this.position.update(x, y);
        this.vector.update(vx, vy);
        this.position.updateNextPosition(this.vector);
    }

    public boolean hasGoalVectorDirection(Goal goal) {
        return this.vector.hasGoalDirection(goal);
    }

    public boolean hasGoalVectorIntersection(Goal goal) {
        return this.vector.hasGoalVectorIntersection(goal);
    }

    public boolean hasInflationVectorIntersection(Goal goal) {
        return this.vector.hasInflationVectorIntersection(goal);
    }

    public void setVector(Vector2D vector) {
        this.vector = vector;
    }


    public Vector2D getVector() {
        return vector;
    }

    public Flipendo flipendMe() {
        return new Flipendo(this.id);
    }

    public Vector2D addVector(Vector2D wizardFlipendoVector) {
        Vector2D newVector = this.vector.sum(wizardFlipendoVector);
        this.vector = newVector;
        return newVector;
    }

    public Petrificus frozenMe() {
        return new Petrificus(id);
    }

}




/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class Snaffle extends FantasticEntity {
    
    public static final int SNAFFLE_RADIUS = 150;
    private static final double SNAFFLE_MASS = 0.5;
    private static final double SNAFFLE_SPEED_VECTOR = 0.75;
    public static final int MARGE_VALUE = 1000;
    
    private Wizard nearestWizard;
    private int nearestWizardDistance;
    int lastAliveTurn = 0;
    
    Snaffle(final int id, final int x, final int y, final int vx, final int vy, final int state) {
        super(id, x, y, vx, vy, state);
        this.mass = SNAFFLE_MASS;
        this.speedVector = SNAFFLE_SPEED_VECTOR;
        this.radius = SNAFFLE_RADIUS;
    }

    @Override
    public void update(int x, int y, int vx, int vy, int state) {
        super.update(x, y, vx, vy, state);
        lastAliveTurn++;
    }

    public boolean isNotAlive(int turn) {
        return lastAliveTurn < turn;
    }
    
    public void setNearestWizard(final List<Wizard> wizards) {
        int distance = Integer.MAX_VALUE;
        final int vxDif = Integer.MAX_VALUE;
        final int vyDif = Integer.MAX_VALUE;
        int totalDiff = Integer.MAX_VALUE;

        Wizard nearestWizard = null;
        for (final Wizard wizard : wizards) {
            final int distanceFromOther = wizard.distanceFromOther(this.position);

            Vector2D wizardVectorToSnaffle = new Vector2D(this.position.x - wizard.position.x,
                    this.position.y - wizard.position.y, wizard.position);

            //final int newVxDif = wizard.vector.vxDif(wizardVectorToSnaffle);
            //final int newVyDif = wizard.vector.vyDif(wizardVectorToSnaffle);
    
            //final int newTotalDif = (newVxDif + newVyDif) * 2 + distanceFromOther;
            if (distance > distanceFromOther) {
                //totalDiff = newTotalDif;
                distance = distanceFromOther;
                nearestWizard = wizard;
            }
            
            /*if (distance > distanceFromOther) {
                distance = distanceFromOther;
                nearestWizard = wizard;
            }*/
        }
        this.nearestWizardDistance = distance;
        this.nearestWizard = nearestWizard;
    }

    public Wizard returnNearestWizard(final List<Wizard> wizards) {
        int distance = Integer.MAX_VALUE;

        Wizard nearestWizard = null;
        for (final Wizard wizard : wizards) {
            final int distanceFromOther = wizard.distanceFromOther(this.position);

            if (distance > distanceFromOther) {
                distance = distanceFromOther;
                nearestWizard = wizard;
            }
        }

        return nearestWizard;
    }

    public Wizard getNearestWizard() {
        return nearestWizard;
    }

    public int getNearestWizardDistance() {
        return nearestWizardDistance;
    }

    public int upCustomFenceY(final Position upFence) {
        return upFence.y + radius + MARGE_VALUE;
    }
    
    public int downCustomFenceY(final Position downFence) {
        return downFence.y - radius - MARGE_VALUE;
    }
    
    public boolean yPositionIsBetween(final int upCustomFenceY, final int downCustomFenceY) {
        return position.y >= upCustomFenceY && position.y <= downCustomFenceY;
    }
    
    public Position bestGoalDistSameY(final int x) {
        return new Position(x, position.y);
    }
    
    public Position nearestPositionToMakeGoal(final Goal goal) {

        final int upCustomFenceY = upCustomFenceY(goal.getUpFence());
        final int downCustomFenceY = downCustomFenceY(goal.getDownFence());

        if (goal.getCenter().x == 0) {
            if (this.position.x < 2000) {
                return goal.getCenter();
            }
        } else {
            if (this.position.x > 14000) {
                return goal.getCenter();
            }
        }
        if (yPositionIsBetween(upCustomFenceY, downCustomFenceY)) {
            return bestGoalDistSameY(goal.getCenter().x);
        } else {
            if (this.position.y < upCustomFenceY) {
                return new Position(goal.getCenter().x, upCustomFenceY);
            } else if (this.position.y > downCustomFenceY) {
                return new Position(goal.getCenter().x, downCustomFenceY);
            }
        }

        return goal.getCenter();
    }


    public GameAction shouldBeFlipended(Wizard wizard, Team wizardTeam) {
        return FlipendoStrategy.FLIPENDO_STRATEGY.flipendASnaffle(this, wizard, wizardTeam);
    }


    public boolean willBeScoredIn_After(Goal goal, int turn) {
        Position positionAfterNumberOfTurn = positionAfter(turn);
        //System.err.println("positionAfterNumberOfTurn("+id+") : " + positionAfterNumberOfTurn.toString());
        return goal.scoredPosition(positionAfterNumberOfTurn) && hasGoalVectorDirection(goal) && hasGoalVectorIntersection(goal);
    }

    private Position positionAfter(int turn) {
        return this.vector.positionAfter(turn, this.speedVector);
    }


}




/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class Wizard extends FantasticEntity {

    private static final double WIZARD_MASS = 1;
    private static final double WIZARD_SPEED_VECTOR = 0.75;
    private static final int GRABBED_STATE = 1;
    private static final int WIZARD_RADIUS = 400;
    private static final int SAFETY_RADIUS = 1000;
    private static final int ESCAPE_DISTANCE = 400;

    private Team wizardTeam;
    private Snaffle shoudBeFlipendo;
    private WizardState state;
    private List<Snaffle> nearestSnaffles = new ArrayList<>();


    Wizard(final int id, final int x, final int y, final int vx, final int vy, final int state) {
        super(id, x, y, vx, vy, state);
        this.mass = WIZARD_MASS;
        this.speedVector = WIZARD_SPEED_VECTOR;
        this.state = new FlayState();
    }

    Wizard setWizardTeam(final Team wizardTeam) {
        this.wizardTeam = wizardTeam;
        return this;
    }

    private Snaffle nearestSnaffle(final List<Snaffle> snaffleList) {
        int distance = Integer.MAX_VALUE;
        int totalSum = Integer.MIN_VALUE;
        Snaffle nearestSnaffle = null;
        for (final Snaffle current : snaffleList) {
            if (this.equals(current.getNearestWizard())) {
                final int distanceFromCurrentSnaffle = current.getNearestWizardDistance();

               /* Vector2D destinationVector = new Vector2D(current.position.x - this.position.x,
                        current.position.y - this.position.y, this.position);

                Vector2D sumDestinationVector = destinationVector.sum(this.vector);



                final int newVxSum = Math.abs(sumDestinationVector.vx);
                final int newVySum = Math.abs(sumDestinationVector.vy);*/

                //System.err.println(current.toString());
                //System.err.println("newVxSum:" + newVxSum);
                //System.err.println("newVySum:" + newVySum);

                //final int newTotalSum = (newVxSum + newVySum) / distanceFromCurrentSnaffle;

                if (distance > distanceFromCurrentSnaffle) {
                    //totalSum = newTotalSum;
                    distance = distanceFromCurrentSnaffle;
                    nearestSnaffle = current;
                }
            }
        }

        if (snaffleList.size() > 1) {
            snaffleList.remove(nearestSnaffle);
        }

        if (nearestSnaffle == null) {
            nearestSnaffle = snaffleList.get(snaffleList.size() - 1);
            if (snaffleList.size() > 1 && !nearestSnaffle.position.equals(nearestSnaffle.getNearestWizard().position)) {
                snaffleList.remove(snaffleList.size() - 1);
            }
        }

        return nearestSnaffle;
    }

    public GameAction doAction(final List<Snaffle> snaffles, final List<Bludger> bludgers) {

        //System.err.println("doAction for wizard("+this.state.getClass().getSimpleName()+") with id = " + id);
        if (GRABBED_STATE == this.inputState) {
            return this.state.react(this);
        } else {

            GameAction gameAction = this.state.react(this);

            if (gameAction != null) {
                return gameAction;
            }
        }


        /*Bludger bludger = getBludgerInSafetyRadius(bludgers);

        if (bludger != null && !bludger.alreadyApplied()) {
            if (wizardTeam.hasEnoughPoints(15)) {
                return new Obliviate(bludger.id);
            }
        }*/


        Snaffle nearestSnaffle = null;
        /*List<Snaffle> nearestSnaffles = wizardTeam.getGame().getOtherTeam().getGoal().getNearestSnaffles();
        List<Snaffle> nearestSnafflesWizardTeamGoal = wizardTeam.getGoal().getNearestSnaffles();
        if (!wizardTeam.goToOtherGoal
                && !nearestSnaffles.isEmpty()
                && nearestSnaffles.size() >= nearestSnafflesWizardTeamGoal.size()) {
            nearestSnaffle = nearestSnaffle(nearestSnaffles);
            wizardTeam.goToOtherGoal = true;
        } else {
            if (wizardTeam.goToOtherGoal) {
                if (!nearestSnafflesWizardTeamGoal.isEmpty()) {
                    nearestSnaffle = nearestSnaffle(nearestSnafflesWizardTeamGoal);
                } else {
                    nearestSnaffle = nearestSnaffle(snaffles);
                }
            } else {

                nearestSnaffle = nearestSnaffle(snaffles);
            }
        }*/
        nearestSnaffle = nearestSnaffle(snaffles);

        GameAction shouldBeFlipendedAction = nearestSnaffle.shouldBeFlipended(this, this.wizardTeam);

        if (shouldBeFlipendedAction != null) {
            return shouldBeFlipendedAction;
        }

        if (wizardTeam.getGame().accioActivated()) {

            int dist = nearestSnaffle.distanceFromOther(this.position.nextPosition(this.vector));
            Goal goal = wizardTeam.getGame().getOtherTeam().getGoal();

            if (dist < 4000 && dist > 700 /*&& goal.nearestSnaffle(snaffles).equals(nearestSnaffle)*/) {
                Accio accio = new Accio(nearestSnaffle.id);

                if (wizardTeam.hasEnoughPoints(accio)) {
                    return accio;
                } else {
                    /*Wizard otherTeamWizard = nearestSnaffle.returnNearestWizard(wizardTeam.getGame().getOtherTeam().getWizards());

                    if (dist > otherTeamWizard.distanceFromOther(nearestSnaffle.position)) {
                        Petrificus petrificus = new Petrificus(otherTeamWizard.id);

                        if (wizardTeam.hasEnoughPoints(petrificus)) {
                            return petrificus;
                        }
                    }*/
                }
            }
        }

        return new MoveAction(nearestSnaffle.position);
    }

    private Snaffle getSnaffleFromList(List<Snaffle> snaffles, Snaffle shoudBeFlipendo) {

        for (Snaffle snaffle : snaffles) {
            if (snaffle.equals(shoudBeFlipendo)) {
                return snaffle;
            }
        }
        return null;
    }

    private Snaffle grabbedSnaffle(final List<Snaffle> snaffles) {
        for (final Snaffle snaffle : snaffles) {
            if (snaffle.position.equals(this.position)) {
                return snaffle;
            }
        }
        return null;
    }

    private Bludger getBludgerInSafetyRadius(final List<Bludger> bludgers) {
        for (final Bludger bludger : bludgers) {
            if (bludger.distanceFromOther(this.position) < SAFETY_RADIUS) {
                return bludger;
            }
        }
        return null;
    }

    public void setState(WizardState state) {
        this.state = state;
    }

    public boolean hasGrabbedASnaffle() {
        return this.inputState == GRABBED_STATE;
    }

    @Override
    public void update(int x, int y, int vx, int vy, int state) {
        super.update(x, y, vx, vy, state);
        this.state.updateState(this);
    }

    public Snaffle grabbedSnaffle() {
        Snaffle grabbedSnaffle = this.grabbedSnaffle(this.wizardTeam.getGame().getSnaffles());
        this.shoudBeFlipendo = (Snaffle) Game.entitiesTable[grabbedSnaffle.id];

        return this.shoudBeFlipendo;
    }

    public Position getGoalCenter() {
        return wizardTeam.getGoal().getCenter();
    }

    public boolean hasThrowedSnaffle() {
        return this.shoudBeFlipendo != null;
    }

    public boolean throwedSnaffleIsAlive() {
        Snaffle snaffle = (Snaffle) Game.entitiesTable[this.shoudBeFlipendo.id];
        return !this.wizardTeam.getGame().isNotAlive(snaffle);
    }

    public boolean frozenSnaffleIsAlive(Snaffle snaffle) {
        Snaffle initSnaffle = (Snaffle) Game.entitiesTable[snaffle.id];
        return !this.wizardTeam.getGame().isNotAlive(initSnaffle);
    }

    public Vector2D wizardFlipendoVector(Snaffle snaffle) {
        System.err.println("id="+this.id + " " + this.position.getNextPosition().toString());
        return new Vector2D(snaffle.position.getNextPosition().x
                - this.position.getNextPosition().x,
                snaffle.position.getNextPosition().y - this.position.getNextPosition().y,
                this.position.getNextPosition());
    }

    public void initShouldBeFlipended() {
        this.shoudBeFlipendo = null;
    }

    public GameAction throwTheSnaffle() {
        return this.shoudBeFlipendo.shouldBeFlipended(this, this.wizardTeam);
    }

    public Snaffle thereIsASnaffleGonnaBeScored() {
        return wizardTeam.thereIsASnaffleGonnaBeScoredByOtherTeam();
    }

    public void usedSnaffle(Snaffle snaffle) {
        wizardTeam.removeSnaffleFromSnaffleList(snaffle);
    }

    public boolean myTeamHasEnoughPoint(MagicAction magicAction) {
        return wizardTeam.hasEnoughPoints(magicAction);
    }
}


/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class FantasticMap {
    public static final int WIDTH = 16001;
    public static final int HEIGHT = 7501;
    
}




/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class Game {

    public static final FantasticEntity[] entitiesTable = new FantasticEntity[50];

    Team myTeam;

    public Team getOtherTeam() {
        return otherTeam;
    }

    Team otherTeam;
    private List<Snaffle> snaffles;
    private List<Snaffle> snafflesTurn;
    private List<Bludger> bludgers;
    int turn = 0;

    public void setBeginSnaffleNumber(int beginSnaffleNumber) {
        this.beginSnaffleNumber = beginSnaffleNumber;
    }

    int beginSnaffleNumber;

    public Game(Team myTeam, Team otherTeam) {

        this.myTeam = myTeam;
        this.otherTeam = otherTeam;
    }

    public void setSnaffles(List<Snaffle> snaffles) {
        this.snaffles = snaffles;
    }

    public List<Snaffle> getSnaffles() {
        return snaffles;
    }

    public void setBludgers(List<Bludger> bludgers) {
        this.bludgers = bludgers;
    }

    public List<Bludger> getBludgers() {
        return bludgers;
    }

    public void calculateSnaffle() {

        this.myTeam.clearNearestSnaffleList();
        this.otherTeam.clearNearestSnaffleList();

        List<Snaffle> snafflesCopy = new ArrayList<>(getSnaffles());
        this.snafflesTurn = snafflesCopy;

        List<Snaffle> notAliveSnaffles = new ArrayList<>();
        for (Snaffle snaffle : this.snafflesTurn) {
            if (snaffle.isNotAlive(this.turn)) {
                notAliveSnaffles.add(snaffle);
            } else {
                myTeam.matchNearestWizard(snaffle);
                myTeam.matchNearestGoal(snaffle);
            }
        }

        for (Snaffle notAliveSnaffle : notAliveSnaffles) {
            System.err.println("remove ");
            snaffles.remove(notAliveSnaffle);
            this.snafflesTurn.remove(notAliveSnaffle);
        }
    }

    public void endTurn() {
        myTeam.nextTurn();
        for (Bludger bludger : bludgers) {
            bludger.nextTurn();
        }
        turn++;
    }

    public boolean isNotAlive(Snaffle snaffle) {
        return snaffle.isNotAlive(this.turn);
    }

    public boolean pushIt() {
        if (beginSnaffleNumber == 5) {
            if (snaffles.size() == 1) {
                return true;
            }
        } else {
            if (snaffles.size() <= 2) {
                return true;
            }
        }

        return false;
    }

    public List<Snaffle> getSnafflesTurn() {
        return snafflesTurn;
    }

    public boolean accioActivated() {
        return this.snaffles.size() <= 2;
    }
}




/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 * The playing field is a rectangle of 16001x 7501 units.
 * The coordinates X=0, Y=0 represent the pixel at the top-left.
 * 2 goals, one per player, are located on either side of the field.
 * Each goal is composed of two poles whose centers are separated by 4000 units.
 * Each pole has a radius of 300 units.
 * The coordinates of the center of each goal are (X=0, Y=3750) for team 0 and (X=16000, Y=3750) for team 1.
 */
class Goal {

    public static final int Y_CENTER = FantasticMap.HEIGHT / 2;
    public static final int DESTANCE_BETWEN_FENCES = 4000;
    public static final int BOOL_FENCE_RADIUS = 300;
    public static final int SNAFFLE_RADIUS = 150;
    public static final int SCORED_RANGE = 10;
    private final Position center;
    
    private final Position upFence;
    private final Position downFence;

    private List<Snaffle> nearestSnaffles = new ArrayList<>();

    private Line2D goalLine;
    
    public Goal(final int id) {
        int X = 0;
        if (id == 0) {
            X = 1;
        }
        
        final int xValue = X * FantasticMap.WIDTH;
        
        this.center = new Position(xValue, Y_CENTER);
        this.upFence = new Position(xValue, Y_CENTER - (DESTANCE_BETWEN_FENCES / 2 - BOOL_FENCE_RADIUS));
        this.downFence = new Position(xValue, Y_CENTER + (DESTANCE_BETWEN_FENCES / 2 - BOOL_FENCE_RADIUS));

        int upY = this.upFence.y + SNAFFLE_RADIUS + SCORED_RANGE;
        int downY = this.downFence.y - SNAFFLE_RADIUS - SCORED_RANGE;

        this.goalLine = new Line2D.Double(xValue, new Double(upY).doubleValue(),
                xValue, new Double(downY).doubleValue());
    }


    public Position getCenter() {
        return center;
    }
    
    protected int distanceFromEntity(final FantasticEntity entity) {
        return new Double(entity.distanceFromOther(this.center)).intValue();
    }
    
    public Snaffle nearestSnaffle(final List<Snaffle> snaffles) {
        int distance = Integer.MAX_VALUE;
        Snaffle nearestSnaffle = null;
        for (final Snaffle current : snaffles) {
            final int distanceFromCurrentSnaffle = this.distanceFromEntity(current);
            if (distance > distanceFromCurrentSnaffle) {
                distance = distanceFromCurrentSnaffle;
                nearestSnaffle = current;
            }
        }

        return nearestSnaffle;
    }
    
    public Position getUpFence() {
        return upFence;
    }
    
    public Position getDownFence() {
        return downFence;
    }

    public Line2D getGoalLine() {
        return goalLine;
    }

    public boolean scoredPosition(Position position) {
        if (center.x == 0) {
            return position.x < center.x;
        }
        return position.x > center.x;
    }

    public void addNearestSnaffle(Snaffle snaffle) {
        nearestSnaffles.add(snaffle);
    }

    public void clearNearestSnaffleList() {
        nearestSnaffles.clear();
    }

    public List<Snaffle> getNearestSnaffles() {
        return nearestSnaffles;
    }
}




//import static fantasticbits.Game.entitiesTable;

/**
 * Grab Snaffles and try to throw them through the opponent's goal!
 * Move towards a Snaffle and use your team id to determine where you need to throw it.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left

        Team myTeam = new Team(myTeamId);
        Team otherTeam = new Team(myTeamId == 0 ? 1 : 0);

        Game game = new Game(myTeam, otherTeam);
        myTeam.setGame(game);

        //FantasticEntity[] entitiesTable = new FantasticEntity[50];

        boolean firstTime = true;
        List<Wizard> myWizards = new ArrayList<>();
        List<Wizard> otherWizards = new ArrayList<>();
        List<Bludger> bludgers = new ArrayList<>();
        List<Snaffle> snaffles = new ArrayList<>();
        int turn = 0;
        // game loop
        while (true) {



            int entities = in.nextInt(); // number of entities still in game
            for (int i = 0; i < entities; i++) {
                int entityId = in.nextInt(); // entity identifier
                String entityType = in.next(); // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
                int x = in.nextInt(); // position
                int y = in.nextInt(); // position
                int vx = in.nextInt(); // velocity
                int vy = in.nextInt(); // velocity
                int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise

                if (firstTime) {

                    FantasticEntity entity = EntityFactory.create(entityType, entityId, x, y, vx, vy, state, myTeam, otherTeam);
                    Game.entitiesTable[entityId] = entity;
                    switch (entityType) {
                        case FantasticEntity.SNAFFLE_TYPE:
                            snaffles.add((Snaffle) entity);
                            break;
                        case FantasticEntity.WIZARD_TYPE: {
                            myWizards.add((Wizard) entity);
                            //System.err.println("myWizard : " + entity.toString());
                        }
                        break;
                        case FantasticEntity.OPPONENT_WIZARD_TYPE:
                            otherWizards.add((Wizard) entity);
                            break;
                        case FantasticEntity.BLUDGER_TYPE: {
                            bludgers.add((Bludger) entity);
                            //System.err.println("BLUDGER : " + entity.toString());
                        }
                        break;
                        default:
                            throw new IllegalArgumentException("bad Arguments");
                    }
                } else {
                    FantasticEntity fantasticEntity = Game.entitiesTable[entityId];

                    fantasticEntity.update(x, y, vx, vy, state);
                    //if (entityId == 4) {
                        //System.err.println(fantasticEntity.toString());
                    //}
                    //System.err.println("entity("+entityId+"): " + x + " " + y);
                    //System.err.println("entity("+entityId+"): " + "Vx="+vx + " Vy="+vy);
                }


            }

            if (firstTime) {
                myTeam.setWizards(myWizards);
                otherTeam.setWizards(otherWizards);
                game.setBludgers(bludgers);
                game.setSnaffles(snaffles);
                game.setBeginSnaffleNumber(snaffles.size());
            }



            game.calculateSnaffle();
            myTeam.makeActions();
            for (int i = 0; i < 2; i++) {

                String currentAction = game.myTeam.actions()[i].toString();
                System.err.println("action("+i+") : " + currentAction.toString());
                System.out.println(currentAction);
            }

            game.endTurn();
            firstTime = false;

            turn++;
        }
    }
}




class FlayState extends WizardState {

    @Override
    public GameAction react(Wizard wizard) {

        Snaffle snaffle = wizard.thereIsASnaffleGonnaBeScored();

        Petrificus petrificus = null;

        if (snaffle != null) {
            petrificus = snaffle.frozenMe();

            if (wizard.frozenSnaffleIsAlive(snaffle) && !snaffle.alreadyApplied() && wizard.myTeamHasEnoughPoint(petrificus)) {
                snaffle.applySpell(petrificus);
                wizard.usedSnaffle(snaffle);
                return petrificus;
            }
        }



        return null;
    }
}



class GrabbedState extends WizardState {

    @Override
    public GameAction react(Wizard wizard) {
        Snaffle snaffle = wizard.grabbedSnaffle();

        int distanceFromGoal = snaffle.distanceFromOther(wizard.getGoalCenter());
        boolean myTeamHasEnoughPoint = wizard.myTeamHasEnoughPoint(new Flipendo(0));

        if ((distanceFromGoal > 11000) || (distanceFromGoal > (FantasticMap.WIDTH / 2 + 500)
                && myTeamHasEnoughPoint)) {

            int yValue = 0;
            if (snaffle.position.y > (FantasticMap.HEIGHT / 2)) {
                yValue = FantasticMap.HEIGHT;
            }

            if ((yValue == 0 && snaffle.position.y > 1600)
                    || (yValue == FantasticMap.HEIGHT
                    && snaffle.position.y < (FantasticMap.HEIGHT - 1600))) {

                int xValue = ((wizard.getGoalCenter().x * (yValue - snaffle.position.y))
                        + (snaffle.position.x * (yValue - wizard.getGoalCenter().y)))
                        /((yValue - snaffle.position.y) + (yValue - wizard.getGoalCenter().y));


                return new ThrowAction(new Position(xValue, yValue));
            }

        }

        return new ThrowAction(wizard.getGoalCenter());

    }
}



/**
 * Created by Mohamed BELMAHI on 03/12/2016.
 */
class HasThrowedSnaffle extends WizardState{

    @Override
    public GameAction react(Wizard wizard) {

        if (wizard.throwedSnaffleIsAlive()) {
            return wizard.throwTheSnaffle();
        }


        return null;
    }
}




/**
 * Created by Mohamed BELMAHI on 03/12/2016.
 */
class FlipendoStrategy {

    public static FlipendoStrategy FLIPENDO_STRATEGY = new FlipendoStrategy();

    private FlipendoStrategy() {
        super();
    }

    public GameAction flipendASnaffle(Snaffle snaffle, Wizard wizard, Team wizardTeam) {
        final Flipendo flipendoAction = snaffle.flipendMe();
        wizard.initShouldBeFlipended();

        Vector2D wizardFlipendoVector = wizard.wizardFlipendoVector(snaffle);

        Vector2D tempVector = snaffle.getVector();

        snaffle.addVector(wizardFlipendoVector);

        if (wizardTeam.hasEnoughPoints(flipendoAction)
                && snaffle.hasGoalVectorDirection(wizardTeam.getGoal())
                && (snaffle.hasGoalVectorIntersection(wizardTeam.getGoal()) || snaffle.hasInflationVectorIntersection(wizardTeam.getGoal()) /*|| wizardTeam.getGame().pushIt()*/)) {
            return flipendoAction;
        }
        snaffle.setVector(tempVector);

        return null;
    }
}




/**
 * Created by Mohamed BELMAHI on 03/12/2016.
 */
class FrozenStrategy {

    public static FrozenStrategy FROZEN_STRATEGY = new FrozenStrategy();

    private static final int TURN_TO_BE_SCORED = 3;

    public Snaffle SnaffleGonnaBeScoredByTeam(List<Snaffle> snafflesTurn, Goal goal) {
        for (Snaffle snaffle : snafflesTurn) {
            if (snaffle.willBeScoredIn_After(goal, TURN_TO_BE_SCORED) && snaffle.distanceFromOther(goal.getCenter()) > 2000) {
                return snaffle;
            }
        }
        return null;
    }
}


class ThrowStrategy {
}



/**
 * Created by Mohamed BELMAHI on 30/11/2016.
 */
abstract class WizardState {

    public abstract GameAction react(Wizard wizard);

    public void changeState(Wizard wizard) {
        wizard.setState(this);
    }


    public void updateState(Wizard wizard) {
        if (wizard.hasGrabbedASnaffle()) {
            wizard.setState(new GrabbedState());
        } else if (wizard.hasThrowedSnaffle()) {
            wizard.setState(new HasThrowedSnaffle());
        } else {
            wizard.setState(new FlayState());
        }

    }
}




/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class Team {
    private final int id;
    private final Goal goal;
    private List<Wizard> wizards;
    private GameAction[] actions;

    public boolean goToOtherGoal = false;
    private boolean secondPlayer;

    public Game getGame() {
        return game;
    }

    private Game game;
    private int gauge;

    public Team(final int id) {
        this.id = id;
        this.goal = new Goal(id);
        this.gauge = 0;
    }

    public void setWizards(final List<Wizard> wizards) {
        this.wizards = wizards;
    }

    public void makeActions() {
        goToOtherGoal = false;
        secondPlayer = false;

        actions = new GameAction[2];

        for (int i = 0; i < wizards.size(); i++) {
            actions[i] = wizards.get(i).doAction(game.getSnafflesTurn(), game.getBludgers());
            decreaseGauge(actions[i]);
            secondPlayer = true;
        }

    }


    public GameAction[] actions() {
        return actions;
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public Goal getGoal() {
        return goal;
    }

    public void nextTurn() {
        this.gauge++;
    }

    private void decreaseGauge(final GameAction action) {
        this.gauge = action.newGaugeValue(this.gauge);
    }

    public boolean hasEnoughPoints(final MagicAction magicAction) {
        return magicAction.canSpell(this.gauge);
    }

    public boolean hasEnoughPoints(int gauge) {
        return this.gauge > gauge;
    }

    public void matchNearestWizard(final Snaffle snaffle) {
        snaffle.setNearestWizard(this.wizards);
    }

    public boolean nearThenGoal(final FantasticEntity entity, final int dist) {
        return goal.distanceFromEntity(entity) < dist;
    }

    public boolean IsNotAlive(Snaffle shoudBeFlipendo) {
        return this.game.isNotAlive(shoudBeFlipendo);
    }

    public Snaffle thereIsASnaffleGonnaBeScoredByOtherTeam() {

        return FrozenStrategy.FROZEN_STRATEGY.SnaffleGonnaBeScoredByTeam(game.getSnafflesTurn(),
                game.otherTeam.getGoal());
    }

    public void removeSnaffleFromSnaffleList(Snaffle snaffle) {
        if (game.getSnafflesTurn().size() > 1) {
            game.getSnafflesTurn().remove(snaffle);
        }
    }

    public void matchNearestGoal(Snaffle snaffle) {
        int distanceFromMyTeamGoal = snaffle.distanceFromOther(this.goal.getCenter());
        int distanceFromOtherTeamGoal = snaffle.distanceFromOther(this.getGame().otherTeam.goal.getCenter());

        if (distanceFromMyTeamGoal < distanceFromOtherTeamGoal) {
            snaffle.setNearestGoal(this.goal);
            this.goal.addNearestSnaffle(snaffle);
        } else {
            snaffle.setNearestGoal(this.getGame().otherTeam.goal);
            this.getGame().otherTeam.goal.addNearestSnaffle(snaffle);
        }
    }

    public void clearNearestSnaffleList() {
        this.goal.clearNearestSnaffleList();
    }

    public boolean isSecondPlayer() {
        return secondPlayer;
    }

    public List<Wizard> getWizards() {
        return wizards;
    }

}




/**
 * Created by Mohamed BELMAHI on 01/12/2016.
 */
class Line extends Line2D.Double {

    LineEquation lineEquation;

}



/**
 * Created by Mohamed BELMAHI on 01/12/2016.
 */
class LineEquation {
    double m;
    double a;
    double b;

    public LineEquation(Point2D p1, Point2D p2) {

        m = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());

        a = m;
        b = -m * p1.getX() + p1.getY();

    }

    public int getYbyX(double x) {
        return new Double(a * x + b).intValue();
    }

    public int getXbyY(int yValue) {
        return new Double((yValue - b) / a).intValue();
    }
}



/**
 * Created by Mohamed BELMAHI on 27/11/2016.
 */
class Position extends Point {

    protected Position nextPosition;

    public Position(final int x, final int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return this.x + " " + this.y;
    }
    
    public void update(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(final Object obj) {
        final Position otherPosition = (Position) obj;
        return this.x == otherPosition.x && this.y == otherPosition.y;
    }

    public Position nextPosition(Vector2D vector) {
        this.nextPosition = new Position(vector.nextX(this.x), vector.nextY(this.y));
        return this.nextPosition;
    }

    public Position getNextPosition() {
        return nextPosition;
    }

    public void updateNextPosition(Vector2D vector) {
        this.nextPosition.update(vector.nextX(this.x), vector.nextY(this.y));
    }
}




/**
 * Created by Mohamed BELMAHI on 28/11/2016.
 */
class Vector2D {
    public int vx;
    public int vy;
    private final Position root;
    private final Line2D vectorLine;
    
    public Vector2D(final int vx, final int vy, final Position root) {
        this.vx = vx;
        this.vy = vy;
        this.root = root;
        this.vectorLine = new Line2D.Double(root, new Position(root.x + vx, root.y + vy));
    }



    private int getX() {
        return new Double(root.getX()).intValue() + vx;
    }

    private int getY() {
        return new Double(root.getY()).intValue() + vy;
    }
    
    public Position escapePosition(final int distance) {
        return new Position(getX() + distance, getY() + distance);
    }
    
    public Vector2D clone(final Position root) {
        return new Vector2D(this.vx, this.vy, root);
    }

    @Override
    public String toString() {
        return "vx=" + this.vx + " vy=" + this.vy;
    }
    
    public void update(final int vx, final int vy) {
        this.vx = vx;
        this.vy = vy;
    }
    
    public boolean hasGoalDirection(final Goal goal) {
        if (goal.getCenter().x == 0) {
            if (this.vx < 0) {
                return true;
            }
        } else {
            if (this.vx > 0) {
                return true;
            }
        }

        return false;
    }
    
    public boolean hasGoalVectorIntersection(final Goal goal) {
        final LineEquation lineEquation = new LineEquation(this.root, new Position(root.x + vx, root.y + vy));
        
        final int y = lineEquation.getYbyX(goal.getCenter().x);
        System.err.println("y=" + y);
        final Line2D fullLine = new Line2D.Double(this.root.x, this.root.y, goal.getCenter().x, y);
        return fullLine.intersectsLine(goal.getGoalLine());
    }
    
    public int vxDif(final Vector2D vector) {
        return Math.abs(vector.vx - this.vx);
    }
    
    public int vyDif(final Vector2D vector) {
        return Math.abs(vector.vy - this.vy);
    }

    public Vector2D sum(Vector2D otherVector) {
        return new Vector2D(this.vx + otherVector.vx, this.vy + otherVector.vy, this.root);
    }

    public int nextX(int x) {
        return x + vx;
    }

    public int nextY(int y) {
        return y + vy;
    }

    public Position positionAfter(int turn, double speedVector) {

        double speedByTurn = speedByTurn(turn, speedVector);

        double newX = this.root.x + vx * speedByTurn;
        double newY = this.root.y + vy * speedByTurn;

        return new Position(new Double(newX).intValue(), new Double(newY).intValue());
    }

    private double speedByTurn(int turn, double speedVector) {
        if (turn == 0) {
            return 1;
        }

        return Math.pow(speedVector, turn - 1) + speedByTurn(turn - 1, speedVector);
    }

    public boolean hasInflationVectorIntersection(Goal goal) {

        int yValue = 0;
        if (this.vy > 0) {
            yValue = FantasticMap.HEIGHT;
        }

        final LineEquation lineEquation = new LineEquation(this.root, new Position(root.x + vx, root.y + vy));

        final int xValue = lineEquation.getXbyY(yValue);

        Vector2D inflationVector = new Vector2D(vx, -vy, new Position(xValue, yValue));

        return inflationVector.hasGoalVectorIntersection(goal);
    }
}