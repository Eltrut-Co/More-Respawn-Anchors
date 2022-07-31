package co.eltrut.morerespawnanchors.common.blocks;

import co.eltrut.differentiate.common.interf.IRenderTypeBlock;
import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import co.eltrut.morerespawnanchors.core.registry.MRABlockEntities;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class NetheriteEndRespawnAnchorBlock extends NetheriteRespawnAnchorBlock implements IRenderTypeBlock, BlockEntityType.BlockEntitySupplier {

	public NetheriteEndRespawnAnchorBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean isValidFuel(ItemStack itemStack) {
		return itemStack.getItem() == Items.ENDER_PEARL;
	}
	
	@Override
	public boolean doesRespawnAnchorWork(Level world) {
		return world.dimension().equals(Level.END);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

	@Override
	public BlockEntity create(BlockPos pos, BlockState state) {
		EndRespawnAnchorTileEntity entity = MRABlockEntities.END_RESPAWN_ANCHOR.get().create(pos, state);
		entity.setCharges(this.getCharges());
		return entity;
	}
}
