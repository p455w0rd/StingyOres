package p455w0rd.stingyores.init;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ModWorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (BlockStingyOre stingyOre : ModGlobals.ORES_LIST) {
			if (stingyOre.getVeinRate() != 0 && stingyOre.getVeinHeight() != 0 && stingyOre.getVeinSize() != 0 && (stingyOre.getDimWhiteList().contains(world.provider.getDimension()) || (stingyOre.getDimWhiteList().isEmpty() && !stingyOre.getDimBlackList().contains(world.provider.getDimension())))) {
				for (int i = 0; i <= stingyOre.getVeinRate(); i++) {
					new WorldGenMinable(stingyOre.getDefaultState(), stingyOre.getVeinSize(), BlockMatcher.forBlock(stingyOre.getUnderlyingBlock())).generate(world, random, new BlockPos(chunkX * 16 + random.nextInt(16), random.nextInt(stingyOre.getVeinHeight()), chunkZ * 16 + random.nextInt(16)));
				}
			}
		}
	}

}
