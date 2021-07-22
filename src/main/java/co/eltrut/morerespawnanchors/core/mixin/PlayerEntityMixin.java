package co.eltrut.morerespawnanchors.core.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import co.eltrut.morerespawnanchors.common.blocks.IRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(PlayerEntity.class)
public final class PlayerEntityMixin {
	
	@Inject(at = @At("RETURN"), method = "findRespawnPositionAndUseSpawnBlock(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;FZZ)Ljava/util/Optional;", cancellable = true)
	private static void findRespawnPositionAndUseSpawnBlock(ServerWorld world, BlockPos blockPos, float f, boolean b1, boolean b2, CallbackInfoReturnable<Optional<Vector3d>> cir) {
		BlockState blockState = world.getBlockState(blockPos);
		if (MoreRespawnAnchors.respawnAfterCredits) {
			BlockPos pos = world.getServer().getLevel(World.OVERWORLD).getSharedSpawnPos();
			MoreRespawnAnchors.respawnAfterCredits = false;
			cir.setReturnValue(Optional.of(new Vector3d(pos.getX(), pos.getY(), pos.getZ())));
		}
		else if (blockState.getBlock() instanceof IRespawnAnchorBlock) {
			IRespawnAnchorBlock block = (IRespawnAnchorBlock)blockState.getBlock();
			if (blockState.getValue(block.getCharges()) > 0 && block.doesRespawnAnchorWork(world)) {
				Optional<Vector3d> respawnPosition = RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, world, blockPos);
                if (!b2 && respawnPosition.isPresent()) {
                    world.setBlock(blockPos, blockState.setValue(block.getCharges(), blockState.getValue(block.getCharges()) - 1), 3);
                }

                cir.setReturnValue(respawnPosition);
			}
		}
	}
	
}
