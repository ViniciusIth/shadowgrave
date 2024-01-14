package cloud.viniciusith.shadowgrave.other;

import cloud.viniciusith.shadowgrave.ShadowGraveMod;
import cloud.viniciusith.shadowgrave.entity.ShadowEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class GraveUtils {

    public static void spawnShadow(ServerPlayerEntity serverPlayer, Vec3d deathPos, DamageSource deathSource) {
        ServerWorld world = (ServerWorld) serverPlayer.getWorld();
        ShadowGraveMod.LOGGER.info("Shadow Entity created!");

        // Spawn entity
        ShadowEntity shadow = new ShadowEntity(ShadowGraveMod.SHADOW_ENTITY, world);

        shadow.setShadowOwner(serverPlayer.getGameProfile());
        shadow.setXp(serverPlayer.totalExperience);
        shadow.setInventories(
                serverPlayer.getInventory().main,
                serverPlayer.getInventory().armor,
                serverPlayer.getInventory().offHand
        );

        shadow.refreshPositionAndAngles(
                deathPos.x,
                deathPos.y,
                deathPos.z,
                serverPlayer.getYaw(),
                serverPlayer.getPitch()
        );

        world.spawnEntity(shadow);
    }
}
