package redgear.liquidfuels.machines.fluidboiler;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiFluidBoiler extends GuiBase<ContainerFluidBoiler>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/FluidBoiler.png");

	public GuiFluidBoiler(ContainerFluidBoiler container) {
		super(container, texture);
		
		name = "gui.boiler.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTankWithGlass(this, 40,  12, myContainer.myTile.waterTank));
		addElement(new ElementFluidTankWithGlass(this, 80,  12, myContainer.myTile.fuel));
		addElement(new ElementFluidTankWithGlass(this, 120, 12, myContainer.myTile.steamTank).setGauge(1));
	}

	@Override
	protected void updateElements() {

	}

}
