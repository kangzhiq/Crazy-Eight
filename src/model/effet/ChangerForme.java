/**
 * 
 */
package model.effet;

import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import model.carte.Carte;
import model.enumeration.Forme;
import model.jeu.Jeu;

/**
 * L'effet qui changer le forme a la couleur dont le joueur possede le plus
 * @see Effet
 */
public class ChangerForme extends Effet {
	/**Constructeur de classe*/
	public ChangerForme() {
		super();
		this.setNom("Changer Forme");
	}

	@Override
	/**@see ChangerForme*/
	public Jeu validerSuperpower(Jeu j) {
		this.declarer();
		// C'est mieux de tranformer la couleur a ce que le joueur possede le plus
		int nbPique = 0;
		int nbCoeur = 0;
		int nbCarreau = 0;
		int nbTrefle = 0;
		LinkedList<Carte> myCartes = j.getJoueurActuel().getCartes();
		Iterator<Carte> it = myCartes.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			switch (c.getForme().getId()) {
			case 0:
				nbPique++;
				break;
			case 1:
				nbCoeur++;
				break;
			case 2:
				nbCarreau++;
				break;
			case 3:
				nbTrefle++;
				break;
			}
		}
		int nbMax = choisirMax(nbPique, nbCoeur, nbCarreau, nbTrefle);
		j.getCarteActuelle().setForme(Forme.values()[nbMax]);
		// System.out.println("Frome est donc "+ Form.values()[nbMax].toString());
		// j.renouvelerJouerActuel();
		return j;

	}

	public int choisirMax(int a1, int a2, int a3, int a4) {
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(a1);
		al.add(a2);
		al.add(a3);
		al.add(a4);
		return al.indexOf(Collections.max(al));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
