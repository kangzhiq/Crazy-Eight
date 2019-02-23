package view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.EffetControleur;
import controller.JeuControleur;
import model.carte.Carte;
import model.effet.Effet;
import model.effet.FairePiocher;
import model.jeu.Jeu;
import view.ui.composant.CarteButton;
/**affiche l'interface gaphique principale sur laquelle le joueur joue ses cartes avec les joueurs virtuels*/
public class Game implements Observer {

	/**le frame fondamental*/
	private JFrame frame;
	/**un cadre qui contient tous les elements*/
	private JPanel window;
	/**l'image au fond*/
	private JPanel bg;
	/**button pour piocher une carte*/
	private JButton piocher;
	/**une liste des panel qui representent les joueurs virtuels*/
	private JPanel[] joueurs;
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
	/**un controleur de jeu qui fait le lien entre l'interface graphique et le jeu
	 * @see JeuControleur*/
	private JeuControleur jeuControleur;
	/** un controleur d'effet qui fait le lien entre cette l'interface graphique et l'effet*/
	private EffetControleur effetControleur;
	/**un entier qui stocke le nombre de joueurs*/
	private int nbJoueurs;
	/**un JLabel qui represente la carte actuelle*/
	private JLabel carteActuelle;
	/**un sous-cadre qui represente la partie de joueur physique*/
	private JPanel carteMainPanel;


	/**@param j le jeu pour fournir des informations et pour effectuer des modifications*/
	public Game(Jeu j) {
		initialiser(j);

	}

	/**Methode pour initialiser le frame
	 * Ce frame est compose par plusieurs sous-cadres qui represente une partie de jeu, par exemple les jouerus virtuels, le joueur physique
	 * Chaque sous-cadre est compose par les composants elementaires comme JLabel, JButton et CarteButton que nous avons defini
	 * @see CarteButton
	 * @param j la reference de jeu*/
	public void initialiser(Jeu j) {
		jeuControleur = new JeuControleur(j);
		effetControleur = new EffetControleur(j);
		frame = new JFrame("Game");
		window = new JPanel();

		bg = new JPanel();

		frame.setSize(800, 600);
		frame.setBackground(new Color(Integer.decode("#1f8387")));
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

		this.listerJoueurV(j);
		ImageIcon icoCartes = new ImageIcon("sources/cards.png");
		JLabel cartesImage = new JLabel(icoCartes);
		windowCenter.setLayout(new FlowLayout());
		windowCenter.add(cartesImage);

		JPanel vide = new JPanel();
		vide.setOpaque(false);
		vide.setPreferredSize(new Dimension(100, 100));
		windowCenter.add(vide);
		// il faut choisir la carte correspondante
		ImageIcon icoCarteActuelle = new ImageIcon("sources/" + j.getCarteActuelle().getId() + ".gif");
		carteActuelle = new JLabel(icoCarteActuelle);
		windowCenter.add(carteActuelle);

		// Les Cartes de joueur physique
		LinkedList<Carte> cartes = j.getJoueurs().get(Jeu.getNombreDeJoueurs() - 1).getCartes();
		int nbCarte = cartes.size();
		windowSouth.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new FlowLayout());

		piocher = new JButton("Piocher");
		piocher.addActionListener(new piocherListener());
		piocher.setVisible(false);
		buttonPanel.add(piocher);
		buttonPanel.setPreferredSize(new Dimension(800, 30));

