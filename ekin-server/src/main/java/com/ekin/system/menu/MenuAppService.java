package com.ekin.system.menu;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.cartisan.util.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class MenuAppService {
    private final MenuConverter converter = MenuConverter.CONVERTER;
    private final MenuRepository repository;

    public MenuAppService(MenuRepository repository) {
        this.repository = repository;
    }

    public List<MenuDto> getMenuTreeList() {
        final List<MenuDto> menuDtos = converter.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sequence")));

        return MenuDto.buildMenuTreeList(menuDtos);
    }
    public List<MenuDto> getMenuOptions() {
        final List<MenuDto> menuDtos = converter.convert(
                repository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("type"), "operate"),
                        Sort.by(Sort.Direction.ASC, "sequence")));

        return MenuDto.buildMenuOptions(menuDtos);
    }


    @Transactional(rollbackOn = Exception.class)
    public MenuDto addMenu(MenuParam menuParam) {
        final Menu menu = new Menu();
        menu.setParentId(Optional.ofNullable(menuParam.getParentId()).orElse(0L));
        menu.setType(menuParam.getType());
        menu.setTitle(menuParam.getTitle());
        menu.setName(menuParam.getName());
        menu.setPath(Optional.ofNullable(menuParam.getPath()).orElse(""));
        menu.setComponent(Optional.ofNullable(menuParam.getComponent()).orElse(""));
        menu.setIcon(menuParam.getIcon());
        menu.setMenuType(menuParam.getMenuType());
        menu.setUrl(menuParam.getUrl());
        menu.setKeepalive(menuParam.getKeepalive());
        menu.setRemark(menuParam.getRemark());
        menu.setSequence(Optional.ofNullable(menuParam.getSequence()).orElse(0));
        menu.setStatus(menuParam.getStatus());

        return converter.convert(repository.save(menu));
    }

    public MenuDto getMenu(Long id) {
        return converter.convert(requirePresent(repository.findById(id)));
    }

    @Transactional(rollbackOn = Exception.class)
    public MenuDto editMenu(Long id, MenuParam menuParam) {
        final Menu menu = requirePresent(repository.findById(id));

        menu.setParentId(Optional.ofNullable(menuParam.getParentId()).orElse(0L));
        menu.setType(menuParam.getType());
        menu.setTitle(menuParam.getTitle());
        menu.setName(menuParam.getName());
        menu.setPath(Optional.ofNullable(menuParam.getPath()).orElse(""));
        menu.setComponent(Optional.ofNullable(menuParam.getComponent()).orElse(""));
        menu.setIcon(menuParam.getIcon());
        menu.setMenuType(menuParam.getMenuType());
        menu.setUrl(menuParam.getUrl());
        menu.setKeepalive(menuParam.getKeepalive());
        menu.setRemark(menuParam.getRemark());
        menu.setSequence(Optional.ofNullable(menuParam.getSequence()).orElse(0));
        menu.setStatus(menuParam.getStatus());

        return converter.convert(repository.save(menu));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMenu(long id) {
        repository.deleteById(id);
    }
}
