package cominixo.morerespawnanchors.core.registry;

import cominixo.morerespawnanchors.common.blocks.EndRespawnAnchorBlock;
import cominixo.morerespawnanchors.common.blocks.NetheriteEndRespawnAnchorBlock;
import cominixo.morerespawnanchors.common.blocks.NetheriteRespawnAnchorBlock;
import cominixo.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRABlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreRespawnAnchors.MOD_ID);
	
	public static final RegistryObject<Block> NETHERITE_RESPAWN_ANCHOR = BLOCKS.register("netherite_respawn_anchor", () -> new NetheriteRespawnAnchorBlock(Properties.RESPAWN_ANCHOR));
	public static final RegistryObject<Block> END_RESPAWN_ANCHOR = BLOCKS.register("end_respawn_anchor", () -> new EndRespawnAnchorBlock(Block.Properties.from(Blocks.RESPAWN_ANCHOR)));
	public static final RegistryObject<Block> NETHERITE_END_RESPAWN_ANCHOR = BLOCKS.register("netherite_end_respawn_anchor", () -> new NetheriteEndRespawnAnchorBlock(Properties.RESPAWN_ANCHOR));
	
	public static class Properties {
		public static final Block.Properties RESPAWN_ANCHOR = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK).setRequiresTool().hardnessAndResistance(50.0F, 1200.0F).setLightLevel((state) -> {
		      return NetheriteRespawnAnchorBlock.getChargeScale(state, 15);
		   });
	}
	
}