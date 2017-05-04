package p455w0rd.stingyores.sprites;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;
import p455w0rd.stingyores.init.ModGlobals;
import p455w0rd.stingyores.init.ModLogger;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class SpriteStingyOre extends TextureAtlasSprite {
	private BlockStingyOre stingyOre;

	public SpriteStingyOre(BlockStingyOre stingyOreIn) {
		super(getDerivedName(stingyOreIn.getName()));
		stingyOre = stingyOreIn;
	}

	@Override
	public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {

		ResourceLocation location1 = new ResourceLocation(location.getResourceDomain(), String.format("%s/%s%s", "textures/blocks", location.getResourcePath(), ".png"));
		try {
			// check to see if the resource can be loaded (someone added an override)
			manager.getResource(location1);
			ModLogger.info("Detected override for stingy " + stingyOre.getName() + " ore");
			return false;
		}
		catch (IOException e) {
			return true;
		}
	}

	public static String getDerivedName(String s2) {
		String s1 = "minecraft";

		int ind = s2.indexOf(58);

		if (ind >= 0) {
			if (ind > 1) {
				s1 = s2.substring(0, ind);
			}

			s2 = s2.substring(ind + 1, s2.length());
		}

		s1 = s1.toLowerCase();

		return ModGlobals.MODID + ":" + s1 + "/stingy_" + s2 + "_ore";
	}

	public static ResourceLocation getBlockResource(String s2) {
		String s1 = "minecraft";
		int ind = s2.indexOf(":");
		if (ind >= 0) {
			if (ind > 1) {
				s1 = s2.substring(0, ind);
			}
			s2 = s2.substring(ind + 1, s2.length());
		}
		s1 = s1.toLowerCase();
		s2 = "textures/" + s2 + ".png";
		return new ResourceLocation(s1, s2);
	}

	private int[] createStingyOreTexture(int w, int div, int[] underlying_data, int[] alpha_data, int[] ore_data, BufferedImage alpha_image) {
		int[] temp_data = new int[w * w];
		for (int ih = 0; ih < w; ih++) {
			for (int iw = 0; iw < w; iw++) {
				float alpha = ((float) ((alpha_data[iw / div + (ih / div) * alpha_image.getWidth()]) & 0xffffff)) / 0xffffff;
				temp_data[iw + ih * w] = 0xff000000 + ((int) (ore_data[iw + ih * w] * alpha + underlying_data[iw + ih * w] * (1 - alpha)) & 0xffffff);
			}
		}
		return temp_data;
	}

	@Override
	public boolean load(IResourceManager manager, ResourceLocation location) {
		int mp = Minecraft.getMinecraft().gameSettings.mipmapLevels + 1;
		BufferedImage[] ore_image = new BufferedImage[mp];
		BufferedImage underlying_image;
		BufferedImage alpha_image;
		int w;
		PngSizeInfo sizeInfo;
		AnimationMetadataSection animation;
		try {
			int index = stingyOre.getBaseBlockTexture().indexOf(":");
			IResource iResourceBase = manager.getResource(new ResourceLocation(stingyOre.getBaseBlockTexture().substring(0, index), "textures/blocks/" + stingyOre.getBaseBlockTexture().substring(index + 1) + ".png"));
			sizeInfo = PngSizeInfo.makeFromResource(manager.getResource(new ResourceLocation(stingyOre.getBaseBlockTexture().substring(0, index), "textures/blocks/" + stingyOre.getBaseBlockTexture().substring(index + 1) + ".png")));
			index = stingyOre.getUnderlyingBlockName().indexOf(":");
			IResource iResourceUnderlying = manager.getResource(new ResourceLocation(stingyOre.getUnderlyingBlockName().substring(0, index), "textures/blocks/" + stingyOre.getUnderlyingBlockName().substring(index + 1) + ".png"));
			IResource iResourceAlpha = manager.getResource(new ResourceLocation(ModGlobals.MODID, "textures/blocks/ore_stingy_alpha_" + stingyOre.getOreRenderType() + ".png"));
			InputStream baseInputStream = iResourceBase.getInputStream();
			ore_image[0] = ImageIO.read(baseInputStream);
			animation = (AnimationMetadataSection) iResourceBase.getMetadata("animation");
			underlying_image = ImageIO.read(iResourceUnderlying.getInputStream());
			alpha_image = ImageIO.read(iResourceAlpha.getInputStream());
			w = ore_image[0].getWidth();
			if (underlying_image.getWidth() != w) {
				List<IResource> resourcePacks = manager.getAllResources(new ResourceLocation(stingyOre.getUnderlyingBlockName().substring(0, index), "textures/blocks/" + stingyOre.getUnderlyingBlockName().substring(index + 1) + ".png"));
				for (int i = resourcePacks.size() - 1; i >= 0; --i) {
					IResource resource = resourcePacks.get(i);
					underlying_image = ImageIO.read(resource.getInputStream());
					if (underlying_image.getWidth() == w) {
						break;
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		if (underlying_image.getWidth() != w) {
			ModLogger.error("Error generating texture for \"" + stingyOre.getName() + "\". Unable to find underlying texture with same size.");
			return true;
		}
		int div = w / alpha_image.getWidth();
		int h = ore_image[0].getHeight();
		BufferedImage output_image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		int[] ore_data = new int[w * w];
		int[] underlying_data = new int[w * w];
		int[] alpha_data = new int[(w / div) * (w / div)];
		underlying_image.getRGB(0, 0, w, w, underlying_data, 0, w);
		alpha_image.getRGB(0, 0, w / div, w / div, alpha_data, 0, w / div);
		for (int y = 0; y < h; y += w) {
			ore_image[0].getRGB(0, y, w, w, ore_data, 0, w);
			int[] new_data = createStingyOreTexture(w, div, underlying_data, alpha_data, ore_data, alpha_image);
			output_image.setRGB(0, y, w, w, new_data, 0, w);
		}
		ore_image[0] = output_image;
		try {
			loadSprite(sizeInfo, animation != null);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(output_image, "PNG", stream);
			byte[] bytes = stream.toByteArray();
			loadSpriteFrames(new IResource() {

				@Nonnull
				@Override
				public ResourceLocation getResourceLocation() {
					return location;
				}

				@Nonnull
				@Override
				public InputStream getInputStream() {
					return new ByteArrayInputStream(bytes);
				}

				@Override
				public boolean hasMetadata() {
					return true;
				}

				@SuppressWarnings("unchecked")
				@Nullable
				@Override
				public <T extends IMetadataSection> T getMetadata(String sectionName) {
					if ("animation".equals(sectionName)) {
						return (T) animation;
					}
					return null;
				}

				@Nonnull
				@Override
				public String getResourcePackName() {
					return "test";
				}

				@Override
				public void close() throws IOException {

				}
			}, mp);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}

		ModLogger.info("Successfully generated texture for \"" + stingyOre.getName() + "\" with background \"" + stingyOre.getUnderlyingBlock().getLocalizedName() + "\".");// Place \"stingy_" + stingyOre.getName() + "_ore.png\" in the assets folder to override.");
		return false;
	}

	public static class Offset {
		public int[] dx;
		public int[] dy;

		public static Offset getOffset(int renderType) {
			Offset offset = new Offset();
			switch (renderType) {
			default:
			case 0:
				offset.dx = new int[] {
						-1,
						2,
						3
				};
				offset.dy = new int[] {
						-1,
						0,
						1
				};
				break;
			case 1:
				offset.dx = new int[] {
						-1,
						1,
						0,
						0,
						-1,
						-1,
						1,
						1,
						-2,
						2,
						0,
						0
				};
				offset.dy = new int[] {
						0,
						0,
						-1,
						1,
						-1,
						1,
						-1,
						1,
						0,
						0,
						-2,
						2
				};
				break;
			case 2:
				offset.dx = new int[] {
						-1,
						0,
						1
				};
				offset.dy = new int[] {
						-1,
						0,
						1
				};
				break;
			case 3:
				offset.dx = new int[] {
						-2,
						2,
						1,
						1
				};
				offset.dy = new int[] {
						1,
						1,
						-2,
						2
				};
			case 4:
				offset.dx = new int[] {
						-6,
						-3,
						3,
						6
				};
				offset.dy = new int[] {
						0,
						0,
						0,
						0
				};
				break;
			case 5:
				offset.dx = new int[] {
						-5,
						-5,
						5,
						5
				};
				offset.dy = new int[] {
						-5,
						5,
						-5,
						5
				};
				break;
			case 6:
				offset.dx = new int[] {
						0,
						1,
						2,
						3
				};
				offset.dy = new int[] {
						0,
						-3,
						2,
						-1
				};
				break;
			case 7:
				offset.dx = new int[] {
						-1,
						1,
						0,
						0
				};
				offset.dy = new int[] {
						0,
						0,
						-1,
						1
				};
				break;
			}
			return offset;
		}
	}

}
