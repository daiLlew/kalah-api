package com.dai.llew.kalah.controllers;

import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.model.Game;
import com.dai.llew.kalah.model.Player;
import com.dai.llew.kalah.model.State;
import com.dai.llew.kalah.responses.GameCreated;
import com.dai.llew.kalah.responses.GameStatus;
import com.dai.llew.kalah.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.dai.llew.kalah.controllers.GamesController.INTERNAL_SERVER_ERR_MSG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class GamesControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private Game game;

    private GamesController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new GamesController();

        ReflectionTestUtils.setField(controller, "gameService", gameService);
    }

    @Test
    public void testCreateGame_Success() throws Exception {
        when(gameService.createNewGame())
                .thenReturn(666);

        GameCreated actual = controller.newGame();

        GameCreated expected = new GameCreated(666);
        assertThat(actual, equalTo(expected));
        verify(gameService, times(1)).createNewGame();
    }

    @Test(expected = ResponseStatusException.class)
    public void testCreateGame_ThrowsGameException() throws Exception {
        when(gameService.createNewGame())
                .thenThrow(new GameException("bad request", BAD_REQUEST));

        try {
            controller.newGame();
        } catch (ResponseStatusException ex) {
            assertThat(ex.getStatus(), equalTo(BAD_REQUEST));
            assertThat(ex.getReason(), equalTo("bad request"));
            verify(gameService, times(1)).createNewGame();
            throw ex;
        }
    }

    @Test(expected = ResponseStatusException.class)
    public void testCreateGame_ThrowsRuntimeException() throws Exception {
        when(gameService.createNewGame())
                .thenThrow(new RuntimeException("whoops"));

        try {
            controller.newGame();
        } catch (ResponseStatusException ex) {
            assertThat(ex.getStatus(), equalTo(INTERNAL_SERVER_ERROR));
            assertThat(ex.getReason(), equalTo(INTERNAL_SERVER_ERR_MSG));
            verify(gameService, times(1)).createNewGame();
            throw ex;
        }
    }

    @Test
    public void testGetGames_Success() {
        List<Game> games = new ArrayList<Game>() {{
            add(game);
        }};

        when(gameService.getGames()).thenReturn(games);

        List<Game> actual = controller.getGames();

        assertThat(actual.size(), equalTo(1));
        assertThat(actual.get(0), equalTo(game));

        verify(gameService, times(1)).getGames();
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetGames_GameException() {
        when(gameService.getGames())
                .thenThrow(new GameException("bad request", BAD_REQUEST));

        try {
            controller.getGames();
        } catch (ResponseStatusException ex) {
            assertThat(ex.getStatus(), equalTo(BAD_REQUEST));
            assertThat(ex.getReason(), equalTo("bad request"));
            verify(gameService, times(1)).getGames();
            throw ex;
        }
    }

    @Test(expected = ResponseStatusException.class)
    public void testGetGames_UnexpectedException() {
        when(gameService.getGames())
                .thenThrow(new RuntimeException("Nargle!"));

        try {
            controller.getGames();
        } catch (ResponseStatusException ex) {
            assertThat(ex.getStatus(), equalTo(INTERNAL_SERVER_ERROR));
            assertThat(ex.getReason(), equalTo(INTERNAL_SERVER_ERR_MSG));
            verify(gameService, times(1)).getGames();
            throw ex;
        }
    }

    @Test
    public void testGetStatus_Success() {
        Game g = new Game(666);

        when(gameService.getGameById(666))
                .thenReturn(g);

        GameStatus gameStatus = controller.getStatus(666);

        assertThat(gameStatus.getGameId(), equalTo(666));
        assertThat(gameStatus.getPlayerTurn(), equalTo(Player.ONE.getId()));
        assertThat(gameStatus.getResult(), is(nullValue()));
        assertThat(gameStatus.getState(), equalTo(State.CREATED.name()));
        verify(gameService, times(1)).getGameById(666);
    }
}
