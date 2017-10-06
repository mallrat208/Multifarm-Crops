package com.mr208.multifarmcrops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.api.farming.FarmDirection;
import forestry.api.farming.Farmables;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmable;
import forestry.farming.logic.FarmLogic;

import com.infinityraider.agricraft.init.AgriItems;

public class FarmLogicAgriCrop extends FarmLogic {

	private final Collection<IFarmable> farmables;
	private final HashMap<BlockPos, Integer> lastExtents = new HashMap<>();

	public FarmLogicAgriCrop() {
		this.farmables = Farmables.farmables.get("farmAgricraft");
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
	public boolean isAcceptedResource(ItemStack itemStack) {
		return false;
	}

	@Override
	public boolean isAcceptedGermling(ItemStack itemStack) {
		return false;
	}

	@Override
	public Collection<ItemStack> collect(World world, IFarmHousing iFarmHousing) {
		return null;
	}

	@Override
	public Collection<ICrop> harvest(World world, BlockPos pos, FarmDirection direction, int extent) {

		if (!lastExtents.containsKey(pos)) {
			lastExtents.put(pos, 0);
		}

		int lastExtent = lastExtents.get(pos);
		if (lastExtent > extent) {
			lastExtent = 0;
		}

		BlockPos position = translateWithOffset(pos.up(), direction, lastExtent);
		Collection<ICrop> crops = getHarvestBlocks(world, position);
		lastExtent++;
		lastExtents.put(pos, lastExtent);

		return crops;
	}

	@Override
	public boolean cultivate(World world, IFarmHousing iFarmHousing, BlockPos blockPos, FarmDirection farmDirection, int extent) {
		return false;
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(AgriItems.getInstance().CROPS);
	}

	@Override
	public boolean isAcceptedWindfall(ItemStack itemStack) {
		return false;
	}

	@Override
	public String getName() {
		return "AgriCraft Crops";
	}

	private Collection<ICrop> getHarvestBlocks(World world, BlockPos position) {
		Set<BlockPos> seen = new HashSet<>();
		Stack<ICrop> crops = new Stack<>();

		// Determine what type we want to harvest.
		IBlockState blockState = world.getBlockState(position);
		Block block = blockState.getBlock();
		if (!block.isWood(world, position) && !isBlockTraversable(blockState, world, position) && !isFruitBearer(world, position)) {
			return crops;
		}

		List<BlockPos> candidates = processHarvestBlock(world, crops, seen, position, position);
		List<BlockPos> temp = new ArrayList<>();
		while (!candidates.isEmpty() && crops.size() < 20) {
			for (BlockPos candidate : candidates) {
				temp.addAll(processHarvestBlock(world, crops, seen, position, candidate));
			}
			candidates.clear();
			candidates.addAll(temp);
			temp.clear();
		}
		return crops;
	}

	private List<BlockPos> processHarvestBlock(World world, Stack<ICrop> crops, Set<BlockPos> seen, BlockPos start, BlockPos position) {

		List<BlockPos> candidates = new ArrayList<>();

		for (int j = 0; j < 3; j++) {
			BlockPos candidate = position.up(j);
			if (seen.contains(candidate)) {
				continue;
			}
			if (world.isAirBlock(candidate)) {
				continue;
			}
			IBlockState blockState = world.getBlockState(candidate);
			Block block = blockState.getBlock();

			if (block.isWood(world, candidate) && isBlockTraversable(blockState, world, candidate)) {
				candidates.add(candidate);
				seen.add(candidate);
			}
			if(isFruitBearer(world,candidate)) {
				candidates.add(candidate);
				seen.add(candidate);

				ICrop crop = getCrop(world, candidate);
				if(crop !=null) {
					crops.push(crop);
				}
			}
		}
		return candidates;
	}
	private boolean isFruitBearer(World world, BlockPos position) {
		for (IFarmable farmable : farmables) {
			if (farmable.isSaplingAt(world, position)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isBlockTraversable(IBlockState blockState, World world, BlockPos position) {
		//Move upwards until the we hit air.
		return !blockState.getBlock().isAir(blockState,world,position);
	}

	private ICrop getCrop(World world, BlockPos blockPos) {
		IBlockState blockState = world.getBlockState(blockPos);
		for(IFarmable seed: farmables) {
			ICrop crop = seed.getCropAt(world,blockPos,blockState);
			if(crop!= null) {
				return crop;
			}
		}
		return null;
	}
}