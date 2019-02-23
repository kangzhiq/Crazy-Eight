package view.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.JeuControleur;
import model.jeu.Jeu;
/**Affiche l'interface graphique sur laquelle le joueur joue les carte et le jeu deroule*/
public class Parametrer extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	/**le frame fondamental*/
	private JFrame frame;
	/**un cadre qui contient tous les elements*/
	private JPanel window;
	/**l'image au fond*/
	private JPanel bg;
	/**un panel qui contient JLabel et JRadioButton pour choisir le jeu de carte*/
	private JPanel jeuDeCartePanel;
	/**un panel qui contient JLabel et JRadioButton pour determiner si l'on joue avec joker*/
	private JPanel avecJokerPanel;
	/**un panel qui contient JLabel et JSlider pour choisir le nombre de joueur*/
	private JPanel nbJoueurPanel;
	/**un panel qui contient JLabel et JRadioButton pour choisir la methode de compter*/
	private JPanel compterPanel;
	/**un panel qui contient JLabel et JComboBox pour choisir la variante*/
	private JPanel variantePanel;
	/**un panel qui contient JLabel et JComboBox pour choisir la difficulte*/
	private JPanel difficultePanel;

    /**un JButton pour lancer le jeu*/
	private JButton lancer;
	/**un JButton pour quitter le jeu*/
	private JButton quitter;
	/**un controleur de jeu qui fait le lien entre l'interface graphique et le jeu
	 * @see JeuControleur*/
	private JeuControleur jeuControleur;

	/**methode pour initialiser le frame sue lequel on peut parametrer le jeu
	 * Ce frame est compose par 2 phases de composants.
	 * Chaque sous-cadres est compose par les composants elementaires comme JLabel, JButton, JRadioButton, etc.
	 * @param j le jeu pour fournir des informations et pour effectuer des modifications*/
	public Parametrer(Jeu j) {
		jeuControleur = new JeuControleur(j);
		frame = new JFrame("Parametrer");
		lancer = new JButton("Lancer");
		lancer.addActionListener(new LancerListener());
		lancer.setPreferredSize(new Dimension(150, 30));
		quitter = new JButton("Quitter");
		quitter.setPreferredSize(new Dimension(150, 30));
		window = new JPanel();
		bg = new JPanel();
		/*
		 * commencer.addActionListener(this); quitter.addActionListener(this);
		 */

		// ajouter controleur
		// new ButtonLancerControleur(j,lancer);

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

		// les donnees a choisir
		JLabel nbJeu = new JLabel("Nombre de jeu de carte");
		JRadioButton jeu1 = new JRadioButton("1");
		// jeu1.setSelected(true);
		jeu1.setOpaque(false);
		jeu1.addActionListener(new RadioButtonListener());
		JRadioButton jeu2 = new JRadioButton("2");
		jeu2.setOpaque(false);
		jeu2.addActionListener(new RadioButtonListener());
		jeuDeCartePanel = new JPanel();
		jeuDeCartePanel.setOpaque(false);
		jeuDeCartePanel.setLayout(new FlowLayout());
		jeuDeCartePanel.add(nbJeu);
		jeuDeCartePanel.add(jeu1);
		jeuDeCartePanel.add(jeu2);

		JLabel avecJoker = new JLabel("Avec joker?");
		JRadioButton oui = new JRadioButton("oui");
		oui.setOpaque(false);
		oui.addActionListener(new RadioButtonListener());
		JRadioButton non = new JRadioButton("non");
		non.setOpaque(false);
		non.addActionListener(new RadioButtonListener());
		avecJokerPanel = new JPanel();
		avecJokerPanel.setOpaque(false);
		avecJokerPanel.setLayout(new FlowLayout());
		avecJokerPanel.add(avecJoker);
		avecJokerPanel.add(oui);
		avecJokerPanel.add(non);

		JLabel nbJoueur = new JLabel("Nb de joueur");
		JSlider choisirNb = new JSlider(2, 10);
		choisirNb.addChangeListener(new NbJoueursListener());
		choisirNb.setOpaque(false);
		choisirNb.setPaintLabels(true);
		Hashtable<Integer, Component> labelTable = new Hashtable<Integer, Component>();
		for (int i = 2; i <= 10; i++) {
			labelTable.put(i, new JLabel("" + i));
		}
		choisirNb.setLabelTable(labelTable);
		nbJoueurPanel = new JPanel();
		nbJoueurPanel.setOpaque(false);
		nbJoueurPanel.setLayout(new FlowLayout());
		nbJoueurPanel.add(nbJoueur);
		nbJoueurPanel.add(choisirNb);

		JLabel compter = new JLabel("Compter");
		JRadioButton positif = new JRadioButton("positif");
		positif.setOpaque(false);
		positif.addActionListener(new RadioButtonListener());
		JRadioButton negatif = new JRadioButton("negatif");
		negatif.setOpaque(false);
		negatif.addActionListener(new RadioButtonListener());
		compterPanel = new JPanel();
		compterPanel.setOpaque(false);
		compterPanel.setLayout(new FlowLayout());
		compterPanel.add(compter);
		compterPanel.add(positif);
		compterPanel.add(negatif);

		JLabel variante = new JLabel("Variante");
		JComboBox<String> choisirVariante = new JComboBox<String>();
		choisirVariante.addActionListener(new VarianteComboBoxListener());
		choisirVariante.setEditable(true);
		choisirVariante.setEnabled(true);
		choisirVariante.addItem("Variante Minimale");
		choisirVariante.addItem("Variante Monclar");
		choisirVariante.addItem("Variante 1");
		choisirVariante.addItem("Variante 2");
		choisirVariante.addItem("Variante 5");
		variantePanel = new JPanel();
		variantePanel.setOpaque(false);
		variantePanel.setLayout(new FlowLayout());
		variantePanel.add(variante);
		variantePanel.add(choisirVariante);

		JLabel difficulte = new JLabel("Difficulte");
		JComboBox<String> choisirDifficulte = new JComboBox<String>();
		choisirDifficulte.addActionListener(new DifficulteComcoBoxListener());
		choisirDifficulte.setEditable(true);
		choisirDifficulte.setEnabled(true);
		choisirDifficulte.addItem("Simple");
		choisirDifficulte.addItem("Moyenne");
		choisirDifficulte.addItem("Difficile");
		difficultePanel = new JPanel();
		difficultePanel.setOpaque(false);
		difficultePanel.setLayout(new FlowLayout());
		difficultePanel.add(difficulte);
		difficultePanel.add(choisirDifficulte);

		JPanel something = new JPanel();
		something.setOpaque(false);
		something.setPreferredSize(new Dimension(600, 100));
		something.add(BorderLayout.WEST, quitter);
		something.add(BorderLayout.EAST, lancer);

		// window.add(logo, BorderLayout.CENTER);
		window.add(something, BorderLayout.SOUTH);
		// set la partie au milieu
		JPanel windowCenter = new JPanel();
		windowCenter.setSize(new Dimension(500, 200));
		windowCenter.setBackground(new Color(Integer.decode("#28b772")));
		windowCenter.setLayout(new BoxLayout(windowCenter, BoxLayout.Y_AXIS));
		windowCenter.add(jeuDeCartePanel);
		windowCenter.add(avecJokerPanel);
		windowCenter.add(nbJoueurPanel);
		windowCenter.add(compterPanel);
		windowCenter.add(variantePanel);
		windowCenter.add(difficultePanel);

		JPanel windowNorth = new JPanel();
		windowNorth.setOpaque(false);
		windowNorth.setPreferredSize(new Dimension(200, 100));

		JPanel windowWest = new JPanel();
		windowWest.setOpaque(false);
		windowWest.setPreferredSize(new Dimension(100, 200));

		JPanel windowEast = new JPanel();
		windowEast.setOpaque(false);
		windowEast.setPreferredSize(new Dimension(100, 200));

		window.add(windowNorth, BorderLayout.NORTH);
		window.add(windowEast, BorderLayout.EAST);
		window.add(windowWest, BorderLayout.WEST);
		window.add(windowCenter, BorderLayout.CENTER);
		layeredPane.add(window, JLayeredPane.MODAL_LAYER);
		layeredPane.add(bg, JLayeredPane.DEFAULT_LAYER);

		frame.setLayeredPane(layeredPane);
		frame.setLocationRelativeTo(frame.getOwner());
		frame.setResizable(false);
	}

	/**classe interne qui recupere le choix de joueur physique par RadioButton*/
	class RadioButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "1":
				jeuControleur.setNbJeux(1);
				break;
			case "2":
				jeuControleur.setNbJeux(2);
				break;
			case "oui":
				jeuControleur.setAvecJoker(1);
				break;
			case "non":
				jeuControleur.setAvecJoker(0);
				break;
			case "positif":
				jeuControleur.setCompter(1);
				break;
			case "negatif":
				jeuControleur.setCompter(0);
				break;
			}

		}

	}

	/**classe interne qui recupere le choix de joueur physique par JCombobox pour choisir la variante*/
	class VarianteComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> comboBox = (JComboBox) e.getSource();
			String variante = comboBox.getSelectedItem().toString();
			switch (variante) {
			case "Variante Minimale":
				jeuControleur.setVariante(0);
				break;
			case "Variante Monclar":
				jeuControleur.setVariante(11);
				break;
			case "Variante 1":
				jeuControleur.setVariante(1);
				break;
			case "Variante 2":
				jeuControleur.setVariante(2);
				break;
			case "Variante 5":
				jeuControleur.setVariante(5);
				break;
			}

		}

	}
	/**classe interne qui recupere le choix de joueur physique par Comcobox* pour la difficulte */
	class DifficulteComcoBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> comboBox = (JComboBox) e.getSource();
			String difficulte = comboBox.getSelectedItem().toString();
			switch (difficulte) {
			case "Simple":
				jeuControleur.setDifficulte(0);
				break;
			case "Moyenne":
				jeuControleur.setDifficulte(1);
				break;
			case "Difficile":
				jeuControleur.setDifficulte(2);
				break;
			}

		}

	}
	/**classe interne qui recupere le choix de joueur physique par JSlider pour le nombre de joueurs */
	class NbJoueursListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {

			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int nb = (int) source.getValue();
				for (int i = 2; i <= 12; i++) {
					if (i == nb) {
						jeuControleur.setNbJoueur(i);
						break;
					}
				}

			}

		}

	}

	/**classe interne qui recupere le choix de joueur physique par JSlider pour le nombre de joueurs */
	class LancerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			jeuControleur.lancer();
			jeuControleur.fermer(frame);
		}

	}

	/**classe interne qui repond a l'appuie sur le button Quitter*/
	class QuitterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	@Override
	public void update(Observable o, Object arg) {
		this.frame.repaint();

	}

}
