package model.Mobs;

import java.util.List;

public abstract class Mob {
	
	private Speed speed;
	private DefenseTypes defense; // i.e. hp
	private ArmorType armor;
	private List<Resistance> resistances;

	private String imageFilePath;

}
