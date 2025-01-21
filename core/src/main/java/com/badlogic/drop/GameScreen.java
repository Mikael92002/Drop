package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.w3c.dom.css.Rect;

public class GameScreen implements Screen {
    final Drop game;
    Texture backgroundTexture;
    Texture bucketTexture;
    Texture dropTexture;
    Texture greenDrop;
    Texture redDrop;
    Sound dropSound;
    Sound greenSound;
    Music music;
    SpriteBatch spriteBatch;
    Sprite bucketSprite;
    Array<Sprite> dropSprites;
    Array<Sprite> greenSprites;
    Array<Sprite> redSprites;
    float dropTimer;
    float greenTimer;
    Rectangle bucketRectangle;
    Rectangle dropRectangle;
    Rectangle greenRectangle;
    Rectangle redRectangle;
    int blueDropsGathered;
    Vector2 touchPos;
    float timer;
    float redTimer;

    public GameScreen(final Drop game) {
        this.game = game;

        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        greenDrop = new Texture("greenDrop.png");
        redDrop = new Texture("redDrop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        greenSound = Gdx.audio.newSound(Gdx.files.internal("greenSound.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1, 1);
        dropSprites = new Array<>();
        redSprites = new Array<>();
        greenSprites = new Array<>();
        bucketRectangle = new Rectangle();
        dropRectangle = new Rectangle();
        greenRectangle = new Rectangle();
        redRectangle = new Rectangle();
        touchPos = new Vector2();
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float v) {
    input();
    logic();
    draw();

    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            game.viewport.unproject(touchPos);
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void logic() {
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        //to prevent bucket from going too far right
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        //prevents going out of screen
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

        float delta = Gdx.graphics.getDeltaTime(); //delta time

        bucketRectangle.set(bucketSprite.getX(), bucketSprite.getY(), bucketWidth, bucketHeight); //sets a rectangle encompassing bucket

        for (int i = dropSprites.size - 1; i >= 0; i--) {
            Sprite dropSprite = dropSprites.get(i);
            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);

            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropSprite.getWidth(), dropSprite.getHeight()); //sets rectangle encompassing drop

            if (dropSprite.getY() < -dropHeight) {
                dropSprites.removeIndex(i);
            }
            //check if drop enters bucket:
            else if (dropRectangle.overlaps(bucketRectangle)) {
                dropSound.play(); // play rain collect sound
                dropSprites.removeIndex(i);
                blueDropsGathered+=100;
            }
        }
        for (int i = greenSprites.size - 1; i >= 0; i--) {
            Sprite greenSprite = greenSprites.get(i);
            float dropWidth = greenSprite.getWidth();
            float dropHeight = greenSprite.getHeight();

            greenSprite.translateY(-2f * delta);

            greenRectangle.set(greenSprite.getX(), greenSprite.getY(), dropWidth, dropHeight);

            if (greenSprite.getY() < -dropHeight) {
                greenSprites.removeIndex(i);
            } else if (greenRectangle.overlaps(bucketRectangle)) {
                greenSound.play();
                greenSprites.removeIndex(i);
                if(blueDropsGathered-100>=0){
                blueDropsGathered-=100;}
            }
        }
        for (int i = redSprites.size - 1; i >= 0; i--) {
            Sprite redSprite = redSprites.get(i);
            float dropWidth = redSprite.getWidth();
            float dropHeight = redSprite.getHeight();

            redSprite.translateY(-3f * delta);

            redRectangle.set(redSprite.getX(), redSprite.getY(), dropWidth, dropHeight);

            if (redSprite.getY() < -dropHeight) {
                redSprites.removeIndex(i);
            } else if (redRectangle.overlaps(bucketRectangle)) {
                greenSound.play();
                redSprites.removeIndex(i);
                if(blueDropsGathered-500>=0){
                blueDropsGathered-=500;}
            }
        }

        dropTimer += delta;
        if (dropTimer > 1f) {
            dropTimer = 0;
            createDroplet();
        }
        greenTimer += delta;
        if(greenTimer>5f && greenTimer<13f){

        }
        if (greenTimer > 15f) {
            greenTimer = 13f;
            createGreen();
        }
        redTimer+=delta;
        if(redTimer>25f){
            redTimer = 10f;
            createRed();
        }


    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        float delta = Gdx.graphics.getDeltaTime();

        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth(); //width of world = 8
        float worldHeight = game.viewport.getWorldHeight(); //height of world = 5

        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); //draw background
        bucketSprite.draw(game.batch);//draw bucket, sprite has its own draw method...
        timer +=delta;
        if(timer>10f && timer<17f){
            game.greenFont.setColor(Color.GREEN);
            game.greenFont.draw(game.batch,"Avoid the green drops!", 3.5f, worldHeight);
        }
        if(timer>23f && timer<27f){
            game.redFont.setColor(Color.RED);
            game.redFont.draw(game.batch,"DEFINITELY AVOID THE RED DROPS!!!", 1.5f, worldHeight);
        }
        game.font.draw(game.batch,"Score: " + blueDropsGathered,0,worldHeight);

        for (Sprite dropSprite : dropSprites) {
            dropSprite.draw(game.batch);
        }
        for (Sprite greenSprite : greenSprites) {
            greenSprite.draw(game.batch);
        }
        for(Sprite redSprite : redSprites){
            redSprite.draw(game.batch);
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    game.viewport.update(width,height,true);
    }
    private void createDroplet() {
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        //randomizes drop's x position:
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));

        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }

    private void createGreen() {
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        Sprite greenSprite = new Sprite(greenDrop);
        greenSprite.setSize(dropWidth, dropHeight);
        greenSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));

        greenSprite.setY(worldHeight);
        greenSprites.add(greenSprite);
    }

    private void createRed() {
        float dropWidth = 3;
        float dropHeight = 3;
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        Sprite redSprite = new Sprite(redDrop);
        redSprite.setSize(dropWidth, dropHeight);
        redSprite.setX(MathUtils.random(0f, worldWidth - dropWidth));

        redSprite.setY(worldHeight);
        redSprites.add(redSprite);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        dropSound.dispose();
        greenDrop.dispose();
        greenSound.dispose();
        music.dispose();
        dropTexture.dispose();
        bucketTexture.dispose();
    }
}
