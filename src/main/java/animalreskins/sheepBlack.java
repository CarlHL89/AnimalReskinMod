package animalreskins;

import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.friendly.SheepMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class sheepBlack extends SheepMob {

    //called in AnimalController
    public static GameTexture black_sheep_texture;
    public static GameTexture black_sheep_sheared;
    public static GameTexture black_lamb;

//not sure how to get this to work
//     private GameTexture getTexture() {
//        if (this.isGrown()) {
//            return this.hasWool() ? GameTexture = black_sheep_texture : GameTexture = black_sheep_sheared;
//        } else {
//            return GameTexture = black_lamb;
//        }
//    }

    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("rawmutton", 1, 2),
            LootItem.between("blackwool", 1, 2)});

    public sheepBlack() {
        this.setHealth(50);
        this.setSpeed(12.0F);
        this.setFriction(3.0F);
        this.collision = new Rectangle(-12, -9, 24, 18);
        this.hitBox = new Rectangle(-16, -12, 32, 24);
        this.selectBox = new Rectangle(-18, -30, 36, 36);
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        // Spawn flesh debris particles
        for (int i = 0; i < 4; i++) {
            getLevel().entityManager.addParticle(new FleshParticle(
                    getLevel(), black_sheep_texture,
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
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;

        // A helper method to get the sprite of the current animation/direction of this mob
        Point sprite = getAnimSprite(x, y, dir);

        drawY += getBobbing(x, y);

        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);

        DrawOptions drawOptions = black_sheep_texture.initDraw()
                .sprite(sprite.x, sprite.y, 64)
                .light(light)
                .pos(drawX, drawY);

        list.add(new MobDrawable() {
            @Override
            public void draw(TickManager tickManager) {
                drawOptions.draw();
            }
        });

        addShadowDrawables(tileList, x, y, light, camera);

    }

    @Override
    public int getRockSpeed() {
        // Change the speed at which this mobs animation plays
        return 10;
    }


}
