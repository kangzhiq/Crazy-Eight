/**
 *
 */
package model.jeu;

import exception.SaisiNonValideException;
import model.carte.*;
import model.effet.*;
import model.enumeration.*;
import model.joueur.*;
import model.variante.*;
import view.ui.Accueil;
import view.uiTexte.VueTexte;

import java.util.*;

/**
 *  <b>Description</b>
 *  <p>La classe qui gère le déroulement du jeu</p>
 *  Observée par l'interface graphique
 *  Mise dans le Thread
 *
 */
public class Jeu extends Observable implements Runnable {
	/**le jeu déroule*/
	private static Jeu jeu;
	/**le tas de carte posée à la table par des joueurs*/
	private TasDeCartePosee tasDeCartePosee;
	/**le tas de carte*/
	private TasDeCarteEnAttente tasDeCarteEnAttente;
	/**la collection de participants, plusieurs joueurs virtuels et un joueur physque*/
	private ArrayList<Joueur> joueurs;
	/**la collection de joueurs gagnés*/
	private ArrayList<Joueur> joueursGagne;
	/**le joueur qui est en train d'agir*/
	private Joueur joueurActuel;
	/**le nombre de cartes à piocher pour le joueur suivant*/
	private int nbCartePiocher;
	/**cette carte fait référence pour le joueur puisse poser une carte*/
	private Carte carteActuelle;
	/**le variante qui s'applique dans le jeu*/
	private Variante variante;
	/**la collection d'observers*/
	private ArrayList<Observer> observers;
	/**on enregistre les effets de façon "pile"*/
	private ArrayList<Effet> effetEnAttente;
	/**tous les effets dans ce jeu*/
	private ArrayList<Effet> effetDeJeu;
	/**si le jeu est en cours de s'exécuser*/
	private boolean jeuEnCours = false;
	/**si le processus pour paramétrer le jeu est terminé*/
	private boolean parametrerTermine = false;
	/**dès qu'un joueur joue, on le met en "true" pour indiquer qu'il faut notifier les observers*/
	private boolean changed = false;
	/**indiquer si un effet finit à s'effectuer*/
	private boolean effetTermine = true;
	/**le thread où on met le jeu*/
	Thread thread;

 /**les paramatrage du jeu*/
	private static int nombreDeJeux;
	private static int nombreDeJoueurs;
	private static int avecJoker;
	private static int versionDeVariante;
	private static int difficulte;
	private static int methodeCompte;
	private static boolean croissante = true;
	private static Scanner scanner;


	public static void main(String[] args) {
		Jeu j = Jeu.getJeu();
		Accueil accueil = new Accueil(j);


	}

/**
*le constructeur du jeu, on utilise le patron conception "Singleton" pour assurer qu'il existe un seul jeu déroule
*/
	private Jeu() {
		this.joueursGagne = new ArrayList<Joueur>();
		this.nbCartePiocher = 0;
		this.jeuEnCours = false;
		observers = new ArrayList<Observer>();
		effetDeJeu = new ArrayList<Effet>();
		effetEnAttente = new ArrayList<Effet>();
		thread = new Thread(this);
		thread.start();
	}

	public static Jeu getJeu() {
		if (jeu == null) {
			jeu = new Jeu();
		}
		return jeu;
	}

