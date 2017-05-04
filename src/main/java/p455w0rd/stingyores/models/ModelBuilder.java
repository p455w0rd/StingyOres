package p455w0rd.stingyores.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;

public class ModelBuilder {
	// create a blank baked model with the default values
	public static SimpleBakedModel newBlankModel(TextureAtlasSprite texture) {
		return new SimpleBakedModel(new LinkedList<BakedQuad>(), newBlankFacingLists(), true, true, texture, ItemCameraTransforms.DEFAULT, new ItemOverrideList(ImmutableList.of()));
	}

	// create a copy of a quad
	public static BakedQuad copyQuad(BakedQuad quad) {
		return new BakedQuad(Arrays.copyOf(quad.getVertexData(), quad.getVertexData().length), quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), quad.getFormat());
	}

	public static BakedQuad changeTexture(BakedQuad quad, TextureAtlasSprite tex) {
		quad = new BakedQuad(Arrays.copyOf(quad.getVertexData(), quad.getVertexData().length), quad.getTintIndex(), quad.getFace(), tex, quad.shouldApplyDiffuseLighting(), quad.getFormat());

		// 4 vertexes on each quad
		for (int i = 0; i < 4; ++i) {
			int j = 7 * i;
			// get the x,y,z coordinates
			float x = Float.intBitsToFloat(quad.getVertexData()[j]);
			float y = Float.intBitsToFloat(quad.getVertexData()[j + 1]);
			float z = Float.intBitsToFloat(quad.getVertexData()[j + 2]);
			float u = 0.0F;
			float v = 0.0F;

			// move x,y,z in boundary if they are outside
			if (x < 0 || x > 1) {
				x = (x + 1) % 1;
			}
			if (y < 0 || y > 1) {
				y = (y + 1) % 1;
			}
			if (z < 0 || z > 1) {
				z = (z + 1) % 1;
			}

			// calculate the UVs based on the x,y,z and the 'face' of the quad
			switch (quad.getFace().ordinal()) {
			case 0:
				u = x * 16.0F;
				v = (1.0F - z) * 16.0F;
				break;
			case 1:
				u = x * 16.0F;
				v = z * 16.0F;
				break;
			case 2:
				u = (1.0F - x) * 16.0F;
				v = (1.0F - y) * 16.0F;
				break;
			case 3:
				u = x * 16.0F;
				v = (1.0F - y) * 16.0F;
				break;
			case 4:
				u = z * 16.0F;
				v = (1.0F - y) * 16.0F;
				break;
			case 5:
				u = (1.0F - z) * 16.0F;
				v = (1.0F - y) * 16.0F;
			}

			// set the new texture uvs
			quad.getVertexData()[j + 4] = Float.floatToRawIntBits(tex.getInterpolatedU(u));
			quad.getVertexData()[j + 4 + 1] = Float.floatToRawIntBits(tex.getInterpolatedV(v));
		}

		return quad;
	}

	// Creates a copy of the baked model with a given texture overlayed on the sides
	public static IBakedModel changeIcon(IBlockState state, IBakedModel model, TextureAtlasSprite texture) {
		LinkedList<BakedQuad> generalQuadsList = new LinkedList<BakedQuad>();
		Map<EnumFacing, List<BakedQuad>> blankFacingLists = newBlankFacingLists();
		SimpleBakedModel bakedModel = new SimpleBakedModel(generalQuadsList, blankFacingLists, model.isGui3d(), model.isAmbientOcclusion(), texture, model.getItemCameraTransforms(), new ItemOverrideList(ImmutableList.of()));

		for (BakedQuad quad : model.getQuads(state, null, 0)) {
			generalQuadsList.add(changeTexture(quad, texture));
		}

		for (EnumFacing facing : EnumFacing.values()) {
			for (BakedQuad o : model.getQuads(state, facing, 0)) {
				blankFacingLists.get(facing).add(changeTexture(o, texture));
			}
		}

		IBakedModel result = bakedModel;

		if (model instanceof IPerspectiveAwareModel) {
			result = new PerspectiveWrapper(result, (IPerspectiveAwareModel) model);
		}

		return result;
	}

	// creates blank lists
	public static Map<EnumFacing, List<BakedQuad>> newBlankFacingLists() {
		Map<EnumFacing, List<BakedQuad>> quadsMap = new EnumMap<>(EnumFacing.class);
		for (EnumFacing facing : EnumFacing.values()) {
			quadsMap.put(facing, new ArrayList<>());
		}

		return quadsMap;
	}

	// Get the default model resource location for a block state
	// Used to put an entry into the model registry
	public static ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(Block.REGISTRY.getNameForObject(state.getBlock()), (new DefaultStateMapper()).getPropertyString(state.getProperties()));
	}
}