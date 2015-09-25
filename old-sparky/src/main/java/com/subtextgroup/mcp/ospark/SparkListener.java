package com.subtextgroup.mcp.ospark;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.metadata.MetadataValue;

public class SparkListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onSparkyActivated(BlockRedstoneEvent event) {
        
        if(event.getNewCurrent() > 0) {
            List<MetadataValue> meta = event.getBlock().getMetadata("spark-target");
            if(meta == null || meta.size() == 0) {
                return;
            }
            for(MetadataValue metaVal : meta) {
                if(metaVal.asBoolean()) {
                    electrocute(event.getBlock());
                }
            }
        }
    }
    private void electrocute(Block block) {
        
        block.getWorld().strikeLightning(block.getLocation());
        
    }
}
