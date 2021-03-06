package com.Da_Technomancer.crossroads.items;

import com.Da_Technomancer.crossroads.API.MiscUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PigZombieChestsplate extends ArmorItem{

	protected PigZombieChestsplate(){
		super(ChickenBoots.BOBO_MATERIAL, EquipmentSlotType.CHEST, new Properties().group(CRItems.TAB_CROSSROADS).maxStackSize(1));
		String name = "pig_zombie_chestplate";
		setRegistryName(name);
		CRItems.toRegister.add(this);
	}

	@Override
	public Rarity getRarity(ItemStack stack){
		return CRItems.BOBO_RARITY;
	}

	@Override
	public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer){
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn){
		tooltip.add(new TranslationTextComponent("tt.crossroads.pig_zombie_chestplate.quip").setStyle(MiscUtil.TT_QUIP));
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player){
		//Believe it or not, it is possible to die of fire while in lava without burning (if it is raining on the player). There is an isInLava check for this reason.
		if(player.getActivePotionEffect(Effects.FIRE_RESISTANCE) == null && (player.isBurning() || player.isInLava())){
			player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 10, 0, false, false));
			world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PIGLIN_HURT, SoundCategory.PLAYERS, 2.5F, 1F);
		}
	}
}
