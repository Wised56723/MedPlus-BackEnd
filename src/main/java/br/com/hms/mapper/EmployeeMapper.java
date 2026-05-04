package br.com.hms.mapper;

import br.com.hms.domain.entity.Employee;
import br.com.hms.dto.employee.EmployeeRequest;
import br.com.hms.dto.employee.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    // password encoding is handled in the service, not here
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Employee toEntity(EmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateEntity(EmployeeRequest request, @MappingTarget Employee employee);
}
