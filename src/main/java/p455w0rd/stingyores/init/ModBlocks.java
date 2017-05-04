package p455w0rd.stingyores.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import p455w0rd.stingyores.items.ItemBlockStingyOre;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ModBlocks {

	public static void preInit() {

	}

	public static void init() {
		for (BlockStingyOre stingyOre : ModGlobals.ORES_LIST) {
			GameRegistry.register(stingyOre);
			GameRegistry.register(new ItemBlockStingyOre(stingyOre), new ResourceLocation(ModGlobals.MODID, "stingy_" + stingyOre.getName() + "_ore"));
			stingyOre.registerOreDict();
			if (stingyOre.getBaseBlock() == Blocks.GOLD_ORE) {
				GameRegistry.addSmelting(stingyOre, new ItemStack(Items.GOLD_NUGGET), 0.1f);
			}
		}
	}

}
