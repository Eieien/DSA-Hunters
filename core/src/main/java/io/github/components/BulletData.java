package io.github.components;

public class BulletData {
    
    public float timeToLive;
    public float damage;
    public float bulletSpeed;
    public float spread;

    public BulletData(float speed, float ttL, float dmg, float spread){
        this.bulletSpeed = speed;
        this.timeToLive = ttL;
        this.damage = dmg;
        this.spread = spread;
    }

}
