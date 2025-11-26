package io.github.entities;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import io.github.managers.WorldManager;

public class Entity {
    
    public float baseHealth;
    public float baseDefense;
    public float baseAtk;

    public Vector2 velocity;
    public Vector2 acceleration;
    public Texture texture;
    public Sprite sprite;
    public BodyDef bodyDef;
    public Body body;
    private boolean dead;
    private boolean isInivinsible = false;

    public Entity(float x, float y, String bodyType, String spriteImage, float size, boolean isBullet){

        // Collider Setup
        bodyDef = new BodyDef();
        
        dead = false;

        switch(bodyType) {
            case "Static":
                bodyDef.type = BodyDef.BodyType.StaticBody;
                break;
            case "Dynamic":
                bodyDef.type = BodyDef.BodyType.DynamicBody;
                break;
            case "Kinematic":
                bodyDef.type = BodyDef.BodyType.DynamicBody;
                break;
            default:
                bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        
        
        bodyDef.position.set(x, y);

        if(isBullet){
            bodyDef.bullet = true;
        }


        body = WorldManager.getWorld().createBody(bodyDef);
        
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);

        // Sprite setup
        texture = new Texture(spriteImage);
        sprite = new Sprite(texture);
        UpdateSpritePosition();
        sprite.setSize(size, size);
        // texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        // body.setUserData(this);

    }

    
    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, getPosition().x, getPosition().y);
    }

    public void dispose(){
        texture.dispose();
    }

    public Body getBody(){
        return body;
    }

    public void handleDamage(float damage){

        if(isInivinsible) System.out.println("Invisinble");

        float dmg = damage * (100f / (100f + baseDefense));

        float totalDmg = Math.max(dmg, 1f);

        baseHealth -= totalDmg;
        isInivinsible = true;
        System.out.println(baseHealth);
        
        sprite.setColor(Color.RED);

        if(baseHealth <= 0){
            kill();
        }

    }

    public void invinsibilityFrames(float amount, float delta){
        if(isInivinsible){
            amount -= delta;
            if(amount <= 0 ){
                isInivinsible = false;
            }
        }
    }

    public boolean getIsInvinsible(){
        return isInivinsible;
    }

    public void setStats(float hp, float atk, float def){
        this.baseHealth = hp;
        this.baseAtk = atk;
        this.baseDefense = def;
    }

    public void setHealth(float hp){
        this.baseHealth = hp;
    }

    public float getBaseAtk(){
        return baseAtk;
    }

    public float getHealth(){
        return baseHealth;
    }

    public boolean isDead(){
        return dead;
    }

    public void kill(){
        dead = true;
    }


    public Vector2 getTargetDirection(Vector2 target){        
        return target.sub(body.getPosition()).nor();
    }

    public void UpdateSpritePosition(){
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2f, body.getPosition().y - sprite.getHeight() / 2);
    }



    

}
