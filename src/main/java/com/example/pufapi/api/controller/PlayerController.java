package com.example.pufapi.api.controller;

import com.example.pufapi.api.model.Figure;
import com.example.pufapi.api.model.Player;
import com.example.pufapi.api.model.Playerhistory;
import com.example.pufapi.service.PlayerService;
import com.google.gson.Gson;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@RestController
public class PlayerController {
    private PlayerService playerservice;

    public PlayerController(PlayerService playerservice) {
        this.playerservice = playerservice;
    }

    @GetMapping("/player")
    public Player getPlayer(@RequestParam String playername) {
        Optional player = playerservice.getPlayer(playername);
        if (player.isPresent()) {
            return (Player) player.get();
        }
        return null;
    }

    @GetMapping("/figure")
    public Figure getFigure(@RequestParam String figurename) {
        Optional figure = playerservice.getFigure(figurename);
        if (figure.isPresent()) {
            return (Figure) figure.get();
        }
        return null;
    }

    @PutMapping("/player")
    public boolean setPlayer(@RequestBody String rb) {
        Player p = new Gson().fromJson(rb, Player.class);
        boolean success = playerservice.updateHighscore(p.getPlayername(), p.getHighscore());
        return success;
    }

    @PutMapping("/history")
    public boolean setHistory(@RequestBody String rb) {
        Playerhistory p = new Gson().fromJson(rb, Playerhistory.class);
        System.out.println("------------------------" + rb);
        boolean success = playerservice.writePlayerHistory(p);
        return success;
    }

    @DeleteMapping("/history")
    public boolean deleteHistory(@RequestParam String playername) {
        boolean success = playerservice.deleteHistory(playername);
        return success;
    }

    @GetMapping("/history")
    public String getHistory(@RequestParam String playername) {
        Optional history = playerservice.getPlayerHistory(playername);
        if (history.isPresent()) {
            return (String) history.get();
        }
        return null;
    }

    @PostMapping("/playername")
    public String valiPlayer(@Validated @RequestBody String rb) throws SQLException {
        Optional player = Optional.empty();
        Player p = new Gson().fromJson(rb, Player.class);
        System.out.println("------------------------" + rb);
        player = player.of(playerservice.validatePlayer(p.getPlayername()));
        if (player.isPresent()) {
            return (String) player.get();
        }
        return null;
    }
}
