package p455w0rd.stingyores.integration;

import appeng.worldgen.QuartzWorldGen;
import p455w0rd.stingyores.init.ModConfig.Options;
import p455w0rd.stingyores.init.ModIntegration;
import p455w0rd.stingyores.init.ModIntegration.Mods;
import p455w0rd.stingyores.init.ModLogger;

/**
 * @author p455w0rd
 *
 */
public class AE2 {

	public static void postInit() {
		if (Options.DISABLE_WORLDGEN) {
			ModLogger.info(Mods.AE2.getName() + " integration enabled");
			ModIntegration.addRemovableGenerator(QuartzWorldGen.class);
		}
	}

}
