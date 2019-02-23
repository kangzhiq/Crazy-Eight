package model.variante;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import model.action.DireMaou;
import model.carte.Carte;
import model.effet.*;
import model.jeu.Jeu;
import model.joueur.Joueur;
/**La variante 2
 * @see Variante */
public class Variante2 extends Variante{
	
	public Variante2(Jeu j) {
		super(j);
		this.setNom("Variante2");
		this.setNumero("2");
	}

	@Override
	public void addEffet(LinkedList<Carte> tas,ArrayList<Joueur> joueurs) {
		ObligeRejouer obligeRejouer = new ObligeRejouer();
		this.getJeu().getEffetDeJeu().add(obligeRejouer);
		PasserSonTour passerSonTour = new PasserSonTour();
		this.getJeu().getEffetDeJeu().add(passerSonTour);
		FairePiocher fairePiocher = new FairePiocher(2);
		this.getJeu().getEffetDeJeu().add(fairePiocher);
		ChangerForme changerCouleur = new ChangerForme();
		this.getJeu().getEffetDeJeu().add(changerCouleur);
		
		Iterator<Carte> it  = tas.iterator();
		while(it.hasNext()){
			Carte c = it.next();
			if (c.getEffet().size() == 0){
				switch(c.getValeur().getId()){
				case 10:
					c.addEffetVariante(obligeRejouer);
					break;
				case 8:
					c.addEffet(passerSonTour);
					break;
				case 7:
					c.addEffet(fairePiocher);
					break;
				case 11:
					c.addEffet(changerCouleur);
					break;
				}
			}
		}
		DireMaou direMaou = new DireMaou();
		Iterator<Joueur> ij = joueurs.iterator();
		while(ij.hasNext()) {
			Joueur jou = ij.next();
			jou.setMyAction(direMaou);
		}
		
		
		
	}

}
