package cominixo.morerespawnanchors.core.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cominixo.morerespawnanchors.common.blocks.BaseRespawnAnchorBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

@Mixin(PlayerEntity.class)
public final class PlayerEntityMixin {
	
	@Inject(at = @At("HEAD"), method = "func_242374_a(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/math/BlockPos;FZZ)Ljava/util/Optional;", cancellable = true)
	public static void func_242374_a(ServerWorld world, BlockPos blockPos, float f, boolean b1, boolean b2, CallbackInfoReturnable<Optional<Vector3d>> cir) {
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() instanceof BaseRespawnAnchorBlock) {
			BaseRespawnAnchorBlock block = (BaseRespawnAnchorBlock)blockState.getBlock();
			if (blockState.get(block.getCharges()) > 0 && block.doesRespawnAnchorWork(world)) {
				Optional<Vector3d> respawnPosition = RespawnAnchorBlock.findRespawnPoint(EntityType.PLAYER, world, blockPos);
                if (!b2 && respawnPosition.isPresent()) {
                    world.setBlockState(blockPos, blockState.with(block.getCharges(), blockState.get(block.getCharges()) - 1), 3);
                }

                cir.setReturnValue(respawnPosition);
			}
		}
	}
	
}
