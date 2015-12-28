package algorithmes;
import java.util.HashMap;

import jeu.Coup;
import jeu.Jeu;
import jeu.Resultat;


public abstract class Hachage {
	
	static void recherche(Jeu j, boolean player){
		HashMap<Long, Byte> table= new HashMap<Long, Byte>();
		timeHachage(j, player, table);
	}
	
	
	static void timeHachage(Jeu j, boolean player, HashMap<Long, Byte> table){
		long t1=System.currentTimeMillis();
		int r=hachage(j, player, -1, 1, table);
		long t2=System.currentTimeMillis();
		long t=t2-t1;
		System.out.println("Résultat obtenu : "+ Resultat.toResultat(r));
		System.out.println("L'algorithme Hachage a tourné en "+t+ " millisecondes, soit " +t/1000+ " secondes, ou encore " + t/60000 + " minutes.");
		
	}
	
	static int hachage(Jeu j, boolean player, int alpha1, int beta1, HashMap<Long, Byte> tableHachage){
		if (j.partieFinie){
			if(player){
				return j.etat;
			}
			else{
				return -j.etat;
			}
		}
		
		long adresse=j.adresse;
		
		int alpha=alpha1;
		int beta=beta1;
		
		Byte stock=null;
		if(adresse>=0){
			stock=tableHachage.get(adresse);
		}
		if (stock==null && j.adresseSymetrique>=0){
			adresse=j.adresseSymetrique;
			stock=tableHachage.get(adresse);
		}
		
		if (stock!=null){
			if(stock/3==0){
				return stock-1;
			}
			else if(stock/3==1){ //alors (m=0 ou m=1)
				if (beta==1){
					alpha=0;
				}
				else{
					return 0;
				}
			}
			else /*if(stock/3==3)*/{ //alors (m=-1 ou m=0)
				if (alpha==-1){
					beta=0;
				}
				else{
					return 0;
				}
			}
			/*else{
				throw new DataFormatException();
			}*/
		}
		
		
		int t;
		int m=alpha;
		int i=0;
		
		while(i<j.courante.largeur && m<beta){
			if(j.jouer(new Coup(player, ordre(i,j.courante.largeur)))){
				t=-hachage(j, !player, -beta	, -m,  tableHachage);
				j.annuler();
				m=Math.max(m,t);
			}
			i++;		
		}
		
		
		if(m==1 || m==-1 || (beta==1 && alpha==-1)){
			tableHachage.put(adresse,(byte)(m+1));
		}
		else{
			tableHachage.put(adresse, (byte) (3*(beta+alpha+2))); //vaut 3*3=9 si (m=0 ou -1) et vaut 3*1=3 si (m=0 ou m=1)
		}

		
		return m;
	}
		
	
	
	
	
	
	
	

static int ordre(int i, int largeur ){
	

	if(i%2==1){
		return (largeur+i)/2;
	}
	else{
		return (largeur-i-1)/2;
	}
}

}
