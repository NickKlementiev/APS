package com.atikin.mariobros.Screens;

import com.atikin.mariobros.MarioBros;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu implements Screen {
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    private Game game;
    private boolean play;

    public MainMenu(Game game) {
        this.game = game;
        atlas = new TextureAtlas("mainmenu/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("mainmenu/uiskin.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(MarioBros.V_WIDTH, MarioBros.V_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        // stage deve ter controle sobre a entrada padrão
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();

        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

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

        mainTable.add(playButton);
        mainTable.center();
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.center();
        mainTable.row();
        mainTable.add(exitButton);
        mainTable.center();
        mainTable.row();

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        if (play) {
            game.setScreen(new PlayScreen((MarioBros) game));
            dispose();
        }

        Gdx.gl.glClearColor(.1f, .32f, .32f, 1);
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
        skin.dispose();
        atlas.dispose();
    }
}
