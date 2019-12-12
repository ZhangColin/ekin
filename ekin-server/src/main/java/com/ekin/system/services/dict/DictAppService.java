package com.ekin.system.services.dict;

import com.cartisan.constants.CommonCodeMessage;
import com.cartisan.dtos.PageResult;
import com.cartisan.exceptions.CartisanException;
import com.ekin.system.domains.dict.Dict;
import com.ekin.system.domains.dict.DictItem;
import com.ekin.system.repositories.DictRepository;
import com.ekin.system.services.dict.param.DictItemParam;
import com.ekin.system.services.dict.param.DictParam;
import com.ekin.system.services.dict.condition.DictCondition;
import com.ekin.system.services.dict.dto.DictConverter;
import com.ekin.system.services.dict.dto.DictDTO;
import com.ekin.system.services.dict.dto.DictItemConverter;
import com.ekin.system.services.dict.dto.DictItemDTO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.cartisan.repositories.ConditionSpecifications.querySpecification;

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

    public PageResult<DictDTO> searchDicts(@NonNull DictCondition dictCondition, @NonNull Pageable pageable) {
        final Page<Dict> searchResult = repository.findAll(querySpecification(dictCondition), pageable);

        return new PageResult<>(searchResult.getTotalElements(), searchResult.getTotalPages(),
                dictConverter.convert(searchResult.getContent()));
    }

    public List<DictItemDTO> getDictItems(@NonNull String dictCode) {
        final Dict dict = requirePresent(repository.findByCode(dictCode));

        dict.getDictItems().sort(Comparator.comparing(DictItem::getSort));

        return dictItemConverter.convert(dict.getDictItems());
    }

    @Transactional(rollbackOn = Exception.class)
    public void addDict(@NonNull DictParam param) {
        if(repository.existsByCode(param.getCode())){
            throw new CartisanException(CommonCodeMessage.SERVER_ERROR);
        }

        final Dict dict = new Dict(param.getName(), param.getCode());
        dict.setDescription(param.getDescription());

        repository.save(dict);
    }

    @Transactional(rollbackOn = Exception.class)
    public void updateDict(@NonNull Long id, @NonNull DictParam param) {
        if (repository.existsByCodeAndIdNot(param.getCode(), id)) {
            throw new CartisanException(CommonCodeMessage.SERVER_ERROR);
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

    private <T> T requirePresent(Optional<T> dictOptional) {
        return dictOptional
                .orElseThrow(() -> new CartisanException(CommonCodeMessage.SERVER_ERROR));
    }
}
