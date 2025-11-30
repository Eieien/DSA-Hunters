package io.github.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.github.components.Gun;

public class Stack extends Enemy{
    
    private Array<Node> nodes;
    private int top = 0;
    public Stack(Vector2 position){
        super(position, "sprites/rectangleNode.png");
        UpdateSpritePosition();
        // setStats(100f, 25f, 15f);
        nodes = new Array<>();
        gun1 = new Gun("normal" , CATEGORY_PLAYER,"sprites/projectiles/bullet_circle.png", 2f, 0.2f, 10f, 1, 2);
        gun2 = new Gun("radial", CATEGORY_PLAYER , "sprites/projectiles/bullet.png",3f, 0.2f , getBaseAtk(), 35f, 4);
        addGun(gun1);
        addGun(gun2);

        int amountOfNodes = MathUtils.random(3, 6);
        for(int i = 0; i < amountOfNodes; i++){
            nodes.add(new Node(position));
        }

        
        top = amountOfNodes;
    }

    public void pop(){
        top--;
    }

    @Override
    public void render(SpriteBatch batch){
        for(Node n: nodes){
            n.UpdateSpritePosition();
            n.sprite.draw(batch);

        }
        
    }

    // @Override
    // public void Follow(Vector2 target){

    // }
}
