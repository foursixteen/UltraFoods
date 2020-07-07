package net.ultravanilla.ultrafoods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class UltraFoods extends JavaPlugin implements Listener {

    private SkullMeta burgerMeta;

    @Override
    public void onEnable() {

        System.out.println("AAAA");

        ItemStack burger = new ItemStack(Material.PLAYER_HEAD, 1);

        burgerMeta = (SkullMeta) burger.getItemMeta();
        burgerMeta.setOwner("claivin");
        burgerMeta.setDisplayName(ChatColor.YELLOW + "Burger.");

        burger.setItemMeta(burgerMeta);

        ShapedRecipe recipe = new ShapedRecipe(burger);

        recipe.shape(
                "SDC"
        );

        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('D', Material.DIRT);
        recipe.setIngredient('C', Material.COBBLESTONE);

        getServer().addRecipe(recipe);

        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if (meta != null && item.getType() == Material.PLAYER_HEAD) {

                SkullMeta skullMeta = (SkullMeta) meta;

                if (player.getFoodLevel() < 20 &&
                        skullMeta.getOwner().equals(burgerMeta.getOwner()) &&
                        skullMeta.getDisplayName().equals(burgerMeta.getDisplayName())) {

                    event.setCancelled(true);

                    player.setSprinting(false);
                    player.setWalkSpeed(player.getWalkSpeed() / 2);
                    item.setAmount(item.getAmount() - 1);

                    BukkitScheduler scheduler1 = getServer().getScheduler();
                    scheduler1.scheduleSyncRepeatingTask(this, new Runnable() {
                                @Override
                                public void run() {
                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_MOOSHROOM_EAT, 1.0F, 1.0F);
                                }
                            }, 0L, 7L
                    );

                    BukkitScheduler scheduler2 = getServer().getScheduler();
                    scheduler2.scheduleSyncDelayedTask(this, new Runnable() {
                        @Override
                        public void run() {
                            scheduler1.cancelTasks(UltraFoods.this);

                            int level = player.getFoodLevel() + 7;
                            player.setFoodLevel(level);
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
                            player.sendMessage("TASTEY MAC DONALD");
                            player.setWalkSpeed(player.getWalkSpeed() * 2);
                        }
                    }, 3 * 20L);

                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("aa....");
    }
}
