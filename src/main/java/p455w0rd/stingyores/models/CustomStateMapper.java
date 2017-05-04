package p455w0rd.stingyores.models;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomStateMapper extends StateMapperBase {

	private ModelResourceLocation location;

	public CustomStateMapper(ModelResourceLocation location) {
		this.location = location;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return location;
	}

}
