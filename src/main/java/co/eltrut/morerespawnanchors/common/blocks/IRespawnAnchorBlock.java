package co.eltrut.morerespawnanchors.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRespawnAnchorBlock {
	
	public IntegerProperty getCharges();
	public int getMaxCharges();
	public boolean doesRespawnAnchorWork(World world);
	public void chargeAnchor(World world, BlockPos pos, BlockState state);
	
}
