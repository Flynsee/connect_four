package jeu;

public enum Resultat {
	DRAW, //représenté par 0
	WIN, // représenté par 1
	LOSS; // représenté par -1
	
	
	
	public int toInt(){
		switch(this){
		case LOSS:
			return -1;
		case DRAW:
			return 0;
		case WIN:
			return 1;
		default:
			return 0;
		}
	}
	
	
		
	public static Resultat toResultat(int b){
		switch(b){
		case -1:
			return LOSS;
		case 0:
			return DRAW;
		case 1:
			return WIN;
		default:
			return DRAW;
		}
	}
	
	public void print(){
		System.out.println(this);
	}
	
	

}
