package controller;

import java.util.Iterator;
import java.util.LinkedList;

import model.carte.Carte;
import model.effet.Effet;
import model.effet.FairePiocher;
import model.jeu.Jeu;

/**
 * Classe de controleur permettant de lier l'interface graphique et l'effet en respectant le patron de conception de MVC*/
public class EffetControleur {
	
	/**Le jeu*/
	private Jeu jeu;
	
	/**Constructeur d'un EffetControleur
	 * @param j la reference de jeu*/
	public EffetControleur(Jeu j) {
		jeu = j;
	}

	/**
	 * Methode permettant le faire continuer le processus(thread) de l'effet conrespondant*/
	public void effetContinu() {
		Effet effetTop = jeu.getEffetEnAttente().get(0);
		effetTop.setContinu(true);
	}

	/**Methode permettant de confirmer le choix de joueur physique sur l'interface graphique  et de le transmettre au jeu
	 * Ce processus est independant de celui dans le jeu
	 * @param id id de la carte selectionnee par joueur physique
	 * */
	public void joueurPhysiquePoser(String id) {
		if (!jeu.getEffetEnAttente().isEmpty()) {
			LinkedList<Carte> cartes = jeu.getJoueurActuel().getCartes();
			Iterator<Carte> it = cartes.iterator();
			Carte carteChoisie;
			while (it.hasNext()) {
				Carte c = it.next();
				if (c.getId() == id) {
					carteChoisie = c;
					jeu.getJoueurActuel().poserCarteChoisie(carteChoisie);
					jeu.setCarteActuelle(carteChoisie);
					jeu.getTasDeCartePosee().addCartePosee(carteChoisie);
					break;
				}
			}
			Effet effetTop = jeu.getEffetEnAttente().get(0);
			effetTop.setContinu(true);

		}

	}

	/**Mehode permettant le joueur physique de piocer une carte
	 * Ce processus est independant de celui dans le jeu*/
	public void joueurPhysiquePiocher() {
		if (!jeu.getEffetEnAttente().isEmpty()) {
			Effet effetTop = jeu.getEffetEnAttente().get(0);
			if (effetTop instanceof FairePiocher) {
				for (int i = 0; i < jeu.getNbCartePiocher(); i++) {
					jeu.getJoueurActuel().piocher(jeu.getCarteDepuisTas());
				}
				System.out.println("joueur physique pioche " + jeu.getNbCartePiocher() + " cartes");
				effetTop.setaPioche(true);
				effetTop.setContinu(true);
			} else {
				jeu.getJoueurActuel().piocher(jeu.getCarteDepuisTas());
				effetTop.setaPioche(true);
				effetTop.setContinu(true);
			}
		}

	}

}
