package com.subtextgroup.mcp.ospark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class OldSparky extends JavaPlugin {

    private static class RangeException extends Exception {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (command.getName().equalsIgnoreCase("oldsparky")) {
            Integer numStrikes = 1;
            if (args.length == 1) {
                try {
                    numStrikes = Integer.parseInt(args[0]);
                    if (numStrikes > 10) {
                        throw new RangeException();
                    }
                } catch (NumberFormatException | RangeException e) {
                    sender.sendMessage("Not a valid strike count: " + args[0]);
                    return false;
                }
            } else if (args.length > 1) {
                sender.sendMessage(command.getUsage());
                return false;
            }
            Player player = (Player) sender;
            Block target = player.getTargetBlock((Set) null, 10);
            if (target == null) {
                return false;
            }

            target.setType(Material.REDSTONE_LAMP_OFF);

            target.setMetadata("spark-target", new FixedMetadataValue(this,
                    numStrikes));
            List<SparkTarget> targets = (List<SparkTarget>) getConfig()
                    .getList("spark-targets", new ArrayList<SparkTarget>());
            if (!targets.contains(target.getLocation())) {
                SparkTarget st = new SparkTarget(target.getLocation(),
                        numStrikes);
                targets.add(st);
                getConfig().set("spark-targets", targets);
                saveConfig();
            }

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
        ConfigurationSerialization.registerClass(SparkTarget.class);
        this.listener = new SparkListener(this);
        getServer().getPluginManager().registerEvents(listener, this);
        loadSparkTargets();
        getServer().broadcastMessage("OldSparky enabled!");
    }

    private void loadSparkTargets() {
        List<SparkTarget> targets = (List<SparkTarget>) getConfig().getList(
                "spark-targets", new ArrayList<SparkTarget>());
        Iterator<SparkTarget> titer = targets.iterator();
        boolean updated = false;
        while (titer.hasNext()) {
            SparkTarget st = titer.next();
            Block target = st.getLoc().getBlock();
            if (target != null
                    && (Material.REDSTONE_LAMP_OFF == target.getType() || Material.REDSTONE_LAMP_ON == target
                            .getType())) {
                target.setMetadata("spark-target", new FixedMetadataValue(this,
                        st.getStrikeCount()));
            } else {
                titer.remove();
                updated = true;
            }
        }
        if (updated) {
            getConfig().set("spark-targets", targets);
            saveConfig();
        }
    }

    protected void removeSparkTarget(Location loc) {
        List<SparkTarget> targets = (List<SparkTarget>) getConfig().getList(
                "spark-targets", new ArrayList<SparkTarget>());
        Iterator<SparkTarget> titer = targets.iterator();
        while (titer.hasNext()) {
            if (titer.next().getLoc().equals(loc)) {
                titer.remove();
            }
        }
        getConfig().set("spark-targets", targets);
        saveConfig();
    }

}
