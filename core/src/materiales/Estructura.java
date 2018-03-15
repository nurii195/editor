package materiales;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import materiales.Material;
import utiles.MaterialState;
import utiles.StructureState;

public class Estructura extends Actor{

	  public List<Material> materials = new ArrayList<Material>();
	  
	    public void act(float delta) {
	        super.act(delta);
	        for (Material m : materials) {
	            m.act();
	        }
	    }

	    public void draw(Batch batch, float parentAlpha) {
	        for (Material m : materials) {
	            m.draw(batch);
	        }
	    }
	    
	    public StructureState getEstado() {
	    	StructureState estado = new StructureState();
	    	
	    	for (Material material : materials) {
				estado.getMateriales().add(material.getState());
			}
	    	
	    	return estado;
	    }
	    
	    public static Estructura fromState(StructureState state, World world) {
	    	Estructura str = new Estructura();
	    	
	    	for (MaterialState matState : state.getMateriales()) {
	    		Material material = Material.fromState(matState, world);
	    		if(material != null)
	    			str.materials.add(material);
			}
	    	
	    	return str;
	    }

}
