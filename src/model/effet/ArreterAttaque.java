/**
 * 
 */
package model.effet;

import model.carte.*;
import model.enumeration.*;
import model.jeu.Jeu;
import model.joueur.*;

import java.util.*;

/**
 * L'effet qui fait arreter une attaque sur le joueur soi-meme
 * @see Effet
 */
public class ArreterAttaque extends Effet {
	/**Constructeur de classe*/
	public ArreterAttaque() {
		super();
		this.setNom("Arreter les attaques");
	}

	@Override
	/**@see ArreterAttaque*/
	public Jeu validerSuperpower(Jeu j) {
		if (j.getNbCartePiocher() > 0) {
			this.declarer();
			j.setNbcartePiocher(0);
		}
		return j;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
