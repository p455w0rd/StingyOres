package p455w0rd.stingyores.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTab {

	public static final CreativeTabs STINGY_ORES = new CreativeTabs(ModGlobals.MODID.toLowerCase()) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModGlobals.ORES_LIST.getFirst());
		}
	};

}
