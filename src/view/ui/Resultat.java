package view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.jeu.Jeu;
import model.joueur.Joueur;
/**Affiche le resultat dans le frame*/
public class Resultat implements Observer {
	/**le frame fondamental*/
	private JFrame frame;
	/**un cadre qui contient tous les elements*/
	private JPanel window;
	/**un button pour quitter*/
	private JButton quitter;
	/**l'image au fond*/
	private JPanel bg;
	/**un sous-cadre au centre*/
	private JPanel windowCenter;
	/**un sous-cadre au nord*/
	private JPanel windowNorth;
	/**un sous-cadre au sud*/
	private JPanel windowSouth;
	/**un sous-cadre a l'est*/
	private JPanel windowEast;
	/**un sous-cadre a l'ouest*/
	private JPanel windowWest;
	
	/**
	 * constructeur de Resultat 
	 * @param j une reference de jeu*/
	public Resultat(Jeu j) {
		initialiser();
		windowCenter.setLayout(new BoxLayout(windowCenter,BoxLayout.Y_AXIS));
		if (Jeu.getMethodeCompte() == 0) {
			windowCenter.add(new JLabel("Compte negatif"));
			Iterator<Joueur> it = j.getJoueurs().iterator();
			while (it.hasNext()) {
				Joueur jo = it.next();
				JLabel res = new JLabel(jo.toString() + "  :  " + jo.calculerPoint());
				windowCenter.add(res);
			}
		} else {
			windowCenter.add(new JLabel("Compte positif"));
			j.getJoueursGagne().get(0).setPoint(50);
			j.getJoueursGagne().get(1).setPoint(20);
			j.getJoueursGagne().get(2).setPoint(10);
			
			Iterator<Joueur> ijg = j.getJoueursGagne().iterator();
			while (ijg.hasNext()) {
				Joueur jo = ijg.next();
				JLabel res = new JLabel(jo.toString() + "  :  " + jo.calculerPoint());
				windowCenter.add(res);
			}
			Iterator<Joueur> it = j.getJoueurs().iterator();
			while (it.hasNext()) {
				Joueur jo = it.next();
				JLabel res = new JLabel(jo.toString() + "  :  " + jo.calculerPoint());
				windowCenter.add(res);
			}
			
			
		}
		
		
	}

	/**methode permettant d'initialiser le frame en ajoutant l'image au fond et les composants dans les sous-cadres*/
	public void initialiser() {
		frame = new JFrame("Resultat");
		window = new JPanel();
		bg = new JPanel();
		quitter = new JButton("Quitter");
		quitter.addActionListener(new QuitterListener());
		/*
		 * commencer.addActionListener(this); quitter.addActionListener(this);
		 */

		frame.setSize(800, 600);
		frame.setBackground(new Color(Integer.decode("#6a0d77")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		ImageIcon image = new ImageIcon("sources/fond.jpg");
		/*
		 * ImageIcon imagLogo = new ImageIcon("sources/logo.png"); logo = new
		 * JLabel(imagLogo);
		 */

		// background
		JLabel bgimage = new JLabel(image); // background image
		bg.setBounds(0, -5, image.getIconWidth(), image.getIconHeight());
		bg.add(bgimage);
		JLayeredPane layeredPane = new JLayeredPane();
		window.setBounds(0, -5, image.getIconWidth(), image.getIconHeight());
		window.setLayout(new BorderLayout(5, 5)); // window.setLayout(new GridLayout(3, 5, 5, 5));
		window.setOpaque(false);

		windowCenter = new JPanel();
		windowCenter.setOpaque(false);
		window.setPreferredSize(new Dimension(200, 200));
		windowCenter.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		windowNorth = new JPanel();
		windowNorth.setOpaque(false);
		windowNorth.setPreferredSize(new Dimension(800, 200));
		windowNorth.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		windowSouth = new JPanel();
		windowSouth.setOpaque(false);
		windowSouth.setPreferredSize(new Dimension(800, 200));
		windowSouth.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		windowWest = new JPanel();
		windowWest.setOpaque(false);
		windowWest.setPreferredSize(new Dimension(100, 200));
		windowWest.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		windowEast = new JPanel();
		windowEast.setOpaque(false);
		windowEast.setPreferredSize(new Dimension(100, 200));
		windowEast.setBorder(BorderFactory.createLineBorder(Color.white, 3));

		window.add(windowNorth, BorderLayout.NORTH);
		window.add(windowSouth, BorderLayout.SOUTH);
		window.add(windowEast, BorderLayout.EAST);
		window.add(windowWest, BorderLayout.WEST);

		window.add(windowCenter, BorderLayout.CENTER);
		layeredPane.add(window, JLayeredPane.MODAL_LAYER);
		layeredPane.add(bg, JLayeredPane.DEFAULT_LAYER);

		frame.setLayeredPane(layeredPane);
		frame.setLocationRelativeTo(frame.getOwner());
		frame.setResizable(false);
		
		windowSouth.add(quitter);

	}
	
	/**Classe interne qui implemente Actionlistener repond a l'appuie de button Quitter
	 * @see ActionListener*/
	class QuitterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
