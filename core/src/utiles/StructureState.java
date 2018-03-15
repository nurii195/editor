package utiles;

import java.io.Serializable;
import java.util.ArrayList;


// gestionar la estructura
public class StructureState implements Serializable{
	ArrayList<MaterialState> materiales = new ArrayList<MaterialState>();

	public ArrayList<MaterialState> getMateriales() {
		return materiales;
	}

	public void setMateriales(ArrayList<MaterialState> materiales) {
		this.materiales = materiales;
	}
}