		carteMainPanel = construireCarteEnMain(j);
		carteMainPanel.setPreferredSize(new Dimension(800, 160));
		windowSouth.add(buttonPanel, BorderLayout.NORTH);
		windowSouth.add(carteMainPanel, BorderLayout.SOUTH);

	}

	/**classe interne qui repond a l'appuie sur le button Piocher et appelle le controleur pour realiser cette action dans jeu*/
	class piocherListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			jeuControleur.joueurPhysiquePiocher();
			effetControleur.joueurPhysiquePiocher();
		}

	}

	/**classe interne qui repond a l'appuie sur une carte pour la poser et appelle le controleur pour realiser cette action dans jeu*/
	class PoserListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			jeuControleur.joueurPhysiquePoser(e.getActionCommand());
			effetControleur.joueurPhysiquePoser(e.getActionCommand());

		}

	}



	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Jeu) {
			Jeu j = (Jeu) o;
			if (arg == "carteActuelle") {
				ImageIcon icoCarteActuelle = new ImageIcon("sources/" + j.getCarteActuelle().getId() + ".gif");
				JLabel nouvelleCarte = new JLabel(icoCarteActuelle);
				windowCenter.remove(carteActuelle);
				windowCenter.add(nouvelleCarte);
				carteActuelle = nouvelleCarte;
				windowCenter.updateUI();
				// update la carte de joueur physique
				windowSouth.remove(carteMainPanel);
				carteMainPanel.removeAll();
				carteMainPanel = this.construireCarteEnMain(j);
				windowSouth.add(carteMainPanel, BorderLayout.SOUTH);
				carteMainPanel.updateUI();
				windowSouth.updateUI();
			} else if (arg == "carteAPoser") {
				windowSouth.remove(carteMainPanel);
				carteMainPanel.removeAll();
				carteMainPanel = this.construireCarteCandidate(j);
				windowSouth.add(carteMainPanel, BorderLayout.SOUTH);
				carteMainPanel.updateUI();
				windowSouth.updateUI();
			} else if (arg == "Fin") {
				new Resultat(j);
				jeuControleur.fermer(frame);
			} else if (arg == "renouveller") {
				this.listerJoueurV(j);
			}
		} else {
			if (arg == "ObligeRejouer") {
				ImageIcon icoCarteActuelle = new ImageIcon("sources/" + jeuControleur.getJeu().getCarteActuelle().getId() + ".gif");
				JLabel nouvelleCarte = new JLabel(icoCarteActuelle);
				windowCenter.remove(carteActuelle);
				windowCenter.add(nouvelleCarte);
				carteActuelle = nouvelleCarte;
				windowCenter.updateUI();
				windowSouth.remove(carteMainPanel);
				carteMainPanel.removeAll();
				carteMainPanel = this.construireCarteEnMain(jeuControleur.getJeu());
				piocher.setVisible(true);
				windowSouth.add(carteMainPanel, BorderLayout.SOUTH);
				carteMainPanel.updateUI();
				windowSouth.updateUI();
			}else if(arg == "FairePiocher"){

			}


		}
	}

	/**construit les carte en main de joueur physique comme un panel
	 * @return retourne le JPanel representant les cartes de joueur physique
	 * @param j la reference de jeu*/
	public JPanel construireCarteEnMain(Jeu j) {
		piocher.setVisible(false);
		JPanel carteMain = new JPanel();
		carteMain.setOpaque(false);
		carteMain.setLayout(new FlowLayout());
		LinkedList<Carte> cartes = j.getJoueurs().get(j.getJoueurs().size() - 1).getCartes();
		Iterator<Carte> it = cartes.iterator();
		while (it.hasNext()) {
			Carte c = it.next();

			CarteButton carte = new CarteButton(c.getId());
			carte.addActionListener(new PoserListener());

			carteMain.add(carte);
		}
		carteMain.setPreferredSize(new Dimension(800, 160));
		return carteMain;
	}
	/**construit les cartes candidates, c'est-a-dire les cartes que le joueur peut jouer dans ce tour,S de joueur physique comme un panel
	 * @return retourne le JPanel representant les cartes candidates de joueur physique
	 * @param j la reference de jeu*/
	public JPanel construireCarteCandidate(Jeu j) {
		piocher.setVisible(true);
		JPanel carteMain = new JPanel();
		carteMain.setOpaque(false);
		carteMain.setLayout(new FlowLayout());
		LinkedList<Carte> cartes = j.getJoueurs().get(j.getJoueurs().size() - 1)
				.getCarteCandidate(j.getCarteActuelle());
		Iterator<Carte> it = cartes.iterator();
		while (it.hasNext()) {
			Carte c = it.next();

			CarteButton carte = new CarteButton(c.getId());
			carte.addActionListener(new PoserListener());

			carteMain.add(carte);
		}
		carteMain.setPreferredSize(new Dimension(800, 160));
		return carteMain;
	}

	/**construit les cartes de joueur physique quand il est attaque par l'effet FairePiocher
	@see FairePiocher
	@return retourne les cartes de joueur physique dans ce cas 
	@param j la reference de jeu */
	public JPanel construireCarteFairePiocher(Jeu j) {
		piocher.setVisible(true);
		JPanel carteMain = new JPanel();
		carteMain.setOpaque(false);
		carteMain.setLayout(new FlowLayout());
		LinkedList<Carte> cartes = new LinkedList<Carte>();
		LinkedList<Carte> carteDeJoueur = j.getJoueurActuel().getCartes();
		Iterator<Carte> it = carteDeJoueur.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			ArrayList<Effet> effets = c.getEffet();
			Iterator<Effet> ie = effets.iterator();
			while (ie.hasNext()) {
				Effet e = ie.next();
				if (e instanceof FairePiocher) {
					cartes.add(c);
				}
			}
		}
		Iterator<Carte> ic = cartes.iterator();
		while (it.hasNext()) {
			Carte c = ic.next();

			CarteButton carte = new CarteButton(c.getId());
			carte.addActionListener(new PoserListener());

			carteMain.add(carte);
		}
		carteMain.setPreferredSize(new Dimension(800, 160));
		return carteMain;
	}

  /**methode qui liste les joueurs virtuels en fonction de nombre de joueurs
   * @param j la reference de jeu*/
	public void listerJoueurV(Jeu j) {
		windowNorth.removeAll();
		ImageIcon ico = new ImageIcon("sources/1-1.png");
		nbJoueurs = j.getJoueurs().size();
		windowNorth.setLayout(new FlowLayout());
		joueurs = new JPanel[nbJoueurs - 1];
		// definir la position du joueur actuel
		int position = j.getJoueurs().indexOf(j.getJoueurActuel());
		for (int i = 0; i < nbJoueurs - 1; i++) {
			JLabel cardImage = new JLabel(ico);
			ImageIcon icoTime = new ImageIcon("sources/time.png");
			JLabel timeImage = new JLabel(icoTime);
			timeImage.setOpaque(false);
			if (position != (nbJoueurs - 1)) {
				if (i != position) {
					timeImage.setVisible(false);
				}
			} else {
				timeImage.setVisible(false);
			}
			JPanel infoPanel = new JPanel();
			infoPanel.setOpaque(false);
			infoPanel.setLayout(new BorderLayout());
			infoPanel.setPreferredSize(new Dimension(50, 50));

			JPanel joueur = new JPanel();
			joueur.setOpaque(false);
			joueur.setBorder(BorderFactory.createLineBorder(Color.white, 1));
			joueur.setLayout(new BorderLayout());
			joueur.setPreferredSize((new Dimension((int) (750 / (nbJoueurs - 1)), 175)));

			joueur.add(cardImage, BorderLayout.CENTER);

			JLabel nbCarte = new JLabel("Joueur" + i + " NB: " + j.getJoueurs().get(i).getCartes().size());

			nbCarte.setPreferredSize(new Dimension(30, 15));
			nbCarte.setForeground(Color.WHITE);

			infoPanel.add(nbCarte, BorderLayout.CENTER);
			infoPanel.add(timeImage, BorderLayout.SOUTH);
			joueur.add(infoPanel, BorderLayout.SOUTH);

			joueurs[i] = joueur;
			windowNorth.add(joueur);

		}

	}

}
