package ru.liga.prereformdatingserver.repository;

import org.springframework.data.repository.CrudRepository;
import ru.liga.prereformdatingserver.domain.entity.Favourites;

public interface FavouritesRepository extends CrudRepository<Favourites, Long> {
}
