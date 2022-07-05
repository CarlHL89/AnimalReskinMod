package animalreskins;

import animalreskins.items.blackwoolItem;
import animalreskins.items.chickenEggItem;
import necesse.engine.GameEventListener;
import necesse.engine.GameEvents;
import necesse.engine.registries.MobRegistry;
import necesse.gfx.gameTexture.GameTexture;
import necesse.engine.events.worldGeneration.GeneratedIslandAnimalsEvent;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.inventory.item.matItem.MatItem;
import necesse.level.maps.biomes.ForestBiome;

@ModEntry
public class AnimalController {



    public void initResources(){



        //load our sheeep textures
        sheepBlack.black_sheep_texture = GameTexture.fromFile("mobs/sheep_black");
        sheepBlack.black_sheep_sheared = GameTexture.fromFile("mobs/sheep_black_sheared");
        sheepBlack.black_lamb = GameTexture.fromFile("mobs/lamb_black");
        blackwoolItem.blackwool= GameTexture.fromFile("items/black_wool");


        //loading cow textures
        cowBrown.brown_cow_texture = GameTexture.fromFile("mobs/cow_brown");
       // cowBrown.texture = GameTexture.fromFile("mobs/calf_brown");

        //chicken I got nothing to extend on using critter for now think everything locked
        chicken.chicken_texture = GameTexture.fromFile("mobs/chicken");
        chicken.chicken_shadow = GameTexture.fromFile("mobs/chicken_shadow");
        chickenEggItem.egg_sprite = GameTexture.fromFile("items/chicken_egg");



    }

    public void init(){
        //Register our new mobs

        MobRegistry.registerMob("browncow", cowBrown.class, true);
        MobRegistry.registerMob("blacksheep", sheepBlack.class,true);
        MobRegistry.registerMob("chicken", chicken.class, true);

        ItemRegistry.registerItem("chickenegg", new chickenEggItem().setItemCategory(new String[]{"materials", "mobdrops"}), 25, true);
        ItemRegistry.registerItem("blackwool", new blackwoolItem().setItemCategory(new String[]{"materials" , "mobdrops"}), 7.0F, true);


        //Spawning Animals sure I can make this more efficient

        GameEvents.addListener(GeneratedIslandAnimalsEvent.class, new GameEventListener<GeneratedIslandAnimalsEvent>() {
            @Override
            public void onEvent(GeneratedIslandAnimalsEvent event) {
                if (event.level.biome instanceof ForestBiome) {
                    event.islandGeneration.spawnMobHerds("blacksheep", //mob stringID
                            event.islandGeneration.random.getIntBetween(10,20), //spawning 10  - 20 instances of animal
                            TileRegistry.grassID, //only spawn on grass tiles
                            2,4 //spawn 2 - 4 in each area
                    );
                }
            }
        });


        GameEvents.addListener(GeneratedIslandAnimalsEvent.class, new GameEventListener<GeneratedIslandAnimalsEvent>() {
            @Override
            public void onEvent(GeneratedIslandAnimalsEvent event) {
                if (event.level.biome instanceof ForestBiome) {
                    event.islandGeneration.spawnMobHerds("browncow", //mob stringID
                            event.islandGeneration.random.getIntBetween(10,20), //spawning 10  - 35 instances of animal
                            TileRegistry.grassID, //only spawn on grass tiles
                            2,4 //spawn 2 - 6 in each area
                    );
                }
            }
        });

        ForestBiome.defaultSurfaceCritters.add(115, "chicken");




    }






}
