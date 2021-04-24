package me.lor3mipsum.next.client.impl.modules.hud;

import com.lukflug.panelstudio.hud.HUDList;
import com.lukflug.panelstudio.hud.ListComponent;
import com.lukflug.panelstudio.theme.Theme;
import me.lor3mipsum.next.client.impl.settings.ColorSetting;
import me.lor3mipsum.next.client.module.Category;
import me.lor3mipsum.next.client.module.HudModule;
import me.lor3mipsum.next.client.utils.player.InventoryUtils;
import net.minecraft.item.Items;
import net.minecraft.util.Formatting;

import java.awt.*;

public class Crystals extends HudModule {
    public ColorSetting color = new ColorSetting("Color", new Color(255, 255, 255, 255));

    public Crystals() {
        super("Crystals", "Shows you how many crystals you have in your inventory", new Point(60, 100), Category.HUD);
    }

    @Override
    public void populate (Theme theme) {
        component = new ListComponent(getName(), theme.getPanelRenderer(), position, new CrystalsList());
    }

    private class CrystalsList implements HUDList {

        public int totems = 0;

        @Override
        public int getSize() {
            return 1;
        }

        @Override
        public String getItem(int index) {
            totems = InventoryUtils.findItemWithCount(Items.END_CRYSTAL).count;
            return "Crystals: " + Formatting.GRAY + totems;
        }

        @Override
        public Color getItemColor(int index) {
            return color.getValue();
        }

        @Override
        public boolean sortUp() {
            return false;
        }

        @Override
        public boolean sortRight() {
            return false;
        }
    }
}