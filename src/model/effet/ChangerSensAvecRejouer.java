package model.effet;

import exception.SaisiNonValideException;
import model.jeu.Jeu;
/**L'effet qui change le sens de jeu et permet de rejouer losqu'il y a seulement 2 joueurs
 * @see ChangerSens*/ 
public class ChangerSensAvecRejouer extends ChangerSens { 
	
	/**Constructeur de classe*/
	public ChangerSensAvecRejouer() {
		super();
		this.setNom("Changer de sens(rejouer s'il y a 2 joueurs)");
	}

	public Jeu validerSuperpower(Jeu j) {
		this.declarer();
		if (j.getJoueurs().size() > 2) {
			this.declarer();
			if (Jeu.isCroissante()) {
				Jeu.setCroissante(false);
			} else {
				Jeu.setCroissante(true);
			}
			return j;
		} else {
			ObligeRejouer o = new ObligeRejouer();
			
				j = o.validerSuperpower(j);
			
			return j;
			
			
		}
	}

}
