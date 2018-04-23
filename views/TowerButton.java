package views;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.Towers.Range;

public class TowerButton extends Button {
	private Range range;
	private ImageView image;
	
	public TowerButton(String buttonText, ImageView buttonImage, Range range) {
		super(buttonText, buttonImage);
		this.image=buttonImage;
		this.range=range;
	}
	
	public Range getRange() {
		return range;
	}
	
	public ImageView getImage() {
		return image;
	}
}
