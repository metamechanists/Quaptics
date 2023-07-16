package org.metamechanists.quaptics.items;

import dev.sefiraat.sefilib.slimefun.itemgroup.DummyItemGroup;
import dev.sefiraat.sefilib.slimefun.itemgroup.SimpleFlexGroup;
import io.github.bakedlibs.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.metamechanists.quaptics.Quaptics;
import org.metamechanists.quaptics.utils.Colors;
import org.metamechanists.quaptics.utils.Keys;


@UtilityClass
public class Groups {
    public final SimpleFlexGroup MAIN = new SimpleFlexGroup(
            Quaptics.getInstance(),
            Colors.QUAPTICS.getFormattedColor() + "Quaptics",
            Keys.MAIN,
            new CustomItemStack(Material.LIGHT_BLUE_STAINED_GLASS, Colors.QUAPTICS.getFormattedColor() + "Quaptics"));

    public final ItemGroup GUIDE = new DummyItemGroup(Keys.GUIDE,
            new CustomItemStack(Material.MAP, "&aGuide"));

    public final ItemGroup CRAFTING_COMPONENTS = new DummyItemGroup(Keys.TOOLS,
            new CustomItemStack(Material.CLOCK, "&3Crafting Components"));

    public final ItemGroup TOOLS = new DummyItemGroup(Keys.TOOLS,
            new CustomItemStack(Material.DIAMOND_HORSE_ARMOR, "&9Tools"));

    public final ItemGroup BEAM_CREATION = new DummyItemGroup(Keys.PRIMITIVE,
            new CustomItemStack(Material.GLASS_PANE, Colors.QUAPTIC_COMPONENTS.getFormattedColor() + "Beam Creation"));
    public final ItemGroup BEAM_MANIPULATION = new DummyItemGroup(Keys.PRIMITIVE,
            new CustomItemStack(Material.GLASS_PANE, Colors.QUAPTIC_COMPONENTS.getFormattedColor() + "Beam Manipulation"));
    public final ItemGroup MACHINES = new DummyItemGroup(Keys.PRIMITIVE,
            new CustomItemStack(Material.LIGHT_BLUE_CONCRETE, Colors.QUAPTIC_COMPONENTS.getFormattedColor() + "Machines"));

    public final ItemGroup BEACON_COMPONENTS = new DummyItemGroup(Keys.PRIMITIVE,
            new CustomItemStack(Material.DEEPSLATE_BRICK_WALL, Colors.BEACONS.getFormattedColor() + "Beacon Components"));
    public final ItemGroup BEACON_MODULES = new DummyItemGroup(Keys.PRIMITIVE,
            new CustomItemStack(Material.DEEPSLATE_BRICK_WALL, Colors.BEACONS.getFormattedColor() + "Beacon Modules"));

    public final ItemGroup TESTING = new DummyItemGroup(Keys.TESTING,
            new CustomItemStack(Material.GRAY_CONCRETE, "&8Testing"));

    public void initialize() {
        final SlimefunAddon addon = Quaptics.getInstance();
        MAIN.addItemGroup(GUIDE);
        MAIN.addItemGroup(TOOLS);
        MAIN.addItemGroup(CRAFTING_COMPONENTS);
        MAIN.addItemGroup(BEAM_CREATION);
        MAIN.addItemGroup(BEAM_MANIPULATION);
        MAIN.addItemGroup(MACHINES);
        MAIN.addItemGroup(BEACON_COMPONENTS);
        MAIN.addItemGroup(BEACON_MODULES);
        MAIN.register(addon);
    }
}
