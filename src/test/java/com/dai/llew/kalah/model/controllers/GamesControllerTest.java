package com.dai.llew.kalah.model.controllers;

import com.dai.llew.kalah.controllers.GamesController;
import com.dai.llew.kalah.exceptions.GameException;
import com.dai.llew.kalah.responses.GameCreatedResponse;
import com.dai.llew.kalah.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class GamesControllerTest {

    @Mock
    private GameService gameService;

    private GamesController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new GamesController();

        ReflectionTestUtils.setField(controller, "gameService", gameService);
    }

    @Test
    public void testCreateGameSuccess() throws Exception {
        when(gameService.createNewGame())
                .thenReturn(666);

        GameCreatedResponse actual = controller.newGame();

        GameCreatedResponse expected = new GameCreatedResponse(666);
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
            assertThat(ex.getReason(), equalTo("internal server error"));
            verify(gameService, times(1)).createNewGame();
            throw ex;
        }
    }
}
