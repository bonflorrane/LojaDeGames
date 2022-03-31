package org.generation.lojaGames.repository;

import java.util.List;

import org.generation.lojaGames.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
	public List<Game>findAllByNomeContainingIgnoreCase(String nome);

}
