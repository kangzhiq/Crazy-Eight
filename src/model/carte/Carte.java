/**
 * 
 */
package model.carte;

import java.util.ArrayList;

import model.effet.*;
import model.enumeration.*;

/**
 * <b>Description</b>
 * La classe modélise les carte utilisées dans le jeu
 * 
 *
 */
public class Carte {
	/**L'identification d'une carte*/
	private String id;
	/**La forme(couleur) de la carte qui est de type Enumération Forme
	 *@see model.enumeration.Forme*/
	private Forme forme;
	/**La valeur de la carte qui est de type Enumération Valeur
	 *@see model.enumeration.Valeur*/
	private Valeur valeur;

	/**L'effet de la carte qui s'effectue quand cette carte est posée sur la table dans ce tour*/
	private Effet effectValide = new NonEffet();
	/**Une carte peut possèder plusieurs effets, par exemple, un effet "Non Effet" et un effet spécial.
	 * On les enregitre dans cette Collection ArrayList*/
	private ArrayList<Effet> effet;

	/**
	 * @param forme 
	 * 				forme de la carte
	 * @param valeur 
	 * 				valeur de la carte
	 */
	public Carte(Forme forme, Valeur valeur) {
		this.forme = forme;
		this.valeur = valeur;
		this.id = new String(this.forme.getId()+"-"+this.valeur.getId());
		this.effet = new ArrayList<Effet>();
	}
	
	/**
	 * La constructeur pour la carte Joker, qui a toujours l'effet "ArreterAttaque" ainsi de valeur égale à "Joker" et de forme égale à "Joker"*/
	public Carte() {
		this.setValeur(valeur.values()[13]);
		this.setForme(Forme.values()[4]);
		this.id = new String(this.forme.getId()+"-"+this.valeur.getId());
		this.effet = new ArrayList<Effet>();
		Effet e = new ArreterAttaque();
		this.addEffet(e);
		this.setEffectValide(e);
	}


	/**
	 * @return the forme
	 */
	public Forme getForme() {
		return forme;
	}

	/**
	 * @param forme
	 *            the forme to set
	 */
	public void setForme(Forme forme) {
		this.forme = forme;
	}

	/**
	 * @return the valeur
	 */
	public Valeur getValeur() {
		return valeur;
	}

	/**
	 * @param valeur
	 *            the valeur to set
	 */
	public void setValeur(Valeur valeur) {
		this.valeur = valeur;
	}

	/**
	 * Pour préparer à imprimer des informations d'une carte
	 * @return the StringBuffer*/
	public String toString() {
		StringBuffer s = new StringBuffer();

		if (this.valeur == null) {
			s.append("Joker");
		} else {
			s.append(this.valeur);
			s.append(" de ");
			s.append(this.forme);
		}
		return s.toString();
	}

	/**
	 * @return la liste des effets
	 */
	public ArrayList<Effet> getEffet() {
		return this.effet;
	}

	/**
	 * @param effet
	 *            rajouter l'effet à la liste des effets
	 */
	public void addEffet(Effet effet) {
		this.effet.add(effet);
	}

	/**
	 * @return l'effet s'effectue
	 */
	public Effet getEffectValide() {
		return effectValide;
	}

	/**
	 * @param effectValide
	 *            the effectValide to set
	 */
	public void setEffectValide(Effet effectValide) {
		this.effectValide = effectValide;
	}
	
	/**
	 * @param e
	 *            rajouter l'effet à la liste des effets
	 *            the effectValide to set
	 */
	public void addEffetVariante(Effet e){
		this.addEffet(e);
		this.setEffectValide(e);
	}

	/**
	 * @return l'id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}
