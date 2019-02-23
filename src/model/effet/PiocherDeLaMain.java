package model.effet;

import java.util.LinkedList;

import exception.SaisiNonValideException;
import model.carte.Carte;
import model.jeu.Jeu;
import model.joueur.Joueur;
/**L'effet qui oblige un joueur au choix de piocher un certain nombre de carte de joueur actuel*/
public class PiocherDeLaMain extends Effet {
	private int nbCarte;

	public PiocherDeLaMain() {
		super();
		this.setNom("Faire piocher " + nbCarte + " de mon jeu");
	}
	
	public PiocherDeLaMain(int i) {
		super();
		this.setNom("Faire piocher " + nbCarte + " de mon jeu");
		this.setNbCarte(i);
	}

	@Override
	public Jeu validerSuperpower(Jeu j) {
		if (j.getJoueurActuel().getCartes().size() == 0) {
			return j;
		}
		LinkedList<Carte> myTasDeCarte = j.getJoueurActuel().getCartes();
		int position = (int) ((myTasDeCarte.size() - 1) * Math.random());
		Carte c = myTasDeCarte.get(position);
		myTasDeCarte.remove(c);
		j.renouvelerJouerActuel();
		Joueur jou = j.getJoueurActuel();
		jou.piocher(c);
		return j;

	}
	
	public void setNbCarte(int i) {
		this.nbCarte = i;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
