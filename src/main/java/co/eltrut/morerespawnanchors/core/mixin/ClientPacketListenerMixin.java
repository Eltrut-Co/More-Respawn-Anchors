package co.eltrut.morerespawnanchors.core.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
	
	@Inject(at = @At("HEAD"), method = "handleGameEvent")
	public void handleGameEvent(ClientboundGameEventPacket packet, CallbackInfo ci) {
		if (packet.getEvent() == ClientboundGameEventPacket.WIN_GAME) {
			MoreRespawnAnchors.respawnAfterCredits = true;
		}
	}

}
