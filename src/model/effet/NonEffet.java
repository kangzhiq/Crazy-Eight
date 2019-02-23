/**
 * 
 */
package model.effet;

import model.jeu.Jeu;

/**
 * L'effet general pour une carte qui n'a pas de effet
 */
public class NonEffet extends Effet {
	public NonEffet() {
		super();
		this.setNom("Carte sans effet");
	}

	@Override
	public Jeu validerSuperpower(Jeu j) {
		// j.renouvelerJouerActuel();
		return j;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
