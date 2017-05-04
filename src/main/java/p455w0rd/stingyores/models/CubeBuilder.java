package p455w0rd.stingyores.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

import javax.vecmath.Vector4f;

import com.google.common.base.Preconditions;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CubeBuilder {

	private VertexFormat format;
	private final List<BakedQuad> output;
	private final EnumMap<EnumFacing, TextureAtlasSprite> textures = new EnumMap<>(EnumFacing.class);
	private EnumSet<EnumFacing> drawFaces = EnumSet.allOf(EnumFacing.class);
	private final EnumMap<EnumFacing, Vector4f> customUv = new EnumMap<>(EnumFacing.class);
	private byte[] uvRotations = new byte[EnumFacing.values().length];
	private int color = 0xFFFFFFFF;
	private boolean renderFullBright;
	private static final VertexFormat itemFormatWithLightMap = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

	public CubeBuilder(VertexFormat format, List<BakedQuad> output) {
		this.output = output;
		this.format = format;
	}

	public CubeBuilder(VertexFormat format) {
		this(format, new ArrayList<>(6));
	}

	public void addCube(float x1, float y1, float z1, float x2, float y2, float z2) {
		x1 /= 16F;
		y1 /= 16F;
		z1 /= 16F;
		x2 /= 16F;
		y2 /= 16F;
		z2 /= 16F;
		VertexFormat savedFormat = null;

		if (renderFullBright) {
			savedFormat = format;
			format = getFormatWithLightMap(format);
		}

		for (EnumFacing face : drawFaces) {
			putFace(face, x1, y1, z1, x2, y2, z2);
		}

		if (savedFormat != null) {
			format = savedFormat;
		}
	}

	public void addQuad(EnumFacing face, float x1, float y1, float z1, float x2, float y2, float z2) {
		VertexFormat savedFormat = null;

		if (renderFullBright) {
			savedFormat = format;
			format = new VertexFormat(savedFormat);

			if (!format.getElements().contains(DefaultVertexFormats.TEX_2S)) {
				format.addElement(DefaultVertexFormats.TEX_2S);
			}
		}

		putFace(face, x1, y1, z1, x2, y2, z2);

		if (savedFormat != null) {
			format = savedFormat;
		}
	}

	private static final class UvVector {
		float u1;
		float u2;
		float v1;
		float v2;
	}

	private void putFace(EnumFacing face, float x1, float y1, float z1, float x2, float y2, float z2) {
		TextureAtlasSprite texture = textures.get(face);
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(texture);
		builder.setQuadOrientation(face);
		UvVector uv = new UvVector();
		Vector4f customUv = this.customUv.get(face);

		if (customUv != null) {
			uv.u1 = texture.getInterpolatedU(customUv.x);
			uv.v1 = texture.getInterpolatedV(customUv.y);
			uv.u2 = texture.getInterpolatedU(customUv.z);
			uv.v2 = texture.getInterpolatedV(customUv.w);
		}
		else {
			uv = getDefaultUv(face, texture, x1, y1, z1, x2, y2, z2);
		}

		switch (face) {
		case DOWN:
			putVertexTR(builder, face, x2, y1, z1, uv);
			putVertexBR(builder, face, x2, y1, z2, uv);
			putVertexBL(builder, face, x1, y1, z2, uv);
			putVertexTL(builder, face, x1, y1, z1, uv);
			break;
		case UP:
			putVertexTL(builder, face, x1, y2, z1, uv);
			putVertexBL(builder, face, x1, y2, z2, uv);
			putVertexBR(builder, face, x2, y2, z2, uv);
			putVertexTR(builder, face, x2, y2, z1, uv);
			break;
		case NORTH:
			putVertexBR(builder, face, x2, y2, z1, uv);
			putVertexTR(builder, face, x2, y1, z1, uv);
			putVertexTL(builder, face, x1, y1, z1, uv);
			putVertexBL(builder, face, x1, y2, z1, uv);
			break;
		case SOUTH:
			putVertexBL(builder, face, x1, y2, z2, uv);
			putVertexTL(builder, face, x1, y1, z2, uv);
			putVertexTR(builder, face, x2, y1, z2, uv);
			putVertexBR(builder, face, x2, y2, z2, uv);
			break;
		case WEST:
			putVertexTL(builder, face, x1, y1, z1, uv);
			putVertexTR(builder, face, x1, y1, z2, uv);
			putVertexBR(builder, face, x1, y2, z2, uv);
			putVertexBL(builder, face, x1, y2, z1, uv);
			break;
		case EAST:
			putVertexBR(builder, face, x2, y2, z1, uv);
			putVertexBL(builder, face, x2, y2, z2, uv);
			putVertexTL(builder, face, x2, y1, z2, uv);
			putVertexTR(builder, face, x2, y1, z1, uv);
			break;
		}

		int[] vertexData = builder.build().getVertexData();
		output.add(new BakedQuad(vertexData, -1, face, texture, true, format));
	}

	private UvVector getDefaultUv(EnumFacing face, TextureAtlasSprite texture, float x1, float y1, float z1, float x2, float y2, float z2) {
		UvVector uv = new UvVector();

		switch (face) {
		case DOWN:
			uv.u1 = texture.getInterpolatedU(x1 * 16);
			uv.v1 = texture.getInterpolatedV(z1 * 16);
			uv.u2 = texture.getInterpolatedU(x2 * 16);
			uv.v2 = texture.getInterpolatedV(z2 * 16);
			break;
		case UP:
			uv.u1 = texture.getInterpolatedU(x1 * 16);
			uv.v1 = texture.getInterpolatedV(z1 * 16);
			uv.u2 = texture.getInterpolatedU(x2 * 16);
			uv.v2 = texture.getInterpolatedV(z2 * 16);
			break;
		case NORTH:
			uv.u1 = texture.getInterpolatedU(x1 * 16);
			uv.v1 = texture.getInterpolatedV(16 - y1 * 16);
			uv.u2 = texture.getInterpolatedU(x2 * 16);
			uv.v2 = texture.getInterpolatedV(16 - y2 * 16);
			break;
		case SOUTH:
			uv.u1 = texture.getInterpolatedU(x1 * 16);
			uv.v1 = texture.getInterpolatedV(16 - y1 * 16);
			uv.u2 = texture.getInterpolatedU(x2 * 16);
			uv.v2 = texture.getInterpolatedV(16 - y2 * 16);
			break;
		case WEST:
			uv.u1 = texture.getInterpolatedU(z1 * 16);
			uv.v1 = texture.getInterpolatedV(16 - y1 * 16);
			uv.u2 = texture.getInterpolatedU(z2 * 16);
			uv.v2 = texture.getInterpolatedV(16 - y2 * 16);
			break;
		case EAST:
			uv.u1 = texture.getInterpolatedU(z2 * 16);
			uv.v1 = texture.getInterpolatedV(16 - y1 * 16);
			uv.u2 = texture.getInterpolatedU(z1 * 16);
			uv.v2 = texture.getInterpolatedV(16 - y2 * 16);
			break;
		}

		return uv;
	}

	private void putVertexTL(UnpackedBakedQuad.Builder builder, EnumFacing face, float x, float y, float z, UvVector uv) {
		float u, v;

		switch (uvRotations[face.ordinal()]) {
		default:
		case 0:
			u = uv.u1;
			v = uv.v1;
			break;
		case 1:
			u = uv.u1;
			v = uv.v2;
			break;
		case 2:
			u = uv.u2;
			v = uv.v2;
			break;
		case 3:
			u = uv.u2;
			v = uv.v1;
			break;
		}

		putVertex(builder, face, x, y, z, u, v);
	}

	private void putVertexTR(UnpackedBakedQuad.Builder builder, EnumFacing face, float x, float y, float z, UvVector uv) {
		float u;
		float v;

		switch (uvRotations[face.ordinal()]) {
		default:
		case 0:
			u = uv.u2;
			v = uv.v1;
			break;
		case 1:
			u = uv.u1;
			v = uv.v1;
			break;
		case 2:
			u = uv.u1;
			v = uv.v2;
			break;
		case 3:
			u = uv.u2;
			v = uv.v2;
			break;
		}
		putVertex(builder, face, x, y, z, u, v);
	}

	private void putVertexBR(UnpackedBakedQuad.Builder builder, EnumFacing face, float x, float y, float z, UvVector uv) {
		float u;
		float v;

		switch (uvRotations[face.ordinal()]) {
		default:
		case 0:
			u = uv.u2;
			v = uv.v2;
			break;
		case 1:
			u = uv.u2;
			v = uv.v1;
			break;
		case 2:
			u = uv.u1;
			v = uv.v1;
			break;
		case 3:
			u = uv.u1;
			v = uv.v2;
			break;
		}

		putVertex(builder, face, x, y, z, u, v);
	}

	private void putVertexBL(UnpackedBakedQuad.Builder builder, EnumFacing face, float x, float y, float z, UvVector uv) {
		float u;
		float v;

		switch (uvRotations[face.ordinal()]) {
		default:
		case 0:
			u = uv.u1;
			v = uv.v2;
			break;
		case 1:
			u = uv.u2;
			v = uv.v2;
			break;
		case 2:
			u = uv.u2;
			v = uv.v1;
			break;
		case 3:
			u = uv.u1;
			v = uv.v1;
			break;
		}

		putVertex(builder, face, x, y, z, u, v);
	}

	private void putVertex(UnpackedBakedQuad.Builder builder, EnumFacing face, float x, float y, float z, float u, float v) {
		VertexFormat format = builder.getVertexFormat();

		for (int i = 0; i < format.getElementCount(); i++) {
			VertexFormatElement e = format.getElement(i);

			switch (e.getUsage()) {
			case POSITION:
				builder.put(i, x, y, z);
				break;
			case NORMAL:
				builder.put(i, face.getFrontOffsetX(), face.getFrontOffsetY(), face.getFrontOffsetZ());
				break;
			case COLOR:
				float r = (color >> 16 & 0xFF) / 255f;
				float g = (color >> 8 & 0xFF) / 255f;
				float b = (color & 0xFF) / 255f;
				float a = (color >> 24 & 0xFF) / 255f;
				builder.put(i, r, g, b, a);
				break;
			case UV:
				if (e.getIndex() == 0) {
					builder.put(i, u, v);
				}
				else {
					final float lightMapU = (float) (15 * 0x20) / 0xFFFF;
					final float lightMapV = (float) (15 * 0x20) / 0xFFFF;
					builder.put(i, lightMapU, lightMapV);
				}
				break;
			default:
				builder.put(i);
				break;
			}
		}
	}

	public void setTexture(TextureAtlasSprite texture) {
		for (EnumFacing face : EnumFacing.values()) {
			textures.put(face, texture);
		}
	}

	public void setTextures(TextureAtlasSprite up, TextureAtlasSprite down, TextureAtlasSprite north, TextureAtlasSprite south, TextureAtlasSprite east, TextureAtlasSprite west) {
		textures.put(EnumFacing.UP, up);
		textures.put(EnumFacing.DOWN, down);
		textures.put(EnumFacing.NORTH, north);
		textures.put(EnumFacing.SOUTH, south);
		textures.put(EnumFacing.EAST, east);
		textures.put(EnumFacing.WEST, west);
	}

	public void setTexture(EnumFacing facing, TextureAtlasSprite sprite) {
		textures.put(facing, sprite);
	}

	public void setDrawFaces(EnumSet<EnumFacing> drawFaces) {
		this.drawFaces = drawFaces;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setColorRGB(int color) {
		setColor(color | 0xFF000000);
	}

	public void setColorRGB(float r, float g, float b) {
		setColorRGB((int) (r * 255) << 16 | (int) (g * 255) << 8 | (int) (b * 255));
	}

	public void setRenderFullBright(boolean renderFullBright) {
		this.renderFullBright = renderFullBright;
	}

	public void setCustomUv(EnumFacing facing, float u1, float v1, float u2, float v2) {
		customUv.put(facing, new Vector4f(u1, v1, u2, v2));
	}

	public void setUvRotation(EnumFacing facing, int rotation) {
		if (rotation == 2) {
			rotation = 3;
		}
		else if (rotation == 3) {
			rotation = 2;
		}

		Preconditions.checkArgument(rotation >= 0 && rotation <= 3, "rotation");
		uvRotations[facing.ordinal()] = (byte) rotation;
	}

	public static VertexFormat getFormatWithLightMap(VertexFormat format) {
		if (FMLClientHandler.instance().hasOptifine() || !ForgeModContainer.forgeLightPipelineEnabled) {
			return format;
		}

		VertexFormat result;

		if (format == DefaultVertexFormats.BLOCK) {
			result = DefaultVertexFormats.BLOCK;
		}
		else if (format == DefaultVertexFormats.ITEM) {
			result = itemFormatWithLightMap;
		}
		else if (!format.hasUvOffset(1)) {
			result = new VertexFormat(format);
			result.addElement(DefaultVertexFormats.TEX_2S);
		}
		else {
			result = format;
		}
		return result;
	}

	public List<BakedQuad> getOutput() {
		return output;
	}

}
