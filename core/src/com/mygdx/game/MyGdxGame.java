package com.mygdx.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.rmi.CORBA.Util;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import materiales.Cuadrado;
import materiales.Estructura;
import materiales.Gatito;
import materiales.TablaHorizontal;
import materiales.TablaVertical;
import utiles.Constants;
import utiles.MaterialState;
import utiles.StructureState;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	World world;
	Box2DDebugRenderer renderer;
	OrthographicCamera cam;
	boolean simulate = true;
	int material = 1;
	Sprite sprite;
	

	
	 private Stage stage;
	 Estructura estructura = new Estructura();
	 
	 Sprite cuadrado;
	 Sprite tablaH;	 
	 Sprite tablaV;
	 Sprite gato;
	 Sprite currentSprite;
	 
	@Override
	public void create () {
		
		  cuadrado = new Cuadrado().sprite();
		  tablaH = new TablaHorizontal().sprite();
		  tablaV = new TablaVertical().sprite();
		  gato = new Gatito().sprite();
		  currentSprite = cuadrado;
		  
		cuadrado.setOrigin(cuadrado.getWidth()/2.0f, cuadrado.getHeight()/2.0f);
		tablaH.setOrigin(tablaH.getWidth()/2.0f, tablaH.getHeight()/2.0f);
		tablaV.setOrigin(tablaV.getWidth()/2.0f, tablaV.getHeight()/2.0f);
		
		
		batch = new SpriteBatch();
		sprite= new Sprite();

		world = new World(new Vector2(0, -9.8f), true);
		renderer = new Box2DDebugRenderer();
		crearSuelo();
		stage = new Stage(new ScreenViewport(new OrthographicCamera()));
		cam =  (OrthographicCamera) stage.getCamera();
		
		StructureState estados = loadEstados();
		if(estados != null) {
			estructura = Estructura.fromState(estados, world);
		}

		stage.addActor(estructura);
		
		Gdx.input.setInputProcessor(this);
	}

	private void crearSuelo() {
		BodyDef bDef = new BodyDef();
		bDef.type = BodyType.StaticBody;
		bDef.position.set(0, -0.05f);

		Body body = world.createBody(bDef);

		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(17f, 0.05f);
		fdef.shape = shape;

		body.createFixture(fdef);
		shape.dispose();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		cam.setToOrtho(false);
		cam.translate(-width / 2.0f, -height / 2.0f);
	}
	
	private void spawnCaja() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();

		Vector3 v3 = cam.unproject(new Vector3(x, y, 0));

		float xMetros = v3.x / Constants.METERS_TO_PIXELS;
		float yMetros = v3.y / Constants.METERS_TO_PIXELS;

		crearCaja(xMetros, yMetros);
	
	}



	private void crearCaja(float xMetros, float yMetros) {
		switch (material) {
		case 1:
			Cuadrado cuadro = new Cuadrado();
			cuadro.init(world);
			cuadro.move(xMetros, yMetros);	
			estructura.materials.add(cuadro);
			
			break;

		case 2:
			TablaHorizontal tablaH = new TablaHorizontal(false);
			tablaH.init(world);
			tablaH.move(xMetros, yMetros);
			estructura.materials.add(tablaH);
			break;
		case 3:
			TablaVertical tablaV = new TablaVertical(false);
			tablaV.init(world);
			tablaV.move(xMetros, yMetros);
			estructura.materials.add(tablaV);
			break;
		case 4:
			Gatito gato = new Gatito();
			gato.init(world);
			gato.move(xMetros, yMetros);
			estructura.materials.add(gato);
			break;
		}
	}
	
	@Override
	public void render () {
//		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//		shapeRenderer.setProjectionMatrix(camera.combined);
//		shapeRenderer.begin(ShapeType.FilledCircle);
//		shapeRenderer.setColor(new Color(0, 1, 0, 0.5f));
//		shapeRenderer.filledCircle(470, 45, 10);
//		shapeRenderer.end();
//		Gdx.gl.glDisable(GL10.GL_BLEND);
//		
//		Gdx.gl.glEnable(GL10.GL_BLEND);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyJustPressed(Keys.P)) {
			simulate = !simulate;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Q)) {
			material = 1;
			currentSprite = cuadrado;
			moverSprite();
		}
		if(Gdx.input.isKeyJustPressed(Keys.W)) {
			material = 2;
			currentSprite = tablaH;
			moverSprite();
		}
		if(Gdx.input.isKeyJustPressed(Keys.E)) {
			material = 3;
			currentSprite = tablaV;
			moverSprite();
		}
		if(Gdx.input.isKeyJustPressed(Keys.R)) {
			material = 4;
			currentSprite = gato;
			moverSprite();
		}
		
		if(simulate) {
			world.step(1 / 60.0f, 6, 2);
		}
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	
		
		Matrix4 matrix4  = batch.getProjectionMatrix().cpy().scl(Constants.METERS_TO_PIXELS, Constants.METERS_TO_PIXELS,
                0);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        ((OrthographicCamera) stage.getCamera()).setToOrtho(false);

        stage.draw();
		

		renderer.render(world, matrix4);
		
		batch.begin();
		currentSprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		guardarEstdos(estructura);
		batch.dispose();
		world.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT)
			spawnCaja();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		moverSprite();
		 
		return true;
		
	}

	private void moverSprite() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();

		Vector3 v3 = cam.unproject(new Vector3(x, y, 0));

		currentSprite.setPosition(v3.x - currentSprite.getWidth() / 2.0f, v3.y - currentSprite.getHeight() / 2.0f);
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	private void guardarEstdos(Estructura str) {
		File f = new File("estados.dat");

		ObjectOutputStream out = null;
		try {

			FileOutputStream stream = new FileOutputStream(f);
			BufferedOutputStream buff = new BufferedOutputStream(stream);
			out = new ObjectOutputStream(buff);
			out.writeObject(str.getEstado());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	private StructureState loadEstados() {
		
		File f = new File("estados.dat");
		ObjectInputStream in = null;
		try {
			FileInputStream stream = new FileInputStream(f);
			BufferedInputStream buff = new BufferedInputStream(stream);
			in = new ObjectInputStream(buff);
			StructureState str = (StructureState) in.readObject();
			return str;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return null;
	}
}
