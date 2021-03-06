package com.Da_Technomancer.crossroads.tileentities.rotary.mechanisms;

import com.Da_Technomancer.crossroads.API.Capabilities;
import com.Da_Technomancer.crossroads.API.rotary.*;
import com.Da_Technomancer.crossroads.items.CRItems;
import com.Da_Technomancer.crossroads.items.itemSets.GearFactory;
import com.Da_Technomancer.crossroads.items.itemSets.OreSetup;
import com.Da_Technomancer.crossroads.render.CRRenderTypes;
import com.Da_Technomancer.crossroads.render.CRRenderUtil;
import com.Da_Technomancer.crossroads.render.TESR.CRModels;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public class MechanismToggleGear extends MechanismSmallGear{

	private final boolean inverted;

	public MechanismToggleGear(boolean inverted){
		this.inverted = inverted;
	}

	@Override
	public void onRedstoneChange(double prevValue, double newValue, IMechanismProperty mat, @Nullable Direction side, @Nullable Direction.Axis axis, double[] motData, MechanismTileEntity te){
		if((newValue == 0) ^ (prevValue == 0)){
			te.getWorld().playSound(null, te.getPos(), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, (newValue != 0) ^ inverted ? 0.6F : 0.5F);
			RotaryUtil.increaseMasterKey(true);
		}
	}

	@Override
	public boolean hasCap(Capability<?> cap, Direction capSide, IMechanismProperty mat, @Nullable Direction side, @Nullable Direction.Axis axis, MechanismTileEntity te){
		return ((cap == Capabilities.COG_CAPABILITY && (te.redstoneIn != 0 ^ inverted)) || cap == Capabilities.AXLE_CAPABILITY) && side == capSide;
	}

	@Override
	public void propagate(IMechanismProperty mat, @Nullable Direction side, @Nullable Direction.Axis axis, MechanismTileEntity te, MechanismTileEntity.SidedAxleHandler handler, IAxisHandler masterIn, byte key, double rotRatioIn, double lastRadius){
		//This mechanism should never be in the axle slot
		if(side == null){
			return;
		}

		if(lastRadius != 0){
			rotRatioIn *= lastRadius * 2D;
		}

		//If true, this has already been checked.
		if(key == handler.updateKey){
			//If true, there is rotation conflict.
			if(handler.rotRatio != rotRatioIn){
				masterIn.lock();
			}
			return;
		}

		if(masterIn.addToList(handler)){
			return;
		}

		handler.rotRatio = rotRatioIn;
		handler.updateKey = key;


		TileEntity sideTE = te.getWorld().getTileEntity(te.getPos().offset(side));

		//Don't connect via cogs if disabled
		if((te.redstoneIn != 0) ^ inverted){
			//Other internal gears
			for(int i = 0; i < 6; i++){
				if(i != side.getIndex() && i != side.getOpposite().getIndex() && te.members[i] != null && te.members[i].hasCap(Capabilities.COG_CAPABILITY, Direction.byIndex(i), te.mats[i], Direction.byIndex(i), te.getAxleAxis(), te)){
					te.axleHandlers[i].propagate(masterIn, key, RotaryUtil.getDirSign(side, Direction.byIndex(i)) * handler.rotRatio, .5D, !handler.renderOffset);
				}
			}

			for(int i = 0; i < 6; i++){
				if(i != side.getIndex() && i != side.getOpposite().getIndex()){
					Direction facing = Direction.byIndex(i);
					// Adjacent gears
					TileEntity adjTE = te.getWorld().getTileEntity(te.getPos().offset(facing));
					if(adjTE != null){
						LazyOptional<ICogHandler> cogOpt;
						if((cogOpt = adjTE.getCapability(Capabilities.COG_CAPABILITY, side)).isPresent()){
							cogOpt.orElseThrow(NullPointerException::new).connect(masterIn, key, -handler.rotRatio, .5D, facing.getOpposite(), handler.renderOffset);
						}else if((cogOpt = adjTE.getCapability(Capabilities.COG_CAPABILITY, facing.getOpposite())).isPresent()){
							//Check for large gears
							cogOpt.orElseThrow(NullPointerException::new).connect(masterIn, key, RotaryUtil.getDirSign(side, facing) * handler.rotRatio, .5D, side, handler.renderOffset);
						}
					}

					// Diagonal gears
					TileEntity diagTE = te.getWorld().getTileEntity(te.getPos().offset(facing).offset(side));
					LazyOptional<ICogHandler> cogOpt;
					if(diagTE != null && (cogOpt = diagTE.getCapability(Capabilities.COG_CAPABILITY, facing.getOpposite())).isPresent() && RotaryUtil.canConnectThrough(te.getWorld(), te.getPos().offset(facing), facing.getOpposite(), side)){
						cogOpt.orElseThrow(NullPointerException::new).connect(masterIn, key, -RotaryUtil.getDirSign(side, facing) * handler.rotRatio, .5D, side.getOpposite(), handler.renderOffset);
					}

					if(sideTE != null && (cogOpt = sideTE.getCapability(Capabilities.COG_CAPABILITY, facing)).isPresent()){
						cogOpt.orElseThrow(NullPointerException::new).connect(masterIn, key, -RotaryUtil.getDirSign(side, facing) * rotRatioIn, .5D, side.getOpposite(), handler.renderOffset);
					}
				}
			}
		}

		//Connected block
		if(sideTE != null){
			LazyOptional<IAxisHandler> axisOpt = sideTE.getCapability(Capabilities.AXIS_CAPABILITY, side.getOpposite());
			if(axisOpt.isPresent()){
				axisOpt.orElseThrow(NullPointerException::new).trigger(masterIn, key);
			}
			LazyOptional<IAxleHandler> axleOpt = sideTE.getCapability(Capabilities.AXLE_CAPABILITY, side.getOpposite());
			if(axleOpt.isPresent()){
				axleOpt.orElseThrow(NullPointerException::new).propagate(masterIn, key, handler.rotRatio, 0, handler.renderOffset);
			}
		}

		//Axle slot
		if(te.getAxleAxis() == side.getAxis() && te.members[6] != null && te.members[6].hasCap(Capabilities.AXLE_CAPABILITY, side, te.mats[6], null, te.getAxleAxis(), te)){
			te.axleHandlers[6].propagate(masterIn, key, handler.rotRatio, 0, handler.renderOffset);
		}
	}

	@Nonnull
	@Override
	public ItemStack getDrop(IMechanismProperty mat){
		if(mat instanceof GearFactory.GearMaterial){
			return inverted ? CRItems.invToggleGear.withMaterial((OreSetup.OreProfile) mat, 1) : CRItems.toggleGear.withMaterial((OreSetup.OreProfile) mat, 1);
		}else{
			return ItemStack.EMPTY;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void doRender(MechanismTileEntity te, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, float partialTicks, IMechanismProperty mat, @Nullable Direction side, @Nullable Direction.Axis axis){
		if(side == null){
			return;
		}

		MechanismTileEntity.SidedAxleHandler handler = te.axleHandlers[side.getIndex()];
		IVertexBuilder builder = buffer.getBuffer(RenderType.getSolid());
		
		matrix.rotate(side.getOpposite().getRotation());//Apply orientation
		float angle = handler.getAngle(partialTicks);
		matrix.translate(0, -0.4375D, 0);
		matrix.rotate(Vector3f.YP.rotationDegrees(- (float) RotaryUtil.getCCWSign(side) * angle));

		TextureAtlasSprite sprite = CRRenderUtil.getTextureSprite(CRRenderTypes.GEAR_8_TEXTURE);
		float top = 0.0625F;

		//If inverted, renders the core as red
		if(inverted){
			int[] invertCol = new int[] {255, 0, 0, 255};
			
			float radius = 2F / 16F;
			float zFightOffset = 0.001F;//Vertical offset to prevent z-fighting
			//Texture coords
			float radiusT = radius * 16F;
			float uSt = sprite.getInterpolatedU(8 - radiusT);
			float uEn = sprite.getInterpolatedU(8 + radiusT);
			float vSt = sprite.getInterpolatedV(8 - radiusT);
			float vEn = sprite.getInterpolatedV(8 + radiusT);
			
			CRRenderUtil.addVertexBlock(builder, matrix, -radius, top + zFightOffset, radius, uSt, vEn, 0, 1, 0, combinedLight, invertCol);
			CRRenderUtil.addVertexBlock(builder, matrix, radius, top + zFightOffset, radius, uEn, vEn, 0, 1, 0, combinedLight, invertCol);
			CRRenderUtil.addVertexBlock(builder, matrix, radius, top + zFightOffset, -radius, uEn, vSt, 0, 1, 0, combinedLight, invertCol);
			CRRenderUtil.addVertexBlock(builder, matrix, -radius, top + zFightOffset, -radius, uSt, vSt, 0, 1, 0, combinedLight, invertCol);
		}

		int[] color = CRRenderUtil.convertColor(mat instanceof GearFactory.GearMaterial ? ((GearFactory.GearMaterial) mat).getColor() : Color.WHITE);

		if(te.redstoneIn != 0 ^ inverted){
			//Render normally when active
			CRModels.draw8Gear(matrix, builder, color, combinedLight);
		}else{
			//Render without prongs
			float lHalf = 7F / 16F;//Half the side length of the octagon
			matrix.scale(2F * lHalf, 1, 2F * lHalf);
			CRModels.draw8Core(builder, matrix, color, combinedLight, sprite);
		}
	}
}
