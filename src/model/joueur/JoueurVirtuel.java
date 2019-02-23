package model.joueur;

import java.util.LinkedList;

import model.carte.*;
import model.enumeration.*;

/**
 * <b>Description</b>
 * la classe gère les actions de joueur virtuel*/
public class JoueurVirtuel extends Joueur {

	/**le stratégie utilisé par ce joueur virtuel*/
	private StrategieDeJoueur strategie;

	public JoueurVirtuel(String id, String nom) {
		super(id, nom);
	}

	public JoueurVirtuel(String id, String nom, int difficulte) {
		super(id, nom);
		if (difficulte == 1) {
			this.strategie = new StrategieSimple();
		} else if (difficulte == 2) {
			this.strategie = new StrategieMoyenne();
		}  else {
			this.strategie = new StrategieSimple();
		}
	}

	/**choisir une carte parmi toutes les cartes candidates en fonction de sa strategie
	 * @param carteCandidate la collection de tous les cartes jouables
	 * @param a les cartes possédées par le joueur*/
	public Carte poserUneCarte(LinkedList<Carte> carteCandidate, LinkedList<Carte> a) {
		Carte c = this.strategie.poser(carteCandidate, a);
		this.getCartes().remove(c);
		return c;
	}

	public void setStrategie(StrategieDeJoueur s) {
		this.strategie = s;
	}

	public StrategieDeJoueur getStrategie() {
		return strategie;
	}

}
