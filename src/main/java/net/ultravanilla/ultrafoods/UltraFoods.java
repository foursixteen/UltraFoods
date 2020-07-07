package net.ultravanilla.ultrafoods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltraFoods extends JavaPlugin implements Listener
{

    @Override
    public void onEnable() {

        System.out.println("AAAA");

        ShapedRecipe recipe = new ShapedRecipe(new ItemStack(Material.DIAMOND, 4));

        recipe.shape(
                "SDC",
                "DFD",
                "CDS"
        );

        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('D', Material.DIRT);
        recipe.setIngredient('F', Material.FLINT);
        recipe.setIngredient('C', Material.COBBLESTONE);

        getServer().addRecipe(recipe);

        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(action == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if(block.getType() == Material.DIRT) {
                player.setHealth(0);
                player.sendMessage(ChatColor.DARK_RED + "HOW DARE YOU TOUCH MY ENVIRONMENT!!!!");
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("aa....");
    }
}
