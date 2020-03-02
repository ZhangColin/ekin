package com.ekin.system.organization;

import com.cartisan.utils.SnowflakeIdWorker;
import com.ekin.system.organization.reponse.*;
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
    private final OrganizationTreeNodeConverter treeNodeConverter = OrganizationTreeNodeConverter.CONVERTER;
    private final OrganizationConverter organizationConverter = OrganizationConverter.CONVERTER;
    private final OrganizationDetailConverter organizationDetailConverter = OrganizationDetailConverter.CONVERTER;

    private final OrganizationRepository repository;
    private final SnowflakeIdWorker idWorker;

    public OrganizationAppService(OrganizationRepository repository, SnowflakeIdWorker idWorker) {
        this.repository = repository;
        this.idWorker = idWorker;
    }

    public List<OrganizationTreeNode> getOrganizationTreeList() {
        final List<OrganizationTreeNode> organizationTreeNodes = treeNodeConverter.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sort")));

        return OrganizationTreeNode.buildOrganizationTreeList(organizationTreeNodes);
    }

    public List<OrganizationDto> getAllOrganizations() {
        return organizationConverter.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sort")));
    }


    @Transactional(rollbackOn = Exception.class)
    public OrganizationDetailDto addOrganization(OrganizationParam organizationParam) {
        final Organization organization = new Organization(idWorker.nextId(), organizationParam.getParentId(), organizationParam.getName());

        organization.describe(organizationParam.getDescription(), organizationParam.getSort());

        return organizationDetailConverter.convert(repository.save(organization));
    }

    @Transactional(rollbackOn = Exception.class)
    public OrganizationDetailDto editOrganization(Long id, OrganizationParam organizationParam) {
        final Organization organization = requirePresent(repository.findById(id));

        organization.changeOrganization(organizationParam.getParentId(), organizationParam.getName());
        organization.describe(organizationParam.getDescription(), organizationParam.getSort());

        return organizationDetailConverter.convert(repository.save(organization));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeOrganization(long id) {
        repository.deleteById(id);
    }
}
