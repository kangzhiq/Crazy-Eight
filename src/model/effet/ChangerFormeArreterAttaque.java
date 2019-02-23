/**
 * 
 */
package model.effet;

import exception.SaisiNonValideException;
import model.jeu.Jeu;

/**
 * L'effet qui changer la forme et arreter l'attaque sur le joueur soi-meme
 * @see ArreterAttaque
 * @see ChangerForme
 * @see Effet
 */
public class ChangerFormeArreterAttaque extends Effet {
	/**Constructeur de classe*/
	public ChangerFormeArreterAttaque() {
		super();
		this.setNom("Changer de forme et arrete les attaques");
	}

	@Override
	/**@see ChangerFormeArreterAttaque*/
	public Jeu validerSuperpower(Jeu j)  {
		this.declarer();
		ArreterAttaque aa = new ArreterAttaque();
		j = aa.validerSuperpower(j);
		ChangerForme cf = new ChangerForme();
		j = cf.validerSuperpower(j);
		return j;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
