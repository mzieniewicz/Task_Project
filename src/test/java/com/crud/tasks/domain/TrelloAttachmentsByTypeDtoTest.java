package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TrelloAttachmentsByTypeDtoTest {
    @Test
    public void GetterTest(){
        //Given
        TrelloAttachmentsByTypeDto attachments = new TrelloAttachmentsByTypeDto(new TrelloTrelloDto(10,20));
        //Then
        assertNotNull(attachments);
        assertNotNull(attachments.getTrello());
        assertEquals(10,attachments.getTrello().getBoard());
        assertEquals(20,attachments.getTrello().getCard());

    }
}

