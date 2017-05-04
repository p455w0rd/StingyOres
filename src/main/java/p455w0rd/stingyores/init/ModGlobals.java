package p455w0rd.stingyores.init;

import java.util.LinkedList;

import p455w0rd.stingyores.items.ItemDustNugget;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ModGlobals {

	public static final String MODID = "stingyores";
	public static final String MOD_NAME = "Stingy Ores";
	public static final String VERSION = "1.0.0";
	public static final String RESOURCE_PREFIX = MODID.toLowerCase() + ":";
	public static final String SERVER_PROXY = "p455w0rd.stingyores.proxy.CommonProxy";
	public static final String CLIENT_PROXY = "p455w0rd.stingyores.proxy.ClientProxy";
	public static final String DEPENDENCIES = "required-after:p455w0rdslib;after:mantle;after:tconstruct;after:appliedenergistics2;after:*";

	public static final LinkedList<BlockStingyOre> ORES_LIST = new LinkedList<BlockStingyOre>();
	public static final LinkedList<ItemDustNugget> NUGGETS_LIST = new LinkedList<ItemDustNugget>();
	public static final LinkedList<ItemDustNugget> DUSTS_LIST = new LinkedList<ItemDustNugget>();

	/*
	public static boolean CONFIG_WORLDGEN_COAL = false;
	public static boolean CONFIG_WORLDGEN_DIAMOND = false;
	public static boolean CONFIG_WORLDGEN_EMERALD = false;
	public static boolean CONFIG_WORLDGEN_GOLD = false;
	public static boolean CONFIG_WORLDGEN_IRON = false;
	public static boolean CONFIG_WORLDGEN_LAPIS = false;
	public static boolean CONFIG_WORLDGEN_REDSTONE = false;
	public static boolean CONFIG_WORLDGEN_QUARTZ = false;
	**/

}
