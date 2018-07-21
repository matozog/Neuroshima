package models;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCardLoader {
	
	private static Image imgBerserker = null;
	private static Image imgMachineGun = null;
	private static Image imgSoldier = null;
	private static Image imgBattle = null;
	
	public ImageCardLoader(){
		try {
			imgBerserker = ImageIO.read(getClass().getResource("/gui/images/cardBerserker2.png"));
			imgMachineGun = ImageIO.read(getClass().getResource("/gui/images/cardMachineGun2.png"));
			imgSoldier = ImageIO.read(getClass().getResource("/gui/images/cardSoldier2.png"));
			imgBattle = ImageIO.read(getClass().getResource("/gui/images/cardBattle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public Image getImageBerserker() {
		return imgBerserker;
	}
	
	public Image getImageMachineGun() {
		return imgMachineGun;
	}
	
	public Image getImageSoldier() {
		return imgSoldier;
	}
	
	public Image getImageBattle() {
		return imgBattle;
	}
}
