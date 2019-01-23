package net.kaikk.mc.quickshop.wg;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.Shop.ShopPreCreateEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class QuickShopWG extends JavaPlugin implements Listener {
        @Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	void onShopPreCreate(ShopPreCreateEvent event) {
               RegionContainer rc =  WorldGuard.getInstance().getPlatform().getRegionContainer();
               RegionManager rm = rc.get(BukkitAdapter.adapt(event.getLocation().getWorld()));
	       LocalPlayer player = WorldGuardPlugin.inst().wrapPlayer(event.getPlayer());
               ApplicableRegionSet set = rm.getApplicableRegions( player.getLocation().toVector().toBlockPoint());

		if (set == null ||set.size()==0  ) {
			event.setCancelled(true);
			return;
		}
		
		if (set.queryState(null, Flags.ENABLE_SHOP)!=State.ALLOW) {
			event.setCancelled(true);
			return;
		}
                           

		if (!rc.createQuery().testState(player.getLocation(), player, Flags.BUILD)) {
			event.setCancelled(true);
			return;
		}
	}
}
