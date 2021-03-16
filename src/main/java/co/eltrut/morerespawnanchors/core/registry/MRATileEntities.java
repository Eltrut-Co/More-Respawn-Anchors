package co.eltrut.morerespawnanchors.core.registry;

import java.util.Arrays;
import java.util.HashSet;

import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRATileEntities {

	public static final DeferredRegister<TileEntityType<?>> HELPER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MoreRespawnAnchors.MOD_ID);
	
	public static final RegistryObject<TileEntityType<EndRespawnAnchorTileEntity>> END_RESPAWN_ANCHOR = HELPER.register("end_respawn_anchor", () -> new TileEntityType<EndRespawnAnchorTileEntity>(EndRespawnAnchorTileEntity::new, new HashSet<Block>(Arrays.asList(new Block[] {MRABlocks.END_RESPAWN_ANCHOR.get(), MRABlocks.NETHERITE_END_RESPAWN_ANCHOR.get()})), null));
	
}
