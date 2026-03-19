package com.htmcts.worktasks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.htmcts.worktasks.domaine.CaseTask;
import com.htmcts.worktasks.dto.UpdateStatusDTO;
import com.htmcts.worktasks.repositories.CaseTasksRepository;
import com.htmcts.worktasks.service.CaseworkTaskServices;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class workTasksControllerTest {

    @MockBean
    private CaseworkTaskServices caseworkTaskServices;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CaseTasksRepository caseTasksRepositoryMock;

    private CaseTask caseTask;
    private UpdateStatusDTO updateStatusDTO;
    private Date time;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.HOUR_OF_DAY, 1);
        time = instance.getTime();

        caseTask = new CaseTask(1, "School", "Saint Mary", "started", time);
        updateStatusDTO = new UpdateStatusDTO(1, "Closed");
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    public static String requestBody(Object request) {
        try {
            return MAPPER.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
        try {
            String contentAsString = result.getResponse().getContentAsString();
            return MAPPER.readValue(contentAsString, responseClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createTask_controller_happy_path_test() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTask));
        when(caseworkTaskServices.createTask(caseTask)).thenReturn(caseTask);

        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().isOk())
                .andReturn();
        CaseTask caseTaskResponse = parseResponse(mvcResult, CaseTask.class);
        assertThat(caseTaskResponse.equals(caseTask));
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("Print status code: " + mvcResult.getResponse().getStatus());

    }

    @Test
    public void retrieveAllTasks_controller_happy_path_test() throws Exception {
        List<CaseTask> caseTasksList = Arrays.asList(caseTask);

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/allcasetasks");
        when(caseworkTaskServices.getAllTasks()).thenReturn(caseTasksList);
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().isOk())
                .andReturn();
        List<CaseTask> response = parseResponse(mvcResult, ArrayList.class);
        assertThat(caseTasksList.equals(response));
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("Print status code: " + mvcResult.getResponse().getStatus());

    }

    @Test
    public void updateTask_controller_happy_path_test() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.put("/updatestatus").contentType(MediaType.APPLICATION_JSON).content(requestBody(updateStatusDTO));
        when(caseworkTaskServices.updateTaskStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus())).thenReturn(caseTask);
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().isOk())
                .andReturn();
        CaseTask caseTaskResponse = parseResponse(mvcResult, CaseTask.class);
        assertThat(caseTaskResponse.equals(caseTask));
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("Status changed: Print status code: " + mvcResult.getResponse().getStatus());
    }

    @Test
    public void taskById_controller_happy_path_test() throws Exception {
        Integer taskId = 1;

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/tasksbyid/{idIn}", taskId);
        when(caseworkTaskServices.getTaskById(taskId)).thenReturn(caseTask);
        //when(caseTasksRepositoryMock.save(caseTask)).thenReturn(caseTask);
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().isOk())
                .andReturn();
        CaseTask caseTaskResponse = parseResponse(mvcResult, CaseTask.class);
        assertThat(caseTaskResponse.equals(caseTask));
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("User found: Print status code: " + mvcResult.getResponse().getStatus());
    }

    @Test
    public void deleteTask_controller_happy_path_test() throws Exception {
        Integer taskId = 1;

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.delete("/deletetask/{idIn}", taskId);
        doNothing().when(caseworkTaskServices).deleteTask(taskId);
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().isOk())
                .andReturn();

        verify(caseworkTaskServices).deleteTask(taskId);
    }

    @Test
    public void createTask_null_Date_negative_path_test() throws Exception {
        CaseTask caseTaskNullDate = new CaseTask(1, "School", "Saint Mary", "started", null);

        when(caseTasksRepositoryMock.save(caseTaskNullDate)).thenReturn(caseTaskNullDate);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTaskNullDate));
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].field").value("due"))
                .andExpect(jsonPath("$.errors[0].message").value("Date cannot be null"))
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createTask_Null_Title_negative_path_test() throws Exception {
        CaseTask caseTaskNullTitle = new CaseTask(1, null, "Saint Mary", "started", time);

        when(caseworkTaskServices.createTask(caseTaskNullTitle)).thenReturn(caseTaskNullTitle);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTaskNullTitle));
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[1].field").value("title"))
                .andExpect(jsonPath("$.errors[1].message").value("The title must not be null"))
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createTask_Blank_Title_negative_path_test() throws Exception {
        CaseTask caseTaskBlankTitle = new CaseTask(1, "", "Saint Mary", "started", time);

        when(caseworkTaskServices.createTask(caseTaskBlankTitle)).thenReturn(caseTaskBlankTitle);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTaskBlankTitle));
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].field").value("title"))
                .andExpect(jsonPath("$.errors[0].message").value("Title cannot be blank"))
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }


    @Test
    public void createTask_Null_Status_negative_path_test() throws Exception {
        CaseTask caseTaskNullStatus = new CaseTask(1, "Daylight", "Saint Mary", null, time);

        when(caseworkTaskServices.createTask(caseTaskNullStatus)).thenReturn(caseTaskNullStatus);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTaskNullStatus));
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[1].field").value("status"))
                .andExpect(jsonPath("$.errors[1].message").value("The status must not be null"))
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    public void createTask_Blank_Status_negative_path_test() throws Exception {
        CaseTask caseTaskBlankStatus = new CaseTask(1, "Daylight", "Saint Mary", "", time);

        when(caseworkTaskServices.createTask(caseTaskBlankStatus)).thenReturn(caseTaskBlankStatus);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTaskBlankStatus));
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].field").value("status"))
                .andExpect(jsonPath("$.errors[0].message").value("Status cannot be blank"))
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void shouldReturnValidationError() throws Exception {

        CaseTask caseTaskBlankStatus = new CaseTask(1, "Daylight", "Saint Mary", "", time);

        when(caseworkTaskServices.createTask(caseTaskBlankStatus)).thenReturn(caseTaskBlankStatus);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/createTask").contentType(MediaType.APPLICATION_JSON).content(requestBody(caseTaskBlankStatus));
        MvcResult mvcResult = mockMvc.perform(content)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errors[0].field").value("status"))
                .andExpect(jsonPath("$.errors[0].message").value("Status cannot be blank"))
                .andReturn();
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(caseTasksRepositoryMock);
    }
}