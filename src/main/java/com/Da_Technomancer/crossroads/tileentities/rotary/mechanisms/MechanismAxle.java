package com.Da_Technomancer.crossroads.tileentities.rotary.mechanisms;

import com.Da_Technomancer.crossroads.API.Capabilities;
import com.Da_Technomancer.crossroads.API.rotary.GearTypes;
import com.Da_Technomancer.crossroads.client.TESR.models.ModelAxle;
import com.Da_Technomancer.crossroads.items.ModItems;
import com.Da_Technomancer.essentials.shared.IAxisHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MechanismAxle implements IMechanism{

	private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[3];
	static{
		BOUNDING_BOXES[0] = new AxisAlignedBB(0, .4375D, .4375D, 1, .5625D, .5625D);//X
		BOUNDING_BOXES[1] = new AxisAlignedBB(.4375D, 0, .4375D, .5625D, 1, .5625D);//Y
		BOUNDING_BOXES[2] = new AxisAlignedBB(.4375D, .4375D, 0, .5625D, .5625D, 1);//Z
	}

	@Override
	public double getInertia(GearTypes mat, @Nullable EnumFacing side, @Nullable EnumFacing.Axis axis){
		return mat.getDensity() / 32_000D;
	}

	@Override
	public boolean hasCap(Capability<?> cap, EnumFacing capSide, GearTypes mat, @Nullable EnumFacing side, @Nullable EnumFacing.Axis axis, MechanismTileEntity te){
		return cap == Capabilities.AXLE_HANDLER_CAPABILITY && side == null && capSide.getAxis() == axis;
	}

	@Override
	public void propogate(GearTypes mat, @Nullable EnumFacing side, @Nullable EnumFacing.Axis axis, MechanismTileEntity te, MechanismTileEntity.SidedAxleHandler handler, IAxisHandler masterIn, byte key, double rotRatioIn, double lastRadius){
		//This mechanism should always be in the axle slot
		if(side != null){
			return;
		}

		if(rotRatioIn == 0){
			rotRatioIn = 1;
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

		if(key == 0){
			handler.resetAngle();
		}
		handler.updateKey = key;

		
		
		for(EnumFacing.AxisDirection direct : EnumFacing.AxisDirection.values()){
			EnumFacing endDir = EnumFacing.getFacingFromAxis(direct, axis);
			
			if(te.members[endDir.getIndex()] != null){
				//Do internal connection
				if(te.members[endDir.getIndex()].hasCap(Capabilities.AXLE_HANDLER_CAPABILITY, endDir, te.mats[endDir.getIndex()], endDir, axis, te)){
					te.axleHandlers[endDir.getIndex()].propogate(masterIn, key, rotRatioIn, 0);
				}
			}else{
				//Connect externally
				TileEntity endTE = te.getWorld().getTileEntity(te.getPos().offset(endDir));
				EnumFacing oEndDir = endDir.getOpposite();
				if(endTE != null){
					if(endTE.hasCapability(Capabilities.AXIS_HANDLER_CAPABILITY, oEndDir)){
						endTE.getCapability(Capabilities.AXIS_HANDLER_CAPABILITY, oEndDir).trigger(masterIn, key);
					}

					if(endTE.hasCapability(Capabilities.SLAVE_AXIS_HANDLER_CAPABILITY, oEndDir)){
						masterIn.addAxisToList(endTE.getCapability(Capabilities.SLAVE_AXIS_HANDLER_CAPABILITY, oEndDir), oEndDir);
					}

					if(endTE.hasCapability(Capabilities.AXLE_HANDLER_CAPABILITY, oEndDir)){
						endTE.getCapability(Capabilities.AXLE_HANDLER_CAPABILITY, oEndDir).propogate(masterIn, key, handler.rotRatio, 0);
					}
				}
			}
		}
	}

	@Nonnull
	@Override
	public ItemStack getDrop(GearTypes mat){
		return new ItemStack(mat == GearTypes.COPSHOWIUM ? ModItems.axleCopshowium : ModItems.axleIron, 1);
	}

	@Override
	public AxisAlignedBB getBoundingBox(@Nullable EnumFacing side, @Nullable EnumFacing.Axis axis){
		return side != null || axis == null ? Block.NULL_AABB : BOUNDING_BOXES[axis.ordinal()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void doRender(MechanismTileEntity te, float partialTicks, GearTypes mat, @Nullable EnumFacing side, @Nullable EnumFacing.Axis axis){
		if(axis == null){
			return;
		}

		MechanismTileEntity.SidedAxleHandler handler = te.axleHandlers[6];

		GlStateManager.pushMatrix();
		GlStateManager.rotate(axis == EnumFacing.Axis.Y ? 0 : 90F, axis == EnumFacing.Axis.Z ? 1 : 0, 0, axis == EnumFacing.Axis.X ? -1 : 0);
		float angle = (float) (handler.getNextAngle() - handler.getAngle());
		angle *= partialTicks;
		angle += handler.getAngle();
		GlStateManager.rotate(angle, 0F, 1F, 0F);
		ModelAxle.render(mat.getColor());
		GlStateManager.popMatrix();
	}
}