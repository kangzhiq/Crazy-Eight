/**
 * 
 */
package model.carte;

import model.enumeration.*;
import model.jeu.*;

import java.util.*;

/**
 *  <b>Description</b>
 *  La classe modélise le tas des carte qui seront piochées
 *
 */

public class TasDeCarteEnAttente {
	
	public final static int sizeValeurs = Valeur.size;
	public final static int sizeFormes = Forme.size;
	
	/**le nombre de cartes en total dans cette partie de jeu, qui se varie en fonction de paramètrer du jeu*/
	private int nbDeCarte;
	/**la Collection qui représente les cartes peut être piocher*/
	private LinkedList<Carte> carteEnAttente;

	/**Le constructeur de la classe TasDeCarteEnAttente*/
	public TasDeCarteEnAttente() {
		this.nbDeCarte = Jeu.getNombreDeJeux() * (52 + Jeu.getAvecJoker() * 2);
		this.carteEnAttente = new LinkedList<Carte>();
		if (Jeu.getAvecJoker() == 1) {
			Carte j = new Carte();
			this.carteEnAttente.add(j);
			this.carteEnAttente.add(j);
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				Valeur v = Valeur.values()[j];
				Forme f = Forme.values()[i];
				Carte c = new Carte(f, v);
				this.carteEnAttente.add(c);
			}
		}
		if (Jeu.getNombreDeJeux() == 2) {
			this.carteEnAttente.addAll(carteEnAttente);
		}
	}

	public int getNOMBRE_DE_CARTES() {
		return this.nbDeCarte;
	}

	public int getTailleDeTas() {
		return this.carteEnAttente.size();
	}

	public LinkedList<Carte> getTasDeCarte() {
		return this.carteEnAttente;
	}

	public Carte getCarte() {
		Carte c = this.carteEnAttente.pop();
		return c;
	}

	/**mélanger les cartes*/
	public void melanger() {
		for (int i = 0; i < this.nbDeCarte; i++) {
			int position = (int) Math.round((Math.random() * (this.nbDeCarte - 1)));
			Carte c = this.carteEnAttente.pop();
			this.carteEnAttente.add(position, c);
		}

	}

	/**Ajouter les cartes dans le tas de cartes posées au tas de cartes à être piochées
	 * @param cp le tas de cartes posées*/
	public void addCartesPosees(TasDeCartePosee cp) {
		this.carteEnAttente.addAll(cp.getCartePosee());
	}

	
	public String toString() {
		return this.carteEnAttente.toString();
	}
}
