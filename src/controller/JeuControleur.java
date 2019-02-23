package controller;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;

import exception.SaisiNonValideException;
import model.carte.Carte;
import model.effet.Effet;
import model.jeu.Jeu;
import model.joueur.Joueur;
import model.joueur.JoueurPhysique;
import view.ui.Game;
import view.ui.Parametrer;
import view.ui.Resultat;
import view.uiTexte.VueTexte;
/**Classe lie le jeu et l'interface graphique en respectant le patron de conception de MVC*/
public class JeuControleur {

	private Jeu jeu;
	/**Constructeur de JeuControleur*/
	public JeuControleur() {

	}
	
	/**@param j le jeu
	 * @see JeuControleur*/
	public JeuControleur(Jeu j) {
		jeu = j;
	}
	
	/**Affiche le frame pour parametrer le jeu quand le joueur physique appuie le button Commencer*/
	/**Ajoute le frame comme un observer dans jeu
	 * @see Jeu
	 * @see Parametrer*/
	public void commencer() {
		// jeu.setJeuEnCours(true);
		Parametrer parametrer = new Parametrer(jeu);
		jeu.add(parametrer);
	}
	
	/**Affiche le frame principal pour le jeu quand le joueur physique appuie le buttno Lancer*/
	/**Effetue les ajoute d'ovservers dans les objets observables
	 * @see Effet
	 * @see Jeu
	 * @see Game*/
	public void lancer() {
		jeu.initialiser();
		jeu.setParametrerTermine(true);
		Game game = new Game(jeu);
		VueTexte vueTexte = new VueTexte(jeu);
		jeu.add(game);
		Iterator<Effet> it = jeu.getEffetDeJeu().iterator();
		while(it.hasNext()) {
			Effet e = it.next();
			e.add(game);
			e.add(vueTexte);
		}

	}

	/**
	 * Affiche le frame de resultat quand le jeu est termine
	 * Ajoute le frame comme un observer dans jeu
	 * @see Jeu
	 * @see Resultat*/
	public void fin() {
		Resultat res = new Resultat(jeu);
		jeu.add(res);
	}
	/**Methode permettant le joueur physique de piocher une carte dans le processus de jeu*/
	public void joueurPhysiquePiocher() {
		if (jeu.getEffetEnAttente().isEmpty()) {
			jeu.getJoueurActuel().piocher(jeu.getCarteDepuisTas());
			System.out.println("joueur physique pioche");
			jeu.getJoueurActuel().setTourTermine(true);
		}

	}

	/**Methode permettant de confirmer le choix de joueur physique sur l'interface graphique  et de le transmettre au jeu
	 * Ce processus est dans le jeu
	 * @param id id de la carte selectionnee par joueur physique
	 * @see EffetControleur*/
	public void joueurPhysiquePoser(String id) {
		if (jeu.getEffetEnAttente().isEmpty()) {
			if(id.equals("0-4"))System.out.println("oui");
			LinkedList<Carte> cartes = jeu.getJoueurActuel().getCartes();
			Iterator<Carte> it = cartes.iterator();
			Carte carteChoisie;
			while (it.hasNext()) {               
				Carte c = it.next();
				if (id.equals(c.getId())) {
					carteChoisie = c;
					jeu.getJoueurActuel().poserCarteChoisie(carteChoisie);
					jeu.setCarteActuelle(carteChoisie);
					jeu.getTasDeCartePosee().addCartePosee(carteChoisie);
					System.out.println("Joueur physique pose " + c.toString());
					break;
				}
			}

			jeu.getJoueurActuel().setPose(true);
			jeu.getJoueurActuel().setTourTermine(true);

		}
	}
	
	/**
	 * ferme le frame
	 * @param frame le frame a femer */
	public void fermer(JFrame frame) {
		frame.dispose();
	}
	
	
	//setters et getters
	public Jeu getJeu() {
		return jeu;
	}

	public void setNbJeux(int nb) {
		jeu.setNombreDeJeux(nb);
	}

	public void setNbJoueur(int nb) {
		jeu.setNombreDeJoueurs(nb);
	}

	public void setDifficulte(int di) {
		jeu.setDifficulte(di);
	}

	public void setCompter(int c) {
		jeu.setMethodeCompte(c);
	}

	public void setAvecJoker(int avec) {
		jeu.setAvecJoker(avec);
	}

	public void setVariante(int variante) {
		jeu.setVariante(variante);
		Jeu.setVersionDeVariante(variante);;
	}

}
