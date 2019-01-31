package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.Vector;
import java.util.Random;
import java.util.Vector;
import controller.ControllerMain;
import javafx.scene.image.Image;

/**
 * Mob is an abstract class modeling an active mob on the map. 
 * Each mob has unique attributes, animations, and audio clips.
 * 
 * @author Ben Walters
 *
 */
public abstract class Mob implements Serializable {
  
  private static Random rng = new Random();
  
  protected TowerGame theGame;
  
  public static int IDNumber = 0;

  private int movementPerturbation = 2;

  // Movement related fields
  private Point currentLocation;

  private Point targetLocation;
  private Vector<Point> movementPath;
  private int pathIndex;
  private int attackTime;
  private Player targetPlayer;

  private double radius;
  private ArmorAttribute armor;
  private AttackAttribute attack;
  public double hp;
  private SpeedAttribute speed;
  private Vector<ResistanceAttribute> resistances;

  // For animation
  private double sx;
  private double sy;
  private double sw;
  private double sh;
  private double delX;
  private double delY;
  private int stepCount;
  private int animationSteps;
 
  private double cashPayout;
  
  // For audio
  private String deathSound;

  // String data of the mob.
  private String name;
  private String imageFilePath;

  private boolean isDank;

  /**
   * Constructor for Mobs
   * 
   * @param movementPath
   *          series of points representing a mob's movement across a path.
   * @param radius
   *          - the maximum distance a mob can move in a given step
   * @param armor
   *          - ArmorAttribute of the mob
   * @param attack
   *          - AttackAttribute of the mob
   * @param defense
   *          - DefenseAttribute of the mob
   * @param speed
   *          - SpeedAttribute of the mob
   * @param resistances
   *          - ResistanceAttribute of the mob
   * @param name
   *          - name of the mob
   * @param imageFP
   *          - string path to the sprite sheet image file
   * @param deathSound
   *          - string referencing the audioclip to be played when a unit dies
   * @param sx
   *          - x position of first mob image on sprite sheet
   * @param sy
   *          - y position of first mob image on sprite sheet
   * @param sw  
   *          - width of a given frame on the sprite sheet
   * @param sh
   *          - height of a given frame on the sprite sheet
   * @param delX
   *          - distance to move in order to get the next horizontal frame on the
   *          sprite sheet
   * @param delY
   *          - distance to move in order to get the next vertical frame on the
   *          sprite sheet
   * @param animationSteps
   *          - the number of frames representing a cycle on the sprite sheet
   */
  public Mob(Vector<Point> movementPath, double radius, ArmorAttribute armor, 
		  AttackAttribute attack,
      DefenseAttribute defense, SpeedAttribute speed, Vector<ResistanceAttribute> resistances, String name,
      String imageFP, String deathSound, double sx, double sy, double sw, double sh, double delX, double delY,
      int animationSteps, double cash, TowerGame game, boolean isDank) {

    // Animation Attributes
    initializeAnimationAttributes(sx, sy, sw, sh, delX, delY, animationSteps, imageFP);

    // Sound Attributes
    initializeSoundAttributes(deathSound);

    // Initialize Attributes
    initializeOtherAttributes(movementPath, radius, cash, armor, attack, defense, speed, 
    		                  resistances, name, game, isDank);
  }
  

  // This method is an encapsulation of all the non-media business of the constructor.
  private void initializeOtherAttributes(Vector<Point> movementPath, double radius, double cash,
		  ArmorAttribute armor,
      AttackAttribute attack, DefenseAttribute defense, SpeedAttribute speed, Vector<ResistanceAttribute> resistances,
      String name, TowerGame game, boolean isDank) {
       
    this.movementPath = movementPath;  
    this.pathIndex = 0;
    this.currentLocation = perturbPoint(movementPath.get(0));
    this.pathIndex++;
    this.targetLocation = movementPath.get(pathIndex);
    this.pathIndex++;
    
    this.isDank=isDank;
    
    this.cashPayout=cash;
    this.radius = radius;
    this.armor = armor;
    this.attack = attack;
    this.speed = speed;
    this.resistances = resistances;
    this.name = name;

    attackTime = 0;       
    this.theGame = game;
    targetPlayer = game.getPlayer();
    

    this.hp = new Double(defense.getDefense());
    hp += theGame.getKillCount() / 2;

  }

