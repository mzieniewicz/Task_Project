package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;

    @Test
    public void fetchTrelloBoardsTest() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("001", "my_list", true));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("my_task", trelloBoardDto.getId());
            assertEquals("1", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("001", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(true, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void fetchTrelloBoardsWithEmptyTrelloListDtoTest() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto());

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());
        assertNotNull(trelloBoardDtos.get(0).getLists().get(0));
        assertEquals(1, trelloBoardDtos.get(0).getLists().size());
    }

    @Test
    public void fetchTrelloBoardsWithEmptyTrelloBoardDtoTest() {
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto());

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());
        assertNotNull(trelloBoardDtos.get(0));
        //  assertEquals(1, trelloBoardDtos.get(0).getLists().get(0));
    }

    @Test
    public void createTrelloCardTest() {
        //Given
        TrelloCardDto newCardDto = new TrelloCardDto("card", "description", "top", "111");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "card",
                "http://test.com"
        );
        when(trelloClient.createNewCard(newCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //When
        CreatedTrelloCardDto resultCard = trelloService.createTrelloCard(newCardDto);
        //Then
        assertNotNull(resultCard);
        assertEquals("1", resultCard.getId());
        assertEquals("card", resultCard.getName());
        assertEquals("http://test.com", resultCard.getShortUrl());
    }

    @Test
    public void createEmptyTrelloCardTest() {
        //Given
        TrelloCardDto newCardDto = new TrelloCardDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();

        when(trelloClient.createNewCard(newCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //When
        CreatedTrelloCardDto resultCard = trelloService.createTrelloCard(newCardDto);
        //Then
        assertNotNull(resultCard);

    }
}
