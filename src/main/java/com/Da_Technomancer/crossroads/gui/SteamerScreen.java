package com.Da_Technomancer.crossroads.gui;

import com.Da_Technomancer.crossroads.API.templates.MachineGUI;
import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.gui.container.SteamerContainer;
import com.Da_Technomancer.crossroads.tileentities.fluid.SteamerTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SteamerScreen extends MachineGUI<SteamerContainer, SteamerTileEntity>{

	private static final ResourceLocation GUI_TEXTURES = new ResourceLocation(Crossroads.MODID, "textures/gui/container/steamer_gui.png");

	public SteamerScreen(SteamerContainer cont, PlayerInventory playerInv, ITextComponent name){
		super(cont, playerInv, name);
	}

	@Override
	public void init(){
		super.init();
		initFluidManager(0, 33, 70);
		initFluidManager(1, 145, 70);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrix, float partialTicks, int mouseX, int mouseY){
		RenderSystem.color4f(1, 1, 1, 1);
		Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURES);

		blit(matrix, guiLeft, guiTop, 0, 0, xSize, ySize);

		blit(matrix, guiLeft + 79, guiTop + 34, 176, 0, container.cookProg.get() * 24 / SteamerTileEntity.REQUIRED, 17);

		super.drawGuiContainerBackgroundLayer(matrix, partialTicks, mouseX, mouseY);
	}
}
