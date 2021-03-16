package co.eltrut.morerespawnanchors.common.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class NetheriteEndRespawnAnchorBlock extends NetheriteRespawnAnchorBlock {

	public NetheriteEndRespawnAnchorBlock(Properties properties) {
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

}
