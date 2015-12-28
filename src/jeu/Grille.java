package jeu;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Grille {
	public int hauteur;
	public int largeur;
	public Cellule[][] tableau;

	Grille(String path) throws IOException {
		BufferedReader contenu = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(
						/*"L:/Documents/Informatique/Java/Workspace/INF431 Projet - Puissance 4/boards/"
						"C:/Users/François/Documents/Eclipse/INF431 Projet - Puissance 4/boards/"
								+*/
							 path))));
		String texte = contenu.readLine().toString();
		largeur = Integer.parseInt(texte.substring(0, 1));
		hauteur = Integer.parseInt(texte.substring(2, 3));
		tableau = new Cellule[hauteur][largeur];
		String symbole;
		for (int i = 0; i < hauteur; i++) {
			texte = contenu.readLine().toString();
			if (!texte.substring(0, 1).equals("0")
					&& !texte.substring(0, 1).equals(".")
					&& !texte.substring(0, 1).equals("@")) {
				texte = contenu.readLine().toString();
			}
			for (int j = 0; j < largeur; j++) {
				symbole = texte.substring(j, j + 1);
				switch (symbole) {
				case ".":
					tableau[hauteur - 1 - i][j] = Cellule.VIDE;
					break;
				case "0":
					tableau[hauteur - 1 - i][j] = Cellule.BLANC;
					break;
				case "@":
					tableau[hauteur - 1 - i][j] = Cellule.NOIR;
					break;
				}
			}
		}
		contenu.close();
	}

	Grille(String texte, boolean b) {
		largeur = Integer.parseInt(texte.substring(0, 1));
		hauteur = Integer.parseInt(texte.substring(2, 3));
		tableau = new Cellule[hauteur][largeur];
		String symbole;
		int l = 3;
		while (!texte.substring(l,l + 1).equals("0")
				&& !texte.substring(l,l + 1).equals(".")
				&& !texte.substring(l,l + 1).equals("@")) {
			l++;
		}
		for (int i = 0; i < hauteur; i++) {

		for (int j = 0; j < largeur; j++) {
			symbole = texte.substring(j+i*largeur+l, j+i*largeur+l+1);
			switch (symbole) {
			case ".":
				tableau[hauteur - 1 - i][j] = Cellule.VIDE;
				break;
			case "0":
				tableau[hauteur - 1 - i][j] = Cellule.BLANC;
				break;
			case "@":
				tableau[hauteur - 1 - i][j] = Cellule.NOIR;
				break;
			}
		}}
	}

	Grille(int h, int l) {
		hauteur = h;
		largeur = l;
		tableau = new Cellule[h][l];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < l; j++) {
				tableau[i][j] = Cellule.VIDE;
			}
		}
	}

	Grille(int h, int l, Cellule c) {
		hauteur = h;
		largeur = l;
		tableau = new Cellule[h][l];
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < l; j++) {
				tableau[i][j] = c;
			}
		}
	}

	public void print() {
		System.out.println();
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				tableau[hauteur - i - 1][j].print();
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean isJouable(Coup c) {
		return tableau[hauteur - 1][c.colonne].isVide();
	}

	public boolean isAnnulable(Coup c) {
		int colonne = c.colonne;
		int i = hauteur - 1;
		while (i > 0 & tableau[i][colonne].isVide()) {
			i--;
		}
		return tableau[i][colonne].isVide();
	}

	public int jouer(Coup c) {
		int colonne = c.colonne;
		int i = 0;
		while (i < hauteur & !tableau[i][colonne].isVide()) {
			i++;
		}
		tableau[i][colonne] = c.getJoueur();
		return i;
	}

	public int annuler(Coup c) {
		int colonne = c.colonne;
		int i = hauteur - 1;
		while (i > 0 & tableau[i][colonne].isVide()) {
			i--;
		}
		tableau[i][colonne] = Cellule.VIDE;
		return i;
	}

	public boolean isGagnant(Coup c) {
		int colonne = c.colonne;
		int i = 0;
		while (i < hauteur & !tableau[i][colonne].isVide()) {
			i++;
		}

		Cellule c1;
		Cellule c2;
		Cellule c3;
		Cellule c4;
		Cellule c5;
		Cellule c6;
		Cellule c7;

		if (i > 2) {
			c1 = tableau[i - 3][colonne];
		} else {
			c1 = Cellule.VIDE;
		}
		if (i > 1) {
			c2 = tableau[i - 2][colonne];
		} else {
			c2 = Cellule.VIDE;
		}
		if (i > 0) {
			c3 = tableau[i - 1][colonne];
		} else {
			c3 = Cellule.VIDE;
		}

		c4 = c.getJoueur();

		if (i < hauteur - 1) {
			c5 = tableau[i + 1][colonne];
		} else {
			c5 = Cellule.VIDE;
		}
		if (i < hauteur - 2) {
			c6 = tableau[i + 2][colonne];
		} else {
			c6 = Cellule.VIDE;
		}
		if (i < hauteur - 3) {
			c7 = tableau[i + 3][colonne];
		} else {
			c7 = Cellule.VIDE;
		}

		boolean colonneGagnante = (Cellule.isLigne(c1, c2, c3, c4)
				|| Cellule.isLigne(c2, c3, c4, c5)
				|| Cellule.isLigne(c3, c4, c5, c6) || Cellule.isLigne(c4, c5,
				c6, c7));

		if (colonne > 2) {
			c1 = tableau[i][colonne - 3];
		} else {
			c1 = Cellule.VIDE;
		}
		if (colonne > 1) {
			c2 = tableau[i][colonne - 2];
		} else {
			c2 = Cellule.VIDE;
		}
		if (colonne > 0) {
			c3 = tableau[i][colonne - 1];
		} else {
			c3 = Cellule.VIDE;
		}

		c4 = c.getJoueur();

		if (colonne < largeur - 1) {
			c5 = tableau[i][colonne + 1];
		} else {
			c5 = Cellule.VIDE;
		}
		if (colonne < largeur - 2) {
			c6 = tableau[i][colonne + 2];
		} else {
			c6 = Cellule.VIDE;
		}
		if (colonne < largeur - 3) {
			c7 = tableau[i][colonne + 3];
		} else {
			c7 = Cellule.VIDE;
		}

		boolean ligneGagnante = (Cellule.isLigne(c1, c2, c3, c4)
				|| Cellule.isLigne(c2, c3, c4, c5)
				|| Cellule.isLigne(c3, c4, c5, c6) || Cellule.isLigne(c4, c5,
				c6, c7));

		if (colonne > 2 & i > 2) {
			c1 = tableau[i - 3][colonne - 3];
		} else {
			c1 = Cellule.VIDE;
		}
		if (colonne > 1 & i > 1) {
			c2 = tableau[i - 2][colonne - 2];
		} else {
			c2 = Cellule.VIDE;
		}
		if (colonne > 0 & i > 0) {
			c3 = tableau[i - 1][colonne - 1];
		} else {
			c3 = Cellule.VIDE;
		}

		c4 = c.getJoueur();

		if (colonne < largeur - 1 & i < hauteur - 1) {
			c5 = tableau[i + 1][colonne + 1];
		} else {
			c5 = Cellule.VIDE;
		}
		if (colonne < largeur - 2 & i < hauteur - 2) {
			c6 = tableau[i + 2][colonne + 2];
		} else {
			c6 = Cellule.VIDE;
		}
		if (colonne < largeur - 3 & i < hauteur - 3) {
			c7 = tableau[i + 3][colonne + 3];
		} else {
			c7 = Cellule.VIDE;
		}

		boolean diagonaleMontanteGagnante = (Cellule.isLigne(c1, c2, c3, c4)
				|| Cellule.isLigne(c2, c3, c4, c5)
				|| Cellule.isLigne(c3, c4, c5, c6) || Cellule.isLigne(c4, c5,
				c6, c7));

		if (colonne > 2 & i < hauteur - 3) {
			c1 = tableau[i + 3][colonne - 3];
		} else {
			c1 = Cellule.VIDE;
		}
		if (colonne > 1 & i < hauteur - 2) {
			c2 = tableau[i + 2][colonne - 2];
		} else {
			c2 = Cellule.VIDE;
		}
		if (colonne > 0 & i < hauteur - 1) {
			c3 = tableau[i + 1][colonne - 1];
		} else {
			c3 = Cellule.VIDE;
		}

		c4 = c.getJoueur();

		if (colonne < largeur - 1 & i > 0) {
			c5 = tableau[i - 1][colonne + 1];
		} else {
			c5 = Cellule.VIDE;
		}
		if (colonne < largeur - 2 & i > 1) {
			c6 = tableau[i - 2][colonne + 2];
		} else {
			c6 = Cellule.VIDE;
		}
		if (colonne < largeur - 3 & i > 2) {
			c7 = tableau[i - 3][colonne + 3];
		} else {
			c7 = Cellule.VIDE;
		}

		boolean diagonaleDescendanteGagnante = (Cellule.isLigne(c1, c2, c3, c4)
				|| Cellule.isLigne(c2, c3, c4, c5)
				|| Cellule.isLigne(c3, c4, c5, c6) || Cellule.isLigne(c4, c5,
				c6, c7));

		return ligneGagnante || colonneGagnante || diagonaleMontanteGagnante
				|| diagonaleDescendanteGagnante;
	}

}
