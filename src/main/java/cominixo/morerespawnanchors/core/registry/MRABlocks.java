package cominixo.morerespawnanchors.core.registry;

import cominixo.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRABlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoreRespawnAnchors.MOD_ID);
	
	public static final RegistryObject<Block> NETHERITE_RESPAWN_ANCHOR = BLOCKS.register("netherite_respawn_anchor", () -> new Block(Block.Properties.from(Blocks.DIRT)));
	public static final RegistryObject<Block> END_RESPAWN_ANCHOR = BLOCKS.register("end_respawn_anchor", () -> new Block(Block.Properties.from(Blocks.DIRT)));
	public static final RegistryObject<Block> NETHERITE_END_RESPAWN_ANCHOR = BLOCKS.register("netherite_end_respawn_anchor", () -> new Block(Block.Properties.from(Blocks.DIRT)));
	
}