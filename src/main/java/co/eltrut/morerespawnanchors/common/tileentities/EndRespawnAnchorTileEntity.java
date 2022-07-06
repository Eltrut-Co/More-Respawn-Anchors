package co.eltrut.morerespawnanchors.common.tileentities;

import co.eltrut.morerespawnanchors.core.registry.MRABlockEntities;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class EndRespawnAnchorTileEntity extends TheEndPortalBlockEntity {

	private IntegerProperty charges;
	
	public EndRespawnAnchorTileEntity() {
		super(MRABlockEntities.END_RESPAWN_ANCHOR.get());
	}
	
	public void setCharges(IntegerProperty charges) {
		this.charges = charges;
	}
	
	public IntegerProperty getCharges() {
		return this.charges;
	}
	
}
