package utiles;

import java.io.Serializable;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
//para poder guardar las posiciones de los materiales
public class MaterialState implements Serializable{
	private float posX;
	private float posY;
	private String clase;
	
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public float getPosX() {
		return posX;
	}
	public void setPosX(float posX) {
		this.posX = posX;
	}
	public float getPosY() {
		return posY;
	}
	public void setPosY(float posY) {
		this.posY = posY;
	}

}
