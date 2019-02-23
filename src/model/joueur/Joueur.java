package model.joueur;

import exception.SaisiNonValideException;
import model.action.Action;
import model.action.NoAction;
import model.carte.*;
import model.effet.Effet;
import model.jeu.*;

import java.util.*;

import javax.swing.JPanel;
/**Classe abstracte qui definit de manirer generale un joueur et ses comportements et actions principaux*/
public abstract class Joueur {
	/**id de joueur*/
	private String id;
	/**le point de joueur a la fin de jeu*/
	private int point = 0;
	/**le nom de joueur*/
	private String nom;
	/**la place de joueur par rapport a son point a la fin*/
	private int place = 0;
	/** une action qu'un joueur fait a la fin de chaque tour, annoncer par exemple*/
	private Action myAction;
	/**liste des cartes a la main de joueur*/
	private LinkedList<Carte> cartes = new LinkedList<Carte>();
	/**liste des cartes qui peuvent etre posees dans un tour*/
	private LinkedList<Carte> carteCandidate = new LinkedList<Carte>();
	/**boolean qui indique si le joueur a pose une carte*/
	private boolean pose = false;
	/**boolean qui indique si le joueur a pioche*/
	private boolean pioche = false;
	/**boolean qui indique si le tour de joueur est termine*/
	private boolean tourTermine = false;
	
	/**constructeur de Joueur*/
	public Joueur() {
	}

	/**@param id id de joueur
	 * @param nom nom de joueur*/
	public Joueur(String id, String nom) {
		this.setId(id);
		this.setNom(nom);
		setMyAction(new NoAction());
	}

	/**joueur pioche une carte
	 * @param carte carte piochee par le joueur */
	public void piocher(Carte carte) {
		cartes.add(carte);
	}


	/** get les carte candidates pour ce tour par rapport a la carte actuelle
	 dans ce cas, la carte precedante ne sera jamais une carte avec effet
	 @param c carte actuelle sur la table
	 @return liste des cartes candidates qui peuvent etre posees par rapport a la carte actuelle*/
	public LinkedList<Carte> getCarteCandidate(Carte c) {
		LinkedList<Carte> carteCandidate = new LinkedList<Carte>();
		Iterator<Carte> it = getCartes().iterator();
		while (it.hasNext()) {
			Carte maCarte = it.next();
			if (maCarte.getForme().equals(c.getForme()) || maCarte.getValeur().equals(c.getValeur())
					|| (!maCarte.getEffet().isEmpty())) {
				carteCandidate.add(maCarte);
			}
		}
		return carteCandidate;

	}

	/**joueur pose une carte
	 * @param c carte que le joueur a pose*/
	public void poserUneCarte(Carte c) {
		this.getCartes().remove(c);

	}
	
	/**joueur pose une carte au choix
	 * @param c carte choisie par joueur*/
	public void poserCarteChoisie(Carte c) {
		this.cartes.remove(c);
	}

    /**methode abstracte permettant le joueur de poser une carte entre les cartes candidates
     * @param carteCandidate liste des cartes qui peuvent etre posees par rapport a la carte actuelle
     * @param myCartes liste des cartes de joueur
     * @return la carte choisie*/
	public abstract Carte poserUneCarte(LinkedList<Carte> carteCandidate, LinkedList<Carte> myCartes);

	/**joueur annonce quand il n'a qu'une seule carte*/
	public void annoncer() {
		if (this.getCartes().size() == 1) {
			System.out.println(this.toString() + " annonce Carte");
		}
	}

	/** calculer le point en fonction de la facon de compter et retourner le point de ce joueur compte positif comme mode 1,compte negatif comme mode 0
	 * @return le point de joueur*/
	public int calculerPoint() {
		if (Jeu.getMethodeCompte() == 0) {
			Iterator<Carte> it = cartes.iterator();
			while (it.hasNext()) {
				Carte myCarte = it.next();
				switch (myCarte.getValeur().getId()) {
				case 12:
				case 13:
					point += 10;
					break;
				case 3:
				case 4:
				case 5:
				case 6:
				case 9:
					point += myCarte.getValeur().getId();
					break;
				case 2:
				case 10:
				case 7:
					point += 20;
					break;
				case 1:
				case 8:
					point += 50;
					break;

				}

			}
		}
		return point;

	}

	/**retourne un boolean pour verifier se le joueur a gagne, c'est-a-dire il n'a pas de carte en main
	 * @return true si le joueur a gagne, false sinon*/
	public boolean aGagne() {
		if (cartes.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	// getter et setter et toString
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Joueur ");
		s.append(this.nom);
	
		return s.toString();
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LinkedList<Carte> getCartes() {
		return this.cartes;
	}

	public void setPlace(int i) {
		this.place = i;
	}

	public int getPlace() {
		return this.place;
	}

	public Action getMyAction() {
		return myAction;
	}

	public void setMyAction(Action myAction) {
		this.myAction = myAction;
	}
	
	public void setPose(boolean b) {
		this.pose = b;
	}
	
	public boolean getPose() {
		return pose;
	}

	public boolean isPioche() {
		return pioche;
	}

	public void setPioche(boolean pioche) {
		this.pioche = pioche;
	}

	public boolean isTourTermine() {
		return tourTermine;
	}

	public void setTourTermine(boolean tourTermine) {
		this.tourTermine = tourTermine;
	}

}
