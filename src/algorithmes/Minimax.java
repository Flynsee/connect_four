package algorithmes;
import jeu.Coup;
import jeu.Jeu;
import jeu.Resultat;


public abstract class Minimax {
	
	static void recherche(Jeu j, boolean player){
		long t1=System.currentTimeMillis();
		int r=minimax(j, player);
		long t2=System.currentTimeMillis();
		long t=t2-t1;
		System.out.println("Résultat obtenu : "+ Resultat.toResultat(r));
		System.out.println("L'algorithme Minimax a tourné en "+t+ " millisecondes, soit " +t/1000+ " secondes, ou encore " + t/60000 + " minutes.");
		
	}
	
	
	
	static int minimax(Jeu j, boolean player){
		int m;
		if(j.partieFinie){
			return j.etat;
		}
		
		
		if(player){
			m=-1;
			for(int i=0; i<j.courante.largeur; i++){
				if(j.jouer(new Coup(true, i))){
					m=Math.max(m,minimax(j, false));
					j.annuler();
				}		
			}
			return m;
		}
		else{
			m=1;
			for(int i=0; i<j.courante.largeur; i++){
				if(j.jouer(new Coup(false, i))){
					m=Math.min(m,minimax(j, true));
					j.annuler();
				}		
			}
			return m;
		}
		
		
	}
	
	
}
