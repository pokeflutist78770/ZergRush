package views;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.Towers.Range;

/**
 * This class represents the Button Towers on the Command Panel.
 * Each tower button has a set of attributes - image, name, range - to represent a Tower.
 * 
 * @author Angel Aguayo
 *
 */

public class TowerButton extends Button {
	private Range range;
	private ImageView image;
	private String name;
	private double cost;
	
	/**
	* Constructor for TowerButton. Create a new Tower Button to represent
	* a specific Tower. Tower attributes include name, image and range.
	* 
	* @param buttonText, buttonImage, name, range
	*          - buttonText: String to text on Button
	*          - buttonImage: Image on Button
	*          - name: String to represent type of tower
	*          - range: Range to represent tower damage radius
	* 
	* @return None
	*/
	public TowerButton(String buttonText, ImageView buttonImage, String name, Range range,
			           double cashThreshold) {
		super(buttonText, buttonImage);
		this.name=name;
		this.image=buttonImage;
		this.range=range;
		cost=cashThreshold;
	}
	
	// Getter - Tower Range
	public Range getRange() {
		return range;
	}
	
	// Getter - Tower Image
	public ImageView getImage() {
		return image;
	}
	
	// Getter - Tower Name
	public String getName() {
		return name;
	}
	
	public double getCost() {
		return cost;
	}
	
	public boolean canBeBought(double cash) {
		return cash>=cost;
	}
}
