package p455w0rd.stingyores.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import p455w0rd.stingyores.StingyOres;
import p455w0rd.stingyores.init.ModBlocks;
import p455w0rd.stingyores.init.ModConfig;
import p455w0rd.stingyores.init.ModConfig.Options;
import p455w0rd.stingyores.init.ModEvents;
import p455w0rd.stingyores.init.ModFuelHandler;
import p455w0rd.stingyores.init.ModIntegration;
import p455w0rd.stingyores.init.ModItems;
import p455w0rd.stingyores.init.ModWorldGenerator;

/**
 * @author p455w0rd
 *
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		StingyOres.instance.configFile = e.getSuggestedConfigurationFile();
		ModEvents.preInit();
		ModBlocks.preInit();
		ModItems.preInit();
	}

	public void init(FMLInitializationEvent e) {
		ModConfig.init(StingyOres.instance.configFile);
		ModBlocks.init();
		ModItems.init();
		ModEvents.init();
		GameRegistry.registerWorldGenerator(new ModWorldGenerator(), Options.DISABLE_WORLDGEN ? 0 : 16);
		GameRegistry.registerFuelHandler(new ModFuelHandler());
	}

	public void postInit(FMLInitializationEvent e) {
		ModItems.postInit();
		ModIntegration.postInit();
	}

	public void serverStarted(FMLServerAboutToStartEvent e) {
		ModIntegration.serverStarted();
	}

}
