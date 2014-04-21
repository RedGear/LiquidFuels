package redgear.liquidfuels.machines.fermenter;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiFermenter extends GuiBase<ContainerFermenter>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/Fermenter.png");

	public GuiFermenter(ContainerFermenter container) {
		super(container, texture);
		
		name = "gui.fermenter.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTankWithGlass(this, 27, 13, myContainer.myTile.inputTank));
		addElement(new ElementFluidTankWithGlass(this, 132, 13, myContainer.myTile.outputTank));
	}

	@Override
	protected void updateElements() {

	}
}
