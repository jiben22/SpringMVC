package com.openclassrooms.watchlist.domain;

import com.openclassrooms.watchlist.validation.GoodMovie;
import com.openclassrooms.watchlist.validation.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@GoodMovie
@AllArgsConstructor
public class WatchlistItem {

    private Integer id;

    @NotBlank(message = "Entrez un titre")
    private String title;

    private String rating;

    @Priority
    public String priority;

    @Size(max = 50, message = "Le commentaire ne doit pas excéder 50 caractères")
    private String comment;

    public static int index = 0;

    public WatchlistItem() {
        this.id = index ++;
    }
}