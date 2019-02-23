package model.action;

import model.joueur.Joueur;
/**
 * Classe generale de definir une action dans le cas ou le joueur n'a pas d'action speciale*/
public class NoAction extends Action {
	/**
	 * Constructeur de la classe permettant de definir le nom
	 */
	public NoAction() {
		super();
		this.setNom("No ACtion");
	}

	/**
	 * Annoncer Carte si le joueur a une seule carte a la main
	 * @param jou le joueur qui fait une action */
	public void agir(Joueur jou) {
		if(jou.getCartes().size()==1) {
			System.out.println(jou.toString()+" annonce Carte");
		}
	}

}
