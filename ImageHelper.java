import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHelper {
	// Scales buffered images
	public static BufferedImage scaleImage(BufferedImage img, int newWidth, int newHeight) {
		Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    return dimg;
	}	
	
	// Flips images
	public static BufferedImage flipImage(BufferedImage img) {
		
		/*
		Graphics2D g2d = img.createGraphics();
		g2d.drawImage(img, 0 - img.getWidth(), 0, -img.getWidth(), img.getHeight(), null);
		g2d.dispose();
		*/
		
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img = op.filter(img, null);

		return img;
	}
	
	// Reads in images
 	public static BufferedImage createImage(String loc) {
 		BufferedImage bufferedImage;
 		try {
 			bufferedImage = ImageIO.read(new File(loc));
 			return bufferedImage;
 		} catch(IOException e) {
 			e.printStackTrace();
 		}
 		return null;
	}
}
