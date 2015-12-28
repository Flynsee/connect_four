package algorithmes;
import tableHachage.CoupleByteByte;
import tableHachage.Table;
import jeu.Coup;
import jeu.Jeu;
import jeu.Resultat;

public abstract class SansHashMap {

	static void recherche(Jeu j, boolean player) {
		Table table = new Table();
		timeProfondeur(j, player, table);
	}

	static void timeProfondeur(Jeu j, boolean player, Table table) {
		long t1 = System.currentTimeMillis();
		int r = profondeur(j, player, table);
		long t2 = System.currentTimeMillis();
		long t = t2 - t1;
		System.out.println("Résultat obtenu : " + Resultat.toResultat(r));
		System.out.println("L'algorithme SansHashMap a tourné en " + t
				+ " millisecondes, soit " + t / 1000 + " secondes, ou encore "
				+ t / 60000 + " minutes.");

	}

	static int profondeur(Jeu j, boolean player, Table table) {
		int temp = 0;
		int profondeurMax = j.courante.hauteur * j.courante.largeur;
		for (int i =100; i < profondeurMax; i++) {
			temp = auxProfondeur(j, player, -1, 1, table, i, i);
			if (temp != 0) {
				return temp;
			}
		}

		return auxProfondeur(j, player, -1, 1, table, profondeurMax, profondeurMax);
	}

	static int auxProfondeur(Jeu j, boolean player, int alpha1, int beta1,
			Table table, int profondeur, int profondeurInitiale) {

		if (j.partieFinie) {
			if (player) {
				return j.etat;
			} else {
				return -j.etat;
			}
		}

		if (profondeur == 0) {
			return 0;
		}

		long adresse = j.adresse;

		int alpha = alpha1;
		int beta = beta1;

		CoupleByteByte stock = null;
		if (adresse >= 0) {
			stock = table.get(adresse);
		}
		if (stock == null && j.adresseSymetrique >= 0) {
			adresse = j.adresseSymetrique;
			stock = table.get(adresse);
		}

		if (stock != null && stock.profondeur==profondeurInitiale) {
			if (stock.value / 3 == 0) {
				return stock.value - 1;
			} else if (stock.value / 3 == 1) { // alors (m=0 ou m=1)
				if (beta == 1) {
					alpha = 0;
				} else {
					return 0;
				}
			} else /* if(stock/3==3) */{ // alors (m=-1 ou m=0)
				if (alpha == -1) {
					beta = 0;
				} else {
					return 0;
				}
			}
			/*
			 * else{ throw new DataFormatException(); }
			 */
		} else if (stock != null && stock.value / 3 == 0 && stock.value != 1) {
			return stock.value - 1;
		}

		int t;
		int m = alpha;
		int i = 0;

		while (i < j.courante.largeur && m < beta) {
			if (j.jouer(new Coup(player, ordre(i, j.courante.largeur)))) {
				t = -auxProfondeur(j, !player, -beta, -m, table, profondeur - 1,profondeurInitiale);
				j.annuler();
				m = Math.max(m, t);
			}
			i++;
		}

		if (m == 1 || m == -1 || (beta == 1 && alpha == -1)) {
			table.put(adresse, (byte) (m + 1), (byte) profondeurInitiale);
		} else {
			table.put(adresse, (byte) (3 * (beta + alpha + 2)), (byte) profondeurInitiale);
			// vaut 3*3=9 si (m=0 ou -1) et vaut 3*1=3 si(m=0 ou m=1)
		}

		return m;
	}

	static int ordre(int i, int largeur) {

		if (i % 2 == 1) {
			return (largeur + i) / 2;
		} else {
			return (largeur - i - 1) / 2;
		}
	}

}
