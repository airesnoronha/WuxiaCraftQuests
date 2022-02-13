package com.lazydragonstudios.wuxiacraftquests.tasks;

import com.lazydragonstudios.wuxiacraftquests.WuxiaCraftQuests;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import net.minecraft.resources.ResourceLocation;

public class WuxiaTaskTypes {

	public static final TaskType CULTIVATION_STAGE_TASK_TYPE = TaskTypes.register(new ResourceLocation(WuxiaCraftQuests.MOD_ID, "cultivation_stage_task_type"), WuxiaStageTask::new, () -> Icon.EMPTY );

	public static void init() {

	}

}
