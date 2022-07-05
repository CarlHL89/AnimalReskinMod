package animalreskins.items;

import necesse.engine.localization.Localization;
import necesse.engine.localization.message.LocalMessage;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTexture.GameSprite;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.matItem.MatItem;






public class chickenEggItem extends MatItem {

    public static GameTexture egg_sprite;
    public GameSprite getItemSprite(InventoryItem item, PlayerMob perspective) {
        return new GameSprite(this.egg_sprite, 0, 0, 32);
    }
    public chickenEggItem(){
        super(100, Rarity.NORMAL);
    }

}
