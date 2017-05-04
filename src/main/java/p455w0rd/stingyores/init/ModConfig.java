package p455w0rd.stingyores.init;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import p455w0rd.stingyores.items.ItemDustNugget;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ModConfig {

	public static Configuration config;
	public static final String ORE_PREFIX = "ores.block_";

	public static void init(File configFile) {
		config = new Configuration(configFile);

		//read worldgen config
		Options.DISABLE_WORLDGEN = config.getBoolean("disable_standard_oregen", Configuration.CATEGORY_GENERAL, false, "Disable all standard oregen (this will also disable ore replacement)");
		/*
		ModGlobals.CONFIG_WORLDGEN_COAL = config.getBoolean("baseWorldgenCoal", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for coal");
		ModGlobals.CONFIG_WORLDGEN_DIAMOND = config.getBoolean("baseWorldgenDiamond", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for diamond");
		ModGlobals.CONFIG_WORLDGEN_EMERALD = config.getBoolean("baseWorldgenEmerald", Configuration.CATEGORY_GENERAL, true, "Enable/Disable worldgen for emerald");
		ModGlobals.CONFIG_WORLDGEN_GOLD = config.getBoolean("baseWorldgenGold", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for gold");
		ModGlobals.CONFIG_WORLDGEN_IRON = config.getBoolean("baseWorldgenIron", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for iron");
		ModGlobals.CONFIG_WORLDGEN_LAPIS = config.getBoolean("baseWorldgenLapis", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for lapis");
		ModGlobals.CONFIG_WORLDGEN_REDSTONE = config.getBoolean("baseWorldgenRedstone", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for redstone");
		ModGlobals.CONFIG_WORLDGEN_QUARTZ = config.getBoolean("baseWorldgenQuartz", Configuration.CATEGORY_GENERAL, false, "Enable/Disable worldgen for quartz");
		*/
		Options.ADD_CRAFTING = config.getBoolean("add_crafting", Configuration.CATEGORY_GENERAL, true, "Enable recipes for crafting");
		Options.ADD_SMELTING = config.getBoolean("add_smelting", Configuration.CATEGORY_GENERAL, true, "Enable recipes for smelting");

		Options.ALL_DUSTS = config.getBoolean("all_dusts", Configuration.CATEGORY_GENERAL, false, "Enable creation of tiny piles of dust for all ores");

		//adding vanilla ores
		if (config.getBoolean("generateVanillaConfig", Configuration.CATEGORY_GENERAL, true, "Adds all vanilla ores to the config file.")) {
			config.get(ORE_PREFIX + "coal", "modID", "minecraft");
			config.get(ORE_PREFIX + "coal", "baseBlock", "coal_ore");
			config.get(ORE_PREFIX + "coal", "burnTime", 200);
			config.get(ORE_PREFIX + "coal", "nuggetColor", "0x363636");
			config.get(ORE_PREFIX + "coal", "isDust", true);
			config.get(ORE_PREFIX + "coal", "veinHeight", 120);
			config.get(ORE_PREFIX + "coal", "veinRate", 12);
			config.get(ORE_PREFIX + "coal", "veinSize", 32);
			config.get(ORE_PREFIX + "diamond", "modID", "minecraft");
			config.get(ORE_PREFIX + "diamond", "baseBlock", "diamond_ore");
			config.get(ORE_PREFIX + "diamond", "nuggetColor", "0x4AEDD1");
			config.get(ORE_PREFIX + "diamond", "veinHeight", 16);
			config.get(ORE_PREFIX + "diamond", "veinRate", 3);
			config.get(ORE_PREFIX + "diamond", "veinSize", 8);
			config.get(ORE_PREFIX + "emerald", "modID", "minecraft");
			config.get(ORE_PREFIX + "emerald", "baseBlock", "emerald_ore");
			config.get(ORE_PREFIX + "emerald", "oreRenderType", 1);
			config.get(ORE_PREFIX + "emerald", "nuggetColor", "0x17DD62");
			config.get(ORE_PREFIX + "emerald", "veinHeight", 40);
			config.get(ORE_PREFIX + "emerald", "veinRate", 1);
			config.get(ORE_PREFIX + "emerald", "veinSize", 1);
			config.get(ORE_PREFIX + "gold", "modID", "minecraft");
			config.get(ORE_PREFIX + "gold", "baseBlock", "gold_ore");
			config.get(ORE_PREFIX + "gold", "nuggetColor", "0xFFFF0B");
			config.get(ORE_PREFIX + "gold", "veinHeight", 32);
			config.get(ORE_PREFIX + "gold", "veinRate", 3);
			config.get(ORE_PREFIX + "gold", "veinSize", 16);
			config.get(ORE_PREFIX + "iron", "modID", "minecraft");
			config.get(ORE_PREFIX + "iron", "baseBlock", "iron_ore");
			config.get(ORE_PREFIX + "iron", "nuggetColor", "0xFFFFFF");
			config.get(ORE_PREFIX + "iron", "veinHeight", 64);
			config.get(ORE_PREFIX + "iron", "veinRate", 8);
			config.get(ORE_PREFIX + "iron", "veinSize", 24);
			config.get(ORE_PREFIX + "lapis", "modID", "minecraft");
			config.get(ORE_PREFIX + "lapis", "baseBlock", "lapis_ore");
			config.get(ORE_PREFIX + "lapis", "nuggetColor", "0x456ED1");
			config.get(ORE_PREFIX + "lapis", "veinHeight", 32);
			config.get(ORE_PREFIX + "lapis", "veinRate", 3);
			config.get(ORE_PREFIX + "lapis", "veinSize", 12);
			config.get(ORE_PREFIX + "redstone", "modID", "minecraft");
			config.get(ORE_PREFIX + "redstone", "baseBlock", "redstone_ore");
			config.get(ORE_PREFIX + "redstone", "nuggetColor", "0xFF0000");
			config.get(ORE_PREFIX + "redstone", "isDust", true);
			config.get(ORE_PREFIX + "redstone", "veinHeight", 16);
			config.get(ORE_PREFIX + "redstone", "veinRate", 4);
			config.get(ORE_PREFIX + "redstone", "veinSize", 16);
			config.get(ORE_PREFIX + "quartz", "modID", "minecraft");
			config.get(ORE_PREFIX + "quartz", "baseBlock", "quartz_ore");
			config.get(ORE_PREFIX + "quartz", "underlyingBlock", "minecraft:netherrack");
			config.get(ORE_PREFIX + "quartz", "oreRenderType", 2);
			config.get(ORE_PREFIX + "quartz", "nuggetColor", "0xEAE4DE");
			config.get(ORE_PREFIX + "quartz", "veinHeight", 128);
			config.get(ORE_PREFIX + "quartz", "veinRate", 10);
			config.get(ORE_PREFIX + "quartz", "veinSize", 32);

			config.get(Configuration.CATEGORY_GENERAL, "generateVanillaConfig", false, "Adds all vanilla ores to the config file. [default: false]").set(false);
		}

		for (String category : config.getCategoryNames()) {
			if (category.startsWith(ORE_PREFIX)) {
				String name = category.substring(ORE_PREFIX.length());
				String modID = config.get(category, "modID", "minecraft").getString();
				if (modID.equals("minecraft") || Loader.isModLoaded(modID)) {
					String baseBlockName = config.get(category, "baseBlock", "").getString();
					int baseBlockMeta = config.get(category, "baseBlockMeta", 0).getInt();
					boolean dust = config.get(category, "isDust", false).getBoolean();
					String baseBlockTexture = config.get(category, "baseBlockTexture", modID + ":" + baseBlockName).getString();
					String underlyingBlockName = config.get(category, "underlyingBlock", "minecraft:stone").getString();
					Block underlyingBlock = Block.getBlockFromName(underlyingBlockName);
					if (underlyingBlock != null) {
						int nuggetColor = 0x0;
						if (config.hasKey(category, "nuggetColor")) {
							nuggetColor = Integer.decode(config.get(category, "nuggetColor", "0x000000").getString());
						}
						int oreRenderType = config.get(category, "oreRenderType", 0).getInt();
						int nuggetRenderType = config.get(category, "nuggetRenderType", 0).getInt();
						int burnTime = config.get(category, "burnTime", 0).getInt();
						int veinRate = config.get(category, "veinRate", 0).getInt();
						int veinSize = config.get(category, "veinSize", 0).getInt();
						int veinHeight = config.get(category, "veinHeight", 0).getInt();
						String dimWhiteListStr = config.get(category, "dimWhiteList", "").getString();
						LinkedList<Integer> dimWhiteList = new LinkedList<Integer>();
						if (dimWhiteListStr.length() != 0) {
							String[] dimWhiteListStrArray = dimWhiteListStr.split(",");
							for (String entry : dimWhiteListStrArray) {
								try {
									dimWhiteList.add(Integer.parseInt(entry));
								}
								catch (NumberFormatException e) {
									ModLogger.warn(name + ": Could not parse " + entry + " to an integer.");
									ModLogger.warn(name + ": Entry will be ignored.");
								}
							}
						}
						String dimBlackListStr = config.get(category, "dimBlackList", "").getString();
						List<Integer> dimBlackList = new LinkedList<Integer>();
						if (dimBlackListStr.length() != 0) {
							String[] dimBlackListStrArray = dimBlackListStr.split(",");
							for (String entry : dimBlackListStrArray) {
								try {
									dimBlackList.add(Integer.parseInt(entry));
								}
								catch (NumberFormatException e) {
									ModLogger.warn(name + ": Could not parse " + entry + " to an integer.");
									ModLogger.warn(name + ": Entry will be ignored.");
								}
							}
						}
						BlockStingyOre stingyOre = new BlockStingyOre(name, modID, baseBlockName, baseBlockMeta, baseBlockTexture, underlyingBlock, oreRenderType, veinRate, veinSize, veinHeight, dimWhiteList, dimBlackList);
						ItemDustNugget nugget = new ItemDustNugget(name, stingyOre, baseBlockMeta, dust, burnTime, nuggetColor, nuggetRenderType);
						if (Options.ALL_DUSTS && !dust) {
							ModGlobals.DUSTS_LIST.add(new ItemDustNugget(name, stingyOre, baseBlockMeta, true, burnTime, nuggetColor, 0));
						}
						stingyOre.setNugget(nugget);
						ModGlobals.ORES_LIST.add(stingyOre);
						if (stingyOre.getBaseBlock() != Blocks.GOLD_ORE) {
							ModGlobals.NUGGETS_LIST.add(nugget);
						}
					}
					else {
						ModLogger.warn(name + ": Underlying Block \"" + underlyingBlockName + "\" not found.");
						ModLogger.warn(name + ": Ore will not be added.");
					}
				}
				else {
					ModLogger.warn(name + ": Corresponding mod \"" + modID + "\" is missing.");
					ModLogger.warn(name + ": Ore will not be added.");
				}
			}
		}

		if (config.hasChanged()) {
			config.save();
		}
	}

	public static class Options {

		public static boolean DISABLE_WORLDGEN = false;
		public static boolean ADD_CRAFTING = true;
		public static boolean ADD_SMELTING = true;
		public static boolean ALL_DUSTS = false;

	}
}
