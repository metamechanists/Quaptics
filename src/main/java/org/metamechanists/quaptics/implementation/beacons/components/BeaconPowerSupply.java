package org.metamechanists.quaptics.implementation.beacons.components;

import dev.sefiraat.sefilib.entity.display.DisplayGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.metamechanists.quaptics.connections.ConnectionGroup;
import org.metamechanists.quaptics.connections.ConnectionPoint;
import org.metamechanists.quaptics.connections.ConnectionPointType;
import org.metamechanists.quaptics.connections.Link;
import org.metamechanists.quaptics.implementation.base.ConnectedBlock;
import org.metamechanists.quaptics.implementation.Settings;
import org.metamechanists.quaptics.items.Lore;
import org.metamechanists.quaptics.items.Tier;
import org.metamechanists.quaptics.utils.BlockStorageAPI;
import org.metamechanists.quaptics.utils.Keys;
import org.metamechanists.quaptics.utils.id.complex.ConnectionGroupId;
import org.metamechanists.quaptics.utils.models.ModelBuilder;
import org.metamechanists.quaptics.utils.models.components.ModelCuboid;

import java.util.List;


public class BeaconPowerSupply extends ConnectedBlock {
    public static final Settings BEACON_POWER_SUPPLY_SETTINGS = Settings.builder()
            .tier(Tier.PRIMITIVE)
            .build();
    public static final SlimefunItemStack BEACON_POWER_SUPPLY = new SlimefunItemStack(
            "QP_BEACON_POWER_SUPPLY",
            Material.GRAY_CONCRETE,
            "&6Beacon Power Supply",
            Lore.create(BEACON_POWER_SUPPLY_SETTINGS,
                    "&7● Part of the Beacon multiblock"));

    public BeaconPowerSupply(final ItemGroup itemGroup, final SlimefunItemStack item, final RecipeType recipeType, final ItemStack[] recipe, final Settings settings) {
        super(itemGroup, item, recipeType, recipe, settings);
    }

    @Override
    protected float getConnectionRadius() {
        return 1.0F;
    }
    @Override
    protected DisplayGroup initModel(@NotNull final Location location, @NotNull final Player player) {
        return new ModelBuilder()
                .add("main", new ModelCuboid()
                        .material(Material.GRAY_CONCRETE)
                        .size(1.1F, 1.0F, 1.1F)
                        .rotation(Math.PI/4))
                .add("panel1", new ModelCuboid()
                        .material(settings.getTier().concreteMaterial)
                        .size(0.8F, 0.7F, 1.2F)
                        .rotation(Math.PI/4))
                .add("panel2", new ModelCuboid()
                        .material(settings.getTier().concreteMaterial)
                        .size(0.8F, 0.7F, 1.2F)
                        .rotation(Math.PI/4))
                .buildAtBlockCenter(location);
    }
    @Override
    protected List<ConnectionPoint> initConnectionPoints(final ConnectionGroupId groupId, final Player player, final Location location) {
        return List.of(
                new ConnectionPoint(ConnectionPointType.INPUT, groupId, "input 1", location.clone().toCenterLocation().add(new Vector(0, 0, getConnectionRadius()))),
                new ConnectionPoint(ConnectionPointType.INPUT, groupId, "input 2", location.clone().toCenterLocation().add(new Vector(0, 0, -getConnectionRadius()))),
                new ConnectionPoint(ConnectionPointType.INPUT, groupId, "input 3", location.clone().toCenterLocation().add(new Vector(getConnectionRadius(), 0, 0))),
                new ConnectionPoint(ConnectionPointType.INPUT, groupId, "input 4", location.clone().toCenterLocation().add(new Vector(-getConnectionRadius(), 0, 0))));
    }
    @Override
    protected void initBlockStorage(final @NotNull Location location) {
        BlockStorageAPI.set(location, Keys.BS_INPUT_POWER, 0.0);
    }

    @Override
    public void onInputLinkUpdated(@NotNull final ConnectionGroup group, @NotNull final Location location) {
        final List<ConnectionPoint> enabledInputs = getEnabledInputs(location);
        if (doBurnoutCheck(group, enabledInputs)) {
            return;
        }

        final double inputPower = getIncomingLinks(location).stream().mapToDouble(Link::getPower).sum();
        BlockStorageAPI.set(location, Keys.BS_INPUT_POWER, inputPower);
    }
}
