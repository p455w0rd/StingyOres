package p455w0rd.stingyores.models;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomModelLoader implements ICustomModelLoader {

	public static final CustomModelLoader INSTANCE = new CustomModelLoader();
	private Map<ResourceLocation, IModel> models = Maps.newHashMap();

	@Override
	public boolean accepts(ResourceLocation location) {
		return models.containsKey(location);
	}

	@Override
	public IModel loadModel(ResourceLocation location) throws Exception {
		return models.get(location);
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	public void registerModel(ModelResourceLocation location, IModel model) {
		models.put(location, model);
	}

}
