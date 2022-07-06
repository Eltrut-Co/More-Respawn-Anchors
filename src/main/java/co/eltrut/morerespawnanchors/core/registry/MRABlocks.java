package co.eltrut.morerespawnanchors.core.registry;

import co.eltrut.differentiate.core.registrator.BlockHelper;
import co.eltrut.morerespawnanchors.common.blocks.EndRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.common.blocks.NetheriteEndRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.common.blocks.NetheriteRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRABlocks {
	
	public static final BlockHelper HELPER = MoreRespawnAnchors.REGISTRATOR.getHelper(ForgeRegistries.BLOCKS);
	
	public static final RegistryObject<Block> NETHERITE_RESPAWN_ANCHOR = HELPER.createBlock("netherite_respawn_anchor", () -> new NetheriteRespawnAnchorBlock(Properties.RESPAWN_ANCHOR), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS).fireResistant());
	public static final RegistryObject<Block> END_RESPAWN_ANCHOR = HELPER.createBlock("end_respawn_anchor", () -> new EndRespawnAnchorBlock(Block.Properties.copy(Blocks.RESPAWN_ANCHOR)), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
	public static final RegistryObject<Block> NETHERITE_END_RESPAWN_ANCHOR = HELPER.createBlock("netherite_end_respawn_anchor", () -> new NetheriteEndRespawnAnchorBlock(Properties.RESPAWN_ANCHOR), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS).fireResistant());

	
	public static class Properties {
		public static final Block.Properties RESPAWN_ANCHOR = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).lightLevel((state) -> {
		      return NetheriteRespawnAnchorBlock.getChargeScale(state, 15);
		});
	}
	
}