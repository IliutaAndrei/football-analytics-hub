package org.iliuta.footballhub.countries.mapper;

import org.iliuta.footballhub.client.dto.countries.ExternalCountryDTO;
import org.iliuta.footballhub.countries.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExternalCountryMapper {

    @Mapping(target = "id", ignore = true)
    CountryEntity toCountryEntity(ExternalCountryDTO external);

    @Mapping(target = "id", ignore = true)
    void updateCountryEntity(@MappingTarget CountryEntity entity, ExternalCountryDTO external);
}
