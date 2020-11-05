package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoardsTest() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("1", "ToDo", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "Done", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto1);
        trelloListDtos.add(trelloListDto2);

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("01", "1", trelloListDtos);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("02", "2", trelloListDtos);
        TrelloBoardDto trelloBoardDto3 = new TrelloBoardDto("03", "2", trelloListDtos);

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);
        trelloBoardDtos.add(trelloBoardDto3);
        //When
        List<TrelloBoard> resultTrelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(trelloBoardDtos.size(), resultTrelloBoards.size());
        assertEquals("01", resultTrelloBoards.get(0).getName());
        assertNotNull(resultTrelloBoards.get(0).getLists());
        assertEquals("2", resultTrelloBoards.get(1).getId());
        assertEquals("1", resultTrelloBoards.get(0).getLists().get(0).getId());
        assertEquals("Done", resultTrelloBoards.get(1).getLists().get(1).getName());
        assertTrue(resultTrelloBoards.get(2).getLists().get(0).isClosed());
    }

    @Test
    public void mapToBoardsDtoTest() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "ToDo", true);
        TrelloList trelloList2 = new TrelloList("2", "Done", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);

        TrelloBoard trelloBoard1 = new TrelloBoard("name1", "1", trelloLists);
        TrelloBoard trelloBoard2 = new TrelloBoard("name2", "22", trelloLists);
        TrelloBoard trelloBoard3 = new TrelloBoard("name3", "33", trelloLists);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        trelloBoards.add(trelloBoard3);
        //When
        List<TrelloBoardDto> resultTrelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(trelloBoards.size(), resultTrelloBoardDtos.size());
        assertEquals("22", resultTrelloBoardDtos.get(1).getId());
        assertEquals("name3", resultTrelloBoardDtos.get(2).getName());
        assertTrue(resultTrelloBoardDtos.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToEmptyListTest() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        //When
        List<TrelloList> resultTrelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertNotNull(resultTrelloLists);
        Assert.assertEquals(0, resultTrelloLists.size());
        assertEquals(trelloListDtos.size(), resultTrelloLists.size());
    }

    @Test
    public void mapToListDtoTest() {
        //Given
        TrelloList trelloList1 = new TrelloList("0", "ToDo", false);
        TrelloList trelloList2 = new TrelloList("1", "Done", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);
        //When
        List<TrelloListDto> resulltTrelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals(trelloLists.size(), resulltTrelloListDtos.size());
        assertEquals("1", resulltTrelloListDtos.get(1).getId());
        assertEquals("ToDo", resulltTrelloListDtos.get(0).getName());
        assertTrue(resulltTrelloListDtos.get(1).isClosed());
        assertFalse(resulltTrelloListDtos.get(0).isClosed());
    }

    @Test
    public void mapToCardDtoTest() {
        //Given
        TrelloCard card1 = new TrelloCard("card1", "test card1", "top", "list1");
        //When
        TrelloCardDto resultCard = trelloMapper.mapToCardDto(card1);
        //Then
        assertEquals("card1", resultCard.getName());
        assertEquals("test card1", resultCard.getDescription());
        assertEquals("top", resultCard.getPos());
        assertEquals("list1", resultCard.getListId());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("card", "to do", "bottom", "001");
        //When
        TrelloCard card = trelloMapper.mapToCard(cardDto);
        //Then
        assertEquals("card", card.getName());
        assertEquals("to do", card.getDescription());
        assertEquals("bottom", card.getPos());
        assertEquals("001", card.getListId());
    }

}