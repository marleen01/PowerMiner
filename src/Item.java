import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {
	
	private BufferedImage itemImage;
	
	public Item(String itemDirectoryPath, String itemName) throws IOException {
		initializeImage(itemDirectoryPath, itemName);
	}
	
	private void initializeImage(String itemDirectoryPath, String itemName) throws IOException {
		File itemImageFile = new File(joinPathWithFileName(itemDirectoryPath, itemName));
		this.itemImage = ImageIO.read(itemImageFile);
	}
	
	private String joinPathWithFileName(String itemDirectoryPath, String itemName) {
		return (itemDirectoryPath + itemName);
	}

	public boolean itemMatchesImage(BufferedImage itemImageToCompare) {
		if (imagesAreTheSameSize(itemImageToCompare)) {
			return imagesMatch(itemImageToCompare);
		}
		return false;
	}
	
	private boolean imagesAreTheSameSize(BufferedImage itemImageToCompare) {
		return (itemImage.getWidth() == itemImageToCompare.getWidth()) && (itemImage.getHeight() == itemImageToCompare.getHeight());
	}
	
	private boolean imagesMatch(BufferedImage itemImageToCompare) {
		return false;
	}
	
	private boolean imagesMatch(BufferedImage itemImageToCompare, int pixelTolerance) {
		int matchingPixel = 0;
		for (int row = 0; row < itemImageToCompare.getWidth(); row++) {
			for (int col = 0; col < itemImageToCompare.getHeight(); col++) {
				if (pixelsAreWithinRGBTolerance(itemImage.getRGB(row, col), itemImageToCompare.getRGB(row, col))) {
					matchingPixel++;
				}
			}
		}
		return true;
	}
	
	private boolean pixelsAreWithinRGBTolerance(int rgb1, int rgb2) {
		int[] colors1 = getRGBValuesFromPixel(rgb1);
		int[] colors2 = getRGBValuesFromPixel(rgb2);
		for (int i = 0; i < 3; i++) {
			if (Math.abs(colors1[i] - colors2[i]) > 5) {
				return false;
			}
		}
		return true;
	}
	
	private int[] getRGBValuesFromPixel(int pixel) {
		int[] colors = {(pixel)&0xFF, (pixel>>8)&0xFF, (pixel>>16)&0xFF, (pixel>>24)&0xFF};
		return colors;
	}	
}
