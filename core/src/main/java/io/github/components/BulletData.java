package io.github.components;

public class BulletData {
    
    private float timeToLive;
    private float damage;
    private float bulletSpeed;
    private String sprite;
    private short category;

    public BulletData(String sprite, short target, float speed, float ttL, float dmg){
        this.sprite = sprite;
        this.bulletSpeed = speed;
        this.timeToLive = ttL;
        this.damage = dmg;
        this.category = target;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }



}
