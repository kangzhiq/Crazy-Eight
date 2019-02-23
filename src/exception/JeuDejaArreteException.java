package exception;
/**Exception qui sera levee quand le joueur veut continuer a jouer un jeu qui est deja termine*/
public class JeuDejaArreteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public JeuDejaArreteException(String m) {
		super(m);
	}

	public JeuDejaArreteException() {
		System.out.println("Le jeu est deja arrte");
	}
}
