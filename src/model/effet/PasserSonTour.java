/**
 * 
 */
package model.effet;

import model.carte.Carte;
import model.jeu.Jeu;
import model.joueur.Joueur;

/**
 * L'effet qui permet de faire le joueur suivant passer son tour*/
public class PasserSonTour extends Effet {

	public PasserSonTour() {
		super();
		this.setNom("Le joueur suivant passe son tour");
	}

	@Override
	public Jeu validerSuperpower(Jeu j) {
		this.declarer();
		j.renouvelerJouerActuel();
		return j;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
