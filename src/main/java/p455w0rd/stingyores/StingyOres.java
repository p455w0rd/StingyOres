package p455w0rd.stingyores;

import java.io.File;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import p455w0rd.stingyores.init.ModGlobals;
import p455w0rd.stingyores.proxy.CommonProxy;

@Mod(modid = ModGlobals.MODID, name = ModGlobals.MOD_NAME, version = ModGlobals.VERSION, dependencies = ModGlobals.DEPENDENCIES)
public class StingyOres {

	@SidedProxy(clientSide = ModGlobals.CLIENT_PROXY, serverSide = ModGlobals.SERVER_PROXY)
	public static CommonProxy proxy;

	@Mod.Instance(ModGlobals.MODID)
	public static StingyOres instance;
	public File configFile;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLInitializationEvent e) {
		proxy.postInit(e);
	}

	@Mod.EventHandler
	public void serverStarted(FMLServerAboutToStartEvent e) {
		proxy.serverStarted(e);
	}
}
