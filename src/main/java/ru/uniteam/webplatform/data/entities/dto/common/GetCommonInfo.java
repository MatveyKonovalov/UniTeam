package ru.uniteam.webplatform.data.entities.dto.common;

import ru.uniteam.models.exceptions.IncorrectTitleException;
import ru.uniteam.usecases.CheckNotNull;

public record GetCommonInfo(long id, String title) {
    public GetCommonInfo{
        CheckNotNull.checkNotNull(title, new IncorrectTitleException());
    }
}
