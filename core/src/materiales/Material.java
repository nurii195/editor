package materiales;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import utiles.Constants;
import utiles.MaterialState;

/**
 * Created by Mary on 04/03/2016.
 */
public abstract class Material {

    protected Body body;
    private float x;
    private float y;

    private float movedx;
    private float movedy;

    
    public abstract BodyDef bodyDef();
    public abstract Sprite sprite();
    public abstract FixtureDef fixtureDef();
    public abstract void disposeShape();

    public void init(World world){
        body = world.createBody(bodyDef());
        body.createFixture(fixtureDef());
        disposeShape();
        sprite().setOrigin(sprite().getWidth()/2.0f, sprite().getHeight()/2.0f);
        
    }

    /**
     *
     * @param x metros
     * @param y metros
     */
    public void move(float x, float y){
    	this.movedx = x;
    	this.movedy = y;
    	
        this.x = x + (sprite().getWidth()) * Constants.PIXELS_TO_METERS;
        this.y = y + (sprite().getHeight()) * Constants.PIXELS_TO_METERS;
        body.setTransform(x,y, body.getAngle());
        //sprite().setPosition(this.x,this.y);
        System.out.println("move " + x + ":" + y);
    }

    /**
     *
     * @param grados en grados
     */
    public void rotar(float grados){
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.toRadians(grados));
        sprite().setRotation(grados);
    }

    public void act(){
        sprite().setPosition((body.getPosition().x * Constants.METERS_TO_PIXELS) - sprite().getWidth() / 2.0f, +
                (body.getPosition().y * Constants.METERS_TO_PIXELS) - sprite().getHeight() / 2.0f);
        sprite().setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void draw(Batch batch){
        sprite().draw(batch);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getAncho(){
        return sprite().getWidth()*Constants.PIXELS_TO_METERS;
    }

    public float getAlto(){
        return sprite().getHeight()*Constants.PIXELS_TO_METERS;
    }
    
    //defino la posicion de los materiales
    public MaterialState getState() {
    	MaterialState state = new MaterialState();
    	
    	state.setClase(this.getClass().getName());
    	state.setPosX(this.movedx);
    	state.setPosY(this.movedy);
    	
    	System.out.println("getState " + x + ":" + y);
    	
    	return state;
    }
    
    public static Material fromState(MaterialState state, World world) {
    	try {
	    	Material material = (Material) Class.forName(state.getClase()).newInstance();
	    	material.init(world);
	    	material.move(state.getPosX(), state.getPosY());
	    	
	    	System.out.println("fromState " + state.getPosX() + ":" + state.getPosY());
	    	
	    	return material;
    	}
    	catch (Exception e) {
			return null;
		}
    }
}
