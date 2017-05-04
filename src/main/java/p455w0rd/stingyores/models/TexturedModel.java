package p455w0rd.stingyores.models;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TexturedModel implements IModel {

	private TextureAtlasSprite texture;

	public TexturedModel(TextureAtlasSprite texture) {
		this.texture = texture;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Lists.newArrayList();
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return Lists.newArrayList();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new TexturedBakedModel(texture);
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

}
