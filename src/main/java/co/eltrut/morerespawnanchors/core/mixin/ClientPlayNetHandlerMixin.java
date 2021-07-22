package co.eltrut.morerespawnanchors.core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.server.SChangeGameStatePacket;

@Mixin(ClientPlayNetHandler.class)
public class ClientPlayNetHandlerMixin {
	
	@Inject(at = @At("HEAD"), method = "handleGameEvent(Lnet/minecraft/network/play/server/SChangeGameStatePacket;)V")
	public void handleGameEvent(SChangeGameStatePacket packet, CallbackInfo ci) {
		if (packet.getEvent() == SChangeGameStatePacket.WIN_GAME) {
			MoreRespawnAnchors.respawnAfterCredits = true;
		}
	}

}
