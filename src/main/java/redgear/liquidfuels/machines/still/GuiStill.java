package redgear.liquidfuels.machines.still;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiStill extends GuiBase<ContainerStill>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/Still.png");

	public GuiStill(ContainerStill container) {
		super(container, texture);
		
		name = "gui.still.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTankWithGlass(this, 18, 13, myContainer.myTile.steamTank));
		addElement(new ElementFluidTankWithGlass(this, 53, 13, myContainer.myTile.stillageTank));
		addElement(new ElementFluidTankWithGlass(this, 132, 13, myContainer.myTile.ethanolTank));
	}

	@Override
	protected void updateElements() {

	}
}
