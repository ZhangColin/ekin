package com.ekin.system.organization;

import com.cartisan.utils.SnowflakeIdWorker;
import com.ekin.system.organization.reponse.OrganizationConverter;
import com.ekin.system.organization.reponse.OrganizationDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class OrganizationAppService {
    private final OrganizationConverter convert = OrganizationConverter.CONVERTER;

    private final OrganizationRepository repository;
    private final SnowflakeIdWorker idWorker;

    public OrganizationAppService(OrganizationRepository repository, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public List<OrganizationDto> getOrganizationTreeList() {
        final List<OrganizationDto> organizationDtos = convert.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sort")));

        return OrganizationDto.buildOrganizationTreeList(organizationDtos);
    }

    public List<OrganizationDto> getAllOrganizations() {
        return convert.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sort")));
    }

    public OrganizationDto getOrganization(Long organizationId) {
        return convert.convert(requirePresent(repository.findById(organizationId)));
    }


    @Transactional(rollbackOn = Exception.class)
    public OrganizationDto addOrganization(OrganizationParam organizationParam) {
        final Organization organization = new Organization(idWorker.nextId(), organizationParam.getParentId(), organizationParam.getName());

        organization.describe(organizationParam.getDescription(), organizationParam.getSort());

        return convert.convert(repository.save(organization));
    }

    @Transactional(rollbackOn = Exception.class)
    public OrganizationDto editOrganization(Long id, OrganizationParam organizationParam) {
        final Organization organization = requirePresent(repository.findById(id));

        organization.changeOrganization(organizationParam.getParentId(), organizationParam.getName());
        organization.describe(organizationParam.getDescription(), organizationParam.getSort());

        return convert.convert(repository.save(organization));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeOrganization(long id) {
        repository.deleteById(id);
    }
}
