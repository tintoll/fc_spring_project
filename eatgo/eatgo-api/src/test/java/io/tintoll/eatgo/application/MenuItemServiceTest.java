package io.tintoll.eatgo.application;

import io.tintoll.eatgo.domain.MenuItem;
import io.tintoll.eatgo.domain.MenuItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

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
    public void bulkUpdate() {
        // bulkUpdate가 테스트가 해야할거?
        // menuRepository.save(menuItem); // 추가

        // given
        // 보내줄 데이터 샘플 만들기
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder().name("KimChi").build());
        menuItems.add(MenuItem.builder().name("Bobzip").build());


        // given
        // menuItemRepository를 사용하는 행위를 해줘야할듯
//        given(menuItemRepository.save(any()));
//        given(menuItemRepository.save(any()));

        // when
        // 실제 서비스 하는행위
        menuItemService.bulkUpdate(1L, menuItems);

        // then
        // 서비스를 하고난 다음 확인할 사항?
        verify(menuItemRepository, times(2)).save(any());

    }
}