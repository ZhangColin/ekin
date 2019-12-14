package com.ekin.system.appservice.dict;

import com.cartisan.constants.CodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.ekin.system.appservice.dict.response.DictDto;
import com.ekin.system.domain.dict.Dict;
import com.ekin.system.domain.dict.DictItem;
import com.ekin.system.repository.DictRepository;
import com.ekin.system.appservice.dict.request.SearchDict;
import com.ekin.system.appservice.dict.response.DictConverter;
import com.ekin.system.appservice.dict.response.DictItemConverter;
import com.ekin.system.appservice.dict.response.DictItemDto;
import com.ekin.system.appservice.dict.request.DictItemParam;
import com.ekin.system.appservice.dict.request.DictParam;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public DictAppService(DictRepository repository, DictConverter dictConverter, DictItemConverter dictItemConverter) {
        this.repository = repository;
        this.dictConverter = dictConverter;
        this.dictItemConverter = dictItemConverter;
    }

    public PageResult<DictDto> searchDicts(@NonNull SearchDict searchDict, @NonNull Pageable pageable) {
        final Page<Dict> searchResult = repository.findAll(querySpecification(searchDict), pageable);

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
