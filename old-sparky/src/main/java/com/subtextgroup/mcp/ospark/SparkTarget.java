package com.subtextgroup.mcp.ospark;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
@SerializableAs("spark-target")
public class SparkTarget implements ConfigurationSerializable {
    private Location loc;
    private Integer strikeCount;
    
    
    
    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Integer getStrikeCount() {
        return strikeCount;
    }

    public void setStrikeCount(Integer strikeCount) {
        this.strikeCount = strikeCount;
    }

    public SparkTarget(Location loc, Integer strikeCount) {
        super();
        this.loc = loc;
        this.strikeCount = strikeCount;
    }

    public SparkTarget(Map<String, Object> props) {
        this.loc = Location.deserialize((Map<String, Object>)props.get("loc"));
        this.strikeCount = Integer.valueOf((String)props.get("strike-count"));
    }
    
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("loc", loc.serialize());
        result.put("strike-count", strikeCount);
        return result;
    }

}
