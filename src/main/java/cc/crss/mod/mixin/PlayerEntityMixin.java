package cc.crss.mod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import cc.crss.mod.CRSSMod;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Redirect(method = "trySleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;sleep(Lnet/minecraft/util/math/BlockPos;)V"))
	private void trySleep(PlayerEntity instance, BlockPos pos) {
		CRSSMod.LOGGER.info("IT WORKIES");
	}
}