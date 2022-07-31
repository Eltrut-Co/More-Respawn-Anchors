package co.eltrut.morerespawnanchors.common.tileentities;

import co.eltrut.morerespawnanchors.core.registry.MRABlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class EndRespawnAnchorTileEntity extends TheEndPortalBlockEntity {

	private IntegerProperty charges;
	
	public EndRespawnAnchorTileEntity(BlockPos pos, BlockState state) {
		super(MRABlockEntities.END_RESPAWN_ANCHOR.get(), pos, state);
	}
	
	public void setCharges(IntegerProperty charges) {
		this.charges = charges;
	}
	
	public IntegerProperty getCharges() {
		return this.charges;
	}
	
}
