package model.effet;

import exception.SaisiNonValideException;
import model.jeu.Jeu;

/**L'effet qui permet le joueur de poser tous les carte de cette forme*/
public class DeMemeCouleur extends Effet {
	
	/**Constructeur de classe*/
	public DeMemeCouleur() {
		super();
		this.setNom("Poser toutes les cartes de meme couleur");
	}

	@Override
	public Jeu validerSuperpower(Jeu j)  {
		
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
