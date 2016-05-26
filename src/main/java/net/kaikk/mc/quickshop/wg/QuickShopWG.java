package net.kaikk.mc.quickshop.wg;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.Shop.ShopPreCreateEvent;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

public class QuickShopWG extends JavaPlugin implements Listener {
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	void onShopPreCreate(ShopPreCreateEvent event) {
		ApplicableRegionSet regionSet = WGBukkit.getPlugin().getRegionManager(event.getPlayer().getWorld()).getApplicableRegions(event.getLocation());
		if (regionSet.size()==0) {
			event.setCancelled(true);
			return;
		}
		
		if (regionSet.queryState(null, DefaultFlag.ENABLE_SHOP)!=State.ALLOW) {
			event.setCancelled(true);
			return;
		}

		if (!WGBukkit.getPlugin().canBuild(event.getPlayer(), event.getLocation())) {
			event.setCancelled(true);
			return;
		}
	}
}
