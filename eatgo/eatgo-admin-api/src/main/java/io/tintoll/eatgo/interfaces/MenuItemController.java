package io.tintoll.eatgo.interfaces;

import io.tintoll.eatgo.application.MenuItemService;
import io.tintoll.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping("/restaurants/{restaurantId}/menuItems")
    public List<MenuItem> getMenuItems(@PathVariable("restaurantId") Long restaurantId) {
        return menuItemService.getMenuItems(restaurantId);
    }

    @PatchMapping("/restaurants/{restaurantId}/menuItems")
    public String bulkUpdate(@PathVariable("restaurantId") Long restaurantId,
                             @RequestBody List<MenuItem> menuItems ) {

        menuItemService.bulkUpdate(restaurantId, menuItems);
        return "";
    }
}
