package co.eltrut.morerespawnanchors.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface IRespawnAnchorBlock {
	
	public IntegerProperty getCharges();
	public int getMaxCharges();
	public boolean doesRespawnAnchorWork(Level world);
	public void chargeAnchor(Level world, BlockPos pos, BlockState state);
	
}
