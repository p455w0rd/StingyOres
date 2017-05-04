package p455w0rd.stingyores.init;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW;
import static net.minecraftforge.fml.common.eventhandler.Event.Result.DENY;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import p455w0rd.stingyores.init.ModConfig.Options;
import p455w0rd.stingyores.items.ItemDustNugget;
import p455w0rd.stingyores.models.EmptyBakedModel;
import p455w0rd.stingyores.models.ModelBuilder;
import p455w0rd.stingyores.sprites.SpriteNugget;
import p455w0rd.stingyores.sprites.SpriteStingyOre;
import p455w0rd.stingyyores.blocks.BlockStingyOre;

public class ModEvents {

	public static void preInit() {
		MinecraftForge.ORE_GEN_BUS.register(new WorldGenEvents());
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.register(EventBusEvents.class);
		//MinecraftForge.EVENT_BUS.register(new EventBusEvents());
	}

	public static class EventBusEvents {

		@SubscribeEvent
		public void onWorldLoad(WorldEvent.Load event) {
			ModIntegration.serverStarted();
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		@SideOnly(Side.CLIENT)
		public static void onTextureStitch(TextureStitchEvent.Pre event) {
			ModelManager manager = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "modelManager", "field_175617_aL", "field_178090_d", "field_178128_c");
			TextureMap textureMap = event.getMap();
			BlockModelShapes shapes = manager.getBlockModelShapes();
			BlockStateMapper mapper = shapes.getBlockStateMapper();
			for (BlockStingyOre stingyOre : ModGlobals.ORES_LIST) {
				IBlockState state = stingyOre.getBaseBlock().getStateFromMeta(stingyOre.getBaseBlockMeta());
				Map<IBlockState, ModelResourceLocation> map = mapper.getVariants(stingyOre.getBaseBlock());
				ModelResourceLocation modelResourceLocation = map.get(state);
				IModel model = null;
				try {
					model = ModelLoaderRegistry.getModel(modelResourceLocation);
				}
				catch (Exception e) {
					continue;
				}
				Collection<ResourceLocation> textures = model.getTextures();
				for (ResourceLocation texture : textures) {
					if (!texture.equals(new ResourceLocation(stingyOre.getBaseBlockTexture()))) {
						stingyOre.setTexture(texture.toString());
						break;
					}
				}

			}

			for (BlockStingyOre stingyOre : ModGlobals.ORES_LIST) {
				if (stingyOre.getTexture() == null || "".equals(stingyOre.getTexture())) {
					stingyOre.setSprite(textureMap.getMissingSprite());
				}
				else {
					SpriteStingyOre textureOre = new SpriteStingyOre(stingyOre);
					if (!textureMap.setTextureEntry(textureOre)) {
						ModLogger.error(stingyOre.getName() + ": Error adding texture to texture atlas");
					}
					else {
						stingyOre.setSprite(textureMap.getTextureExtry(textureOre.getIconName()));
					}
				}
			}

			for (ItemDustNugget nugget : ModGlobals.NUGGETS_LIST) {
				TextureAtlasSprite texture = textureMap.getTextureExtry(nugget.getName());
				if (texture == null) {
					texture = new SpriteNugget(nugget);
					if (!textureMap.setTextureEntry(texture)) {
						ModLogger.error(nugget.getName() + ": Error adding texture to texture atlas");
					}
					else {
						nugget.setSprite(textureMap.getTextureExtry(texture.getIconName()));
					}
				}
			}

		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		@SideOnly(Side.CLIENT)
		public static void onBake(ModelBakeEvent event) {
			IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
			ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
			ModelManager manager = event.getModelManager();
			BlockModelShapes shapes = manager.getBlockModelShapes();
			BlockStateMapper mapper = shapes.getBlockStateMapper();

			for (BlockStingyOre stingyOre : ModGlobals.ORES_LIST) {
				Item item = Item.getItemFromBlock(stingyOre);
				registry.putObject(new ModelResourceLocation(Item.REGISTRY.getNameForObject(item), "inventory"), new EmptyBakedModel());
				Map<IBlockState, ModelResourceLocation> locations = new DefaultStateMapper().putStateModelLocations(stingyOre);
				final ModelResourceLocation[] invModels = new ModelResourceLocation[1];
				for (IBlockState iBlockState : stingyOre.getBlockState().getValidStates()) {
					ModelResourceLocation blockLocation = locations.get(iBlockState);
					ModelResourceLocation inventoryLocation = new ModelResourceLocation(Item.REGISTRY.getNameForObject(item), "inventory");

					ModelResourceLocation location = mapper.getVariants(stingyOre.getBaseBlock()).get(stingyOre.getBaseBlockState());
					IBakedModel parentModel = null;
					if (location != null) {
						parentModel = registry.getObject(location);
					}

					if (parentModel == null) {
						parentModel = registry.getObject(mapper.getVariants(Blocks.STONE).get(Blocks.STONE.getDefaultState()));
					}

					IBakedModel iBakedModel = ModelBuilder.changeIcon(stingyOre.getBaseBlockState(), parentModel, stingyOre.getSprite());

					registry.putObject(blockLocation, iBakedModel);
					registry.putObject(inventoryLocation, iBakedModel);

					mesher.register(item, 0, inventoryLocation);
					invModels[0] = inventoryLocation;
				}

				mesher.register(item, new ItemMeshDefinition() {
					@Nonnull
					@Override
					public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {

						return invModels[0];
					}
				});
			}

			for (ItemDustNugget nugget : ModGlobals.NUGGETS_LIST) {
				ModelResourceLocation inventoryLocation = new ModelResourceLocation(Item.REGISTRY.getNameForObject(nugget), "inventory");
				registry.putObject(inventoryLocation, ModelBuilder.newBlankModel(nugget.getSprite()));
				IBakedModel model = (new ItemLayerModel(ImmutableList.of(nugget.getSprite().getLocation()))).bake(part -> Optional.absent(), DefaultVertexFormats.ITEM, location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
				registry.putObject(inventoryLocation, model);
				mesher.register(nugget, 0, inventoryLocation);
				mesher.register(nugget, new ItemMeshDefinition() {
					@Nonnull
					@Override
					public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
						return inventoryLocation;
					}
				});
			}
		}

	}

	public static class WorldGenEvents {

		@SubscribeEvent
		public void onGeneration(OreGenEvent.GenerateMinable event) {
			event.setResult((Options.DISABLE_WORLDGEN) ? DENY : ALLOW);
		}

	}

}
