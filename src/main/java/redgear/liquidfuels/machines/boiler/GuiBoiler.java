package redgear.liquidfuels.machines.boiler;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiBoiler extends GuiBase<ContainerBoiler>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/Boiler.png");

	public GuiBoiler(ContainerBoiler container) {
		super(container, texture);
		
		name = "gui.boiler.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTankWithGlass(this, 40,  12, myContainer.myTile.waterTank).setGauge(1));
		addElement(new ElementFluidTankWithGlass(this, 120, 12, myContainer.myTile.steamTank).setGauge(1));
	}

	@Override
	protected void updateElements() {

	}
}
