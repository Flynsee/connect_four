package algorithmes;
import jeu.Coup;
import jeu.Jeu;
import jeu.Resultat;

public abstract class AlphaBeta {

	static void recherche(Jeu j, boolean player) {
		long t1 = System.currentTimeMillis();
		int r = alphaBeta(j, player, -1, 1);
		long t2 = System.currentTimeMillis();
		long t = t2 - t1;
		System.out.println("Résultat obtenu : " + Resultat.toResultat(r));
		System.out.println("L'algorithme AlphaBeta a tourné en " + t
				+ " millisecondes, soit " + t / 1000 + " secondes, ou encore "
				+ t / 60000 + " minutes.");

	}

	static int alphaBeta(Jeu j, boolean player, int alpha, int beta) {
		if (j.partieFinie) {
			if (player) {
				return j.etat;
			} else {
				return -j.etat;
			}
		}

		int t;
		int m = alpha;
		int i = 0;

		while (i < j.courante.largeur && m < beta) {
			if (j.jouer(new Coup(player, ordre(i, j.courante.largeur)))) {
				t = -alphaBeta(j, !player, -beta, -m);
				j.annuler();
				m = Math.max(m, t);
			}
			i++;
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
