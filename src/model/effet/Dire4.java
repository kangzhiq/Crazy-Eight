package model.effet;

import exception.SaisiNonValideException;
import model.jeu.Jeu;
/**L'effet qui oblige le joueur de dire 4 quand il pose une carte 4*/
public class Dire4 extends Effet {

	/**Constructeur de classe*/
	public Dire4() {
		super();
		this.setNom("Dire 4");
	}

	@Override
	public Jeu validerSuperpower(Jeu j) {
		System.out.println(j.getJoueurActuel().toString() + " annonce 4");
		return j;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