  // This method is an encapsulation of all audio logic of the constructor.
  private void initializeSoundAttributes(String deathSound) {
    this.deathSound = deathSound;
  }

  // This method is an encapsulation of all visual logic of the constructor.
  private void initializeAnimationAttributes(double sx, double sy, double sw, double sh, double delX, double delY,
      int animationSteps, String imageFP) {
    this.stepCount = 0;
    this.sx = sx;
    this.sy = sy;
    this.sw = sw;
    this.sh = sh;
    this.delX = delX;
    this.delY = delY;
    this.animationSteps = animationSteps;
    this.imageFilePath = imageFP;
  }

  /**
   * Updates the game state of the mob.
   * If the mob has reached where it was headed, change its target location to the next one.
   * Otherwise, take a step toward its target.
   * If the mob has reached the end of its path, then updateTarget makes the mob attack.
   */
  public void update() {
    // reached the next place, need to change direction
    if (reachedTarget()) {
      updateTarget();
    }
    // move closer to target location
    else {
      takeStep();
    }
  }
  
  /**
   * Tells if the mob is ready for cleanup.
   * @return True, if the mob is ready to be removed from the game. False, if otherwise.
   */
  public boolean isDone() {
    return hp <= 0;
  }
  
  /**
   * This method randomly perturbs a point it was fed. 
   * The use of this is to allow mobs to follow fuzzy paths.
   * In particular, a given mob doesn't follow the path given to it.
   * Rather, if you perturb all the vertices of the path given to the mob, 
   * then the mob walks linearly along this perturbed path.
   * 
   * In practice, what happens, is that each point is perturbed on an "as need" basis.
   * A consequence of this: if a mob were to walk backward along the path, 
   *   it wouldn't actually walk the same linear path.
   * @param inPoint The point to perturb
   * @return The perturbed point.
   */
  private Point perturbPoint(Point inPoint) {
    return new Point((int) (inPoint.getX()
        + (ControllerMain.TILE_SIZE * (rng.nextInt(movementPerturbation * 100) / 100.0 - 1))),
        (int) (inPoint.getY() + (ControllerMain.TILE_SIZE
            * (rng.nextInt(movementPerturbation * 100) / 100.0 - 1))));
  }

  /*
   * Moves this mob a step toward its target. This method assumes that the mob
   * does not move further than its radius in a single step.
   */
  protected void takeStep() {
    double oldX = currentLocation.getX();
    double oldY = currentLocation.getY();

    double spd = this.speed.getSpeed();
    Vector<Double> unitV = getDirectionVector();

    double newX = oldX + spd * unitV.get(0);
    double newY = oldY + spd * unitV.get(1);
    currentLocation.setLocation(newX, newY);
  }

  

  /*
   * Updates the mob to have the next target point, if there is one. If the mob arrived
   * at the End-Zone, then it calls the cleanup method.
   */
  private void updateTarget() {
    if (pathIndex < movementPath.size()) {
      targetLocation = perturbPoint(movementPath.get(pathIndex));
      pathIndex++;
    } else {
      cleanupMobEndZone();
    }
  }

  /*
   * cleanupMobEndZone handles when a mob has reached end zone Parameters: None
   * Returns: None
   */
  private void cleanupMobEndZone() {
    attackTime++;

    if (attackTime % 60 == 0) {
      attack(targetPlayer);
    }
  }

  /*
   * Determines if this mob has reached its target yet. Takes into account the
   * radius of the mob.
   * 
   * @return True, if the mob has reached its target. False, otherwise.
   */
  private boolean reachedTarget() {
    return Metric.closeEnough(currentLocation, targetLocation, radius);
    
  }

