/**
 * 
 */
package model.carte;

import java.util.*;

/**
 * <b>Description</b>
 *  La classe modélise le tas des carte qui sont posées sur la table
 */

public class TasDeCartePosee {
	/**La collection des cartes posée sur le table*/
	private LinkedList<Carte> cartePosee;

	public TasDeCartePosee() {
		this.cartePosee = new LinkedList<Carte>();
	}

	/**
	 * @return the cartePosee
	 */
	public LinkedList<Carte> getCartePosee() {
		return cartePosee;
	}

	/**
	 * @param cartePosee
	 *            the cartePosee to set
	 */
	public void addCartePosee(Carte cartePosee) {
		this.cartePosee.add(cartePosee);
	}

	/**vider le tas*/
	public void clearCartePosee() {
		this.cartePosee.clear();
	}
}
