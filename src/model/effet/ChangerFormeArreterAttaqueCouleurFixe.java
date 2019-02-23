package model.effet;

import java.util.Iterator;
import java.util.LinkedList;

import exception.SaisiNonValideException;
import model.carte.Carte;
import model.jeu.Jeu;
import model.joueur.Joueur;

/**
 * L'effet qui change la forme a la forme de la carte posee et arrete l'attaque sur le joueur
 * @see ChangerForme
 * @see ArreterAttaque*/
public class ChangerFormeArreterAttaqueCouleurFixe extends Effet {

	/**Constructeur de classe*/
	public ChangerFormeArreterAttaqueCouleurFixe() {
		super();
		this.setNom("Arreter une attaque et changer le forme a couleur fixe");
	}

	@Override
	/**
	 * @see ChangerFormeArreterAttaqueCouleurFixe*/
	public Jeu validerSuperpower(Jeu j)  {
		ArreterAttaque a = new ArreterAttaque();
		j = a.validerSuperpower(j);
		System.out.println("La carte a effectue son superpower: joueur suivant peut poser la carte de meme couleur!");
		Carte cartePosee = j.getCarteActuelle();
		j.renouvelerJouerActuel();
		Joueur jou = j.getJoueurActuel();
		Iterator<Carte> it = jou.getCartes().iterator();
		LinkedList<Carte> myCartes = new LinkedList<Carte>();
		while (it.hasNext()) {
			Carte c = it.next();
			if (cartePosee.getForme().getId() == 1 || cartePosee.getForme().getId() == 2) {
				if (c.getForme().getId() == 1 || c.getForme().getId() == 2) {
					myCartes.add(c);
				}
			} else {
				if (c.getForme().getId() == 0 || c.getForme().getId() == 4) {
					myCartes.add(c);
				}

			}
		}
		Carte carteChoisie = new Carte();
		if (myCartes.isEmpty()) {
			jou.piocher(j.getCarteDepuisTas());
		} else {
			carteChoisie = jou.poserUneCarte(myCartes, jou.getCartes());
		}
		j.getTasDeCartePosee().addCartePosee(carteChoisie);
		j = carteChoisie.getEffectValide().validerSuperpower(j);
		return j;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
