package cominixo.morerespawnanchors.core.registry;

import cominixo.morerespawnanchors.core.MoreRespawnAnchors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = MoreRespawnAnchors.MOD_ID, bus = Bus.MOD)
public class MRAItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoreRespawnAnchors.MOD_ID);
	
	public static final RegistryObject<Item> NETHERITE_RESPAWN_ANCHOR = ITEMS.register("netherite_respawn_anchor", () -> new BlockItem(MRABlocks.NETHERITE_RESPAWN_ANCHOR.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
	public static final RegistryObject<Item> END_RESPAWN_ANCHOR = ITEMS.register("end_respawn_anchor", () -> new BlockItem(MRABlocks.END_RESPAWN_ANCHOR.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
	public static final RegistryObject<Item> NETHERITE_END_RESPAWN_ANCHOR = ITEMS.register("netherite_end_respawn_anchor", () -> new BlockItem(MRABlocks.NETHERITE_END_RESPAWN_ANCHOR.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
	
}
