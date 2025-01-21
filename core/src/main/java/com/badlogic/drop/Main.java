package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Drop {

//    Texture backgroundTexture;
//    Texture bucketTexture;
//    Texture dropTexture;
//    Texture greenDrop;
//    Texture redDrop;
//    Sound dropSound;
//    Sound greenSound;
//    Music music;
//    SpriteBatch spriteBatch;
//    FitViewport viewport;
//    Sprite bucketSprite;
//    Array<Sprite> dropSprites;
//    Array<Sprite> greenSprites;
//    Array<Sprite> redSprites;
//    float dropTimer;
//    float greenTimer;
//    Rectangle bucketRectangle;
//    Rectangle dropRectangle;
//    Rectangle greenRectangle;
//
//
//    @Override
//    public void create() {
//        // Prepare your application here.
//        backgroundTexture = new Texture("background.png");
//        bucketTexture = new Texture("bucket.png");
//        dropTexture = new Texture("drop.png");
//        greenDrop = new Texture("greenDrop.png");
//        redDrop = new Texture("redDrop.png");
//        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
//        greenSound = Gdx.audio.newSound(Gdx.files.internal("greenSound.mp3"));
//        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
//        spriteBatch = new SpriteBatch();
//        viewport = new FitViewport(8, 5);
//        bucketSprite = new Sprite(bucketTexture);
//        bucketSprite.setSize(1, 1);
//        dropSprites = new Array<>();
//        redSprites = new Array<>();
//        greenSprites = new Array<>();
//        bucketRectangle = new Rectangle();
//        dropRectangle = new Rectangle();
//        greenRectangle = new Rectangle();
//        music.setLooping(true);
//        music.setVolume(.5f);
//        music.play();
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        // Resize your application here. The parameters represent the new window size.
//        viewport.update(width, height, true); //true centers camera
//    }
//
//    @Override
//    public void render() {
//        // Draw your application here.
//        input();
//        logic();
//        draw();
//    }
//
//    private void input() {
//        float speed = 4f;
//        float delta = Gdx.graphics.getDeltaTime();
//
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            bucketSprite.translateX(speed * delta);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            bucketSprite.translateX(-speed * delta);
//        }
//    }
//
//    private void logic() {
//        float worldWidth = viewport.getWorldWidth();
//        float worldHeight = viewport.getWorldHeight();
//
//        //to prevent bucket from going too far right
//        float bucketWidth = bucketSprite.getWidth();
//        float bucketHeight = bucketSprite.getHeight();
//
//        //prevents going out of screen
//        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));
//
//        float delta = Gdx.graphics.getDeltaTime(); //delta time
//
//        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight); //sets a rectangle encompassing bucket
//
//        for (int i = dropSprites.size - 1; i >= 0; i--) {
//            Sprite dropSprite = dropSprites.get(i);
//            float dropWidth = dropSprite.getWidth();
//            float dropHeight = dropSprite.getHeight();
//
//            dropSprite.translateY(-2f * delta);
//
//            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropSprite.getWidth(), dropSprite.getHeight()); //sets rectangle encompassing drop
//
//            if (dropSprite.getY() < -dropHeight) {
//                dropSprites.removeIndex(i);
//            }
//            //check if drop enters bucket:
//            else if (dropRectangle.overlaps(bucketRectangle)) {
//                dropSound.play(); // play rain collect sound
//                dropSprites.removeIndex(i);
//            }
//        }
//        for (int i = greenSprites.size - 1; i >= 0; i--) {
//            Sprite greenSprite = greenSprites.get(i);
//            float dropWidth = greenSprite.getWidth();
//            float dropHeight = greenSprite.getHeight();
//
//            greenSprite.translateY(-2f * delta);
//
//            dropRectangle.set(greenSprite.getX(), greenSprite.getY(), dropWidth, dropHeight);
//
//            if (greenSprite.getY() < -dropHeight) {
//                greenSprites.removeIndex(i);
//            } else if (dropRectangle.overlaps(bucketRectangle)) {
//                greenSound.play();
//                greenSprites.removeIndex(i);
//            }
//        }
//
//        dropTimer += delta;
//        if (dropTimer > 1f) {
//            dropTimer = 0;
//            createDroplet();
//        }
//        greenTimer += delta;
//        if (greenTimer > 15f) {
//            greenTimer = 13f;
//            createGreen();
//        }
//
//
//    }
//
//    private void draw() {
//        ScreenUtils.clear(Color.BLACK);
//        viewport.apply();
//        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
//        spriteBatch.begin();
//
//        float worldWidth = viewport.getWorldWidth(); //width of world = 8
//        float worldHeight = viewport.getWorldHeight(); //height of world = 5
//
//        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); //draw background
//        bucketSprite.draw(spriteBatch); //draw bucket, sprite has its own draw method...
//        for (Sprite dropSprite : dropSprites) {
//            dropSprite.draw(spriteBatch);
//        }
//        for (Sprite greenSprite : greenSprites) {
//            greenSprite.draw(spriteBatch);
//        }
//
//
//
//        spriteBatch.end();
//    }
//
//    private void createDroplet() {
//        float dropWidth = 1;
//        float dropHeight = 1;
//        float worldWidth = viewport.getWorldWidth();
//        float worldHeight = viewport.getWorldHeight();
//
//        Sprite dropSprite = new Sprite(dropTexture);
//        dropSprite.setSize(dropWidth, dropHeight);
//        //randomizes drop's x position:
//        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
//
//        dropSprite.setY(worldHeight);
//        dropSprites.add(dropSprite);
//    }
//
//    private void createGreen() {
//        float dropWidth = 1;
//        float dropHeight = 1;
//        float worldWidth = viewport.getWorldWidth();
//        float worldHeight = viewport.getWorldHeight();
//
//        Sprite greenSprite = new Sprite(greenDrop);
//        greenSprite.setSize(dropWidth, dropHeight);
//        greenSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
//
//        greenSprite.setY(worldHeight);
//        greenSprites.add(greenSprite);
//    }
//
//    private void createRed() {
//        float dropWidth = 1;
//        float dropHeight = 1;
//        float worldWidth = viewport.getWorldWidth();
//        float worldHeight = viewport.getWorldHeight();
//
//        Sprite redSprite = new Sprite(redDrop);
//        redSprite.setSize(dropWidth, dropHeight);
//        redSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));
//
//        redSprite.setY(worldHeight);
//        redSprites.add(redSprite);
//    }
//
//    @Override
//    public void pause() {
//        // Invoked when your application is paused.
//    }
//
//    @Override
//    public void resume() {
//        // Invoked when your application is resumed after pause.
//    }
//
//    @Override
//    public void dispose() {
//        // Destroy application's resources here.
//    }
//
//
//}
}
