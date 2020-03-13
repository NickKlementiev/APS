package com.atikin.mariobros.Screens;

import com.atikin.mariobros.MarioBros;
import com.atikin.mariobros.Scenes.Hud;
import com.atikin.mariobros.Sprites.Mario;
import com.atikin.mariobros.Tools.B2WorldCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    // Referências ao jogo e à tela principal
    private MarioBros game;

    // Variáveis básicas da classe PlayScreen
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    // Variáveis básicas de mapas (vindos de Tiled)
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Variáveis da biblioteca Box2d
    private World world;
    private Box2DDebugRenderer b2dr;

    private Mario player;

    public PlayScreen(MarioBros game) {
        this.game = game;

        // Criar câmera que segue o personagem
        gameCam = new OrthographicCamera();

        // Criar tamanho de tela dinamicamente proporcional ao tamanho da janela
        gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gameCam);

        // Criar instância de Hud (com informações básicas de jogo)
        hud = new Hud(game.batch);

        // Carregar o mapa e configurar o renderizador
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);

        // Centralizar a câmera de jogo ao início
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        // Criar o mundo Box2D, colocando gravidade no eixo vertical e permitindo o repouso dos objetos do tipo corpo
        world = new World(new Vector2(0, -10), true);

        // Permitir a saída de linhas de debug
        b2dr = new Box2DDebugRenderer();

        // Separação de código Box2D
        new B2WorldCreator(world, map);

        // Criar o personagem principal
        player = new Mario(world);
    }

    @Override
    public void show() {

    }

    // Configurar controles básicos
    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            //gameCam.position.x -= 100 * dt / MarioBros.PPM;
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            //gameCam.position.x += 100 * dt / MarioBros.PPM;
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }

    }

    public void update(float dt) {
        // Receber movimentos de antemão
        handleInput(dt);

        // Configura 1 passo na simulação física (60 passos por segundo)
        world.step(1/60f, 6, 2);

        gameCam.position.x = player.b2body.getPosition().x;

        // Atualizar a câmera do jogo com a movimentação do personagem
        gameCam.update();

        // Renderizar apenas o que é visto pela câmera
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        // Separar a lógica de atualização da lógica de renderização
        update(delta);

        // Limpar a tela de jogo com a cor preta
        Gdx.gl.glClearColor(0, 0, 0 , 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renderizar o mapa principal
        renderer.render();

        // Renderizar linhas de debug da biblioteca Box2D
        b2dr.render(world, gameCam.combined);

        // Não renderizar por onde o Hud estaria "passando"
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Atualizar o tamanho da tela do jogo em caso de mudanças
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
