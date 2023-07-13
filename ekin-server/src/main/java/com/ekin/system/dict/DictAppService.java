package com.ekin.system.dict;

import com.cartisan.constant.CodeMessage;
import com.cartisan.dto.PageResult;
import com.cartisan.exception.CartisanException;
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

import static com.cartisan.repository.ConditionSpecifications.querySpecification;
import static com.cartisan.util.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class DictAppService {
    public static final String ERR_CODE_EXISTS = "字典编码已存在。";

    private final DictRepository repository;
    private final DictConverter dictConverter = DictConverter.CONVERTER;
    private final DictItemConverter dictItemConverter = DictItemConverter.CONVERTER;

    public DictAppService(DictRepository repository) {
        this.repository = repository;
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
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_CODE_EXISTS));
        }

        final Dict dict = new Dict(param.getCode());
        dict.describe(param.getName(), param.getDescription());

        repository.save(dict);
    }

    @Transactional(rollbackOn = Exception.class)
    public void editDict(@NonNull Long id, @NonNull DictParam param) {
        if (repository.existsByCodeAndIdNot(param.getCode(), id)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs(ERR_CODE_EXISTS));
        }

        final Dict dict = requirePresent(repository.findById(id));

        dict.changeCode(param.getCode());
        dict.describe(param.getName(), param.getDescription());

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
