package cominixo.morerespawnanchors.common.blocks;

import net.minecraft.state.IntegerProperty;
import net.minecraft.world.World;

public interface IRespawnAnchorBlock {
	
	public IntegerProperty getCharges();
	public int getMaxCharges();
	public boolean doesRespawnAnchorWork(World world);
	
}
