/**
 * 
 */
package model.variante;

import model.carte.*;
import model.effet.*;
import model.jeu.Jeu;
import model.joueur.Joueur;

import java.util.*;

/**
 * La variante de version minimale
 * @see Variante
 */
public class Minimale extends Variante {
	


	public Minimale(Jeu j) {
		super(j);
		this.setNom("Version Minimale");
		this.setNumero("0");
	}

	@Override
	public void addEffet(LinkedList<Carte> tas,ArrayList<Joueur> joueurs) {
		FairePiocher fairePiocher = new FairePiocher(2);
		this.getJeu().getEffetDeJeu().add(fairePiocher);
		ObligeRejouer obligeRejouer = new ObligeRejouer();
		this.getJeu().getEffetDeJeu().add(obligeRejouer);
		ChangerForme changerforme = new ChangerForme();
		this.getJeu().getEffetDeJeu().add(changerforme);
		//NonEffet nonEffet = new NonEffet();
		Iterator<Carte> it = tas.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			if (c.getEffet().size() == 0) {
				switch (c.getValeur().getId()) {
				case 2:
					c.addEffetVariante(fairePiocher);
					break;
				case 8:
					c.addEffetVariante(changerforme);
					break;
				case 10:
					c.addEffetVariante(obligeRejouer);
					break;
				}
			}
		}
	}
}