	/**
	*
	* gérer le déroulement du jeu et son interaction avec le GUI
	*/
	public void run() {
		Jeu jeu = Jeu.getJeu();
		while (!jeu.parametrerTermine) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		variante.addEffet(tasDeCarteEnAttente.getTasDeCarte(), joueurs);
		while (!this.jeuTermine()) {
			Iterator<Joueur> it = getJoueurs().iterator();
			while (it.hasNext()) {
				Joueur j = it.next();
				if (j.aGagne()) {
					getJoueursGagne().add(j);
					it.remove();

					this.setChanged();
					this.notifyObservers("renouveller");
				}
			}

			if (getJoueurs().size() == 1) {
				getJoueursGagne().add(getJoueurs().get(0));
				break;
			}
			if (getTasDeCarteEnAttente().getTailleDeTas() > 0) {
				if (getJoueurActuel() instanceof JoueurPhysique) {
					this.setChanged(true);
					this.notifyObservers("carteAPoser");
					System.out.println("Carte de joueur physique: " + this.joueurActuel.getCartes());
					jeu.getJoueurActuel().setTourTermine(false);
					while (!jeu.getJoueurActuel().isTourTermine()) {
						try {
							Thread.sleep(500);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					jeu.getJoueurActuel().setTourTermine(false);
					if (jeu.getJoueurActuel().getPose()) {
						jeu.getJoueurActuel().setPose(false);
						jeu.getCarteActuelle().getEffectValide().validerSuperpower(jeu);
						try {
							Thread.sleep(500);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						while (!this.isEffetTermine()) {
							try {
								Thread.sleep(500);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}

				} else {
					jeu.joueurJoueUnTour();
				}
				jeu.renouvelerJouerActuel();
				this.setChanged(true);
				this.notifyObservers("renouveller");
			} else {
				jeu.renouvelerTasDeCarteEnattente();

			}
		}

		this.setChanged(true);
		this.notifyObservers("Fin");
		this.compterPoint();
		try {
			Thread.sleep(1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


/**paraméter le jeu*/
	public void paramtrerJeu() {
		if (jeuEnCours == false) {
			Jeu.setNombreDeJeux(this.validerUneSaisie(
					"Combien de jeux de cartes voulez vous joueur? Veuilliez saisir '1' ou '2'.", 1, 2));
			Jeu.setAvecJoker(this.validerUneSaisie(
					"Voulez-vous ajouter les carte 'Joker'? Veuilliez saisir '1' pour oui ou '0' pour non.", 0, 1));
			if (this.nombreDeJeux == 1) {
				Jeu.setNombreDeJoueurs(
						this.validerUneSaisie("Veuillez saisir le nombre de joueurs en total dans votre jeu.", 2, 8));
			} else {
				Jeu.setNombreDeJoueurs(
						this.validerUneSaisie("Veuillez saisir le nombre de joueurs en total dans votre jeu.", 2, 12));
			}
			Jeu.setVersionDeVariante(this.validerUneSaisie("Veuillez choisir la version de variante.", 0, 16));
			Jeu.setMethodeCompte(this.validerUneSaisie(
					"Veuillez choisir le methode de compter, 1 pour compte positif, 0 pour compte negatif", 0, 1));
			Jeu.setDifficulte(this.validerUneSaisie(
					"Veuillez choisir la difficulte,1 pour simple,2 pour moyenne,3 pour difficile.", 1, 3));
		}
	}

/**
* valider une saisie
* @param phrase le phrase s'affiche
* @param min le bord inférieur
* @param max le bord supérieur
* @return res le résultat de saisie*/
	public int validerUneSaisie(String phrase, int min, int max) {
		try {
			int res = demandeUser(phrase, max, min);
			return res;
		} catch (SaisiNonValideException e) {
			System.out.println("ERREUR!!" + e.getMessage());
			int res;
			do {
				System.out.println(phrase);
				res = scanner.nextInt();
			} while (res > max || res < min);
			return res;
		}
	}

/**
* demander à utilisateur de saisir
* @param phrase le phrase s'affiche
* @param min le bord inférieur
* @param max le bord supérieur
* @exception SaisiNonValideException exception spéciale
* @see exception.SaisiNonValideException
* @return resultat le résultat de saisie*/
	public int demandeUser(String phrase, int max, int min) throws SaisiNonValideException {
		System.out.println(phrase);
		int resultat = scanner.nextInt();
		if (resultat >= min && resultat <= max) {
			return resultat;
		} else {
			throw new SaisiNonValideException("Mauvaise saisie, la valeur doit etre entre " + min + " et " + max);
		}

	}

/**initialiser le jeu*/
	public void initialiser() {
		jeuEnCours = true;
		tasDeCarteEnAttente = new TasDeCarteEnAttente();
		setVariante(versionDeVariante);
		variante.addEffet(tasDeCarteEnAttente.getTasDeCarte(), joueurs);
		tasDeCartePosee = new TasDeCartePosee();
		initialiserJoueurs();
		distribuerCarte();
	}

/**ajouter les joueurs dans la liste*/
	public void initialiserJoueurs() {
		joueurs = new ArrayList<Joueur>();
		for (int i = 0; i < Jeu.getNombreDeJoueurs() - 1; i++) {
			JoueurVirtuel j = new JoueurVirtuel("jv" + i, "Joueur " + i, Jeu.getDifficulte());
			joueurs.add(j);
		}
		JoueurPhysique jp = new JoueurPhysique("jp", "wxw");
		joueurs.add(jp);

	}

	/**distribuer les cartes à joueurs*/
	public void distribuerCarte() {
		tasDeCarteEnAttente.melanger();
		Iterator<Joueur> it = joueurs.iterator();
		while ((!distributionEstTerminee())) {
			if (it.hasNext()) {
				Joueur j = it.next();
				Carte c = tasDeCarteEnAttente.getCarte();
				j.getCartes().add(c);
			} else {
				it = joueurs.iterator();
			}

		}
		System.out.println("Distribution terminee");
		Carte cartePosee = tasDeCarteEnAttente.getCarte();
		tasDeCartePosee.addCartePosee(cartePosee);
		this.setCarteActuelle(cartePosee);
		int position = (int) (Math.random() * (Jeu.getNombreDeJoueurs() - 1));
		this.setJoueurActuel(joueurs.get(position));

	}

/**
*@return res un boolean pour indiquer si la distribution de carte est terminee
*/
	public boolean distributionEstTerminee() {
		boolean res = false;
		int nbCarte = 8;
		if (Jeu.getNombreDeJoueurs() == 2) {
			switch (Jeu.getNombreDeJeux()) {
			case 1:
				nbCarte = 10;
				break;
			case 2:
				nbCarte = 15;
				break;
			}
		} else if (Jeu.getNombreDeJoueurs() == 3) {
			switch (Jeu.getNombreDeJeux()) {
			case 1:
				nbCarte = 8;
				break;
			case 2:
				nbCarte = 12;
				break;
			}
		} else {
			switch (Jeu.getNombreDeJeux()) {
			case 1:
				nbCarte = 6;
				break;
			case 2:
				nbCarte = 9;
				break;
			}

		}
		Joueur j = joueurs.get(Jeu.getNombreDeJoueurs() - 1);
		if (j.getCartes().size() == nbCarte) {
			res = true;
		}
		return res;
	}

/**effectuer le processus de piocher certaines cartes
*@param nbCarte le nombre de cartes à piocher*/
	public void joueurPiocher(int nbCarte) {
		Joueur jou = this.getJoueurActuel();
		if (this.getTasDeCarteEnAttente().getTailleDeTas() < nbCarte) {
			this.renouvelerTasDeCarteEnattente();
		}
		for (int i = 0; i < nbCarte; i++) {
			jou.piocher(this.getCarteDepuisTas());
		}
	}

/**
*@return un boolean qui indique si le jeu est terminé*/
	public boolean jeuTermine() {
		boolean res = false;
		if (methodeCompte == 0) {
			Iterator<Joueur> it = joueurs.iterator();
			while (it.hasNext()) {
				Joueur j = it.next();
				if (j.aGagne()) {
					res = true;
				}
			}
		} else {
			if (this.joueursGagne.size() > 2) {
				res = true;
			}
		}
		return res;
	}

/**les joueurs(physique ou virtuels) annoncent automatique "uno" ou des annoncements spécifiques en fonction de variante*/
	public void annoncer() {
		Iterator<Joueur> it = joueurs.iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			if (j.getCartes().size() == 1) {
				j.annoncer();
			}
		}
	}

/**calculer le point de chaque joueur*/
	public void compterPoint() {
		System.out.println("Point des joueurs");
		if (Jeu.methodeCompte == 0) {
			Iterator<Joueur> it = joueurs.iterator();
			while (it.hasNext()) {
				Joueur j = it.next();
				System.out.println(j.toString() + " : " + j.calculerPoint());
			}
		} else {
			this.joueursGagne.get(0).setPoint(50);
			this.joueursGagne.get(1).setPoint(20);
			this.joueursGagne.get(2).setPoint(10);
			StringBuffer sb = new StringBuffer();
			Iterator<Joueur> it = this.joueursGagne.iterator();
			while (it.hasNext()) {
				Joueur j = it.next();
				sb.append(j.getNom() + " : " + j.getPoint() + " points\n");
			}
			System.out.println(sb);
		}
	}

	/**renouveller le joueur sous contrainte de la direction*/
	public void renouvelerJouerActuel() {
		this.getJoueurActuel().getMyAction().agir(this.getJoueurActuel());
		int i = joueurs.indexOf(joueurActuel);
		int nbJoueurTotal = this.joueurs.size();
		if (croissante == true) {
			if (i + 1 < nbJoueurTotal/* Jeu.getNombreDeJoueurs() */) {
				this.joueurActuel = joueurs.get(i + 1);
			} else {
				this.joueurActuel = joueurs.get(0);
			}

		} else {
			if (i - 1 >= 0) {
				this.joueurActuel = joueurs.get(i - 1);
			} else {
				this.joueurActuel = joueurs.get(nbJoueurTotal/* Jeu.nombreDeJoueurs */ - 1);
			}
		}
	}

/**recharcher le tas de cartes en attentes*/
	public void renouvelerTasDeCarteEnattente() {
		this.getTasDeCarteEnAttente().addCartesPosees(this.getTasDeCartePosee());
		this.getTasDeCartePosee().clearCartePosee();
	}

/**gérer le déroulement du jeu*/
	public void derouler() {
		this.paramtrerJeu();
		this.initialiser();
		Iterator<Joueur> itJ = this.joueurs.iterator();
		while (itJ.hasNext()) {
			Joueur ja = itJ.next();
			System.out.println(ja.getCartes());
		}
		int nb = 1;

		while ((!this.jeuTermine())) {
			Iterator<Joueur> it = this.joueurs.iterator();
			while (it.hasNext()) {
				Joueur j = it.next();
				if (j.aGagne()) {
					this.joueursGagne.add(j);
					it.remove();
				}
			}
			if (this.joueurs.size() == 1) {
				this.joueursGagne.add(this.joueurs.get(0));
				break;
			}
			if (this.getTasDeCarteEnAttente().getTailleDeTas() > 0) {
				System.out.println(" ");
				System.out.println("C'est le tour " + nb);
				System.out.println(this.getJoueurs());
				nb++;
				joueurJoueUnTour();
				this.renouvelerJouerActuel();
			} else {
				this.renouvelerTasDeCarteEnattente();

			}
			// this.annoncer();
			System.out.println("nombre en attente" + this.getTasDeCarteEnAttente().getTailleDeTas());
			System.out.println("nombre de carte posee" + this.getTasDeCartePosee().getCartePosee().size());
		}
		this.compterPoint();
		// Jeu.getScanner().close();

		// jeu.initialiserJeu();
		// System.out.println(jeu.get);

	}

/**gérer les actions pour qu'un joueur joue un tour*/
	public void joueurJoueUnTour() {
		LinkedList<Carte> carteCandidate = this.getJoueurActuel().getCarteCandidate(this.getCarteActuelle());
		System.out.println("------Carte Acteulle------" + this.carteActuelle);
		if (carteCandidate.size() == 0) {
			this.getJoueurActuel().piocher(this.getCarteDepuisTas());
			System.out.println(this.getJoueurActuel().toString() + " pioche");
		} else {
			try {
				Carte c = this.getJoueurActuel().poserUneCarte(carteCandidate, this.getJoueurActuel().getCartes());
				jeu.getJoueurActuel().getCartes().remove(c);
				System.out.println(this.getJoueurActuel().toString() + " pose " + c.toString());
				this.setCarteActuelle(c);
				this.getTasDeCartePosee().addCartePosee(c);
				c.getEffectValide().validerSuperpower(this);
				System.out.println("       ");
				this.changed = true;
				jeu.notifyObservers("carteActuelle");
				Thread.sleep(1000);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void add(Observer o) {
		if (observers.contains(o)) {
			return;
		}
		observers.add(o);
	}

	public void notifyObservers(Object arg) {
		if (!changed) {
			return;
		}
		Iterator<Observer> it = observers.iterator();
		while (it.hasNext()) {
			Observer o = it.next();
			o.update(this, arg);
		}
		this.clearChanged();

	}

	public void clearChanged() {
		this.changed = false;
	}

	/**setter et getter
	 * @return boolean qui indique s'il y a un changement*/
	public boolean isChanged() {
		return this.changed;
	}

	public void setChanged(boolean b) {
		this.changed = b;
	}

	public boolean isJeuEnCours() {
		return jeuEnCours;
	}

	public void setJeuEnCours(boolean jeuEnCours) {
		this.jeuEnCours = jeuEnCours;
	}

	public Joueur getJoueurActuel() {
		return this.joueurActuel;
	}

	public void setJoueurActuel(Joueur j) {
		this.joueurActuel = j;
	}

	public Carte getCarteDepuisTas() {
		Carte c = this.tasDeCarteEnAttente.getCarte();
		return c;
	}

	public boolean aAssezDeCarte() {
		boolean res = true;
		if (this.getNbCartePiocher() > this.getTasDeCarteEnAttente().getTailleDeTas()) {
			res = false;
		}
		return res;
	}

	public int getNbCartePiocher() {
		return this.nbCartePiocher;
	}

	public void setNbcartePiocher(int nb) {
		this.nbCartePiocher = nb;
	}

	public static int getMethodeCompte() {
		return methodeCompte;
	}

	public static void setMethodeCompte(int methodeCompte) {
		Jeu.methodeCompte = methodeCompte;
	}

	public Carte getCarteActuelle() {
		return carteActuelle;
	}

	public void setCarteActuelle(Carte carteActuelle) {
		this.carteActuelle = carteActuelle;
	}

	public void setParametrerTermine(boolean b) {
		this.parametrerTermine = b;
	}

	public boolean getParametrerTermine() {
		return this.parametrerTermine;
	}

	public static int getDifficulte() {
		return difficulte;
	}

	public static void setDifficulte(int difficulte) {
		Jeu.difficulte = difficulte;
	}

	public static int getNombreDeJeux() {
		return nombreDeJeux;
	}

	public static void setNombreDeJeux(int nombreDeJeux) {
		Jeu.nombreDeJeux = nombreDeJeux;
	}

	public static int getNombreDeJoueurs() {
		return nombreDeJoueurs;
	}

	public static void setNombreDeJoueurs(int nombreDeJoueurs) {
		Jeu.nombreDeJoueurs = nombreDeJoueurs;
	}

	public static int getAvecJoker() {
		return avecJoker;
	}

	public static void setAvecJoker(int avecJoker) {
		Jeu.avecJoker = avecJoker;
	}

	public static int getVersionDeVariante() {
		return versionDeVariante;
	}

	public static void setVersionDeVariante(int versionDeVariante) {
		Jeu.versionDeVariante = versionDeVariante;
	}

	public ArrayList<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public static boolean isCroissante() {
		return croissante;
	}

	public static void setCroissante(boolean coissante) {
		Jeu.croissante = coissante;
	}

	public TasDeCarteEnAttente getTasDeCarteEnAttente() {
		return this.tasDeCarteEnAttente;
	}

	public void setTasDeCarteEnAttente(TasDeCarteEnAttente t) {
		this.tasDeCarteEnAttente = t;
	}

	public TasDeCartePosee getTasDeCartePosee() {
		return this.tasDeCartePosee;
	}

	public void setTasDeCartePosee(TasDeCartePosee t) {
		this.tasDeCartePosee = t;
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public static void setScanner(Scanner scanner) {
		Jeu.scanner = scanner;
	}

	public Variante getVariante() {
		return variante;
	}

	public void setVariante(Variante variante) {
		this.variante = variante;
	}

	public ArrayList<Joueur> getJoueursGagne() {
		return joueursGagne;
	}

	public ArrayList<Effet> getEffetEnAttente() {
		return effetEnAttente;
	}

	public void setEffetEnAttente(ArrayList<Effet> effetEnAttente) {
		this.effetEnAttente = effetEnAttente;
	}

	public ArrayList<Effet> getEffetDeJeu() {
		return effetDeJeu;
	}

	public void setEffetDeJeu(ArrayList<Effet> effetDeJeu) {
		this.effetDeJeu = effetDeJeu;
	}

	public void setEffetTermine(boolean b) {
		this.effetTermine = b;
	}

	public boolean isEffetTermine() {
		return this.effetTermine;
	}

	public void setVariante(int i) {
		switch (i) {
		case 0:
			variante = new Minimale(this);
			break;
		case 11:
			variante = new Monclar(this);
			break;
		case 1:
			variante = new Variante1(this);
			break;
		case 5:
			variante = new Variante5(this);
			break;
		case 2:
			variante = new Variante2(this);
			break;
		}

	}
}
