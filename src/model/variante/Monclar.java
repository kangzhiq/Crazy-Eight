/**
 * 
 */
package model.variante;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import model.carte.Carte;
import model.effet.*;
import model.jeu.Jeu;
import model.joueur.Joueur;

/**
 * La variante Monclar
 * @see Variante
 */
public class Monclar extends Variante {
	public Monclar(Jeu j) {
		super(j);
		this.setNom("Version Monclar");
		this.setNumero("11");
	}

	@Override
	public void addEffet(LinkedList<Carte> tas,ArrayList<Joueur> joueurs) {
		ObligeRejouer obligeRejouer = new ObligeRejouer();
		this.getJeu().getEffetDeJeu().add(obligeRejouer);
		PasserSonTour passerSonTour = new PasserSonTour();
		this.getJeu().getEffetDeJeu().add(passerSonTour);
		ChangerSens changerSens = new ChangerSens();
		this.getJeu().getEffetDeJeu().add(changerSens);
		FairePiocherSansRecours fairePiocherSansRecours = new FairePiocherSansRecours(1);
		this.getJeu().getEffetDeJeu().add(fairePiocherSansRecours);
		FairePiocher fairePiocher = new FairePiocher(3);
		this.getJeu().getEffetDeJeu().add(fairePiocher);
		ChangerFormeArreterAttaque changerFormeArreterAttaque = new ChangerFormeArreterAttaque();
		this.getJeu().getEffetDeJeu().add(changerFormeArreterAttaque);

		Iterator<Carte> it = tas.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			if (c.getEffet().size() == 0) {
				switch (c.getValeur().getId()) {
				case 10:
					c.addEffetVariante(obligeRejouer);
					break;
				case 7:
					c.addEffetVariante(passerSonTour);
					break;
				case 11:
					c.addEffetVariante(changerSens);
					break;
				case 9:
					c.addEffetVariante(fairePiocherSansRecours);
					break;
				case 1:
					c.addEffetVariante(fairePiocher);
					break;
				case 8:
					c.addEffetVariante(changerFormeArreterAttaque);
					break;

				}
			}
		}
	}
}
