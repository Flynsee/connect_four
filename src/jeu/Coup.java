package jeu;

public class Coup {
	public boolean joueur; //true: blanc, false: noir
	public int colonne;
	
	public Coup(boolean player, int c){
		joueur=player;
		colonne=c;
	}

	public Coup(Cellule player, int c) {
		if (player==Cellule.BLANC){
			joueur=true;
		}
		else{
			joueur=false;
		}
		colonne=c;
	}
	
	public Cellule getJoueur(){
		if (joueur){
			return Cellule.BLANC;
		}
		else{
			return Cellule.NOIR;
		}
	}
	
}
