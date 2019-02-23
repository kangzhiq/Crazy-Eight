package model.effet;

import java.util.Iterator;
import java.util.LinkedList;

import exception.SaisiNonValideException;
import model.carte.Carte;
import model.jeu.Jeu;
import model.joueur.Joueur;
/**L'effet qui permet le joueur de poser tous les carte de valeur Roi et Dame*/
public class SecretDeRoiEtDame extends Effet {

	public SecretDeRoiEtDame() {
		super();
		this.setNom("Effet sur n'importe quelle Dame ou Roi");
	}

	@Override
	public Jeu validerSuperpower(Jeu j){
		Joueur jou = j.getJoueurActuel();
		LinkedList<Carte> myCartes = jou.getCartes();
		Iterator<Carte> it = myCartes.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			if (c.getValeur().getId() == 12 || c.getValeur().getId() == 13) {
				it.remove();
				j.getTasDeCartePosee().addCartePosee(c);
				System.out.println(jou.toString() + " pose " + c.toString());
			}
		}
		return j;


	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
