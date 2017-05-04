package p455w0rd.stingyores.items;

import java.util.Random;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import p455w0rd.stingyores.init.ModConfig.Options;
import p455w0rd.stingyores.init.ModCreativeTab;
import p455w0rd.stingyores.init.ModLogger;
import p455w0rd.stingyores.sprites.SpriteNugget;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ItemDustNugget extends Item {

	private String name;
	private BlockStingyOre stingyOre;
	private int meta;
	private boolean dust;
	private String oreDictName;
	private int burnTime;
	private int nuggetColor;
	private int nuggetRenderType;
	private ItemStack ingot = null;
	private SpriteNugget sprite;

	public ItemDustNugget(String name, BlockStingyOre stingyOre, int meta, boolean dust, int burnTime, int nuggetRenderType) {
		super();
		if (dust) {
			setName(name + "_dustTiny");
		}
		else {
			setName(name + "_nugget");
		}
		setRegistryName(getName());
		setUnlocalizedName(getName());
		setStingyOre(stingyOre);
		setMeta(meta);
		setDust(dust);
		setBurnTime(burnTime);
		if (dust) {
			setOreDictName("dustTiny" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
		}
		else {
			setOreDictName("nugget" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
		}
		setNuggetRenderType(nuggetRenderType);
		setCreativeTab(ModCreativeTab.STINGY_ORES);
	}

	public ItemDustNugget(String name, BlockStingyOre stingyOre, int meta, boolean dust, int burnTime, int nuggetColor, int nuggetRenderType) {
		this(name, stingyOre, meta, dust, burnTime, nuggetRenderType);
		setNuggetColor(nuggetColor);
	}

	public SpriteNugget getSprite() {
		return sprite;
	}

	public void setSprite(TextureAtlasSprite spriteIn) {
		sprite = (SpriteNugget) spriteIn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BlockStingyOre getStingyOre() {
		return stingyOre;
	}

	public void setStingyOre(BlockStingyOre stingyOre) {
		this.stingyOre = stingyOre;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public boolean getDust() {
		return dust;
	}

	public void setDust(boolean Dust) {
		dust = Dust;
	}

	public String getOreDictName() {
		return oreDictName;
	}

	public void setOreDictName(String oreDictName) {
		this.oreDictName = oreDictName;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}

	public int getNuggetColor() {
		return nuggetColor;
	}

	public void setNuggetColor(int nuggetColor) {
		this.nuggetColor = nuggetColor;
	}

	public int getNuggetRenderType() {
		return nuggetRenderType;
	}

	public void setNuggetRenderType(int nuggetRenderType) {
		this.nuggetRenderType = nuggetRenderType;
	}

	public ItemStack getIngot() {
		if (ingot == null) {
			ItemStack smelting = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(getStingyOre().getBaseBlock(), 1, getMeta()));
			if (smelting == null) {
				Item item = getStingyOre().getBaseBlock().getItemDropped(getStingyOre().getBaseBlockState(), new Random(), 0);
				ingot = new ItemStack(item, 1, getStingyOre().getBaseBlock().damageDropped(getStingyOre().getBaseBlockState()));
			}
			else {
				ingot = smelting;
			}
		}
		return ingot;
	}

	public void registerSmelting() {
		if (Options.ADD_SMELTING) {
			GameRegistry.addSmelting(getStingyOre(), new ItemStack(this), 0.1f);
		}
	}

	public void registerCrafting() {
		if (Options.ADD_CRAFTING) {
			ItemStack ingot = getIngot();
			if (ingot != null) {
				GameRegistry.addShapelessRecipe(new ItemStack(this, 9), ingot);
				GameRegistry.addRecipe(new ShapedOreRecipe(ingot, "nnn", "nnn", "nnn", 'n', getOreDictName()));
			}
			else {
				ModLogger.error(getName() + ": Could not get Ingot. Recipes will not be added.");
			}
		}
	}

	public void registerOreDict() {
		OreDictionary.registerOre(getOreDictName(), this);
	}

	@Override
	public String getItemStackDisplayName(ItemStack nugget) {
		String prefix;
		if (getDust()) {
			prefix = (I18n.translateToLocal("stingyores.dusts.prefix")).trim();
			String material = (I18n.translateToLocal("stingyores.material.dust." + getStingyOre().getName())).trim();
			if (material.equals(("stingyores.material.dust." + getStingyOre().getName()).trim())) {
				material = (I18n.translateToLocal("stingyores.material." + getStingyOre().getName())).trim();
				if (material.equals(("stingyores.material." + getStingyOre().getName()).trim())) {
					return prefix.replaceFirst("DUSTNAME", new ItemStack(getStingyOre().getBaseBlock(), 1, getStingyOre().getBaseBlockMeta()).getDisplayName());
				}
				else {
					return prefix.replaceFirst("DUSTNAME", material);
				}
			}
			else {
				return prefix.replaceFirst("DUSTNAME", material);
			}
		}
		else {
			prefix = (I18n.translateToLocal("stingyores.nuggets.prefix")).trim();
			String material = (I18n.translateToLocal("stingyores.material." + getStingyOre().getName())).trim();
			if (material.equals(("stingyores.material." + getStingyOre().getName()).trim())) {
				return prefix.replaceFirst("NUGGETNAME", new ItemStack(getStingyOre().getBaseBlock(), 1, getStingyOre().getBaseBlockMeta()).getDisplayName());
			}
			else {
				return prefix.replaceFirst("NUGGETNAME", material);
			}
		}
	}

}