  /**
   * takeDamage calculates and subtracts the damage from a Projectile object
   * Parameters: damage: base damage to be taken element: elemental multiplier for
   * the damage Returns: None
   */
  public void takeDamage(double damage, ElementalAttribute element) {
    double newDamage = calculateNewDamage(damage, element);

    if (newDamage >= hp) {
      hp = -1; // in case of some weird random bugs with oveflow, or underflow in this case
      /*
       * //ControllerMain.isPlaying=false;
       * 
       * Platform.runLater(() -> { //This code will be moved to when a player reaches
       * a set amount of waves, //but for the demo this will suffice
       * ControllerMain.currentView.setEffect(new GaussianBlur());
       * 
       * VBox pauseRoot = new VBox(5); pauseRoot.getChildren().add(new
       * Label("You win!"));
       * pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
       * pauseRoot.setAlignment(Pos.CENTER); pauseRoot.setPadding(new Insets(20));
       * 
       * Button resume = new Button("Main Menu"); pauseRoot.getChildren().add(resume);
       * 
       * Stage popupStage = new Stage(StageStyle.TRANSPARENT);
       * popupStage.initOwner(ControllerMain.getStage());
       * popupStage.initModality(Modality.APPLICATION_MODAL); popupStage.setScene(new
       * Scene(pauseRoot, Color.TRANSPARENT));
       * 
       * resume.setOnAction(event -> { ControllerMain.currentView.setEffect(null);
       * ControllerMain.resetMainMenu(); popupStage.hide(); });
       * 
       * popupStage.show(); });
       */
    } else {
      hp -= newDamage;
    }
      


    // cue animation of stuff for getting hurt
  }

  /**
   * Attack attacks the main player Parameters: player - current player Returns:
   * None
   */
  public void attack(Player player) {
    double damage = this.attack.getAttack();
    player.takeDamage(damage);
  }

  /*
   * calculateNewDamage calculates a new damage based on modifieers Parameters:
   * damage: base damage element: element attribute Returns: double representing
   * the new damage
   */
  private double calculateNewDamage(double damage, ElementalAttribute element) {
    double newDamage = damage * element.getElementalMultiplier();

    for (ResistanceAttribute resistance : ResistanceAttribute.values()) {
      if (resistance.name().equals(element.name())) {
        newDamage *= (1 - resistance.getResistance());
      }
    }

    newDamage *= (1 - armor.getArmor());

    return newDamage;
  }

  /*----------    Getters/Setters     -------------*/

  
  /**
   * A getter method
   * 
   * @return The current X-coordinate
   */
  public double getX() {
    return currentLocation.getX();
  }

  /**
   * A getter method
   * 
   * @return The current Y-coordinate
   */
  public double getY() {
    return currentLocation.getY();
  }

  // getter for the direction vector
  public Vector<Double> getDirectionVector() {
    return Metric.getDirectionVector(currentLocation, targetLocation);
  }

  // direction angle for the mob
  public double getDirectionAngle() {
    return Metric.getDirectionAngle(currentLocation, targetLocation);
  }
  
  public double getRadius() {
    return radius;
  }

  public Point getCurrentLocation() {
    return currentLocation;
  }

  public String getImageFilePath() {
    return imageFilePath;
  }

  public void setImageFilePath(String imageFilePath) {
    this.imageFilePath = imageFilePath;
  }

  public Image getImage() {
    return ControllerMain.getGraphic(this.getImageFilePath());
  }

  public double getSX() {
    return this.sx;
  }

  public double getSY() {
    return this.sy;
  }

  public double getSW() {
    return this.sw;
  }

  public double getSH() {
    return this.sh;
  }

  public double getDelX() {
    return this.delX;
  }

  public double getDelY() {
    return this.delY;
  }

  public int getAnimationSteps() {
    return this.animationSteps;
  }

  public int getStepCount() {
    return this.stepCount;
  }

  public void step() {
    this.stepCount++;
  }

  public String getName() { 
    return name;
  }
  
  public double getCashPayout() {
	  return cashPayout*(4-theGame.getMap().getWaveRatio());
  }

  public void setSpeed(SpeedAttribute s) {
	  this.speed = s;
  }

  public SpeedAttribute getSpeed() {
	return this.speed;
  }

  public String getDeathSoundStr() {
	if(isDank) {
		return "dankDeath";
	}
    return deathSound;
  }
    
  public double getAttack() {
	return attack.getAttack();  
  }
  
  public double getHP() {
	  return hp;
  }
}
