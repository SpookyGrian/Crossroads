package com.Da_Technomancer.crossroads.blocks.rotary;

import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.tileentities.rotary.MillstoneTileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class Millstone extends ContainerBlock{

	public Millstone(){
		super(CRBlocks.getRockProperty());
		String name = "millstone";
		setRegistryName(name);
		CRBlocks.toRegister.add(this);
		CRBlocks.blockAddQue(this);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit){
		TileEntity te;
		if(!worldIn.isRemote && (te = worldIn.getTileEntity(pos)) instanceof INamedContainerProvider){
			NetworkHooks.openGui((ServerPlayerEntity) playerIn, (INamedContainerProvider) te, pos);
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn){
		return new MillstoneTileEntity();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state){
		return BlockRenderType.MODEL;
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving){
		InventoryHelper.dropInventoryItems(world, pos, (IInventory) world.getTileEntity(pos));
		super.onReplaced(state, world, pos, newState, isMoving);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag advanced){
		tooltip.add(new TranslationTextComponent("tt.crossroads.millstone.power", MillstoneTileEntity.POWER));
		tooltip.add(new TranslationTextComponent("tt.crossroads.millstone.speed", MillstoneTileEntity.PEAK_SPEED));
		tooltip.add(new TranslationTextComponent("tt.crossroads.boilerplate.inertia", MillstoneTileEntity.INERTIA));
	}
}
