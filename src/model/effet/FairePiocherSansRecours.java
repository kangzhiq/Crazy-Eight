/**
 * 
 */
package model.effet;

import exception.SaisiNonValideException;
import model.jeu.Jeu;
import model.joueur.Joueur;
/**L'effet qui fait le joueur suivant piocher deux cartes sans recours
 * @see FairePiocher*/
public class FairePiocherSansRecours extends FairePiocher {
	private int nbCarte;

	public FairePiocherSansRecours(int nbCarte) {
		super(nbCarte);
		this.setNom("Faire piocher " + nbCarte + " cartes sans recours");
		this.setNbCarte(nbCarte);

	}

	@Override
	public Jeu validerSuperpower(Jeu j) {
		this.declarer();
		j.renouvelerJouerActuel();
		int nbAPiocher = j.getNbCartePiocher();
		nbAPiocher = nbAPiocher + nbCarte;
		j.joueurPiocher(nbAPiocher);
		j.setNbcartePiocher(0);
		return j;
	}

	public int getNbCarte() {
		return nbCarte;
	}

	public void setNbCarte(int nbCarte) {
		this.nbCarte = nbCarte;
	}

}
