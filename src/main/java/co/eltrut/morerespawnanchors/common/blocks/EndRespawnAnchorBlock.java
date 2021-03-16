package co.eltrut.morerespawnanchors.common.blocks;

import co.eltrut.differentiate.common.interf.IRenderTypeBlock;
import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import co.eltrut.morerespawnanchors.core.registry.MRATileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EndRespawnAnchorBlock extends BaseRespawnAnchorBlock implements IRenderTypeBlock {

	public EndRespawnAnchorBlock(Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidFuel(ItemStack itemStack) {
		return itemStack.getItem() == Items.ENDER_PEARL;
	}
	
	@Override
	public boolean doesRespawnAnchorWork(World world) {
		return world.getDimensionKey().equals(World.THE_END);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		EndRespawnAnchorTileEntity entity = MRATileEntities.END_RESPAWN_ANCHOR.get().create();
		entity.setCharges(this.getCharges());
		return entity;
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.getCutout();
	}

}
