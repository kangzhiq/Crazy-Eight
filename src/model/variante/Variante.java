/**
 * 
 */
package model.variante;

import java.util.ArrayList;
import java.util.LinkedList;

import model.carte.Carte;
import model.jeu.Jeu;
import model.joueur.Joueur;

/**
 * Classe mere de la variante qui definit de maniere generale les fonctionnalites d'une variante
 */
public abstract class Variante {
	/**le nom d'une variante*/
	private String nom;
	/**le numero d'une variante qui represente une identification*/
	private String numero;
	/**le jeu pour fournir des informations et pour effectuer des modifications*/
	private Jeu jeu;

	/**
	 * @param nom nom de variante
	 * @param numero le numero d'une variante qui represente une identification
	 */
	public Variante(String nom, String numero) {
		this.nom = nom;
		this.numero = numero;
	}

	public Variante(Jeu j) {
		this.setJeu(j);
	}
	
	/**methode abstracte permettant d'ajouter aux cartes les effets corresondants
	 * @param tas le tas des cartes
	 * @param joueurs la liste des joueurs*/
	public abstract void addEffet(LinkedList<Carte> tas,ArrayList<Joueur> joueurs);

	
	//getter et setter
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

}
