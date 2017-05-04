package p455w0rd.stingyores.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import p455w0rd.stingyores.items.ItemDustNugget;

public class ModFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() instanceof ItemDustNugget) {
			ItemDustNugget n = (ItemDustNugget) fuel.getItem();
			return n.getBurnTime();
		}
		else {
			return 0;
		}
	}

}
