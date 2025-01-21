package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Drop extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont greenFont;
    public BitmapFont redFont;
    //to fit to screen:
    public FitViewport viewport;
    @Override
    public void create() {
        batch = new SpriteBatch();
        //libgdx default font
        font = new BitmapFont();
        greenFont = new BitmapFont();
        redFont = new BitmapFont();
        viewport = new FitViewport(8,5);

        greenFont.setUseIntegerPositions(false);
        greenFont.getData().setScale(viewport.getWorldHeight()/Gdx.graphics.getHeight());
        redFont.setUseIntegerPositions(false);
        redFont.getData().setScale(2*(viewport.getWorldHeight()/Gdx.graphics.getHeight()));
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight()/ Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
    }

    public void render(){
        super.render();
    }
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
