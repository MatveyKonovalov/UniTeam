package ru.uniteam.webplatform.data.entities.dto.tasks.userinfo;

import ru.uniteam.models.exceptions.IncorrectTitleException;
import ru.uniteam.usecases.CheckNotNull;

public record PrTaskInfo(
        Long id,
        String title
) {
    public PrTaskInfo{
        CheckNotNull.checkNotNull(title, new IncorrectTitleException());
    }
}
