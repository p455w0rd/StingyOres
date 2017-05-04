package p455w0rd.stingyores.integration;

import p455w0rd.stingyores.init.ModConfig.Options;
import p455w0rd.stingyores.init.ModIntegration;
import p455w0rd.stingyores.init.ModIntegration.Mods;
import p455w0rd.stingyores.init.ModLogger;
import slimeknights.tconstruct.shared.worldgen.NetherOreGenerator;

/**
 * @author p455w0rd
 *
 */
public class TiC {

	public static void postInit() {
		if (Options.DISABLE_WORLDGEN) {
			ModLogger.info(Mods.TINKERS.getName() + " integration enabled");
			slimeknights.tconstruct.common.config.Config.genArdite = false;
			slimeknights.tconstruct.common.config.Config.genCobalt = false;
			ModIntegration.addRemovableGenerator(NetherOreGenerator.class);
		}
	}

}
