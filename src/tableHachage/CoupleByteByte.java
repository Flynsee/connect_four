package tableHachage;
public class CoupleByteByte {
	public Byte value;
	public Byte profondeur;

	CoupleByteByte(Byte v, Byte p) {
		profondeur = p;
		value = v;
	}

	public void print() {
		if (this != null) {
			System.out.println("Valeur : " + value + " - Prof : " + profondeur);
		}
	}
}
