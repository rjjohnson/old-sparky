package com.subtextgroup.mcp.ospark;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SparkListener implements Listener {
    OldSparky plugin;
    
    public SparkListener(OldSparky plugin) {
        this.plugin = plugin;
    }
    @EventHandler(ignoreCancelled = true)
    public void onSparkyActivated(BlockRedstoneEvent event) {
        
        if(event.getNewCurrent() > 0) {
            List<MetadataValue> meta = event.getBlock().getMetadata("spark-target");
            if(meta == null || meta.size() == 0) {
                return;
            }
            for(MetadataValue metaVal : meta) {
                electrocute(event.getBlock(), (Integer)metaVal.value());
            }
        }
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onSparkyBroken(BlockBreakEvent event) {
        List<MetadataValue> meta = event.getBlock().getMetadata("spark-target");
        if(meta != null && meta.size() > 0) {
            plugin.removeSparkTarget(event.getBlock().getLocation());
        }
    }
    private void electrocute(Block block, Integer count) {
        final World world = block.getWorld();
        final Location loc = block.getLocation();
        for(int i = 0; i < count; i++) {
            BukkitRunnable striker = new BukkitRunnable() {
                @Override
                public void run() {
                    world.strikeLightning(loc);
                }
            };
            striker.runTaskLater(plugin, i * 5);
            
        }
        
    }
}
