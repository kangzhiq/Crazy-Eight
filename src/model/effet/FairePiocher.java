/**
 * 
 */
package model.effet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import exception.SaisiNonValideException;
import model.carte.Carte;
import model.jeu.*;
import model.joueur.Joueur;
import model.joueur.JoueurPhysique;

/**
 * L'effet qui fait le joueur suivant pocher deux cartes */
public class FairePiocher extends Effet {
	private int nbCarte;
	private Thread thread;
	private Jeu jeu;
	private boolean continu = false;

	public FairePiocher(int nbCarte) {
		this.nbCarte = nbCarte;
		this.setNom("Faire piocher" + nbCarte + " cartes");
		thread = new Thread(this);
		thread.start();
	}

	public void effectuer(Jeu j) {
		int nbPiocher = j.getNbCartePiocher() + this.nbCarte;
		j.setNbcartePiocher(nbPiocher);
	}

	public int getNbCarte() {
		return this.nbCarte;
	}

	// pas encore ajouer crazy8 pour annuler les attaques
	@Override
	public Jeu validerSuperpower(Jeu j) {
		this.jeu = j;
		this.setActive(true);
		/*this.declarer();
		int nbPiocher = j.getNbCartePiocher() + this.nbCarte;
		j.setNbcartePiocher(nbPiocher);
		j.renouvelerJouerActuel();
		Joueur jou = j.getJoueurActuel();
		LinkedList<Carte> carteCandidate = new LinkedList<Carte>();
		LinkedList<Carte> carteDeJoueur = jou.getCartes();
		Iterator<Carte> it = carteDeJoueur.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			ArrayList<Effet> effets = c.getEffet();
			Iterator<Effet> ie = effets.iterator();
			while (ie.hasNext()) {
				Effet e = ie.next();
				if (e instanceof FairePiocher) {
					carteCandidate.add(c);
				}
			}
		}
		if (carteCandidate.isEmpty()) {
			System.out.println(jou.toString() + " doit piocher " + j.getNbCartePiocher() + " cartes");
			/*
			 * if (!(j.aAssezDeCarte())) { j.renouvelerTasDeCarteEnattente(); } for (int i =
			 * 0; i < j.getNbCartePiocher(); i++) { jou.piocher(j.getCarteDepuisTas()); }
			 */
			//j.joueurPiocher(nbPiocher);
			//j.setNbcartePiocher(0);

		/*} else {
			Carte c = jou.poserUneCarte(carteCandidate, jou.getCartes());
			// jou.getCartes().remove(c);
			j.setCarteActuelle(c);
			j.getTasDeCartePosee().addCartePosee(c);
			j = c.getEffectValide().validerSuperpower(j);

		}
		// j.renouvelerJouerActuel();*/
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
			int nbPiocher = jeu.getNbCartePiocher() + this.nbCarte;
			jeu.setNbcartePiocher(nbPiocher);
			jeu.renouvelerJouerActuel();
			Joueur jou = jeu.getJoueurActuel();
			LinkedList<Carte> carteCandidate = new LinkedList<Carte>();
			LinkedList<Carte> carteDeJoueur = jou.getCartes();
			Iterator<Carte> it = carteDeJoueur.iterator();
			while (it.hasNext()) {
				Carte c = it.next();
				ArrayList<Effet> effets = c.getEffet();
				Iterator<Effet> ie = effets.iterator();
				while (ie.hasNext()) {
					Effet e = ie.next();
					if (e instanceof FairePiocher) {
						carteCandidate.add(c);
					}
				}
			}
			if (carteCandidate.isEmpty()) {
				System.out.println(jou.toString() + " doit piocher " + jeu.getNbCartePiocher() + " cartes");
				jeu.joueurPiocher(nbPiocher);
				jeu.setNbcartePiocher(0);

			} else {
				if (jou instanceof JoueurPhysique) {
					jeu.setEffetTermine(false);
					jeu.getEffetEnAttente().add(this);
					this.setChanged(true);
					this.notifyObservers("FairePiocher");
					while(this.isContinu() == false) {
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
					Carte c = jou.poserUneCarte(carteCandidate, jou.getCartes());
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
