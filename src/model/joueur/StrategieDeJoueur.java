package model.joueur;

import java.util.LinkedList;

import model.carte.Carte;

/**
 * <b>Description</b>
 * l'interface qui sera implémenté par les joueurs virtuels
 * Avec l'implémentation de cela, on applique le patron conception "Strategy"*/
public interface StrategieDeJoueur {
	/**Strategies differentes pour poser une carte
	 * @param carteCandidate les cartes peuvent être posées selon les contraintes
	 * @param myCartes les cartes possédées par le joueur
	 * @return carte que l'on décide à poser*/
	public Carte poser(LinkedList<Carte> carteCandidate, LinkedList<Carte> myCartes);

	/**Strategie differentes pour annoncer carte ou contre carte*/
	public void annoncer();
}
