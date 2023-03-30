package com.elraj.palaeotrek.service.mapper;

import com.elraj.palaeotrek.domain.Hike;
import com.elraj.palaeotrek.service.dto.HikeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hike} and its DTO {@link HikeDTO}.
 */
@Mapper(componentModel = "spring")
public interface HikeMapper extends EntityMapper<HikeDTO, Hike> {}
