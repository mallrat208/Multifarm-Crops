package com.mr208.multifarmcrops;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.infinityraider.agricraft.api.v1.crop.IAgriCrop;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import forestry.core.proxy.Proxies;
import forestry.farming.logic.Crop;

import com.agricraft.agricore.plant.AgriPlant;
import com.agricraft.agricore.registry.AgriPlants;
import com.infinityraider.agricraft.AgriCraft;
import com.infinityraider.agricraft.tiles.TileEntityCrop;

public class CropMFCAgricraftCrop extends Crop {

	private TileEntity tileEntityCrop;
	private static Random rand;


	protected CropMFCAgricraftCrop(@Nonnull World world, @Nonnull BlockPos position) {
		super(world, position);
		tileEntityCrop = world.getTileEntity(position);
		rand = new Random();
	}

	@Override
	protected boolean isCrop(World world, BlockPos blockPos) {
		return canHarvestCrop(this.tileEntityCrop);
	}

	@Override
	protected Collection<ItemStack> harvestBlock(World world, BlockPos blockPos) {
		return getCropDrops(this.tileEntityCrop);
	}

	public static boolean isAgricraftCrop(TileEntity tileEntity) {
		return tileEntity instanceof IAgriCrop;
	}

	public static boolean canHarvestCrop(TileEntity tileEntity) {
		if (isAgricraftCrop(tileEntity)) {
			TileEntityCrop crop = (TileEntityCrop) tileEntity;

			if (crop.isMature() && crop.isFertile()) {
				return true;
			}
		}
		return false;
	}

	private static List<ItemStack> getCropDrops (TileEntity tileEntity){
			if (isAgricraftCrop(tileEntity)) {
				IAgriCrop crop = (IAgriCrop) tileEntity;
				List<ItemStack> harvestResults;
				harvestResults = new ArrayList<>();
				for(int attempt = (crop.getSeed().getStat().getGain() +3)/3; attempt >0; attempt--) {
					crop.getSeed().getPlant().getHarvestProducts(harvestResults::add, crop, crop.getSeed().getStat(), rand);
				}
				Proxies.common.addBlockDestroyEffects(tileEntity.getWorld(), tileEntity.getPos(), ((TileEntityCrop) tileEntity).getState());
				crop.setGrowthStage(0);
				return harvestResults.isEmpty() ? null : harvestResults;
			}
			return null;
		}

	}
