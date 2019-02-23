package view.ui.composant;

import java.awt.Color;

import javax.swing.JFrame;
/**Classe qui definit le frame fontamental pour l'interface graphique*/
public class Fenetre extends JFrame {
	private static final long serialVersionUID = 1L;
	/**contructeur de Fenetre qui initilalise le titre,la taille(800*600) et la couleur au fond
	 * @param title le title du fenetre*/
	public Fenetre(String title) {
		this.setTitle(title);
		this.setSize(800, 600);
		this.setBackground(Color.blue);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
