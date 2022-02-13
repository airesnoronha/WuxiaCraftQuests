package com.lazydragonstudios.wuxiacraftquests.rewards;

import com.lazydragonstudios.wuxiacraftquests.WuxiaCraftQuests;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import net.minecraft.resources.ResourceLocation;

public class WuxiaRewardTypes {

	public static RewardType FOUNDATION_REWARD_TYPE = RewardTypes.register(new ResourceLocation(WuxiaCraftQuests.MOD_ID, "foundation_reward_type"), WuxiaFoundationReward::new, () -> Icon.EMPTY);

	public static RewardType CULTIVATION_BASE_REWARD_TYPE = RewardTypes.register(new ResourceLocation(WuxiaCraftQuests.MOD_ID, "cultivation_base_type"), WuxiaCultivationBaseReward::new, () -> Icon.EMPTY);

	public static void init() {

	}

}
