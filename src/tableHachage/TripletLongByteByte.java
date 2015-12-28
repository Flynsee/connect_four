package tableHachage;

public class TripletLongByteByte {
	long key;
	Byte value;
	Byte profondeur;
	TripletLongByteByte(long k, Byte v, Byte p){
		key=k;
		value=v;
		profondeur=p;
	}
	
	public void print(){
		System.out.println("Clef : "+key+" - Valeur : "+value+" - Prof : "+profondeur);
	}
}
