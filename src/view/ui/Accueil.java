package view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import controller.JeuControleur;
import model.jeu.Jeu;
import view.ui.composant.Fenetre;
/**Affiche un frame d'accueil*/
public class Accueil implements Runnable{

	private static final long serialVersionUID = 1L;
	/**le frame fondamental*/
	private JFrame frame;
	/**un cadre qui contient tous les elements*/
	private JPanel window;
	/**l'image au fond*/
	private JPanel bg;
	/**l'image de logo*/
	private JLabel logo;
	/**le button pour commencer*/
	private JButton commencer;
	/**le button pour quitter le jeu*/
	private JButton quitter;
	/**le controleur qui fait le lien entre le jeu et cette interface graphique*/
	private JeuControleur jeuControleur;
	/**le jeu pour fournir des informations et pour effectuer des modifications*/
	private Jeu j;
	/**le thread de cette interface graphique*/
	private Thread thread;

	/**constructeur de Accueil 
	 * initialise le frame en posant plusieurs sous-cadre dans un cadre window
	 * un sous-cadre est un ensemble des composants elementaires
	 * @param jeu la reference de jeu*/
	public Accueil(Jeu jeu) {
		thread= new Thread(this);
		thread.start();
		j =jeu;
		jeuControleur = new JeuControleur(j);
		frame = new JFrame("Accueil");
		commencer = new JButton("Commencer");
		commencer.addActionListener(new CommencerListener());
		commencer.setPreferredSize(new Dimension(150, 30));
		quitter = new JButton("Quitter");
		quitter.setPreferredSize(new Dimension(150, 30));
		window = new JPanel();
		bg = new JPanel();


		frame.setSize(800, 600);
		frame.setBackground(new Color(Integer.decode("#1f8387")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		ImageIcon image = new ImageIcon("sources/fond.jpg");
		ImageIcon imagLogo = new ImageIcon("sources/logo.png");
		logo = new JLabel(imagLogo);

		// background
		JLabel bgimage = new JLabel(image); // background image
		bg.setBounds(0, -5, image.getIconWidth(), image.getIconHeight());
		bg.add(bgimage);
		JLayeredPane layeredPane = new JLayeredPane();
		window.setBounds(0, -5, image.getIconWidth(), image.getIconHeight());
		window.setLayout(new BorderLayout(5, 5)); // window.setLayout(new GridLayout(3, 5, 5, 5));
		window.setOpaque(false);
		JPanel something = new JPanel();
		something.setOpaque(false);
		something.setPreferredSize(new Dimension(600, 150));
		something.add(BorderLayout.WEST, quitter);
		something.add(BorderLayout.EAST, commencer);

		window.add(logo, BorderLayout.CENTER);
		window.add(something, BorderLayout.SOUTH);

		layeredPane.add(window, JLayeredPane.MODAL_LAYER);
		layeredPane.add(bg, JLayeredPane.DEFAULT_LAYER);

		frame.setLayeredPane(layeredPane);
		frame.setLocationRelativeTo(frame.getOwner());
		frame.setResizable(false);

	}

	/**classe interne qui implemente ActionListener qui commence le jeu en appelant le controleur pour y reagir*/
	class CommencerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			jeuControleur.commencer();
			//jeuControleur.fermer(frame);
			frame.dispose();
		}

	}

	@Override
	public void run() {
		//frame.repaint();

	}


}
