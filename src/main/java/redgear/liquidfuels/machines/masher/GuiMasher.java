package redgear.liquidfuels.machines.masher;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiMasher extends GuiBase<ContainerMasher>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/Masher.png");

	public GuiMasher(ContainerMasher container) {
		super(container, texture);
		
		name = "gui.masher.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTankWithGlass(this, 11, 13, myContainer.myTile.waterTank));
		addElement(new ElementFluidTankWithGlass(this, 149, 13, myContainer.myTile.biomassTank));
	}

	@Override
	protected void updateElements() {

	}
}
