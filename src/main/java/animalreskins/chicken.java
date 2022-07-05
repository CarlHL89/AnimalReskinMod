package animalreskins;


import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.critters.CritterMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class chicken extends CritterMob {


    public static GameTexture chicken_texture;
    public static GameTexture chicken_shadow;

    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("chickenegg", 1, 3)});

    public chicken(){
        this.setSpeed(15.0F);
        this.setFriction(3.0F);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-12, -14, 24, 24);
        this.selectBox = new Rectangle(-16, -28, 32, 34);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        // Spawn flesh debris particles
        for (int i = 0; i < 4; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(), chicken_texture,
                    GameRandom.globalRandom.nextInt(5), // Randomize between the debris sprites
                    8,
                    32,
                    x, y, 20f, // Position
                    knockbackX, knockbackY // Basically start speed of the particles
            ), Particle.GType.IMPORTANT_COSMETIC);
        }
    }

    @Override
    public LootTable getLootTable() {
        return lootTable;
    }

    @Override
    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        // Tile positions are basically level positions divided by 32. getTileX() does this for us etc.
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 30;
        int drawY = camera.getDrawY(y) - 48;

        // A helper method to get the sprite of the current animation/direction of this mob
        Point sprite = getAnimSprite(x, y, dir);

        drawY += getBobbing(x, y);

        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);

        DrawOptions drawOptions = chicken_texture.initDraw()
                .sprite(sprite.x, sprite.y, 64)
                .light(light)
                .pos(drawX, drawY);

        list.add(new MobDrawable() {
            @Override
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });

        //addShadowDrawables(tileList, x, y, light, camera);
        TextureDrawOptions shadow = chicken_shadow.initDraw().sprite(0, this.dir, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });

    }

    protected int getRockSpeed() {
        return 8;
    }




}
