/**
 * 
 */
package model.effet;

import exception.SaisiNonValideException;
import model.carte.*;
import model.jeu.*;
import model.joueur.*;

/**
 * L'effet qui oblige le joueur actuel de rejouer
 */
public class ObligeRejouer extends Effet {
	private Thread thread;

	public ObligeRejouer() {
		super();
		this.setNom("Obliger a rejouer");
		thread = new Thread(this);
		thread.start();
	}

	private Jeu jeu;
	private boolean continu = false;

	@Override
	public Jeu validerSuperpower(Jeu j) {
		jeu = j;
		/*
		 * this.setActive(true);
		 * 
		 * this.declarer(); Joueur joueurActuel = j.getJoueurActuel(); int nbCarte =
		 * joueurActuel.getCartes().size(); if (nbCarte == 0) { Carte carte =
		 * j.getCarteDepuisTas(); joueurActuel.piocher(carte); } else { Carte c =
		 * joueurActuel.poserUneCarte(joueurActuel.getCartes(),
		 * joueurActuel.getCartes()); System.out.println(j.getJoueurActuel().toString()
		 * + " pose " + c.toString()); j.setCarteActuelle(c);
		 * j.getTasDeCartePosee().addCartePosee(c); j =
		 * c.getEffectValide().validerSuperpower(j); }
		 */

		this.setActive(true);
		return j;
	}

	@Override
	public void run() {

		while (!this.isMort()) {
			while (!this.isActive()) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.declarer();
			jeu.setEffetTermine(false);
			Joueur joueurActuel = jeu.getJoueurActuel();
			int nbCarte = joueurActuel.getCartes().size();

			if (nbCarte == 0) {
				Carte carte = jeu.getCarteDepuisTas();
				joueurActuel.piocher(carte);
			} else {
				if (joueurActuel instanceof JoueurPhysique) {
					jeu.getEffetEnAttente().add(this);
					this.setChanged(true);
					this.notifyObservers("ObligeRejouer");
					while (this.isContinu() == false) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) { // TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					this.setContinu(false);
					if (!this.isaPioche()) {
						Carte c = jeu.getCarteActuelle();
						System.out.println(jeu.getJoueurActuel().toString() + " pose " + c.toString());

						jeu = c.getEffectValide().validerSuperpower(jeu);
					}
					this.setaPioche(false);
					jeu.getEffetEnAttente().remove(this);
				} else {
					Carte c = joueurActuel.poserUneCarte(joueurActuel.getCartes(), joueurActuel.getCartes());
					System.out.println(jeu.getJoueurActuel().toString() + " pose " + c.toString());
					jeu.setCarteActuelle(c);
					jeu.getTasDeCartePosee().addCartePosee(c);
					jeu = c.getEffectValide().validerSuperpower(jeu);

				}
			}
			this.setActive(false);
			jeu.setEffetTermine(true);

		}

	}

}
