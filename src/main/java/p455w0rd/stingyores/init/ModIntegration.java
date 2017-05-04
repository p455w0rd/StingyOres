package p455w0rd.stingyores.init;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Loader;
import p455w0rd.stingyores.integration.AE2;
import p455w0rd.stingyores.integration.TiC;
import p455w0rdslib.util.MCPrivateUtils;

/**
 * @author p455w0rd
 *
 */
public class ModIntegration {

	public static List<Class<? extends IWorldGenerator>> gensToBeRemoved = Lists.newLinkedList();

	public static void postInit() {
		if (Mods.TINKERS.isLoaded()) {
			TiC.postInit();
		}
		if (Mods.AE2.isLoaded()) {
			AE2.postInit();
		}
	}

	public static void serverStarted() {
		disableOreGen();
	}

	public static void disableOreGen() {
		Set<IWorldGenerator> generators = MCPrivateUtils.getWorldGenerators();
		Map<IWorldGenerator, Integer> generatorsIndex = MCPrivateUtils.getWorldGeneratorIndexList();

		Iterator<IWorldGenerator> iterator = generators.iterator();
		while (iterator.hasNext()) {
			IWorldGenerator current = iterator.next();
			if (gensToBeRemoved.contains(current.getClass())) {
				iterator.remove();
				generatorsIndex.remove(current);
			}
		}
	}

	public static void addRemovableGenerator(Class<? extends IWorldGenerator> generator) {
		if (!gensToBeRemoved.contains(generator)) {
			gensToBeRemoved.add(generator);
		}
	}

	public static enum Mods {
			TINKERS("tconstruct", "Tinker's Construct"), AE2("appliedenergistics2", "Applied Energistics 2");

		private String modid, name;

		Mods(String modidIn, String nameIn) {
			modid = modidIn;
			name = nameIn;
		}

		public String getId() {
			return modid;
		}

		public String getName() {
			return name;
		}

		public boolean isLoaded() {
			return Loader.isModLoaded(getId());
		}

	}

}
