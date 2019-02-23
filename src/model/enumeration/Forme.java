package model.enumeration;

/**
 *  <b>Description</b>
 *  La couleur de la carte
 *
 */
public enum Forme {
	Pique(0), Coeur(1),Carreau(2), Trefle(3), Joker(4);
	
	private final int id;
	public final static int size = Forme.values().length;
	
	Forme(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	
	

}
