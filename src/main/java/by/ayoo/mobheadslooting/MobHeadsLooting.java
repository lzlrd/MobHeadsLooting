package by.ayoo.mobheadslooting;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobHeadsLooting extends JavaPlugin implements Listener {
	// Every head Jodek's Mob Heads can drop: its own player heads and the vanilla ones.
	private static final Set<Material> HEADS = EnumSet.of(
			Material.PLAYER_HEAD,
			Material.ZOMBIE_HEAD,
			Material.SKELETON_SKULL,
			Material.WITHER_SKELETON_SKULL,
			Material.CREEPER_HEAD,
			Material.PIGLIN_HEAD,
			Material.DRAGON_HEAD);

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event) {
		// Like vanilla Looting, only count kills a player caused, read from their main hand.
		if (!(event.getDamageSource().getCausingEntity() instanceof Player killer))
			return;
		int looting = killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING);
		if (looting <= 0)
			return;
		// Looting only adds chances; a mob never drops a second head.
		if (event.getDrops().stream().anyMatch(item -> HEADS.contains(item.getType())))
			return;
		LivingEntity entity = event.getEntity();
		LootTable table = Bukkit.getLootTable(new NamespacedKey("mob_heads", "entities/" + entity.getType().getKey().getKey()));
		if (table == null)
			return;
		// The killer fills killed_by_player, so Mob Heads' own drop rules and rates still apply.
		LootContext context = new LootContext.Builder(entity.getLocation())
				.lootedEntity(entity)
				.killer(killer)
				.build();
		for (int roll = 0; roll < looting; roll++) {
			for (ItemStack item : table.populateLoot(ThreadLocalRandom.current(), context)) {
				if (HEADS.contains(item.getType())) {
					event.getDrops().add(item);
					return;
				}
			}
		}
	}
}
