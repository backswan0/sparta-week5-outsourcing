package com.example.outsourcingproject.menu.service;

import com.example.outsourcingproject.category.repository.MenuCategoryRepository;
import com.example.outsourcingproject.entity.Menu;
import com.example.outsourcingproject.entity.MenuCategory;
import com.example.outsourcingproject.entity.Store;
import com.example.outsourcingproject.exception.badrequest.CategoryCountExcessException;
import com.example.outsourcingproject.exception.badrequest.StoreMismatchException;
import com.example.outsourcingproject.exception.notfound.MenuNotFoundException;
import com.example.outsourcingproject.exception.notfound.StoreNotFoundException;
import com.example.outsourcingproject.menu.dto.request.CreateMenuRequestDto;
import com.example.outsourcingproject.menu.dto.request.UpdateMenuRequestDto;
import com.example.outsourcingproject.menu.dto.response.CreateMenuResponseDto;
import com.example.outsourcingproject.menu.dto.response.UpdateMenuResponseDto;
import com.example.outsourcingproject.menu.repository.MenuRepository;
import com.example.outsourcingproject.store.repository.StoreRepository;
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

    @Transactional
    @Override
    public CreateMenuResponseDto createMenu(
        Long storeId,
        CreateMenuRequestDto requestDto
    ) {
        Store foundStore = storeRepository.findById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        List<String> menuCategoryNameList = new ArrayList<>();

        menuCategoryNameList = requestDto.getMenuCategoryNameList();

        List<MenuCategory> menuCategoryList = new ArrayList<>();

        menuCategoryList = menuCategoryRepository.findAllByNameIn(
            menuCategoryNameList,
            Sort.unsorted()
        );

        if (menuCategoryList.size() != 3) {
            throw new CategoryCountExcessException();
        }

        Menu menuToSave = new Menu(
            requestDto.getMenuName(),
            requestDto.getMenuPrice(),
            requestDto.getMenuInfo(),
            foundStore,
            menuCategoryList.get(0),
            menuCategoryList.get(1),
            menuCategoryList.get(2)
        );

        Menu savedMenu = menuRepository.save(menuToSave);

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