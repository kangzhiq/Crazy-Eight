package model.effet;

import java.util.ArrayList;
import java.util.Iterator;

import exception.SaisiNonValideException;
import model.carte.Carte;
import model.jeu.Jeu;
import model.joueur.*;
/**L'effet qui permet le joueur de donner deux cartes a un joueur au choix*/
public class DonnerDeuxCartes extends Effet {

	/**Constructeur de classe*/
	public DonnerDeuxCartes() {
		super();
		this.setNom("Donner deux cartes a joueur au choix");
	}

	@Override
	public Jeu validerSuperpower(Jeu j)  {
		Joueur jou = j.getJoueurActuel();
		Joueur joueurChoisi;
		if (jou instanceof JoueurVirtuel) {
			joueurChoisi = choisirUnJoueurParJV(j.getJoueurs());
		} else {
			joueurChoisi = choisirUnJoueurParJP(j);
		}
		if (jou.getCartes().size() == 0) {
			return j;
		} else if (jou.getCartes().size() == 1) {
			joueurChoisi.piocher(jou.getCartes().pop());
			return j;
		}
		int p1 = (int) ((jou.getCartes().size() - 1) * Math.random());
		Carte c1 = jou.getCartes().get(p1);
		jou.getCartes().remove(c1);
		int p2 = (int) ((jou.getCartes().size() - 1) * Math.random());
		Carte c2 = jou.getCartes().get(p2);
		jou.getCartes().remove(c2);

		joueurChoisi.piocher(c1);
		joueurChoisi.piocher(c2);
		return j;

	}

	public Joueur choisirUnJoueurParJV(ArrayList<Joueur> joueurs) {
		joueurs.remove(this);
		int position = (int) ((Math.random()) * (joueurs.size() - 1));
		return joueurs.get(position);
	}

	// utiliser Jeu comme parametre pour profiter les fonctions demanderUser,
	// validerUneSaisie dans cette classe
	public Joueur choisirUnJoueurParJP(Jeu j) {
		ArrayList<Joueur> joueurs = j.getJoueurs();
		joueurs.remove(this);
		Iterator<Joueur> it = joueurs.iterator();
		StringBuffer info = new StringBuffer();
		info.append("Un joueur de ces joueurs obtiendra vos cartes: ");
		int i = 0;
		while (it.hasNext()) {
			Joueur jou = it.next();
			info.append("[n.");
			info.append(i);
			info.append(" ");
			info.append(jou.toString());
			info.append(" ]  ");
			i++;
		}
		System.out.println(info.toString());
		int position = j.validerUneSaisie("Veuillez choisir un joueur a penaliser(numero)", 0, joueurs.size() - 1);
		return joueurs.get(position);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
