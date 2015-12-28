package tableHachage;
public class Table {

	public TripletLongByteByte[] tableVal;
	static int tailleMax = (int) Math.pow(3, 17);
	static int inverseFacteurChargeMax = 2;
	public int taille;
	public int collision;
	boolean tableComplete;
	
	public Table() {
		tableVal = new TripletLongByteByte[tailleMax];
		taille = 0;
		tableComplete=false;
	}


	public CoupleByteByte get(long k) {
		int i = 0;
		int adresse = collision(k, 0);
		if (adresse < 0) {
			return null;
		}
		while (tableVal[adresse] != null && tableVal[adresse].key != k) {
			i++;
			adresse = collision(k, i);
		}
		if (i < tailleMax && tableVal[adresse] != null) {
			return new CoupleByteByte(tableVal[adresse].value,
					tableVal[adresse].profondeur);
		}
		return null;
	}

	public void put(long k, Byte b, Byte p) {
		int i = 0;
		int adresse = collision(k, 0);
		if (adresse < 0 || tableComplete) {
			return;
		}
		while (tableVal[adresse]!=null && tableVal[adresse].key != k) {
			collision++;
			i++;
			adresse = collision(k, i);
		}
		if (tableVal[adresse]== null) {
			tableVal[adresse] = new TripletLongByteByte(k, b, p);
			taille++;
			if((taille * inverseFacteurChargeMax > tailleMax)){
				tableComplete=true;
			}
		}
	}

	public int collision(long k, int i) {
		return (int) ( ( (  (k+i*(i+1))    )+  31*(k/tailleMax)) % tailleMax);
	}

}
