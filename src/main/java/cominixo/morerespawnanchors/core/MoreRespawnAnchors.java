package cominixo.morerespawnanchors.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cominixo.morerespawnanchors.core.registry.MRABlocks;
import cominixo.morerespawnanchors.core.registry.MRAItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("morerespawnanchors")
@Mod.EventBusSubscriber(modid = "morerespawnanchors", bus = Bus.MOD)
public class MoreRespawnAnchors
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "morerespawnanchors";
    public static MoreRespawnAnchors instance;

    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public MoreRespawnAnchors() {
        modEventBus.addListener(this::doCommonStuff);
        modEventBus.addListener(this::doClientStuff);
        MRAItems.ITEMS.register(modEventBus);
        MRABlocks.BLOCKS.register(modEventBus);
        instance = this;
        
        MinecraftForge.EVENT_BUS.register(this);
        
    }
    
    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
    }

    private void doCommonStuff(final FMLCommonSetupEvent event)
    {
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
    }
}
