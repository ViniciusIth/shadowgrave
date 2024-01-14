package cloud.viniciusith.shadowgrave.mixin;

import cloud.viniciusith.shadowgrave.ShadowGraveMod;
import cloud.viniciusith.shadowgrave.other.GraveUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "drop", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;dropInventory()V",
            shift = At.Shift.BEFORE), cancellable = true)
    private void shadowgrave$replaceWithShadow(DamageSource source, CallbackInfo ci) {
        if (((Object) this) instanceof ServerPlayerEntity player) {
            ShadowGraveMod.LOGGER.info("Shadow grave dropped by player " + player.getGameProfile().getName());
            GraveUtils.spawnShadow(player, player.getPos(), source);

            ci.cancel();
        }
    }
}
