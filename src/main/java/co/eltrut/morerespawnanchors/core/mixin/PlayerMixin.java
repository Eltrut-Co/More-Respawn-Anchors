package co.eltrut.morerespawnanchors.core.mixin;

import co.eltrut.morerespawnanchors.common.blocks.IRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Player.class)
public final class PlayerMixin {
	
	@Inject(at = @At("RETURN"), method = "findRespawnPositionAndUseSpawnBlock(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;FZZ)Ljava/util/Optional;", cancellable = true)
	private static void findRespawnPositionAndUseSpawnBlock(ServerLevel world, BlockPos blockPos, float f, boolean b1, boolean b2, CallbackInfoReturnable<Optional<Vec3>> cir) {
		BlockState blockState = world.getBlockState(blockPos);
		if (MoreRespawnAnchors.respawnAfterCredits) {
			BlockPos pos = world.getServer().getLevel(Level.OVERWORLD).getSharedSpawnPos();
			MoreRespawnAnchors.respawnAfterCredits = false;
			cir.setReturnValue(Optional.of(new Vec3(pos.getX(), pos.getY(), pos.getZ())));
		}
		else if (blockState.getBlock() instanceof IRespawnAnchorBlock) {
			IRespawnAnchorBlock block = (IRespawnAnchorBlock)blockState.getBlock();
			if (blockState.getValue(block.getCharges()) > 0 && block.doesRespawnAnchorWork(world)) {
				Optional<Vec3> respawnPosition = RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, world, blockPos);
                if (!b2 && respawnPosition.isPresent()) {
                    world.setBlock(blockPos, blockState.setValue(block.getCharges(), blockState.getValue(block.getCharges()) - 1), 3);
                }

                cir.setReturnValue(respawnPosition);
			}
		}
	}
	
}
