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
 * La variante 5
 * @see Variante
 */
public class Variante5 extends Variante{
	public Variante5(Jeu j){
		super(j);
		this.setNom("Variante5");
		this.setNumero("5");
	}


	@Override
	public void addEffet(LinkedList<Carte> tas,ArrayList<Joueur> joueurs) {
		ObligeRejouer obligeRejouer = new ObligeRejouer();
		this.getJeu().getEffetDeJeu().add(obligeRejouer);
		ChangerSens changerSens = new ChangerSens();
		this.getJeu().getEffetDeJeu().add(changerSens);
		FairePiocherOuMeme fairePiocher = new FairePiocherOuMeme(2);
		this.getJeu().getEffetDeJeu().add(fairePiocher);
		ChangerFormeArreterAttaque changerFormeArreterAttaque = new ChangerFormeArreterAttaque();
		this.getJeu().getEffetDeJeu().add(changerFormeArreterAttaque);
		
		Iterator<Carte> it  = tas.iterator();
		while(it.hasNext()){
			Carte c = it.next();
			if (c.getEffet().size() == 0){
				switch(c.getValeur().getId()){
				case 10:
					c.addEffetVariante(obligeRejouer);
					break;
				case 7:
					c.addEffetVariante(changerSens);
					break;
				case 1:
					c.addEffet(fairePiocher);
					break;
				case 8:
					c.addEffet(changerFormeArreterAttaque);
					break;
				}
			}
		}
	}
	
}
