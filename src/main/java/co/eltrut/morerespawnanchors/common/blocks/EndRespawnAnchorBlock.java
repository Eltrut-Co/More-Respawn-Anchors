package co.eltrut.morerespawnanchors.common.blocks;

import co.eltrut.differentiate.common.interf.IRenderTypeBlock;
import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import co.eltrut.morerespawnanchors.core.registry.MRABlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EndRespawnAnchorBlock extends BaseRespawnAnchorBlock implements IRenderTypeBlock {

	public EndRespawnAnchorBlock(Properties properties) {
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
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public BlockEntity createBlockEntity(BlockState state, BlockGetter world) {
		EndRespawnAnchorTileEntity entity = MRABlockEntities.END_RESPAWN_ANCHOR.get().create();
		entity.setCharges(this.getCharges());
		return entity;
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.cutout();
	}

}
