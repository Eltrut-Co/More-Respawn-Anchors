package co.eltrut.morerespawnanchors.core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
	
	@Inject(at = @At("RETURN"), method = "getRespawnDimension()Lnet/minecraft/util/RegistryKey;", cancellable = true)
	public void getRespawnDimension(CallbackInfoReturnable<RegistryKey<World>> cir) {
		cir.setReturnValue(MoreRespawnAnchors.respawnAfterCredits ? World.OVERWORLD : cir.getReturnValue());
	}

}
