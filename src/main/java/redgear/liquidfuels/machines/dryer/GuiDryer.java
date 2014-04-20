package redgear.liquidfuels.machines.dryer;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTank;

public class GuiDryer extends GuiBase<ContainerDryer>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/Dryer.png");

	public GuiDryer(ContainerDryer container) {
		super(container, texture);
		
		name = "gui.dryer.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTank(this, 33, 13, myContainer.myTile.tank));
	}

	@Override
	protected void updateElements() {

	}
}
