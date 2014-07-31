package redgear.liquidfuels.machines.molder;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTankWithGlass;

public class GuiMolder  extends GuiBase<ContainerMolder>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/Molder.png");

	public GuiMolder(ContainerMolder container) {
		super(container, texture);
		
		name = "gui.molder.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTankWithGlass(this, 66, 13, myContainer.myTile.tank));
	}

	@Override
	protected void updateElements() {

	}
}
