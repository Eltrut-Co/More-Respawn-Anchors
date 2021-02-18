package cominixo.morerespawnanchors.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class EndRespawnAnchorBlock extends RespawnAnchorBlock {

	public EndRespawnAnchorBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	      ItemStack itemstack = player.getHeldItem(handIn);
	      if (handIn == Hand.MAIN_HAND && !isValidFuel(itemstack) && isValidFuel(player.getHeldItem(Hand.OFF_HAND))) {
	         return ActionResultType.PASS;
	      } else if (isValidFuel(itemstack) && notFullyCharged(state)) {
	         chargeAnchor(worldIn, pos, state);
	         if (!player.abilities.isCreativeMode) {
	            itemstack.shrink(1);
	         }

	         return ActionResultType.func_233537_a_(worldIn.isRemote);
	      } else if (state.get(CHARGES) == 0) {
	         return ActionResultType.PASS;
	      } else if (!doesRespawnAnchorWork(worldIn)) {
	         if (!worldIn.isRemote) {
	            this.triggerExplosion(state, worldIn, pos);
	         }

	         return ActionResultType.func_233537_a_(worldIn.isRemote);
	      } else {
	         if (!worldIn.isRemote) {
	            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;
	            if (serverplayerentity.func_241141_L_() != worldIn.getDimensionKey() || !serverplayerentity.func_241140_K_().equals(pos)) {
	               serverplayerentity.func_242111_a(worldIn.getDimensionKey(), pos, 0.0F, false, true);
	               worldIn.playSound((PlayerEntity)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
	               return ActionResultType.SUCCESS;
	            }
	         }

	         return ActionResultType.CONSUME;
	      }
	   }
	
	private static boolean isValidFuel(ItemStack stack) {
		return stack.getItem() == Items.ENDER_PEARL;
	}
	
	private static boolean notFullyCharged(BlockState state) {
	      return state.get(CHARGES) < 4;
	}

}
