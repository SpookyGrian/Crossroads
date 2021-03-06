package com.Da_Technomancer.crossroads.crafting.recipes;

import com.Da_Technomancer.crossroads.blocks.CRBlocks;
import com.Da_Technomancer.crossroads.crafting.CRRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class StampMillRec extends SingleIngrRecipe{

	public StampMillRec(ResourceLocation location, String name, Ingredient input, ItemStack output, boolean active){
		super(CRRecipes.STAMP_MILL_TYPE, CRRecipes.STAMP_MILL_SERIAL, location, name, input, output, active);
	}

	@Override
	public ItemStack getIcon(){
		return new ItemStack(CRBlocks.stampMill);
	}
}
