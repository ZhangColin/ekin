package com.ekin.system.menurule;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.util.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class MenuRuleAppService {
    private final MenuRuleConverter converter = MenuRuleConverter.CONVERTER;
    private final MenuRuleRepository repository;

    public MenuRuleAppService(MenuRuleRepository repository) {
        this.repository = repository;
    }

    public List<MenuRuleDto> getMenuRuleTreeList() {
        final List<MenuRuleDto> menuRuleDtos = converter.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sequence")));

        return MenuRuleDto.buildMenuRuleTreeList(menuRuleDtos);
    }
    public List<MenuRuleDto> getMenuRuleOptions() {
        final List<MenuRuleDto> menuRuleDtos = converter.convert(
                repository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("type"), "operate"),
                        Sort.by(Sort.Direction.ASC, "sequence")));

        return MenuRuleDto.buildMenuRuleOptions(menuRuleDtos);
    }


    @Transactional(rollbackOn = Exception.class)
    public MenuRuleDto addMenuRule(MenuRuleParam menuRuleParam) {
        final MenuRule menuRule = new MenuRule();
        menuRule.setParentId(Optional.ofNullable(menuRuleParam.getParentId()).orElse(0L));
        menuRule.setType(menuRuleParam.getType());
        menuRule.setTitle(menuRuleParam.getTitle());
        menuRule.setName(menuRuleParam.getName());
        menuRule.setPath(Optional.ofNullable(menuRuleParam.getPath()).orElse(""));
        menuRule.setComponent(Optional.ofNullable(menuRuleParam.getComponent()).orElse(""));
        menuRule.setIcon(menuRuleParam.getIcon());
        menuRule.setMenuType(menuRuleParam.getMenuType());
        menuRule.setUrl(menuRuleParam.getUrl());
        menuRule.setKeepalive(menuRuleParam.getKeepalive());
        menuRule.setRemark(menuRuleParam.getRemark());
        menuRule.setSequence(Optional.ofNullable(menuRuleParam.getSequence()).orElse(0));
        menuRule.setStatus(menuRuleParam.getStatus());

        return converter.convert(repository.save(menuRule));
    }

    public MenuRuleDto getMenuRule(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public MenuRuleDto editMenuRule(Long id, MenuRuleParam menuRuleParam) {
        final MenuRule menuRule = requirePresent(repository.findById(id));

        menuRule.setParentId(Optional.ofNullable(menuRuleParam.getParentId()).orElse(0L));
        menuRule.setType(menuRuleParam.getType());
        menuRule.setTitle(menuRuleParam.getTitle());
        menuRule.setName(menuRuleParam.getName());
        menuRule.setPath(Optional.ofNullable(menuRuleParam.getPath()).orElse(""));
        menuRule.setComponent(Optional.ofNullable(menuRuleParam.getComponent()).orElse(""));
        menuRule.setIcon(menuRuleParam.getIcon());
        menuRule.setMenuType(menuRuleParam.getMenuType());
        menuRule.setUrl(menuRuleParam.getUrl());
        menuRule.setKeepalive(menuRuleParam.getKeepalive());
        menuRule.setRemark(menuRuleParam.getRemark());
        menuRule.setSequence(Optional.ofNullable(menuRuleParam.getSequence()).orElse(0));
        menuRule.setStatus(menuRuleParam.getStatus());

        return converter.convert(repository.save(menuRule));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMenuRule(long id) {
        repository.deleteById(id);
    }
}
