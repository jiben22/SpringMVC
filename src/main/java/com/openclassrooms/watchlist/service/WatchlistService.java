package com.openclassrooms.watchlist.service;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.watchlist.domain.WatchlistItem;
import com.openclassrooms.watchlist.exception.DuplicateTitleException;
import com.openclassrooms.watchlist.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class WatchlistService {

    private WatchlistRepository watchlistRepository;
    private MovieRatingService movieRatingService;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, MovieRatingService movieRatingService) {
        super();
        this.watchlistRepository = watchlistRepository;
        this.movieRatingService = movieRatingService;
    }

    public List<WatchlistItem> getWatchlistItems() {
        List<WatchlistItem> watchlistItems = watchlistRepository.getList();

        for (WatchlistItem watchlistItem : watchlistItems) {
            Optional<String> movieRating = Optional.ofNullable(movieRatingService.getMovieRating(watchlistItem.getTitle()));
            if (movieRating.isPresent()) {
                watchlistItem.setRating(movieRating.get());
            }
        }
        return watchlistItems;
    }

    public int getWatchlistItemsSize() {
        return watchlistRepository.getList().size();
    }

    public WatchlistItem findWatchlistItemById(Integer id) {
        return watchlistRepository.findById(id);
    }

    public void addOrUpdateWatchlistItem(WatchlistItem watchlistItem) throws DuplicateTitleException {

        WatchlistItem existingItem = findWatchlistItemById(watchlistItem.getId());

        if (existingItem == null) {
            if (watchlistRepository.findByTitle(watchlistItem.getTitle())!=null) {
                throw new DuplicateTitleException();
            }
            watchlistRepository.addItem(watchlistItem);
        } else {
            existingItem.setComment(watchlistItem.getComment());
            existingItem.setPriority(watchlistItem.getPriority());
            existingItem.setRating(watchlistItem.getRating());
            existingItem.setTitle(watchlistItem.getTitle());
        }
    }
}