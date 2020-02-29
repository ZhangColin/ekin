package com.ekin.system.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.cartisan.utils.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class MenuAppService {
    private final MenuConverter converter = MenuConverter.CONVERTER;
    private final MenuRepository repository;

    @Autowired
    public MenuAppService(MenuRepository repository) {
        this.repository = repository;
    }

    public List<MenuDto> getMenuTreeList() {
        final List<MenuDto> menuDtos = converter.convert(
                repository.findAll(Sort.by(Sort.Direction.ASC, "sort")));

        return MenuDto.buildMenuTreeList(menuDtos);
    }


    @Transactional(rollbackOn = Exception.class)
    public MenuDto addMenu(MenuParam menuParam) {
        final Menu menu = new Menu(menuParam.getParentId(), menuParam.getTitle(),
                menuParam.getName(), menuParam.getIcon(),
                menuParam.getHidden(), 0, menuParam.getSort());

        return converter.convert(repository.save(menu));
    }

    @Transactional(rollbackOn = Exception.class)
    public MenuDto editMenu(Long id, MenuParam menuParam) {
        final Menu menu = requirePresent(repository.findById(id));

        menu.change(menuParam.getParentId(), menuParam.getTitle(),
                menuParam.getName(), menuParam.getIcon(),
                menuParam.getHidden(), 0, menuParam.getSort());

        return converter.convert(repository.save(menu));
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeMenu(long id) {
        repository.deleteById(id);
    }
}
