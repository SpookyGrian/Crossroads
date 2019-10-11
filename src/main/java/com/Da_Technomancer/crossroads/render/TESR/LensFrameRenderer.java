package com.Da_Technomancer.crossroads.render.TESR;

import com.Da_Technomancer.crossroads.API.templates.BeamRenderTEBase;
import com.Da_Technomancer.crossroads.Crossroads;
import com.Da_Technomancer.crossroads.blocks.CrossroadsBlocks;
import com.Da_Technomancer.crossroads.tileentities.beams.LensFrameTileEntity;
import com.Da_Technomancer.essentials.blocks.EssentialsProperties;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LensFrameRenderer extends BeamRenderer{

	private static final ResourceLocation[] textures = new ResourceLocation[6];

	static{
		textures[0] = new ResourceLocation(Crossroads.MODID, "textures/items/gem_ruby.png");
		textures[1] = new ResourceLocation("textures/items/emerald.png");
		textures[2] = new ResourceLocation("textures/items/diamond.png");
		textures[3] = new ResourceLocation(Crossroads.MODID, "textures/items/pure_quartz.png");
		textures[4] = new ResourceLocation(Crossroads.MODID, "textures/items/glow_quartz.png");
		textures[5] = new ResourceLocation(Crossroads.MODID, "textures/items/void_crystal.png");
	}

	@Override
	public void render(BeamRenderTEBase beam, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		if(!beam.getWorld().isBlockLoaded(beam.getPos(), false)){
			return;
		}
		BlockState state = beam.getWorld().getBlockState(beam.getPos());
		super.render(beam, x, y, z, partialTicks, destroyStage, alpha);
		int content = ((LensFrameTileEntity) beam).getContents();
		if(content != 0 && state.getBlock() == CrossroadsBlocks.lensFrame){
			Direction.Axis axis = state.get(EssentialsProperties.AXIS);
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.translate(x + 0.5F, y + 0.5F, z + 0.5F);
			if(axis == Direction.Axis.X){
				GlStateManager.rotate(90, 0, 1, 0);
			}
			if(axis != Direction.Axis.Y){
				GlStateManager.rotate(90, 1, 0, 0);
			}

			Minecraft.getInstance().getTextureManager().bindTexture(textures[content - 1]);

			BufferBuilder buf = Tessellator.getInstance().getBuffer();
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(-0.5F, 0.1F, -0.5F).tex(0, 0).endVertex();
			buf.pos(-0.5F, 0.1F, 0.5F).tex(0, 1).endVertex();
			buf.pos(0.5F, 0.1F, 0.5F).tex(1, 1).endVertex();
			buf.pos(0.5F, 0.1F, -0.5F).tex(1, 0).endVertex();

			buf.pos(-0.5F, -0.1F, -0.5F).tex(0, 0).endVertex();
			buf.pos(0.5F, -0.1F, -0.5F).tex(1, 0).endVertex();
			buf.pos(0.5F, -0.1F, 0.5F).tex(1, 1).endVertex();
			buf.pos(-0.5F, -0.1F, 0.5F).tex(0, 1).endVertex();
			Tessellator.getInstance().draw();
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}
}