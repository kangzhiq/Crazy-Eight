package model.action;

import model.joueur.Joueur;

/**
 * Classe mere permettant un joueur effectuer une certaine action a la fin de son tour
 */
public class Action {
	/** Nom de cette action */
	String nom;

	/**
	 * Constructeur de la classe
	 */
	public Action() {

	}

	/**
	 *Classe definissant l'action du joueur 
	 *@param jou le joueur qui agit
	 */
	public void agir(Joueur jou) {
	}

	
	//getters et setters
	public void setNom(String s) {
		this.nom = s;
	}

	public String getNom() {
		return this.nom;
	}

}
