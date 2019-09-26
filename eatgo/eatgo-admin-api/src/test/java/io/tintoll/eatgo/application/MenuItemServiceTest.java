package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.MenuItem;
import io.tintoll.eatgo.domain.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MenuItemServiceTest {

    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        // @Mock 객체들을 초기화
        MockitoAnnotations.initMocks(this);

        // 레파지토리는 Mock해주고 Service는 객체를 생성해준다. 왜?
        menuItemService = new MenuItemService(menuItemRepository);
    }

    @Test
    public void getMenuItems() {
        List<MenuItem> mockMenuItems = new ArrayList<>();
        mockMenuItems.add(MenuItem.builder().name("kimchi").build());

        given(menuItemRepository.findAllByRestaurantId(1L)).willReturn(mockMenuItems);

        List<MenuItem> menuItems = menuItemService.getMenuItems(1L);
        MenuItem menuItem = menuItems.get(0);

        assertThat(menuItem.getName(), is("kimchi"));
    }

    @Test
    public void bulkUpdate() {

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("KimChi").build());
        menuItems.add(MenuItem.builder().id(2L).name("Bob  zip").build());
        menuItems.add(MenuItem.builder().id(1004L).destroy(true).build());

        menuItemService.bulkUpdate(1L, menuItems);

        verify(menuItemRepository, times(2)).save(any());
        verify(menuItemRepository, times(1)).deleteById(1004L);

    }
}