package views;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.Towers.Range;

public class TowerButton extends Button {
	private Range range;
	private ImageView image;
	private String name;
	
	public TowerButton(String buttonText, ImageView buttonImage, String name, Range range) {
		super(buttonText, buttonImage);
		this.name=name;
		this.image=buttonImage;
		/*image.setFitHeight(50);
		image.setFitWidth(50);
		image.setPreserveRatio(true); */
		this.range=range;
	}
	
	public Range getRange() {
		return range;
	}
	
	public ImageView getImage() {
		return image;
	}
	
	public String getName() {
		return name;
	}
}
