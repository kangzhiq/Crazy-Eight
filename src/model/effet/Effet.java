/**
 * 
 */
package model.effet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import exception.SaisiNonValideException;
import model.jeu.Jeu;

/**
 * Classe mere decrivant de maniere generale l'effet d'une carte 
 */
public abstract class Effet extends Observable implements Runnable {
	/**le nom d'effet*/
	private String nom;
	/**indique si l'effet est active pour commencer le thread*/
	private boolean active = false;
	/**indique si l'effet est dans son cycle de vie*/
	private boolean mort = false;
	/**indique si le thread peut sortir de son boucle de wait*/
	private boolean continu = false;
	/**indique si le joueur physique a pioche une carte*/
	private boolean aPioche = false;
	/**indique s'il y un changement a notify les observers*/
	private boolean changed =false;
	/**la liste des observers*/
	private ArrayList<Observer> observers;
	/**contructeur de la calsse Effet*/
	public Effet() {
		setObservers(new ArrayList<Observer>());
	}
	
	/**declare l'effet valide*/
	public void declarer() {
		System.out.println("La carte valide son superpower: " + this.getNom() );
	}

	/**
	 * Methode abstracte qui valide l'effet
	 * Cette methode est implementee par chaque classe fille en respectant leur propre fonctionnalite
	 * @param j le jeu pour founir des informations et pour realiser des modifications  
	 * @return le jeu est eventuellement modifie
	 */
	public abstract Jeu validerSuperpower(Jeu j);
	
	public void add(Observer o) {
		if (observers.contains(o)) {
			return;
		}
		observers.add(o);
	}

	
	

	// setters et getters
	public void setChanged(boolean b) {
		this.changed=b;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public String toString() {
		return this.nom;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isMort() {
		return mort;
	}

	public void setMort(boolean mort) {
		this.mort = mort;
	}

	public boolean isContinu() {
		return continu;
	}

	public void setContinu(boolean continu) {
		this.continu = continu;
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}

	public boolean isaPioche() {
		return aPioche;
	}

	public void setaPioche(boolean aPioche) {
		this.aPioche = aPioche;
	}
}
