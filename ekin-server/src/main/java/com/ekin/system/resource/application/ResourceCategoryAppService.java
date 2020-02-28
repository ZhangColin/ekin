package com.ekin.system.resource.application;

import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import com.ekin.system.resource.domain.ResourceCategory;
import com.ekin.system.resource.repository.ResourceCategoryRepository;
import com.ekin.system.resource.request.ResourceCategoryParam;
import com.ekin.system.resource.response.ResourceCategoryConverter;
import com.ekin.system.resource.response.ResourceCategoryDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class ResourceCategoryAppService {
    public static final String ERR_NAME_EXISTS = "资源分类名称已存在。";
    private final ResourceCategoryConverter converter = ResourceCategoryConverter.CONVERTER;

    private final ResourceCategoryRepository repository;

    public ResourceCategoryAppService(ResourceCategoryRepository repository) {
        this.repository = repository;
    }

    public List<ResourceCategoryDto> getAllResourceCategories() {
        return converter.convert(repository.findAll(Sort.by(Sort.Direction.ASC, "sort")));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResourceCategoryDto addResourceCategory(ResourceCategoryParam resourceCategoryParam) {
        if (repository.existsByName(resourceCategoryParam.getName())) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final ResourceCategory resourceCategory =
                new ResourceCategory(resourceCategoryParam.getName(), resourceCategoryParam.getSort());

        return converter.convert(repository.save(resourceCategory));
    }

    @Transactional(rollbackOn = Exception.class)
    public ResourceCategoryDto editResourceCategory(Long id, ResourceCategoryParam resourceCategoryParam) {
        if (repository.existsByNameAndIdNot(resourceCategoryParam.getName(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_NAME_EXISTS));
        }

        final ResourceCategory resourceCategory = requirePresent(repository.findById(id));

        resourceCategory.describe(resourceCategoryParam.getName(), resourceCategoryParam.getSort());

        return converter.convert(repository.save(resourceCategory));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeResourceCategory(long id) {
        repository.deleteById(id);
    }

}
