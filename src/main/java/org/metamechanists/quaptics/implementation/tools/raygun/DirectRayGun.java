package org.metamechanists.quaptics.implementation.tools.raygun;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.metamechanists.quaptics.beams.DeprecatedBeamStorage;
import org.metamechanists.quaptics.beams.beam.LifetimeDirectBeam;
import org.metamechanists.quaptics.implementation.blocks.Settings;
import org.metamechanists.quaptics.items.Lore;
import org.metamechanists.quaptics.utils.transformations.TransformationUtils;


public class DirectRayGun extends AbstractRayGun {
    public static final Settings RAY_GUN_2_SETTINGS = Settings.builder()
            .chargeCapacity(1000.0)
            .emissionPower(5.0)
            .damage(2)
            .projectileMaterial(Material.LIGHT_BLUE_CONCRETE)
            .build();

    public static final SlimefunItemStack RAY_GUN_2 = new SlimefunItemStack(
            "QP_RAY_GUN_2",
            Material.DIAMOND_HORSE_ARMOR,
            "&bRay Gun &3II",
            Lore.buildChargeableLore(RAY_GUN_2_SETTINGS, 0,
                    "&7● &eRight Click &7to fire"));

    public DirectRayGun(final ItemGroup itemGroup, final SlimefunItemStack item, final RecipeType recipeType, final ItemStack[] recipe, final Settings settings) {
        super(itemGroup, item, recipeType, recipe, settings);
    }

    @Override
    public void fireRayGun(final Player player, final Location source, final Location target) {
        final RayTraceResult result = source.getWorld().rayTrace(
                source, Vector.fromJOML(TransformationUtils.getDisplacement(source, target)),
                64, FluidCollisionMode.NEVER, true, 0.095F, entity -> true);
        
        if (result == null) {
            DeprecatedBeamStorage.deprecate(new LifetimeDirectBeam(settings.getProjectileMaterial(), source, target, 0.095F, 5));
            target.getNearbyEntities(0.095F, 0.095F, 0.095F)
                    .stream()
                    .filter(Damageable.class::isInstance)
                    .map(Damageable.class::cast)
                    .findFirst()
                    .ifPresent(entity -> entity.damage(settings.getDamage()));
            return;
        }

        final Vector position = result.getHitPosition();
        DeprecatedBeamStorage.deprecate(new LifetimeDirectBeam(
                settings.getProjectileMaterial(),
                source,
                new Location(source.getWorld(), position.getX(), position.getY(), position.getZ()),
                0.095F,
                5));

        if (result.getHitEntity() instanceof final Damageable damageable) {
            damageable.damage(settings.getDamage());
        }
    }
}
