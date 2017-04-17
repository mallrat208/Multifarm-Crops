package com.mr208.multifarmcrops;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.core.ForestryAPI;
import forestry.api.farming.Farmables;
import forestry.core.PluginCore;
import forestry.core.items.EnumElectronTube;
import forestry.core.utils.ModUtil;
import forestry.farming.circuits.CircuitFarmLogic;
import forestry.plugins.BlankForestryPlugin;
import forestry.plugins.ForestryPlugin;
import forestry.plugins.ForestryPluginUids;


@ForestryPlugin(pluginID = "AgriCraft", name = "AgriCraft", unlocalizedDescription = "for.plugin.multifarmcrops.desc")
public class PluginAgricraft extends BlankForestryPlugin{

	private static final String AgriCraft = "AgriCraft";
	public static ICircuit farmLogicAgricraft;

	@Override
	public boolean isAvailable() {
		return ModUtil.isModLoaded(AgriCraft.toLowerCase());
	}

	@Override
	public String getFailMessage() {
		return "AgriCraft not Found";
	}

	@Override
	public void doInit() {

		MultifarmCrops.parseTraversableBlocks();
		farmLogicAgricraft = new CircuitFarmLogic("manualAgricraft",new FarmLogicAgriCrop()).setManual();
	}

	@Override
	public void registerRecipes() {

		ICircuitLayout layoutManual = ChipsetManager.circuitRegistry.getLayout("forestry.farms.manual");
		ChipsetManager.solderManager.addRecipe(layoutManual,PluginCore.items.tubes.get(EnumElectronTube.EMERALD,1),farmLogicAgricraft);

		if(ForestryAPI.enabledPlugins.contains(ForestryPluginUids.FARMING)) {
			Farmables.farmables.put("farmAgricraft",new FarmableMFCAgricraftCrop());
		}
	}
}
