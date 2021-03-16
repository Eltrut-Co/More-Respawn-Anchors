package co.eltrut.morerespawnanchors.common.tileentities;

import co.eltrut.morerespawnanchors.core.registry.MRATileEntities;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.EndPortalTileEntity;

public class EndRespawnAnchorTileEntity extends EndPortalTileEntity {

	private IntegerProperty charges;
	
	public EndRespawnAnchorTileEntity() {
		super(MRATileEntities.END_RESPAWN_ANCHOR.get());
	}
	
	public void setCharges(IntegerProperty charges) {
		this.charges = charges;
	}
	
	public IntegerProperty getCharges() {
		return this.charges;
	}
	
}
