package p455w0rd.stingyores.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import p455w0rd.stingyores.items.ItemDustNugget;

public class ModItems {

	public static void preInit() {

	}

	public static void init() {
		for (ItemDustNugget nugget : ModGlobals.NUGGETS_LIST) {
			GameRegistry.register(nugget);
			nugget.registerOreDict();
			nugget.registerSmelting();
		}
		for (ItemDustNugget dust : ModGlobals.DUSTS_LIST) {
			GameRegistry.register(dust);
			dust.registerOreDict();
		}
	}

	public static void postInit() {
		for (ItemDustNugget nugget : ModGlobals.NUGGETS_LIST) {
			nugget.registerCrafting();
		}
	}
}
