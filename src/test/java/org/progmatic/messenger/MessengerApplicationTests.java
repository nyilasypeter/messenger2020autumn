package org.progmatic.messenger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.progmatic.messenger.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MessengerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    @WithUserDetails("admin")
    void deleteShouldReturnFalseWhenMessageIdIsInvalid() throws Exception {
        MsgArchiveDTO archiveDTO = new MsgArchiveDTO();
        archiveDTO.setAction(MsgArchiveAction.DELETE);
        final MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.put("/rest/messages/100")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(archiveDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MessageDeleteResultDTO messageDeleteResultDTO = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8")),
                MessageDeleteResultDTO.class);
        System.out.println(messageDeleteResultDTO.isSuccessFullyDeleted());
        Assertions.assertFalse(messageDeleteResultDTO.isSuccessFullyDeleted());
    }

    @Test
    @WithUserDetails("user")
    void userShouldBeAbleToCreateMessage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/createmessage")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("text", "Helló"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/messages"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(StringContains.containsString("Helló")));

    }

    @Test
    @WithUserDetails("user")
    void userShouldBeAbleToCreateMessageWitRest() throws Exception {
        MessageRequestDTO mr = new MessageRequestDTO();
        mr.setText("Helló");
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/messages")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mr)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MessageResponseDTO responseDTO =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8")), MessageResponseDTO.class);
        Assertions.assertNotNull(responseDTO.getId());
        Assertions.assertEquals("Helló", responseDTO.getText());

        mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/rest/messages/" + responseDTO.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        MessageResponseDTO messageSearchResponseDTO =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8")), MessageResponseDTO.class);
        Assertions.assertEquals("Helló", messageSearchResponseDTO.getText());
    }


}
