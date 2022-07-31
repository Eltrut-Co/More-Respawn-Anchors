package co.eltrut.morerespawnanchors.core;

import org.apache.commons.lang3.tuple.Pair;

import co.eltrut.differentiate.core.condition.BooleanRecipeCondition;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class MRAConfig {
	
	public static class Common {
		
		public final ConfigValue<Boolean> enableEndRespawnAnchor;
		public final ConfigValue<Boolean> enableNetheriteRespawnAnchor;
		public final ConfigValue<Boolean> enableNetheriteEndRespawnAnchor;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.push("common");
			
			enableEndRespawnAnchor = builder.define("Enable end respawn anchor", true);
			enableNetheriteRespawnAnchor = builder.define("Enable netherite respawn anchor", true);
			enableNetheriteEndRespawnAnchor = builder.define("Enable netherite end respawn anchor", true);
			
			builder.pop();
			
			BooleanRecipeCondition.MAP.put("end_respawn_anchor", enableEndRespawnAnchor);
			BooleanRecipeCondition.MAP.put("netherite_respawn_anchor", enableNetheriteRespawnAnchor);
			BooleanRecipeCondition.MAP.put("netherite_end_respawn_anchor", enableNetheriteEndRespawnAnchor);
		}
		
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

}
