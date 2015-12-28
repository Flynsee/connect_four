package jeu;
public enum Cellule {
	BLANC, NOIR, VIDE;

	public void print() {
		switch (this) {
		case BLANC:
			System.out.print("0");
			break;
		case NOIR:
			System.out.print("@");
			break;
		case VIDE:
			System.out.print(".");
			break;
		}
	}

	public Boolean isVide() {
		return this == VIDE;
	}

	static boolean isLigne(Cellule c1, Cellule c2, Cellule c3, Cellule c4) {
		if (c1 == c2 & c2 == c3 & c3 == c4 & (c1 != VIDE)) {
			return true;
		}
		return false;
	}

}
