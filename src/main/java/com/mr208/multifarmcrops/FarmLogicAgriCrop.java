package com.mr208.multifarmcrops;

import com.google.common.collect.ImmutableList;


import java.util.Collection;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import forestry.api.farming.Farmables;
import forestry.api.farming.IFarmable;
import forestry.farming.logic.FarmLogicOrchard;

import com.infinityraider.agricraft.init.AgriItems;


public class FarmLogicAgriCrop extends FarmLogicOrchard {

	private final Collection<IFarmable> farmables;
	private final HashMap<BlockPos, Integer> lastExtents = new HashMap<>();
	private final ImmutableList<Block> traversalBlocks;



	public FarmLogicAgriCrop() {

		this.farmables = Farmables.farmables.get("farmAgricraft");
		ImmutableList.Builder<Block> traversalBlocksBuilder = ImmutableList.builder();
		for(Block traversable:MultifarmCrops.traversableBlock) {
			traversalBlocksBuilder.add(traversable);
		}
		traversalBlocksBuilder.build();
		this.traversalBlocks = traversalBlocksBuilder.build();

	}

	@Override
	public int getFertilizerConsumption() {
		return 10;
	}

	@Override
	public int getWaterConsumption(float hydrationModifier) {
		return (int) (15 * hydrationModifier);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(AgriItems.getInstance().CROPS);
	}

	@Override
	public String getName() {
		return "AgriCraft Crops";
	}
}