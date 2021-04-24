package co.eltrut.morerespawnanchors.core;

import co.eltrut.differentiate.core.registrator.Registrator;
import co.eltrut.morerespawnanchors.core.other.MRACompat;
import co.eltrut.morerespawnanchors.core.registry.MRATileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("morerespawnanchors")
@Mod.EventBusSubscriber(modid = "morerespawnanchors", bus = Bus.MOD)
public class MoreRespawnAnchors {
    public static final String MOD_ID = "morerespawnanchors";
    public static final Registrator REGISTRATOR = new Registrator(MOD_ID);
    public static MoreRespawnAnchors instance;

    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public MoreRespawnAnchors() {
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MRAConfig.COMMON_SPEC);
    	
        modEventBus.addListener(this::doCommonStuff);
        modEventBus.addListener(this::doClientStuff);
        instance = this;
        
        MRATileEntities.HELPER.register(modEventBus);
        
        MinecraftForge.EVENT_BUS.register(this);
        
    }
    
    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
    }

    private void doCommonStuff(final FMLCommonSetupEvent event) {
    	MRACompat.registerDispenserBehaviors();
    }
    
    private void doClientStuff(final FMLClientSetupEvent event) {
    	MRACompat.registerEntityRenderers();
    }
}
