package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class DistBox2D extends ApplicationAdapter {
	// глобальные константы
	public static final float WORLD_WIDTH = 16, WORLD_HEIGHT = 9;
	public static final int TYPE_CIRCLE = 0, TYPE_BRICK = 1;

	// системные объекты
	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;

	// ресурсы
	Texture imgBox;
	Texture imgBoxBlue;
	Texture imgCircle;
	Texture imgLoot;

	// собственные объекты и переменные
	StaticBody floor;
	StaticBody wallLeft, wallRight;

	KinematicBody platform;

	DynamicBody[] loot = new DynamicBody[33];
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		world = new World(new Vector2(0, -9.8f), true);
		debugRenderer = new Box2DDebugRenderer();

		imgBox = new Texture("purplebox.png");
		imgBoxBlue = new Texture("bluebox.png");
		imgCircle = new Texture("arrowBlue.png");

		floor = new StaticBody(world, 8, 0.6f, 15, 1);
		wallLeft = new StaticBody(world, 0.6f, 5, 1, 7);
		wallRight = new StaticBody(world, 16-0.6f, 5, 1, 7);
		platform = new KinematicBody(world, 0, 3, 4, 0.5f);
		for (int i = 0; i < loot.length; i++) {
			if(i<20) {
				loot[i] = new DynamicBody(world, 8 + MathUtils.random(-0.01f, 0.01f), 8 + i * 2, 0.4f);
			}
			else {
				loot[i] = new DynamicBody(world, 8 + MathUtils.random(-0.01f, 0.01f), 8 + i * 2, 1.2f, 0.6f);
			}
		}
	}

	@Override
	public void render () {
		platform.move();

		ScreenUtils.clear(0.2f, 0, 0.3f, 1);
		debugRenderer.render(world, camera.combined);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(imgBoxBlue, floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
		batch.draw(imgBoxBlue, wallLeft.getX(), wallLeft.getY(), wallLeft.getWidth(), wallLeft.getHeight());
		batch.draw(imgBoxBlue, wallRight.getX(), wallRight.getY(), wallRight.getWidth(), wallRight.getHeight());

		batch.draw(imgBoxBlue, platform.getX(), platform.getY(), platform.getWidth()/2, platform.getHeight()/2,
				platform.getWidth(), platform.getHeight(), 1, 1, platform.getAngle(), 10, 10, 20, 20, false, false);

		for(DynamicBody b: loot){
			if(b.type == TYPE_CIRCLE) imgLoot = imgCircle;
			else if (b.type == TYPE_BRICK) imgLoot = imgBox;

			batch.draw(imgLoot, b.getX(), b.getY(), b.getWidth()/2, b.getHeight()/2,
					b.getWidth(), b.getHeight(), 1, 1, b.getAngle(), 0, 0, 500, 500, false, false);
		}

		batch.end();
		world.step(1/60f, 6, 2);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgBox.dispose();
		imgBoxBlue.dispose();
		imgCircle.dispose();
		world.dispose();
		debugRenderer.dispose();
	}
}
