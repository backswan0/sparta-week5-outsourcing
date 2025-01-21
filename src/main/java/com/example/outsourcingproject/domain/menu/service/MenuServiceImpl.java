package com.example.outsourcingproject.domain.menu.service;

import com.example.outsourcingproject.common.entity.MappingMenuCategory;
import com.example.outsourcingproject.common.entity.Menu;
import com.example.outsourcingproject.common.entity.MenuCategory;
import com.example.outsourcingproject.common.entity.Store;
import com.example.outsourcingproject.common.exception.badrequest.CategoryCountExcessException;
import com.example.outsourcingproject.common.exception.badrequest.StoreMismatchException;
import com.example.outsourcingproject.common.exception.notfound.MenuNotFoundException;
import com.example.outsourcingproject.common.exception.notfound.StoreNotFoundException;
import com.example.outsourcingproject.domain.category.repository.MenuCategoryRepository;
import com.example.outsourcingproject.domain.menu.dto.request.CreateMenuRequestDto;
import com.example.outsourcingproject.domain.menu.dto.request.UpdateMenuRequestDto;
import com.example.outsourcingproject.domain.menu.dto.response.CreateMenuResponseDto;
import com.example.outsourcingproject.domain.menu.dto.response.UpdateMenuResponseDto;
import com.example.outsourcingproject.domain.menu.repository.MenuRepository;
import com.example.outsourcingproject.domain.store.repository.StoreRepository;
import com.example.outsourcingproject.mapping.MappingMenuCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MappingMenuCategoryRepository mappingMenuCategoryRepository;

    @Transactional
    @Override
    public CreateMenuResponseDto createMenu(
        Long storeId,
        CreateMenuRequestDto requestDto
    ) {
        Store foundStore = storeRepository.findById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        Menu menuToSave = new Menu(
            requestDto.getMenuName(),
            requestDto.getMenuPrice(),
            requestDto.getMenuInfo(),
            foundStore
        );

        Menu savedMenu = menuRepository.save(menuToSave);

        List<MenuCategory> menuCategoryList = new ArrayList<>();

        menuCategoryList = menuCategoryRepository.findAllByNameIn(
            requestDto.getCategoryList(),
            Sort.unsorted()
        );

        if (menuCategoryList.size() > 3) {
            throw new CategoryCountExcessException();
        }

        List<MappingMenuCategory> mappingMenuCategoryList = new ArrayList<>();

        mappingMenuCategoryList = menuCategoryList.stream()
            .map(category -> new MappingMenuCategory(
                    category,
                    savedMenu
                )
            )
            .toList();

        mappingMenuCategoryRepository.saveAll(mappingMenuCategoryList);

        savedMenu.addMenuCategoryList(mappingMenuCategoryList);

        return new CreateMenuResponseDto(savedMenu);
    }

    @Transactional
    @Override
    public UpdateMenuResponseDto updateMenu(
        Long storeId,
        Long menuId,
        UpdateMenuRequestDto requestDto
    ) {
        Store foundStore = storeRepository.findById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        Menu foundMenu = menuRepository.findById(menuId)
            .orElseThrow(MenuNotFoundException::new);

        boolean isMenuFromDifferentStore = !foundMenu.getStore().getId().equals(foundStore.getId());

        if (isMenuFromDifferentStore) {
            throw new StoreMismatchException();
        }

        foundMenu.update(
            requestDto.getMenuName(),
            requestDto.getMenuPrice(),
            requestDto.getMenuInfo()
        );

        return new UpdateMenuResponseDto(
            foundMenu.getMenuName(),
            foundMenu.getMenuPrice(),
            foundMenu.getMenuInfo()
        );
    }

    @Transactional
    @Override
    public void deleteMenu(Long menuId) {
        Menu foundMenu = menuRepository.findById(menuId)
            .orElseThrow(MenuNotFoundException::new);

        foundMenu.markAsDeleted();
    }
}