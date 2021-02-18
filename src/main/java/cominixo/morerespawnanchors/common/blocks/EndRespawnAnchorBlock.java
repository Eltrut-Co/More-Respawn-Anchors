package cominixo.morerespawnanchors.common.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class EndRespawnAnchorBlock extends BaseRespawnAnchorBlock {

	public EndRespawnAnchorBlock(Properties properties) {
		super(properties, 4);
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
