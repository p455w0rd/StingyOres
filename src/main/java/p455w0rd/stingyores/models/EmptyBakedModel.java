package p455w0rd.stingyores.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.util.EnumFacing;

public class EmptyBakedModel extends SimpleBakedModel {

	protected static final List<BakedQuad> EMPTY_QUADS = ImmutableList.of();
	protected static final boolean EMPTY_AMBIENTOCCLUSION = false;
	protected static final boolean EMPTY_GUI3D = false;
	protected static final ItemCameraTransforms EMPTY_CAMERATRANSFORMS = ItemCameraTransforms.DEFAULT;
	protected static final Map<EnumFacing, List<BakedQuad>> EMPTY_FACE_QUADS;
	protected boolean isItem;

	static {
		HashMap<EnumFacing, List<BakedQuad>> map = new HashMap<>();
		for (EnumFacing facing : EnumFacing.values()) {
			map.put(facing, ImmutableList.of());
		}

		EMPTY_FACE_QUADS = map;
	}

	public EmptyBakedModel() {
		super(EMPTY_QUADS, EMPTY_FACE_QUADS, EMPTY_AMBIENTOCCLUSION, EMPTY_GUI3D, Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite(), EMPTY_CAMERATRANSFORMS, new ItemOverrideList(ImmutableList.of()));
	}

}