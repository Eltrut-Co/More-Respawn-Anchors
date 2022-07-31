package co.eltrut.morerespawnanchors.core.mixin;

import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
	
	@Inject(at = @At("RETURN"), method = "getRespawnDimension", cancellable = true)
	public void getRespawnDimension(CallbackInfoReturnable<ResourceKey<Level>> cir) {
		cir.setReturnValue(MoreRespawnAnchors.respawnAfterCredits ? ServerLevel.OVERWORLD : cir.getReturnValue());
	}

}
