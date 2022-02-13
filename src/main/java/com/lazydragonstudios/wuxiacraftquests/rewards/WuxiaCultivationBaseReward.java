package com.lazydragonstudios.wuxiacraftquests.rewards;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.cultivation.System;
import com.lazydragonstudios.wuxiacraft.cultivation.SystemContainer;
import com.lazydragonstudios.wuxiacraft.cultivation.stats.PlayerSystemStat;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import java.math.BigDecimal;

public class WuxiaCultivationBaseReward extends Reward {

	public System system = System.ESSENCE;

	public BigDecimal amount = BigDecimal.ZERO;

	public WuxiaCultivationBaseReward(Quest q) {
		super(q);
	}

	@Override
	public void getConfig(ConfigGroup config) {
		super.getConfig(config);
		var nameMap = NameMap.of(System.ESSENCE, System.values()).create();
		config.addEnum("system", this.system, system -> this.system = system, nameMap);
		config.addDouble("amount", this.amount.doubleValue(), amount -> this.amount = new BigDecimal(amount), 0, 0, Double.MAX_VALUE);
	}

	@Override
	public RewardType getType() {
		return WuxiaRewardTypes.CULTIVATION_BASE_REWARD_TYPE;
	}

	@Override
	public void writeData(CompoundTag nbt) {
		super.writeData(nbt);
		nbt.putString("system", system.toString());
		nbt.putString("amount", this.amount.toPlainString());
	}

	@Override
	public void claim(ServerPlayer serverPlayer, boolean b) {
		var cultivation = Cultivation.get(serverPlayer);
		SystemContainer systemData = cultivation.getSystemData(this.system);
		systemData.setStat(PlayerSystemStat.CULTIVATION_BASE, systemData.getStat(PlayerSystemStat.CULTIVATION_BASE).add(this.amount).max(BigDecimal.ZERO));
	}

	@Override
	public void readData(CompoundTag nbt) {
		super.readData(nbt);
		if(nbt.contains("system")) {
			this.system = System.valueOf(nbt.getString("system"));
		}
		if(nbt.contains("amount")) {
			this.amount = new BigDecimal(nbt.getString("amount"));
		}
	}

	@Override
	public void writeNetData(FriendlyByteBuf buffer) {
		super.writeNetData(buffer);
		buffer.writeEnum(this.system);
		buffer.writeComponent(new TextComponent(this.amount.toPlainString()));
	}

	@Override
	public void readNetData(FriendlyByteBuf buffer) {
		super.readNetData(buffer);
		this.system = buffer.readEnum(System.class);
		var component = buffer.readComponent();
		if(component instanceof TextComponent textComponent) {
			this.amount = new BigDecimal(textComponent.getText());
		}
	}
}
