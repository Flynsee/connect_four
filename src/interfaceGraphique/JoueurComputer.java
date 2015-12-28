package interfaceGraphique;
import java.util.HashMap;

import algorithmes.*;
import jeu.*;

public class JoueurComputer {

	static void timeMeilleurCoup(Jeu j, boolean player) {
		long t1 = System.currentTimeMillis();
		Coup c = meilleurCoup(j, player);
		long t2 = System.currentTimeMillis();
		long t = t2 - t1;
		System.out.println("Résultat obtenu : " + c.colonne);
		System.out.println("L'algorithme MeilleurCoup a tourné en " + t
				+ " millisecondes, soit " + t / 1000 + " secondes, ou encore "
				+ t / 60000 + " minutes.");
	}

	static Coup meilleurCoup(Jeu j, boolean player) {
		HashMap<Long, Byte> table = new HashMap<Long, Byte>();
		return meilleurCoup(j, player, table);
	}

	static Coup meilleurCoup(Jeu j, boolean player,
			HashMap<Long, Byte> tableValeur) {
		int r;
		int c = -1;
		for (int i = 0; i < j.courante.largeur; i++) {
			if (j.jouer(new Coup(player, ordre(i, j.courante.largeur)))) {
				r = Profondeur.profondeur(j, !player, tableValeur);
				j.annuler();
				if (r == -1) {
					return new Coup(player, ordre(i, j.courante.largeur));
				} else if (r == 0 && c == -1) {
					c = i;
				}

			}
		}
		if(j.courante.isJouable(new Coup(player, ordre(c, j.courante.largeur)))){
			return new Coup(player, ordre(c, j.courante.largeur));
		}
		for(int i=0; i < j.courante.largeur; i++) {
			if(j.courante.isJouable(new Coup(player, ordre(i, j.courante.largeur)))){
				return new Coup(player, ordre(i, j.courante.largeur));
			}
		}
		return new Coup(player, ordre(c, j.courante.largeur));
	}

	static Coup meilleurCoupBis(Jeu j, boolean player,
			HashMap<Long, Byte> tableValeur) {

		int profondeurMax = j.courante.hauteur * j.courante.largeur;
		int temp = 0;
		boolean[] coupsPerdants = new boolean[j.courante.largeur];
		for (int l = 0; l < j.courante.largeur; l++) {
			coupsPerdants[l] = true;
		}
		HashMap<Long, Boolean> tableBool = new HashMap<Long, Boolean>();
		for (int k = 0; k < profondeurMax; k++) {
			for (int i = 0; i < j.courante.largeur; i++) {
				if (j.jouer(new Coup(player, ordre(i, j.courante.largeur)))) {
					temp = Profondeur.auxProfondeur(j, !player, -1, 1,
							tableValeur, tableBool, k);
					j.annuler();
					if (temp == -1) {
						return new Coup(player, ordre(i, j.courante.largeur));
					} else if (temp == 1) {
						coupsPerdants[i] = false;
					}
				}
			}
			tableBool.clear();
		}
		for (int i = 0; i < j.courante.largeur; i++) {
			if (j.jouer(new Coup(player, ordre(i, j.courante.largeur)))) {

				temp = Profondeur.auxProfondeur(j, !player, -1, 1, tableValeur,
						tableBool, profondeurMax);
				j.annuler();
				if (temp == -1) {
					return new Coup(player, ordre(i, j.courante.largeur));
				} else if (temp == 1) {
					coupsPerdants[i] = false;
				}
			}
		}
		for (int l = 0; l < j.courante.largeur; l++) {
			if (coupsPerdants[l]
					&& j.courante.isJouable(new Coup(player, ordre(l,
							j.courante.largeur)))) {
				return new Coup(player, ordre(l, j.courante.largeur));
			}
		}
		for (int l = 0; l < j.courante.largeur; l++) {
			if (j.courante.isJouable(new Coup(player, ordre(l,
					j.courante.largeur)))) {
				return new Coup(player, ordre(l, j.courante.largeur));
			}
		}
		return null;
	}

	static int ordre(int i, int largeur) {

		if (i % 2 == 1) {
			return (largeur + i) / 2;
		} else {
			return (largeur - i - 1) / 2;
		}
	}
}
