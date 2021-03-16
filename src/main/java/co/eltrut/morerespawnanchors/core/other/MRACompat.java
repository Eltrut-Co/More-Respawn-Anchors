package co.eltrut.morerespawnanchors.core.other;

import co.eltrut.morerespawnanchors.common.blocks.IRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.core.registry.MRABlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MRACompat {

	public static void registerDispenserBehaviors() {

		IDispenseItemBehavior newBehavior = new OptionalDispenseBehavior() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				BlockPos blockpos = source.getBlockPos().offset(direction);
				World world = source.getWorld();
				BlockState blockstate = world.getBlockState(blockpos);
				this.setSuccessful(true);
				if (blockstate.isIn(MRABlocks.NETHERITE_RESPAWN_ANCHOR.get())) {
					IRespawnAnchorBlock block = (IRespawnAnchorBlock)blockstate.getBlock();
					if (blockstate.get(block.getCharges()) != block.getMaxCharges()) {
						block.chargeAnchor(world, blockpos, blockstate);
						stack.shrink(1);
					} else {
						this.setSuccessful(false);
					}

					return stack;
				} else {
					return super.dispenseStack(source, stack);
				}
			}
		};
		IDispenseItemBehavior behavior = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.GLOWSTONE);

		DispenserBlock.registerDispenseBehavior(Items.GLOWSTONE, (source, stack) -> {
			Direction dir = source.getBlockState().get(DispenserBlock.FACING);
			BlockPos pos = source.getBlockPos().offset(dir);
			BlockState block = source.getWorld().getBlockState(pos);

			return block.isIn(MRABlocks.NETHERITE_RESPAWN_ANCHOR.get()) ? newBehavior.dispense(source, stack) : behavior.dispense(source, stack);
		});
		
		DispenserBlock.registerDispenseBehavior(Items.ENDER_PEARL, new OptionalDispenseBehavior() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				BlockPos blockpos = source.getBlockPos().offset(direction);
				World world = source.getWorld();
				BlockState blockstate = world.getBlockState(blockpos);
				this.setSuccessful(true);
				if (blockstate.isIn(MRABlocks.END_RESPAWN_ANCHOR.get()) || blockstate.isIn(MRABlocks.NETHERITE_END_RESPAWN_ANCHOR.get())) {
					IRespawnAnchorBlock block = (IRespawnAnchorBlock)blockstate.getBlock();
					if (blockstate.get(block.getCharges()) != block.getMaxCharges()) {
						block.chargeAnchor(world, blockpos, blockstate);
						stack.shrink(1);
					} else {
						this.setSuccessful(false);
					}

					return stack;
				} else {
					return super.dispenseStack(source, stack);
				}
			}
		});

	}

}
