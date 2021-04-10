package co.eltrut.morerespawnanchors.core.other;

import co.eltrut.differentiate.core.util.BlockUtil;
import co.eltrut.morerespawnanchors.client.renderer.EndRespawnAnchorTileEntityRenderer;
import co.eltrut.morerespawnanchors.common.blocks.IRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.core.registry.MRABlocks;
import co.eltrut.morerespawnanchors.core.registry.MRATileEntities;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class MRACompat {

	public static void registerEntityRenderers() {
		ClientRegistry.bindTileEntityRenderer(MRATileEntities.END_RESPAWN_ANCHOR.get(), EndRespawnAnchorTileEntityRenderer::new);
	}
	
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
		BlockUtil.registerDispenserBehavior(Items.GLOWSTONE, MRABlocks.NETHERITE_RESPAWN_ANCHOR.get(), newBehavior);
		
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
