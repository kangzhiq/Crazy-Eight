package exception;
/**L'exceptin sera levee quand le joueur physique saisi une valeur incoherente */
public class SaisiNonValideException extends Exception {
	private static final long serialVersionUID = 1L;

	public SaisiNonValideException(String m) {
		super(m);
	}

	public SaisiNonValideException() {
		System.out.println("Le saisi est incorrecte, il faut un entier");
	}

}
