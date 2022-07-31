package co.eltrut.morerespawnanchors.core.registry;

import co.eltrut.differentiate.core.registrator.BlockEntityHelper;
import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import co.eltrut.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.HashSet;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRABlockEntities {

	public static final BlockEntityHelper HELPER = MoreRespawnAnchors.REGISTRATOR.getHelper(ForgeRegistries.BLOCK_ENTITIES);

	public static final RegistryObject<BlockEntityType<EndRespawnAnchorTileEntity>> END_RESPAWN_ANCHOR = HELPER.createBlockEntity("end_respawn_anchor", EndRespawnAnchorTileEntity::new, () -> new Block[] {MRABlocks.END_RESPAWN_ANCHOR.get(), MRABlocks.NETHERITE_END_RESPAWN_ANCHOR.get()});
	
}
