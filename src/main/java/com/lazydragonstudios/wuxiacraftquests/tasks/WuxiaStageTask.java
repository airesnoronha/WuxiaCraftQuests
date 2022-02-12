package com.lazydragonstudios.wuxiacraftquests.tasks;

import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;

import static com.lazydragonstudios.wuxiacraftquests.tasks.WuxiaTaskTypes.STAGE_TASK_TYPE;

public class WuxiaStageTask extends Task {


	public WuxiaStageTask(Quest q) {
		super(q);
	}

	@Override
	public TaskType getType() {
		return STAGE_TASK_TYPE;
	}
}
