package com.lazydragonstudios.wuxiacraftquests.tasks;

import com.lazydragonstudios.wuxiacraft.cultivation.Cultivation;
import com.lazydragonstudios.wuxiacraft.init.WuxiaRegistries;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.BooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WuxiaStageTask extends BooleanTask {

	private ResourceLocation cultivationStage = new ResourceLocation("wuxiacraft:essence_mortal_stage");

	public WuxiaStageTask(Quest q) {
		super(q);
	}

	@Override
	public TaskType getType() {
		return WuxiaTaskTypes.CULTIVATION_STAGE_TASK_TYPE;
	}

	@Override
	public void getConfig(ConfigGroup config) {
		super.getConfig(config);
		var nameMap = NameMap.of(new ResourceLocation("wuxiacraft:essence_mortal_stage"),
						WuxiaRegistries.CULTIVATION_STAGES.getKeys().toArray(new ResourceLocation[0]))
				.create();
		config.addEnum("stage", this.cultivationStage, (stageLocation) -> this.cultivationStage = stageLocation, nameMap);
	}

	@Override
	public int autoSubmitOnPlayerTick() {
		return 5;
	}

	@Override
	public boolean canSubmit(TeamData teamData, ServerPlayer serverPlayer) {
		var stage = WuxiaRegistries.CULTIVATION_STAGES.getValue(this.cultivationStage);
		if(stage == null) return false;
		var cultivation = Cultivation.get(serverPlayer);
		while(stage != null) {
			if(cultivation.getSystemData(stage.system).getStage() == stage) {
				return true;
			}
			stage = WuxiaRegistries.CULTIVATION_STAGES.getValue(stage.nextStage);
		}
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public Icon getAltIcon() {
		return super.getAltIcon();
	}

	@Override
	public void writeData(CompoundTag nbt) {
		super.writeData(nbt);
		nbt.putString("stage", this.cultivationStage.toString());
	}

	@Override
	public void readData(CompoundTag nbt) {
		super.readData(nbt);
		if(nbt.contains("stage")) {
			this.cultivationStage = new ResourceLocation(nbt.getString("stage"));
		}
	}

	@Override
	public void writeNetData(FriendlyByteBuf buffer) {
		super.writeNetData(buffer);
		buffer.writeResourceLocation(this.cultivationStage);
	}

	@Override
	public void readNetData(FriendlyByteBuf buffer) {
		super.readNetData(buffer);
		this.cultivationStage = buffer.readResourceLocation();
	}
}
