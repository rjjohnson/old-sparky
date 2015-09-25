package com.subtextgroup.mcp.ospark;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

public class OldSparky extends JavaPlugin
{


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("oldsparky")) {
            Player player = (Player)sender;
            Location loc = player.getLocation();
            BlockIterator bit = new BlockIterator(loc, 10);
            Block next;
            Block target = null;
            while(bit.hasNext()) {
                next = bit.next();
                if(!next.isEmpty() && !next.isLiquid()) {
                    target = next;
                }
            }
            if(target == null) {
                return false;
            }
            
            final JavaPlugin owningPlugin = this;
            target.setType(Material.REDSTONE_LAMP_OFF);
            
            target.setMetadata("spark-target", new MetadataValue() {
                
                @Override
                public Object value() {
                    return "old-sparky";
                }
                
                @Override
                public void invalidate() {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public Plugin getOwningPlugin() {
                    return owningPlugin;
                }
                
                @Override
                public String asString() {
                    // TODO Auto-generated method stub
                    return "1";
                }
                
                @Override
                public short asShort() {
                    // TODO Auto-generated method stub
                    return 1;
                }
                
                @Override
                public long asLong() {
                    // TODO Auto-generated method stub
                    return 1;
                }
                
                @Override
                public int asInt() {
                    // TODO Auto-generated method stub
                    return 1;
                }
                
                @Override
                public float asFloat() {
                    // TODO Auto-generated method stub
                    return 1.0f;
                }
                
                @Override
                public double asDouble() {
                    // TODO Auto-generated method stub
                    return 1.0d;
                }
                
                @Override
                public byte asByte() {
                    return 1;
                }
                
                @Override
                public boolean asBoolean() {
                    return true;
                }
            });
            return true;
        }
        return false;
    }

    SparkListener listener = null;
    @Override
    public void onDisable() {
        HandlerList.unregisterAll(listener);
    }

    @Override
    public void onEnable() {
        this.listener = new SparkListener();
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().broadcastMessage("SpawnThrottler enabled!");
    }

}
