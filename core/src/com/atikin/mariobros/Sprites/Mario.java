package com.atikin.mariobros.Sprites;

import com.atikin.mariobros.MarioBros;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Mario extends Sprite {
    public World world;
    public Body b2body;

    public Mario(World world) {
        // Inicializar valores padrão
       this.world = world;
       defineMario();
    }

    public void defineMario() {
        // Definição básica de objeto "corpo" e de suas dimensões
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
