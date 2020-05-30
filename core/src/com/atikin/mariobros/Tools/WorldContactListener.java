package com.atikin.mariobros.Tools;

import com.atikin.mariobros.Items.Item;
import com.atikin.mariobros.MarioBros;
import com.atikin.mariobros.Sprites.Enemy;
import com.atikin.mariobros.Sprites.InteractiveTileObject;
import com.atikin.mariobros.Sprites.Mario;
import com.badlogic.gdx.physics.box2d.*;

// Classe responsável por "ouvir" eventos de interação entre o personagem e os objetos do jogo
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // cDef significa a definição dos bits que vão interagir entre si
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        // Tratamento de cada interação
        switch (cDef) {
            case MarioBros.MARIO_HEAD_BIT | MarioBros.BRICK_BIT:
            case MarioBros.MARIO_HEAD_BIT | MarioBros.COIN_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                else if (fixB.getFilterData().categoryBits == MarioBros.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                break;
            case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
                else if (fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else if (fixB.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy) fixB.getUserData());
                else if (fixB.getFilterData().categoryBits == MarioBros.MARIO_BIT)
                    ((Mario) fixB.getUserData()).hit((Enemy) fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).onEnemyHit((Enemy) fixB.getUserData());
                ((Enemy)fixB.getUserData()).onEnemyHit((Enemy) fixA.getUserData());
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else if (fixB.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                else if (fixB.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
