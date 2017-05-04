package p455w0rd.stingyyores.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import p455w0rd.stingyores.init.ModCreativeTab;
import p455w0rd.stingyores.init.ModLogger;
import p455w0rd.stingyores.items.ItemDustNugget;
import p455w0rd.stingyores.sprites.SpriteStingyOre;

public class BlockStingyOre extends Block {

	private String name;
	private ItemDustNugget nugget;
	private int veinRate;
	private int veinSize;
	private int veinHeight;
	private List<Integer> dimWhiteList;
	private List<Integer> dimBlackList;
	private Block baseBlock;
	private String modID;
	private String baseBlockName;
	private int baseBlockMeta;
	private String baseBlockTexture;
	private Block underlyingBlock;
	private String oreDictName;
	private int oreRenderType;
	private SpriteStingyOre sprite;
	private String texture;
	private IBlockState baseBlockState;

	public BlockStingyOre(String name, String modID, String baseBlockName, int baseBlockMeta, String baseBlockTexture, Block underlyingBlock, int oreRenderType, int veinRate, int veinSize, int veinHeight, List<Integer> dimWhiteList, List<Integer> dimBlackList) {
		super(underlyingBlock.getDefaultState().getMaterial());
		setName(name);
		setRegistryName("stingy_" + name + "_ore");
		setUnlocalizedName("stingy_" + name + "_ore");
		setVeinRate(veinRate);
		setVeinSize(veinSize);
		setVeinHeight(veinHeight);
		setDimWhiteList(dimWhiteList);
		setDimBlackList(dimBlackList);
		setModID(modID);
		setBaseBlockName(baseBlockName);
		setBaseBlockMeta(baseBlockMeta);
		setBaseBlockTexture(baseBlockTexture);
		setUnderlyingBlock(underlyingBlock);
		setOreRenderType(oreRenderType);
		setOreDictName("oreStingy" + Character.toString(name.charAt(0)).toUpperCase() + name.substring(1));
		setCreativeTab(ModCreativeTab.STINGY_ORES);
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this);
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String textureIn) {
		texture = textureIn;
	}

	public SpriteStingyOre getSprite() {
		return sprite;
	}

	public void setSprite(TextureAtlasSprite spriteIn) {
		sprite = (SpriteStingyOre) spriteIn;
	}

	public String getOreDictName() {
		return oreDictName;
	}

	public void setOreDictName(String oreDictName) {
		this.oreDictName = oreDictName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item getNugget() {
		return getBaseBlock() == Blocks.GOLD_ORE ? Items.GOLD_NUGGET : nugget;
	}

	public void setNugget(ItemDustNugget nugget) {
		this.nugget = nugget;
	}

	public int getVeinRate() {
		return veinRate;
	}

	public void setVeinRate(int veinRate) {
		this.veinRate = veinRate;
	}

	public int getVeinSize() {
		return veinSize;
	}

	public void setVeinSize(int veinSize) {
		this.veinSize = veinSize;
	}

	public int getVeinHeight() {
		return veinHeight;
	}

	public void setVeinHeight(int veinHeight) {
		this.veinHeight = veinHeight;
	}

	public List<Integer> getDimWhiteList() {
		return dimWhiteList;
	}

	public void setDimWhiteList(List<Integer> dimWhiteList) {
		this.dimWhiteList = dimWhiteList;
	}

	public List<Integer> getDimBlackList() {
		return dimBlackList;
	}

	public void setDimBlackList(List<Integer> dimBlackList) {
		this.dimBlackList = dimBlackList;
	}

	public Block getBaseBlock() {
		if (baseBlock == null) {
			if (setBaseBlock(Block.getBlockFromName(getModID() + ":" + getBaseBlockName())) == null) {
				ModLogger.error(getName() + ": Could not get baseBlock.\"" + getModID() + ":" + getBaseBlockName() + "\"");
			}
		}
		return baseBlock;
	}

	public IBlockState getBaseBlockState() {
		if (baseBlockState == null) {
			baseBlockState = getBaseBlock().getStateFromMeta(getBaseBlockMeta());
			if (baseBlockState == null) {
				baseBlockState = getBaseBlock().getDefaultState();
			}
		}
		return baseBlockState;
	}

	public Block setBaseBlock(Block baseBlock) {
		this.baseBlock = baseBlock;
		return baseBlock;
	}

	public String getModID() {
		return modID;
	}

	public void setModID(String modID) {
		this.modID = modID;
	}

	public String getBaseBlockName() {
		return baseBlockName;
	}

	public void setBaseBlockName(String baseBlockName) {
		this.baseBlockName = baseBlockName;
	}

	public int getBaseBlockMeta() {
		return baseBlockMeta;
	}

	public void setBaseBlockMeta(int baseBlockMeta) {
		this.baseBlockMeta = baseBlockMeta;
	}

	public String getBaseBlockTexture() {
		return baseBlockTexture;
	}

	public void setBaseBlockTexture(String baseBlockTexture) {
		this.baseBlockTexture = baseBlockTexture;
	}

	public String getUnderlyingBlockName() {
		return underlyingBlock.getRegistryName().toString();
	}

	public Block getUnderlyingBlock() {
		return underlyingBlock;
	}

	public void setUnderlyingBlock(Block underlyingBlock) {
		this.underlyingBlock = underlyingBlock;
	}

	public int getOreRenderType() {
		return oreRenderType;
	}

	public void setOreRenderType(int oreRenderType) {
		this.oreRenderType = oreRenderType;
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
		return getBaseBlock().getBlockHardness(state, world, pos);
	}

	public void registerOreDict() {
		OreDictionary.registerOre(getOreDictName(), this);
	}

	@Override
	public int getHarvestLevel(IBlockState state) {
		return getBaseBlockState().getBlock().getHarvestLevel(getBaseBlockState());
	}

	@Override
	public String getHarvestTool(IBlockState state) {
		return getBaseBlockState().getBlock().getHarvestTool(getBaseBlockState());
	}

	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		for (ItemStack drops : baseBlock.getDrops(world, pos, state, fortune)) {
			if (drops.getItem() == Item.getItemFromBlock(baseBlock)) {
				ret.add(new ItemStack(this, drops.stackSize));
			}
			else {
				ret.add(new ItemStack(getNugget(), drops.stackSize));
			}
		}

		return ret;
	}

}
