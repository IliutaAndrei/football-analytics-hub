package org.iliuta.footballhub.countries.mapper;

import org.iliuta.footballhub.countries.CountryEntity;
import org.iliuta.footballhub.countries.dto.CountryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InternalCountryMapper {

    CountryDTO toCountryDTO(CountryEntity entity);
    List<CountryDTO> toCountryDTOs(List<CountryEntity> entities);
}
