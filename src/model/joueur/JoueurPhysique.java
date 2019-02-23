package model.joueur;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import exception.*;
import model.carte.*;
import model.effet.Effet;
import model.jeu.*;

/**
 * <b>Description</b>
 * la classe gère les actions de joueur physique*/
public class JoueurPhysique extends Joueur {

	public JoueurPhysique() {
	}

	public JoueurPhysique(String id, String nom) {
		super(id, nom);
	}

	/**
	 * faire arrêtre les actions d'un joueur physique
	 * @param j le jeu actuel
	 * @exception JeuDejaArreteException exception spécifique
	 * @see exception.JeuDejaArreteException*/
	public void arreter(Jeu j) throws JeuDejaArreteException {
		if (j.isJeuEnCours()) {
			j.setJeuEnCours(false);
		} else {
			throw new JeuDejaArreteException("Le jeu est deja arrete");
		}

	}

/**
 * permettre au joueur de poser une carte
 * @param carteCandidate les cartes peuvent être posées selon les contraintes
 * @param myCartes les cartes possédées par le joueur
 * @return carte que l'on décide à poser*/
	public Carte poserUneCarte(LinkedList<Carte> carteCandidate, LinkedList<Carte> myCartes) {
		int position =0;
		Carte carteChoisie = carteCandidate.get(position);
		myCartes.remove(carteChoisie);
		return carteChoisie;
	}

/**afficher les cartes joueable à l'utilisateur
 *@param carteCandidate les cartes jouables */
	public void listerCarteCandidate(LinkedList<Carte> carteCandidate) {
		Iterator<Carte> it = carteCandidate.iterator();
		StringBuffer s = new StringBuffer();
		s.append("Vos carte jouable:  ");
		int i = 0;
		while (it.hasNext()) {
			s.append("[n.");
			s.append(i);
			s.append(" ");
			Carte carte = it.next();
			s.append(carte.toString());
			s.append(" ]  ");
			i++;
		}
		System.out.println(s.toString());

	}

}
