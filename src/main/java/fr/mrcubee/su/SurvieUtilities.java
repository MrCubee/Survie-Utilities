package fr.mrcubee.su;

import fr.mrcubee.su.listeners.BlockBreakListener;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvieUtilities extends JavaPlugin {

    @Override
    public void onEnable() {
        final FurnaceRecipe rottenFleshRecipe = new FurnaceRecipe(new ItemStack(Material.PORKCHOP), new MaterialData(Material.ROTTEN_FLESH), 0.0f);

        getServer().addRecipe(rottenFleshRecipe);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
    }
}
