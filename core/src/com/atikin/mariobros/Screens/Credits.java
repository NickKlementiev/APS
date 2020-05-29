package com.atikin.mariobros.Screens;

import com.atikin.mariobros.MarioBros;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Credits implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    private Game game;
    private boolean play;
    private TextButton playButton;
    private TextButton exitButton;

    public Credits(Game game) {
        this.game = game;
        atlas = new TextureAtlas("mainmenu/flat-earth-ui.atlas");
        skin = new Skin(Gdx.files.internal("mainmenu/flat-earth-ui.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table credits = new Table();
        credits.setFillParent(true);
        credits.top();

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.ORANGE);

        //Label phrase1Label = new Label("As abelhas não precisam de tantos obstáculos", skin, "button", Color.ORANGE);
        //Label phrase2Label = new Label("para fazerem algo que nos ajuda no final,", skin, "button", Color.ORANGE);
        //Label phrase3Label = new Label("valorizem nossas amigas abelhas!", skin, "button", Color.ORANGE);

        Label phrase1Label = new Label("As abelhas não precisam de tantos obstáculos", font);
        Label phrase2Label = new Label("para fazerem algo que nos ajuda no final,", font);
        Label phrase3Label = new Label("valorizem nossas amigas abelhas!", font);

        //Label thanksLabel = new Label("Muito obrigado por jogar!", skin, "button", Color.ORANGE);
        Label thanksLabel = new Label("Muito obrigado por jogar!", font);

        Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.bottom();

        playButton = new TextButton("Jogar novamente", skin);
        exitButton = new TextButton("Sair do jogo", skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                play = true;
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        credits.add(phrase1Label);
        credits.row();
        credits.add(phrase2Label);
        credits.row();
        credits.add(phrase3Label);
        credits.row();
        credits.add(thanksLabel);
        buttonTable.add(playButton).pad(7.5f);
        buttonTable.row();
        buttonTable.add(exitButton);
        buttonTable.row();

        stage.addActor(credits);
        stage.addActor(buttonTable);
    }

    @Override
    public void render(float delta) {
        if (play) {
            game.setScreen(new PlayScreen((MarioBros) game));
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }
}

