package p455w0rd.stingyores.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author p455w0rd
 *
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		//ModelLoaderRegistry.registerLoader(CustomModelLoader.INSTANCE);
		//ModBlocks.preInitModels();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

}
