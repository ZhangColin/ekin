package com.ekin.system.dict;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.ekin.system.dict.domain.Dict;
import com.ekin.system.dict.domain.DictItem;
import com.ekin.system.dict.request.DictItemParam;
import com.ekin.system.dict.request.DictParam;
import com.ekin.system.dict.request.DictQuery;
import com.ekin.system.dict.response.DictConverter;
import com.ekin.system.dict.response.DictDto;
import com.ekin.system.dict.response.DictItemConverter;
import com.ekin.system.dict.response.DictItemDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;
import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class DictAppService {
    private final DictRepository repository;
    private final DictConverter dictConverter;
    private final DictItemConverter dictItemConverter;

    public DictAppService(DictRepository repository, DictConverter dictConverter, DictItemConverter dictItemConverter) {
        this.repository = repository;
        this.dictConverter = dictConverter;
        this.dictItemConverter = dictItemConverter;
    }

    public PageResult<DictDto> searchDicts(@NonNull DictQuery dictQuery, @NonNull Pageable pageable) {
        final Page<Dict> searchResult = repository.findAll(querySpecification(dictQuery), pageable);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                dictConverter.convert(searchResult.getContent()));
    }

    public List<DictItemDto> getDictItems(@NonNull String dictCode) {
        final Dict dict = requirePresent(repository.findByCode(dictCode));

        dict.getDictItems().sort(Comparator.comparing(DictItem::getSort));

        return dictItemConverter.convert(dict.getDictItems());
    }

    @Transactional(rollbackOn = Exception.class)
    public void addDict(@NonNull DictParam param) {
        if(repository.existsByCode(param.getCode())){
            throw new CartisanException(CodeMessage.ENTITY_EXIST);
        }

        final Dict dict = new Dict(param.getName(), param.getCode());
        dict.setDescription(param.getDescription());

        repository.save(dict);
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateDict(@NonNull Long id, @NonNull DictParam param) {
        if (repository.existsByCodeAndIdNot(param.getCode(), id)) {
            throw new CartisanException(CodeMessage.ENTITY_EXIST);
        }

        final Dict dict = requirePresent(repository.findById(id));

        dict.setName(param.getName());
        dict.setCode(param.getCode());

        dict.setDescription(param.getDescription());

        repository.save(dict);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeDict(long id) {
        repository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public void submitDictItem(@NonNull String dictCode, @NonNull DictItemParam param) {
        final Dict dict = requirePresent(repository.findByCode(dictCode));
        dict.submitItem(param.getLabel(), param.getValue(), param.getSort());

        repository.save(dict);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeDictItem(@NonNull String dictCode, @NonNull DictItemParam param) {
        final Dict dict = requirePresent(repository.findByCode(dictCode));
        dict.removeItem(param.getValue());

        repository.save(dict);
    }
}
