package model.variante;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import model.carte.Carte;
import model.effet.*;
import model.jeu.Jeu;
import model.joueur.Joueur;
/**La variante 1
 * @see Variante*/
public class Variante1 extends Variante {

	public Variante1(Jeu j) {
		super(j);
		this.setNom("Variante 1");
		this.setNumero("1");
	}

	@Override
	public void addEffet(LinkedList<Carte> tas,ArrayList<Joueur> joueurs) {
		ObligeRejouer obligeReJouer = new ObligeRejouer();
		this.getJeu().getEffetDeJeu().add(obligeReJouer);
		FairePiocher fairePiocher2 = new FairePiocher(2);
		this.getJeu().getEffetDeJeu().add(fairePiocher2);
		PasserSonTour passerSonTour = new PasserSonTour();
		this.getJeu().getEffetDeJeu().add(passerSonTour);
		ChangerSens changerSens = new ChangerSens();
		this.getJeu().getEffetDeJeu().add(changerSens);
		SecretDeRoiEtDame roiEtDame = new SecretDeRoiEtDame();
		this.getJeu().getEffetDeJeu().add(roiEtDame);
		FairePiocher fairePiocher1 = new FairePiocher(1);
		this.getJeu().getEffetDeJeu().add(fairePiocher1);
		PiocherDeLaMain piocherDeLaMain = new PiocherDeLaMain();
		this.getJeu().getEffetDeJeu().add(piocherDeLaMain);
		DonnerDeuxCartes donnerDeuxCartes = new DonnerDeuxCartes();
		this.getJeu().getEffetDeJeu().add(donnerDeuxCartes);
		Dire4 dire4 = new Dire4();
		this.getJeu().getEffetDeJeu().add(dire4);
		ArreterAttaque effetDe5 = new ArreterAttaque();
		this.getJeu().getEffetDeJeu().add(effetDe5);
		PasserMonTourAGauche passerMonTourAGauche = new PasserMonTourAGauche();
		this.getJeu().getEffetDeJeu().add(passerMonTourAGauche);
		ChangerForme changerForme = new ChangerForme();
		this.getJeu().getEffetDeJeu().add(changerForme);

		Iterator<Carte> it = tas.iterator();
		while (it.hasNext()) {
			Carte c = it.next();
			if (c.getEffet().size() == 0) {
				switch (c.getValeur().getId()) {
				case 2:
					c.addEffetVariante(fairePiocher2);
					break;
				case 10:
					c.addEffetVariante(obligeReJouer);
					break;
				case 7:
					c.addEffetVariante(passerSonTour);
					break;
				case 11:
					c.addEffetVariante(changerSens);
					break;
				case 12:
				case 13:
					c.addEffetVariante(roiEtDame);
					break;
				case 6:
					c.addEffetVariante(fairePiocher1);
					break;
				case 9:
					c.addEffetVariante(piocherDeLaMain);
					break;
				case 1:
					c.addEffetVariante(donnerDeuxCartes);
					break;
				case 4:
					c.addEffetVariante(dire4);
					break;
				case 5:
					c.addEffetVariante(effetDe5);
					break;
				case 3:
					if (c.getForme().getId() == 1) {
						c.addEffetVariante(passerMonTourAGauche);
					}
					break;
				case 8:
					c.addEffetVariante(changerForme);
					break;
				}
			}
		}

	}

}
