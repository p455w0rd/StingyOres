package p455w0rd.stingyores.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import p455w0rd.stingyores.init.ModGlobals;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ItemBlockStingyOre extends ItemBlock {

	private BlockStingyOre stingyOre;

	public ItemBlockStingyOre(Block stingyOreIn) {
		super(stingyOreIn);

		setStingyOre((BlockStingyOre) stingyOreIn);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		String prefix = ("" + I18n.translateToLocal(ModGlobals.MODID + ".prefix")).trim();
		return prefix.replaceFirst("ORENAME", new ItemStack(getStingyOre().getBaseBlock(), 1, getStingyOre().getBaseBlockMeta()).getDisplayName());
	}

	public BlockStingyOre getStingyOre() {
		return stingyOre;
	}

	public void setStingyOre(BlockStingyOre block) {
		stingyOre = block;
	}
}
