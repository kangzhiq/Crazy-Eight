package model.joueur;

import java.util.*;

import model.carte.Carte;

/**
 * <b>Description</b>
 * Dans ce stratégie, on  selectionne la carte avec effet s'il y en a, sinon poser ce qui est de forme la plus frequente*/
public class StrategieMoyenne implements StrategieDeJoueur {
	@Override
	public Carte poser(LinkedList<Carte> carteCandidate, LinkedList<Carte> myCartes) {
		if (carteCandidate.isEmpty()) {
			return null;
		} else {
			int nbPique = 0;
			int nbCoeur = 0;
			int nbCarreau = 0;
			int nbTrefle = 0;
			Iterator<Carte> it = myCartes.iterator();
			LinkedList<Carte> carteEffet = new LinkedList<Carte>();
			while (it.hasNext()) {
				Carte c = it.next();
				switch (c.getForme().getId()) {
				case 0:
					nbPique++;
					break;
				case 1:
					nbCoeur++;
					break;
				case 2:
					nbCarreau++;
					break;
				case 3:
					nbTrefle++;
					break;
				}
				if (!(c.getEffet().isEmpty())) {
					carteEffet.add(c);
				}
			}
			if (carteEffet.isEmpty()) {
				LinkedList<Carte> carteC = new LinkedList<Carte>();
				carteC.addAll(carteCandidate);
				int nbMax = choisirMax(nbPique, nbCoeur, nbCarreau, nbTrefle);
				Iterator<Carte> ite = /* carteCandidate */carteC.iterator();
				while (ite.hasNext()) {
					Carte c = ite.next();
					if (c.getForme().getId() != nbMax) {
						ite.remove();
					}
				}
				if (!carteC.isEmpty()) {
					carteCandidate = carteC;
				}
				int position = (int) ((carteCandidate.size() - 1) * Math.random());
				return carteCandidate.get(position);
			} else {
				int position = (int) ((carteEffet.size() - 1) * Math.random());
				return carteEffet.get(position);
			}
		}

	}

	@Override
	public void annoncer() {
		// TODO Auto-generated method stub

	}

	public int choisirMax(int a1, int a2, int a3, int a4) {
		/*
		 * if (a >= b && a >= c && a >= d) { return 0; } else if (b > a && b >= c && b
		 * >= d) { return 1; } else if (c > a && c > b && c >= d) { return 2; } else {
		 * return 3; }
		 */

		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(a1);
		al.add(a2);
		al.add(a3);
		al.add(a4);
		return al.indexOf(Collections.max(al));
	}

}
