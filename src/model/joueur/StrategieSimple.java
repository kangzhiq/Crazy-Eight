package model.joueur;

import java.util.*;

import model.carte.*;

/**
 * <b>Description</b>
 * Dans ce stratégie, on choisit aléatoirement la carte à poser de cartes qui peuvent être posée selon les contraintes*/
public class StrategieSimple implements StrategieDeJoueur {
	@Override
	
	public Carte poser(LinkedList<Carte> carteCandidate, LinkedList<Carte> myCartes) {
		if (carteCandidate.isEmpty()) {
			return null;
		} else {
			int position = (int) ((carteCandidate.size() - 1) * Math.random());
			Carte carteChoisit = carteCandidate.get(position);
			carteCandidate.remove(position);
			return carteChoisit;
		}

	}

	@Override
	public void annoncer() {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return ("Strategie simples");
	}

}
