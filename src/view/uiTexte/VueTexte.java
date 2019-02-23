package view.uiTexte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import controller.EffetControleur;
import controller.JeuControleur;
import model.jeu.Jeu;
/**
 * Classe permettant le joueur physique de jouer dans le console en respectant le patron de conception MVC*/
public class VueTexte implements Observer, Runnable {
	/**Chaine de caractere reservee pour le joueur physique de quitter le jeu*/
	public static String QUITTER = "quit";
	/**Chaine de caractere reservee pour indiquer l'attente de console pour une saisie de joueur physique*/
	public static String PROMPT = "<";
	/**le jeu pour fournir des informations et pour effectuer des modifications*/
	private Jeu jeu;
	/**controleur de jeu*/
	private JeuControleur jeuControleur;
	/**controleur d'effet*/
	private EffetControleur effetControleur;
	/**Le thread de cette vueTexte*/
	private Thread t;
	/**boolean qui indique si c'est le tour de joueur physique afin d'activer le processus de console*/
	private boolean tourJoueurPhysique = false;

	/**
	 * Constructeur de VueTexte qui initilise le jeu, les controleurs et le thread
	 * @param j le jeu pour fournir des informations et pour effectuer des modifications*/
	public VueTexte(Jeu j) {
		this.jeu = j;
		this.jeu.add(this);
		this.jeuControleur=new JeuControleur(jeu);
		this.effetControleur =new EffetControleur(jeu);
		// a faire
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		String saisi = null;
		boolean quitter = false;
		System.out.println("Taper " + VueTexte.QUITTER + " pour quitter");
		do {
			if (this.tourJoueurPhysique) {
				saisi = this.lireChaine();
				if (saisi != null) {
					if (saisi.equals("quit")) {
						quitter = true;
					} if(saisi.equals("piocher")){
						this.getJeuControleur().joueurPhysiquePiocher();
						
					}else  {
						System.out.println("!!!"+saisi.toString()+"!!!");
						this.getJeuControleur().joueurPhysiquePoser(saisi.toString());
						this.getEffetControleur().joueurPhysiquePoser(saisi.toString());
					}
				}
				this.tourJoueurPhysique = false;
			}
			try {
				Thread.sleep(500);
			}catch(Exception e) {
				e.printStackTrace();
			}
		} while (quitter == false);
		System.exit(0);
	}

	/**Methode permettant de lire les chaines de caracteres saisies par le joueur physique*/
	private String lireChaine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String resultat = null;
		try {
			System.out.println(" ");
			System.out.println(">>>>>>>>>>>>>>>>VueTexte");
			System.out.println("Piocher:piocher ou Choisir la carte:[Pique(0), Coeur(1),Carreau(2), Trefle(3), Joker(4)]- valeur");
			System.out.println(VueTexte.PROMPT);
			resultat = br.readLine();
			System.out.println(" ");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return resultat;
	}

	@Override
	public void update(Observable instanceObservable, Object arg) {
		if (arg == "carteAPoser") {
			this.tourJoueurPhysique = true;
		}
		if (arg == "ObligeRejouer") {
			this.tourJoueurPhysique = true;
		}
	}

	public EffetControleur getEffetControleur() {
		return effetControleur;
	}

	public void setEffetControleur(EffetControleur effetControleur) {
		this.effetControleur = effetControleur;
	}

	public JeuControleur getJeuControleur() {
		return jeuControleur;
	}

	public void setJeuControleur(JeuControleur jeuControleur) {
		this.jeuControleur = jeuControleur;
	}

}
