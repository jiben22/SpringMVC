package com.openclassrooms.watchlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class WatchlistController {

    List<WatchlistItem> watchlistItems = new ArrayList<>();

    @GetMapping("/watchlist")
    public ModelAndView getWatchlist() {

        String viewName = "watchlist";
        Map<String, Object> model = new HashMap<>();

        model.put("watchlistItems", watchlistItems);
        model.put("numberOfMovies", watchlistItems.size());

        return new ModelAndView(viewName, model);
    }

    @PostMapping("/watchlistItem")
    public ModelAndView watchlistItemSubmit(@Valid WatchlistItem watchlistItem, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("watchlistItem");
        }

        if (itemAlreadyExists(watchlistItem.getTitle())) {
            bindingResult.rejectValue("title", "", "This movie is already on your watchlist");
            return new ModelAndView("watchlistItem");
        }

        watchlistItems.add(watchlistItem);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/watchlist");

        return new ModelAndView(new RedirectView("/watchlist"));
    }

    @GetMapping("/watchlistItemForm")
    public ModelAndView showWatchlistItemForm(@RequestParam(required=false) Integer id) {
        Map<String, Object> model = new HashMap<>();

        WatchlistItem watchlistItem = findWatchlistItemById(id);
        if (watchlistItem == null) {
            model.put("watchlistItem", new WatchlistItem());
        } else {
            model.put("watchlistItem", watchlistItem);
        }
        return new ModelAndView("watchlistItemForm" , model);
    }

    private WatchlistItem findWatchlistItemById(Integer id) {
        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getId().equals(id)) {
                return watchlistItem;
            }
        }
        return null;
    }

    private boolean itemAlreadyExists(String title) {

        for (WatchlistItem watchlistItem : watchlistItems) {
            if (watchlistItem.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}