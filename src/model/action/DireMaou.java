package model.action;

import model.joueur.Joueur;

/**
 * Classe representant l'action de joueur de dire Maou dans la variante 2
 * 
 * @see Action
 */
public class DireMaou extends Action {

	/**
	 * Constructeur de la classe permettant de definir le nom
	 */
	public DireMaou() {
		super();
		this.setNom("Dire Maou");
	}

	/**
	 * Methode pour realiser l'annonce de Maou
	 * @param jou le joueur qui veut faire une annonce
	 */
	public void agir(Joueur jou) {
		if (jou.getCartes().size() == 0) {
			System.out.println(jou.toString() + " annonce Maou");
		} else if (jou.getCartes().size() == 1) {
			System.out.println(jou.toString() + " annonce Carte");
		}

	}

}
