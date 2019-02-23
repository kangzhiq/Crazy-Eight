package view.ui.composant;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
/**Classe qui definit un button de l'interface graphique*/
public class CarteButton extends JButton {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	/**definit la hauteur du button*/
	private int buttonHeight;
	/**definit la largeur*/
	private int buttonWidth;
	/**un tableau des puxels*/
	private int[] pixels;

	/**@param id id de la carte correspondante*/
	public CarteButton(String id) {
		this.setActionCommand(id);
		image = loadImage("sources/" + id + ".gif");

		buttonWidth = image.getWidth();
		buttonHeight = image.getHeight();

		pixels = new int[buttonWidth * buttonHeight];
		PixelGrabber pg = new PixelGrabber(image, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
		try {
			pg.grabPixels();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setOpaque(false);
		this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
	}

	/**Constructeur de CarteButton
	 * @param id id de la carte correspondante
	 * @param candidate boolean qui indique si la carte est une carte candidate de joueur*/
	public CarteButton(String id, boolean candidate) {
		this.setActionCommand(id);
		image = loadImage("sources/" + id + ".gif");
		buttonWidth = image.getWidth();
		buttonHeight = image.getHeight();

		pixels = new int[buttonWidth * buttonHeight];
		PixelGrabber pg = new PixelGrabber(image, 0, 0, buttonWidth, buttonHeight, pixels, 0, buttonWidth);
		try {
			pg.grabPixels();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setOpaque(false);

		this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

		if (!candidate) {
			 float[] scales = {0.5f,0.5f,0.5f,0.5f };
             float[] offsets = new float[4];
             RescaleOp rop = new RescaleOp(scales, offsets, null);
             rop.filter(image,image);
		}
	}

	/**introduise l'image dans le repertoire sources selon le nom de fichier
	 * @param filename le nom du fichier
	 * @return l'image de la source*/
	public BufferedImage loadImage(String filename) {
		File file = new File(filename);

		if (!file.exists())
			return null;

		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paintBorder(Graphics g) {
		
	}
	

	@Override
	public boolean contains(int x, int y) {
		if (!super.contains(x, y))
			return false;
		int alpha = (pixels[(buttonWidth * y + x)] >> 24) & 0xff;
		repaint();
		if (alpha == 0) {
			return false;
		} else {
			return true;
		}
	}



	

	

}
