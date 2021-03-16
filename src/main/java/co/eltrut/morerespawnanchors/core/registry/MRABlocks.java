package co.eltrut.morerespawnanchors.core.registry;

import co.eltrut.differentiate.core.registrator.BlockHelper;
import co.eltrut.morerespawnanchors.common.blocks.EndRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.common.blocks.NetheriteEndRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.common.blocks.NetheriteRespawnAnchorBlock;
import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRABlocks {
	
	public static final BlockHelper HELPER = MoreRespawnAnchors.REGISTRATOR.getHelper(ForgeRegistries.BLOCKS);
	
	public static final RegistryObject<Block> NETHERITE_RESPAWN_ANCHOR = HELPER.createBlock("netherite_respawn_anchor", () -> new NetheriteRespawnAnchorBlock(Properties.RESPAWN_ANCHOR), new Item.Properties().group(ItemGroup.DECORATIONS).isImmuneToFire());
	public static final RegistryObject<Block> END_RESPAWN_ANCHOR = HELPER.createBlock("end_respawn_anchor", () -> new EndRespawnAnchorBlock(Block.Properties.from(Blocks.RESPAWN_ANCHOR)), new Item.Properties().group(ItemGroup.DECORATIONS));
	public static final RegistryObject<Block> NETHERITE_END_RESPAWN_ANCHOR = HELPER.createBlock("netherite_end_respawn_anchor", () -> new NetheriteEndRespawnAnchorBlock(Properties.RESPAWN_ANCHOR), new Item.Properties().group(ItemGroup.DECORATIONS).isImmuneToFire());
	
	public static class Properties {
		public static final Block.Properties RESPAWN_ANCHOR = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0F, 1200.0F).setLightLevel((state) -> {
		      return NetheriteRespawnAnchorBlock.getChargeScale(state, 15);
		   });
	}
	
}